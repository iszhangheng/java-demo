package com.example.securitydemo.security;

import com.example.securitydemo.mapper.IUserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@Qualifier("normalUserDetailService")
public class UserDetailServiceImpl implements UserDetailsService {
    private final IUserDao userDao;

    public UserDetailServiceImpl(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        club.cearnach.movie.entity.User user = userDao.findByAccount(username)
                .orElseThrow(() -> new UsernameNotFoundException("找不到指定的用户"));
        List<GrantedAuthority> authorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(
                        MovieSecurityConstants.ROLE_PREFIX.concat(user.getRole().getName()));
        return new User(user.getAccount(), user.getPassword(), authorities);
    }
}
