package cn.ming.service;

import cn.ming.pojo.User;

/**
 * Created by ASUS on 2020/8/14.
 */
public interface UserService {
    public User queryUserByName(String name);
}
