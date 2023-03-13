package com.anan.userservice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.anan.userservice.common.ErrorCode;
import com.anan.userservice.common.IdRequest;
import com.anan.userservice.common.PageRequest;
import com.anan.userservice.constant.UserConstant;
import com.anan.userservice.exception.BusinessException;

import com.anan.userservice.model.user.dto.*;
import com.anan.userservice.model.user.vo.SafeUser;
import com.anan.userservice.utils.RedisUtil;
import com.anan.userservice.utils.UserUtil;
import com.anan.userservice.utils.jwt.JwtCheckResult;
import com.anan.userservice.utils.jwt.JwtConstant;
import com.anan.userservice.utils.jwt.JwtUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.anan.userservice.model.user.entity.User;
import com.anan.userservice.service.UserService;
import com.anan.userservice.mapper.UserMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author anan
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2023-03-09 08:45:41
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    UserMapper userMapper;
    @Resource
    RedisUtil redisUtil;

    @Override
    public long pwdRegister(UserPwdRegisterRequest userPwdRegisterRequest) {
        String userAccount = userPwdRegisterRequest.getUserAccount();
        String userPassword = userPwdRegisterRequest.getUserPassword();
        String checkPassword = userPwdRegisterRequest.getCheckPassword();
        if (StrUtil.isBlank(userAccount) || StrUtil.isBlank(userPassword) || StrUtil.isBlank(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }
        synchronized (userAccount.intern()) {
            // 账户不能重复
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getUserAccount, userAccount);
            long count = userMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
            }
            // 2. 加密
            String encryptPassword = DigestUtils.md5DigestAsHex((userPassword).getBytes());
            // 3. 分配 accessKey, secretKey
            String accessKey = DigestUtil.md5Hex(userAccount + RandomUtil.randomNumbers(5));
            String secretKey = DigestUtil.md5Hex(userAccount + RandomUtil.randomNumbers(8));
            // 4. 插入数据
            User user = new User();
            user.setUserAccount(userAccount);
            user.setUserPassword(encryptPassword);
            user.setAccessKey(accessKey);
            user.setSecretKey(secretKey);
            boolean saveResult = this.save(user);
            if (!saveResult) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
            }
            return user.getId();
        }
    }

    @Override
    public long phoneRegister(UserPhoneRegisterRequest userPhoneRegisterRequest) {
        return 0;
    }

    @Override
    public String pwdLogin(UserPwdLoginRequest userPwdLoginRequest) {
        String userAccount = userPwdLoginRequest.getUserAccount();
        String userPassword = userPwdLoginRequest.getUserPassword();
        // 1. 校验
        if (StrUtil.isBlank(userAccount) || StrUtil.isBlank(userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号错误");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码错误");
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((userPassword).getBytes());
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserAccount, userAccount);
        queryWrapper.eq(User::getUserPassword, encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        // 用户不存在
        if (user == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在或密码错误");
        }
        SafeUser safeUser = BeanUtil.copyProperties(user, SafeUser.class);
        Map<String, Object> safeUserMap = BeanUtil.beanToMap(safeUser, false, true);
        String token = JwtUtil.genJwtToken(userAccount);
        redisUtil.hmset(UserConstant.USER_INFO_CACHE + userAccount, safeUserMap, UserConstant.USER_INFO_CACHE_TTL);
        return token;
    }

    @Override
    public Page pageUser(PageRequest<UserQueryRequest> userPageQueryRequest) {
        return null;
    }


    @Override
    public String getLoginUserRole(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)) {
            log.error("请求IP:" + request.getRemoteAddr());
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        JwtCheckResult checkResult = JwtUtil.validateJWT(token);
        if (!checkResult.isSuccess()) {
            switch (checkResult.getErrCode()) {
                case JwtConstant.JWT_ERRCODE_NULL:
                    throw new JwtException("Token不存在");
                case JwtConstant.JWT_ERRCODE_FAIL:
                    throw new JwtException("Token验证不通过");
                case JwtConstant.JWT_ERRCODE_EXPIRE:
                    throw new JwtException("Token过期");
            }
        }
        Claims claims = JwtUtil.parseJWT(token);
        String userAccount = claims.getSubject();
        String role = (String) redisUtil.hget(UserConstant.USER_INFO_CACHE + userAccount, "role");
        Object id = redisUtil.hget(UserConstant.USER_INFO_CACHE + userAccount, "id");
        UserUtil.setId(Long.valueOf(id.toString()));
        return role;
    }

    @Override
    public boolean updateUser(UserUpdateRequest userUpdateRequest) {
        CopyOptions copyOptions = new CopyOptions().setIgnoreNullValue(true);
        User user = new User();

        BeanUtil.copyProperties(userUpdateRequest, user, new CopyOptions().ignoreNullValue());
        boolean b = updateById(user);
        if (!b) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        redisUtil.del(UserConstant.USER_INFO_CACHE + user.getUserAccount());
        SafeUser safeUser = BeanUtil.copyProperties(getById(user.getId()), SafeUser.class);
        Map<String, Object> safeUserMap = BeanUtil.beanToMap(safeUser, false, true);
        redisUtil.hmset(UserConstant.USER_INFO_CACHE + user.getUserAccount(), safeUserMap, UserConstant.USER_INFO_CACHE_TTL);
        return true;
    }

    @Override
    public boolean updateStatus(IdRequest idRequest) {
        User user = new User();
        user.setId(idRequest.getId());
        user.setStatus(1);
        boolean b = updateById(user);
        if (!b) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return b;
    }

    @Override
    public SafeUser getLoginUser(String userAccount) {
        Map map = redisUtil.hmget(UserConstant.USER_INFO_CACHE + userAccount);
        SafeUser safeUser = BeanUtil.mapToBean(map, SafeUser.class, true, new CopyOptions().ignoreCase());
        return safeUser;
    }
}




