package com.anan.userservice.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author anan
 * @Description TODO
 * @Date 2023/3/9 9:06
 * @Version 1.0
 */
@Data
public class IdRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;

    public IdRequest() {}
    public IdRequest(Long id) {
        this.id = id;
    }
}
