package com.ubisys.bootwithsercurity.sys.web;


import com.baomidou.mybatisplus.mapper.Condition;
import com.ubisys.bootwithsercurity.sys.entity.SysUser;
import com.ubisys.bootwithsercurity.sys.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 系统用户 前端控制器
 * </p>
 *
 * @author miaomiao
 * @since 2018-10-30
 */
@Controller
@RequestMapping("/sys/sysUser")
@Slf4j
public class SysUserController {


    @Autowired
    private ISysUserService iSysUserService;

    @RequestMapping(value = "/test",method = RequestMethod.POST)
    @ResponseBody
    public List<SysUser> test(){
        List<SysUser> ll= new ArrayList<SysUser>();

     ll= iSysUserService.selectList(Condition.create().eq("username","admin"));
      log.info(ll.toString());
        return ll;
    }



    @RequestMapping(value = "/test2",method = RequestMethod.POST)
    @ResponseBody
    public List<SysUser> test2(){
        List<SysUser> ll= new ArrayList<SysUser>();

        ll= iSysUserService.selectList(Condition.create().eq("username","admin"));
        log.info(ll.toString());
        return ll;
    }


}

