package com.anan.userservice.service;

import com.anan.userservice.common.IdRequest;
import com.anan.userservice.common.PageRequest;

import com.anan.userservice.model.user.dto.*;
import com.anan.userservice.model.user.entity.User;
import com.anan.userservice.model.user.vo.SafeUser;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author anan
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2023-03-09 08:45:41
*/
public interface UserService extends IService<User> {

    /**
     * 密码注册
     * @param userPwdRegisterRequest
     * @return
     */
    long pwdRegister(UserPwdRegisterRequest userPwdRegisterRequest);

    /**
     * 手机号注册
     * @param userPhoneRegisterRequest
     * @return
     */

    long phoneRegister(UserPhoneRegisterRequest userPhoneRegisterRequest);

    /**
     * 密码登录
     * @param userPwdLoginRequest
     * @return
     */

    String pwdLogin(UserPwdLoginRequest userPwdLoginRequest);

    /**
     * 分页查询用户
     * @param userPageQueryRequest
     * @return
     */
    Page pageUser(PageRequest<UserQueryRequest> userPageQueryRequest);

    /**
     * 获取用户角色
     * @param request
     * @return
     */

    String getLoginUserRole(HttpServletRequest request);

    /**
     * 更新用户信息
     * @param userUpdateRequest
     * @return
     */

    boolean updateUser(UserUpdateRequest userUpdateRequest);

    /**
     * 启用禁用用户
     * @param idRequest
     * @return
     */
    boolean updateStatus(IdRequest idRequest);

    /**
     * 获取当前登录用户
     * @param userAccount
     * @return
     */
    SafeUser getLoginUser(String userAccount);
}
