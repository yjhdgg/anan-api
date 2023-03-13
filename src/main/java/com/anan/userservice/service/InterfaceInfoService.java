package com.anan.userservice.service;

import com.anan.userservice.common.IdRequest;
import com.anan.userservice.common.PageRequest;
import com.anan.userservice.model.interfaceInfo.dto.InterfaceInfoAddRequest;
import com.anan.userservice.model.interfaceInfo.dto.InterfaceInfoQueryRequest;
import com.anan.userservice.model.interfaceInfo.dto.InterfaceInfoUpdateRequest;
import com.anan.userservice.model.interfaceInfo.entity.InterfaceInfo;
import com.anan.userservice.model.interfaceInfo.vo.SafeInterfaceInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author anan
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2023-03-09 08:45:41
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    boolean updateInterfaceInfo(InterfaceInfoUpdateRequest interfaceInfoUpdateRequest);

    SafeInterfaceInfo getInterfaceInfo(IdRequest idRequest);

    boolean updateStatus(IdRequest idRequest);

    Page pageInterfaceInfo(PageRequest<InterfaceInfoQueryRequest> interfaceInfoQueryPageRequest);

    boolean addInterfaceInfo(InterfaceInfoAddRequest interfaceInfoAddRequest);

    boolean deleteInterfaceInfo(IdRequest idRequest);
}
