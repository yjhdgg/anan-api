package com.anan.ananapicommon.service.impl;


import com.anan.ananapicommon.mapper.UserMapper;
import com.anan.ananapicommon.model.entity.User;
import com.anan.ananapicommon.service.InnerUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
/**
 * @Author 13630
 * @Description TODO
 * @Date 2023/3/13 16:35
 * @Version 1.0
 */
@Service
public class InnerUserServiceImpl extends ServiceImpl<UserMapper, User> implements InnerUserService {
    @Override
    public User getInvokeUser(String accessKey) {
        User user = getOne(new LambdaQueryWrapper<User>().eq(User::getAccessKey, accessKey));
        if (user==null){
            return null;
        }
        return user;
    }
}
