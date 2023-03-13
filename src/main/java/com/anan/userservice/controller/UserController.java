package com.anan.userservice.controller;

import com.anan.userservice.annotation.AuthCheck;
import com.anan.userservice.common.*;
import com.anan.userservice.constant.UserConstant;
import com.anan.userservice.exception.BusinessException;

import com.anan.userservice.model.user.dto.*;
import com.anan.userservice.model.user.vo.SafeUser;
import com.anan.userservice.service.UserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author anan
 * @Description TODO
 * @Date 2023/3/9 8:45
 * @Version 1.0
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理")
public class UserController {

    @Resource
    UserService userService;

    @PostMapping("/pwdRegister")
    @ApiOperation("密码注册")
    public BaseResponse<Long> pwdRegister(@RequestBody UserPwdRegisterRequest userPwdRegisterRequest) {
        if (userPwdRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long result = userService.pwdRegister(userPwdRegisterRequest);
        return ResultUtils.success(result);
    }

    @PostMapping("/phoneRegister")
    @ApiOperation("手机号注册")
    public BaseResponse<Long>  phoneRegister(@RequestBody UserPhoneRegisterRequest userPhoneRegisterRequest) {
        if (userPhoneRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long result = userService.phoneRegister(userPhoneRegisterRequest);
        return ResultUtils.success(result);
    }

    @PostMapping("/login")
    @ApiOperation("密码登录")
    public BaseResponse<String> pwdLogin(@RequestBody UserPwdLoginRequest userPwdLoginRequest) {
        if (userPwdLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String result = userService.pwdLogin(userPwdLoginRequest);
        return ResultUtils.success(result);
    }

    @PostMapping("/update")
    @AuthCheck
    @ApiOperation("更新用户")
    public BaseResponse<Boolean> updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        if (userUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = userService.updateUser(userUpdateRequest);
        return ResultUtils.success(result);
    }

    @GetMapping("/details")
    @AuthCheck
    @ApiOperation("获取当前用户")
    public BaseResponse<SafeUser> getLoginUser(@RequestParam("userAccount") String userAccount){
        SafeUser result =  userService.getLoginUser(userAccount);
        return ResultUtils.success(result);
    }

    @PostMapping("/status")
    @AuthCheck(mustRole = UserConstant.USER_ADMIN_ROLE)
    @ApiOperation(value = "修改用户状态")
    public BaseResponse<Boolean> updateStatus(@RequestBody IdRequest idRequest) {

        boolean result =  userService.updateStatus(idRequest);
        return ResultUtils.success(result);
    }
    @PostMapping("/page")
    @AuthCheck(mustRole = UserConstant.USER_ADMIN_ROLE)
    @ApiOperation("分页查询用户")
    public BaseResponse<Page> pageUser(@RequestBody PageRequest<UserQueryRequest> userPageQueryRequest) {
        if (userPageQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
      Page result =  userService.pageUser(userPageQueryRequest);
        return ResultUtils.success(result);
    }

}
