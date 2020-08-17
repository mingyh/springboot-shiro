package cn.ming.mapper;

import cn.ming.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Created by ASUS on 2020/8/14.
 */
@Repository
@Mapper
public interface UserMapper  {
    public User queryUserByName(String name);
}
