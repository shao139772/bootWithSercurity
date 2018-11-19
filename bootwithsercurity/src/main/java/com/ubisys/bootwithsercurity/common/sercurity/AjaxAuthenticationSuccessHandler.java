package com.ubisys.bootwithsercurity.common.sercurity;

import com.alibaba.fastjson.JSON;
import com.ubisys.bootwithsercurity.common.utils.AccessAddressUtil;
import com.ubisys.bootwithsercurity.common.utils.RedisUtil;
import com.ubisys.bootwithsercurity.common.utils.ResultEnum;
import com.ubisys.bootwithsercurity.common.utils.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import  com.ubisys.bootwithsercurity.common.sercurity.JwtTokenUtil;

/**
 * Created by cw on 2018/10/31.
 * 处理用户登录成功
 */
@Component
@Slf4j
public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private int expirationSeconds = 300;

    private int validTime = 7;

    @Autowired
    private RedisUtil redisUtil;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        SelfUserDetails userDetails = (SelfUserDetails) authentication.getPrincipal();

        //获取请求的ip地址
        String ip = AccessAddressUtil.getIpAddress(httpServletRequest);
        Map<String, Object> map = new HashMap<>();
        map.put("ip", ip);


        String jwtToken = JwtTokenUtil.createToken(userDetails.getUsername(), expirationSeconds, map);

        //刷新时间
        Integer expire = validTime * 24 * 60 * 60 * 1000;
        //获取请求的ip地址
        String currentIp = AccessAddressUtil.getIpAddress(httpServletRequest);
        redisUtil.setTokenRefresh(jwtToken, userDetails.getUsername(), currentIp);
        log.info("用户{}登录成功，信息已保存至redis", userDetails.getUsername());

        //这句话的意思，是让浏览器用utf8来解析返回的数据
        httpServletResponse.setHeader("Content-type", "text/html;charset=UTF-8");
        //这句话的意思，是告诉servlet用UTF-8转码，而不是用默认的ISO8859
        httpServletResponse.setCharacterEncoding("UTF-8");

        httpServletResponse.getWriter().write(JSON.toJSONString(ResultVO.result(ResultEnum.USER_LOGIN_SUCCESS, jwtToken, true)));

    }


}
