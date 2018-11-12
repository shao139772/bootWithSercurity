package com.ubisys.bootwithsercurity.sys.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by cw on 2018/11/12.
 */
@Controller
@Slf4j
public class LoginController {

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

}
