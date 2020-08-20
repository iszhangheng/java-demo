package com.example.securitydemo.security;

import com.example.securitydemo.mapper.IAdminService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Qualifier("adminDetailService")
public class AdminUserDetailServiceImpl implements UserDetailsService {
    private final IAdminService adminService;

    public AdminUserDetailServiceImpl(IAdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminService.findByAccount(username)
                .orElseThrow(() -> new UsernameNotFoundException(AdminException.ADMIN_CAN_NOT_FOUNT));
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(
                MovieSecurityConstants.ROLE_PREFIX.concat(admin.getRole().getName()));
        return new User(admin.getAccount(), admin.getPassword(), authorities);
    }
}
