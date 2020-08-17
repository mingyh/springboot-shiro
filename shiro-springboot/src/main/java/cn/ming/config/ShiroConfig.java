package cn.ming.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ASUS on 2020/8/14.
 */
@Configuration
public class ShiroConfig {
    //ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);

        //添加shiro的内置过滤器
        /*
            anno:   无需认证就可以访问
            authc： 必须认证了才能访问
            user：  必须拥有 记住我 功能才能用
            perms： 拥有对某个资源的权限才能访问
            role：  拥有某个角色才能访问
         */
        //拦截
        Map<String,String> filterMap = new LinkedHashMap<>();

        //授权    未授权会跳转到未授权页面
        filterMap.put("/user/add","perms[user:add]");
        filterMap.put("/user/update","perms[user:update]");

//        filterMap.put("/user/add","authc");
//        filterMap.put("/user/update","authc");
        filterMap.put("/user/*","authc");
        bean.setFilterChainDefinitionMap(filterMap);
        //设置登录的请求
        bean.setLoginUrl("/toLogin");
        //设置未授权页面
        bean.setUnauthorizedUrl("/noauth ");
        return bean;
    }

    //DafaultWebSecurityManager
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联UserRealm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    //创建realm对象 用户认证
//    @Bean(name="userRealm")  可加可不加
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }

    //整合ShiroDialect：用来整合shiro和thymeleaf
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }
}
