package cn.ming.config;

import cn.ming.pojo.User;
import cn.ming.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by ASUS on 2020/8/14.
 */
//自定义的UserRealm
public class UserRealm extends AuthorizingRealm{
    @Autowired
    UserService userService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermission("user:add");  //拥有所有权限

        //拿到当前登录的用户
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User) subject.getPrincipal();   //拿到用户权限

        //设置当前用户权限
        info.addStringPermission(currentUser.getPerms());
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //用户名、密码    数据中取
//        String name = "root";
//        String password = "123456";

        //连接真实数据库
        UsernamePasswordToken  userToken = (UsernamePasswordToken) token;

        User user = userService.queryUserByName(userToken.getUsername());
        if (user==null){
            return null;
        }

        //获取session中的user对象
        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        session.setAttribute("loginUser",user);

//        if (!userToken.getUsername().equals(name)){
//            return null;    //抛出异常  UnknownAccountException
//        }

        //可以加密  MD5加密  MD5盐值加密
        //密码认证  shiro自动做
        return new SimpleAuthenticationInfo(user,user.getPwd(),"");
    }
}
