package com.ubisys.bootwithsercurity.common.sercurity;

import com.baomidou.mybatisplus.mapper.Condition;
import com.ubisys.bootwithsercurity.sys.entity.SysUser;
import com.ubisys.bootwithsercurity.sys.mapper.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by cw on 2018/10/31.
 * 用户认证授权
 */
@Component
@Slf4j
public class SelfUserDetailsService  implements UserDetailsService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        //通过username查询用户

      List<SysUser> ll= sysUserMapper.selectList(Condition.create().eq("username",username));

        if (ll.isEmpty()) {
            //仍需要细化处理
            throw new UsernameNotFoundException("该用户不存在");
        }

        SelfUserDetails user =new SelfUserDetails();
        SysUser real=ll.get(0);
        user.setId(Integer.valueOf(real.getUserId()+""));
        user.setUsername(real.getUsername());
        user.setPassword(real.getPassword());


        Set authoritiesSet = new HashSet();
        // 模拟从数据库中获取用户角色
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN");

        authoritiesSet.add(authority);
        user.setAuthorities(authoritiesSet);

        log.info("用户{}验证通过", username);
        return user;

    }


}
