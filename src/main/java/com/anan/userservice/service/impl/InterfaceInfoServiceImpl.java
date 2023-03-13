package com.anan.userservice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.anan.userservice.common.ErrorCode;
import com.anan.userservice.common.IdRequest;
import com.anan.userservice.common.PageRequest;
import com.anan.userservice.constant.InterfaceInfoConstant;
import com.anan.userservice.constant.UserConstant;
import com.anan.userservice.exception.BusinessException;
import com.anan.userservice.model.interfaceInfo.dto.InterfaceInfoAddRequest;
import com.anan.userservice.model.interfaceInfo.dto.InterfaceInfoQueryRequest;
import com.anan.userservice.model.interfaceInfo.dto.InterfaceInfoUpdateRequest;
import com.anan.userservice.model.interfaceInfo.vo.SafeInterfaceInfo;

import com.anan.userservice.model.user.entity.User;
import com.anan.userservice.utils.RedisUtil;
import com.anan.userservice.utils.UserUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.anan.userservice.model.interfaceInfo.entity.InterfaceInfo;
import com.anan.userservice.service.InterfaceInfoService;
import com.anan.userservice.mapper.InterfaceInfoMapper;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

import static com.anan.userservice.constant.InterfaceInfoConstant.INTERFACE_INFO_CACHE;
import static com.anan.userservice.constant.InterfaceInfoConstant.INTERFACE_INFO_CACHE_TTL;

/**
 * @author anan
 * @description 针对表【interface_info(接口信息)】的数据库操作Service实现
 * @createDate 2023-03-09 08:45:41
 */
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
        implements InterfaceInfoService {

    @Resource
    RedisUtil redisUtil;

    @Override
    public boolean updateInterfaceInfo(InterfaceInfoUpdateRequest interfaceInfoUpdateRequest) {
        InterfaceInfo interfaceInfo = BeanUtil.copyProperties(interfaceInfoUpdateRequest, InterfaceInfo.class);
        boolean b = updateById(interfaceInfo);
        if (!b) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        redisUtil.del(UserConstant.USER_INFO_CACHE + interfaceInfo.getId());
        SafeInterfaceInfo safeInterfaceInfo = BeanUtil.copyProperties(getById(interfaceInfo.getId()), SafeInterfaceInfo.class);
        String toJson = new Gson().toJson(safeInterfaceInfo);
        redisUtil.set(INTERFACE_INFO_CACHE + interfaceInfo.getId(), toJson, INTERFACE_INFO_CACHE_TTL);
        return true;
    }

    @Override
    public SafeInterfaceInfo getInterfaceInfo(IdRequest idRequest) {
        Long id = idRequest.getId();
        if (id == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = getById(id);
        SafeInterfaceInfo safeInterfaceInfo = BeanUtil.copyProperties(interfaceInfo, SafeInterfaceInfo.class);
        return safeInterfaceInfo;
    }

    @Override
    public boolean updateStatus(IdRequest idRequest) {
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setId(idRequest.getId());
        interfaceInfo.setStatus(1);
        boolean b = updateById(interfaceInfo);
        if (!b) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return b;
    }

    @Override
    public Page pageInterfaceInfo(PageRequest<InterfaceInfoQueryRequest> interfaceInfoQueryPageRequest) {
        long current = interfaceInfoQueryPageRequest.getCurrent();
        long pageSize = interfaceInfoQueryPageRequest.getPageSize();
        if (current<0||pageSize<0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<InterfaceInfo> queryWrapper = new LambdaQueryWrapper<>();
        Page<InterfaceInfo> page = new Page<>(current, pageSize);
        InterfaceInfoQueryRequest interfaceInfoQueryRequest = interfaceInfoQueryPageRequest.getParams();
        if (interfaceInfoQueryPageRequest.getSortOrder()!=null){
            queryWrapper.orderByDesc(InterfaceInfo::getCreateTime);
        }else {
            queryWrapper.orderByAsc(InterfaceInfo::getCreateTime);
        }
        if (interfaceInfoQueryRequest!=null ){
            String interfaceName = interfaceInfoQueryRequest.getInterfaceName();
            String url = interfaceInfoQueryRequest.getUrl();
            String method = interfaceInfoQueryRequest.getMethod();
            if (StrUtil.isNotBlank(interfaceName)){
                queryWrapper.or();
                queryWrapper.like(InterfaceInfo::getInterfaceName,interfaceName);
            }
            if (StrUtil.isNotBlank(url)){
                queryWrapper.or();
                queryWrapper.like(InterfaceInfo::getUrl,url);
            }
            if (StrUtil.isNotBlank(method)){
                queryWrapper.or();
                queryWrapper.like(InterfaceInfo::getMethod,method);
            }
        }
        page(page, queryWrapper);

        return page;
    }

    @Override
    public boolean addInterfaceInfo(InterfaceInfoAddRequest interfaceInfoAddRequest) {

        String interfaceName = interfaceInfoAddRequest.getInterfaceName();
        String url = interfaceInfoAddRequest.getUrl();
        if (StrUtil.isBlank(interfaceName) || StrUtil.isBlank(url)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        synchronized (interfaceName.intern()) {

            InterfaceInfo interfaceInfo = BeanUtil.copyProperties(interfaceInfoAddRequest, InterfaceInfo.class);
            interfaceInfo.setUserId(UserUtil.getId());
            boolean save = save(interfaceInfo);
            if (!save) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
            return true;
        }


    }

    @Override
    public boolean deleteInterfaceInfo(IdRequest idRequest) {
        if (idRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = removeById(idRequest.getId());
        if (!b) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return true;
    }
}




