package com.ubisys.bootwithsercurity.common.utils;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;
import org.springframework.security.core.Authentication;

/**
 * Created by cw on 2018/11/12.
 */
@Component("rbacauthorityservice")
public class RbacAuthorityService {


    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

        Object userInfo = authentication.getPrincipal();

        boolean hasPermission = false;

        if (userInfo instanceof UserDetails) {

            String username = ((UserDetails) userInfo).getUsername();

            //获取资源
            Set<String> urls = new HashSet();
            // 这些 url 都是要登录后才能访问，且其他的 url 都不能访问！
            urls.add("/sys/sysUser/**");//application.yml里设置了项目路径，百度一下我就不贴了
            Set set2 = new HashSet();
            Set set3 = new HashSet();

            AntPathMatcher antPathMatcher = new AntPathMatcher();

            for (String url : urls) {
                if (antPathMatcher.match(url, request.getRequestURI())) {
                    hasPermission = true;
                    break;
                }
            }

            return hasPermission;
        } else {
            return false;
        }
    }


}
