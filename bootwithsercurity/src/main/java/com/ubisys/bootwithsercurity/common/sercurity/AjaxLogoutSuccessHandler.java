package com.ubisys.bootwithsercurity.common.sercurity;

import com.alibaba.fastjson.JSON;
import com.ubisys.bootwithsercurity.common.utils.ResultEnum;
import com.ubisys.bootwithsercurity.common.utils.ResultVO;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by cw on 2018/10/31.
 * 退出成功
 */
@Component
public class AjaxLogoutSuccessHandler implements LogoutSuccessHandler {


    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
//这句话的意思，是让浏览器用utf8来解析返回的数据
        httpServletResponse.setHeader("Content-type", "text/html;charset=UTF-8");
        //这句话的意思，是告诉servlet用UTF-8转码，而不是用默认的ISO8859
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.getWriter().print(JSON.toJSON(ResultVO.result(ResultEnum.USER_LOGOUT_SUCCESS,true)));
    }
}
