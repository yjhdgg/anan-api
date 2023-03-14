package com.anan.ananapicommon.service.impl;

import com.anan.ananapicommon.service.InnerUserInterfaceInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author 13630
 * @Description TODO
 * @Date 2023/3/14 10:10
 * @Version 1.0
 */
@SpringBootTest
class InnerUserInterfaceInfoServiceImplTest {

    @Autowired
    InnerUserInterfaceInfoService innerUserInterfaceInfoService;
    @Test
    void invokeCount() {
        innerUserInterfaceInfoService.invokeCount(1,1);
    }
}