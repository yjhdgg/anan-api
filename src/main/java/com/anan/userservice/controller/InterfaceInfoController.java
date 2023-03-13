package com.anan.userservice.controller;

import com.anan.userservice.annotation.AuthCheck;
import com.anan.userservice.common.*;
import com.anan.userservice.constant.UserConstant;
import com.anan.userservice.exception.BusinessException;
import com.anan.userservice.model.interfaceInfo.dto.InterfaceInfoAddRequest;
import com.anan.userservice.model.interfaceInfo.dto.InterfaceInfoQueryRequest;
import com.anan.userservice.model.interfaceInfo.dto.InterfaceInfoUpdateRequest;
import com.anan.userservice.model.interfaceInfo.vo.SafeInterfaceInfo;

import com.anan.userservice.service.InterfaceInfoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author anan
 * @Description TODO
 * @Date 2023/3/9 16:38
 * @Version 1.0
 */
@RestController
@RequestMapping("/interfaceInfo")
@Api(tags = "接口管理")
public class InterfaceInfoController {
    @Resource
    InterfaceInfoService interfaceInfoService;
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.USER_ADMIN_ROLE)
    @ApiOperation("添加接口")
    public BaseResponse<Boolean> addInterfaceInfo(@RequestBody InterfaceInfoAddRequest interfaceInfoAddRequest) {
        if (interfaceInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = interfaceInfoService.addInterfaceInfo(interfaceInfoAddRequest);
        return ResultUtils.success(result);
    }

    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.USER_ADMIN_ROLE)
    @ApiOperation("删除接口")
    public BaseResponse<Boolean> deleteInterfaceInfo(@RequestBody IdRequest idRequest) {
        if (idRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = interfaceInfoService.deleteInterfaceInfo(idRequest);
        return ResultUtils.success(result);
    }


    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.USER_ADMIN_ROLE)
    @ApiOperation("更新接口")
    public BaseResponse<Boolean> updateInterfaceInfo(@RequestBody InterfaceInfoUpdateRequest interfaceInfoUpdateRequest) {
        if (interfaceInfoUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = interfaceInfoService.updateInterfaceInfo(interfaceInfoUpdateRequest);
        return ResultUtils.success(result);
    }

    @PostMapping("/details")
    @AuthCheck
    @ApiOperation("获取接口信息")
    public BaseResponse<SafeInterfaceInfo> getInterfaceInfo(@RequestBody IdRequest idRequest){
        SafeInterfaceInfo result =  interfaceInfoService.getInterfaceInfo(idRequest);
        return ResultUtils.success(result);
    }

    @PostMapping("/status")
    @AuthCheck(mustRole = UserConstant.USER_ADMIN_ROLE)
    @ApiOperation(value = "修改接口状态")
    public BaseResponse<Boolean> updateStatus(@RequestBody IdRequest idRequest) {
        boolean result =  interfaceInfoService.updateStatus(idRequest);
        return ResultUtils.success(result);
    }
    @PostMapping("/page")
    @AuthCheck
    @ApiOperation("分页查询接口")
    public BaseResponse<Page> pageInterfaceInfo(@RequestBody PageRequest<InterfaceInfoQueryRequest> interfaceInfoQueryPageRequest) {
        if (interfaceInfoQueryPageRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Page result =  interfaceInfoService.pageInterfaceInfo(interfaceInfoQueryPageRequest);
        return ResultUtils.success(result);
    }
}
