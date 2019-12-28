package af.asr.identity.infrastructure.security.service;

import af.asr.identity.data.model.Privilege;
import af.asr.identity.data.model.Role;
import af.asr.identity.data.model.User;
import af.asr.identity.data.repository.UserRepository;
import af.asr.identity.infrastructure.security.data.CustomUser;
import af.asr.identity.service.PrivilegeService;
import af.asr.identity.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;


@Service
public class CustomUserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService groupService;

    @Autowired
    private PrivilegeService permissionService;

//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public CustomUser loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Active Tenant: "+ getCurrentEnv());

        return loadUserByUsername(username, 0L);
    }

    public CustomUser loadUserByUsername(String username, Long tenantId) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        Collection<Role> userGroups = new ArrayList<>();

        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        if(tenantId != null) {
            Collection<Privilege> userPermissions = permissionService.findAll();
            return new CustomUser(user.getUsername(), user.getPassword(),
                    user.isActive(), true, true, true, getGrantedAuthorities(userPermissions), 0);
        }


        CustomUser userDetails = new CustomUser(user.getUsername(), user.getPassword(),
                user.isActive(), true, true, true, getAuthorities(userGroups), 0);
        return userDetails;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> groups) {
        return getGrantedAuthorities(getPermissions(groupService.findAll()));
    }

    private List<String> getPermissions(Collection<Role> roles) {
        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (Role role : roles) {
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

    private List<GrantedAuthority> getGrantedAuthorities(Collection<Privilege> permissions) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Privilege permission : permissions) {
            authorities.add(new SimpleGrantedAuthority(permission.getName()));
        }
        return authorities;
    }

    public HashSet<Privilege> getPrivileges(Collection<Role> roles) {
        HashSet<Privilege> privileges = new HashSet<>();
        for (Role r : roles) {
            privileges.addAll(r.getPrivileges());
        }
        return privileges;
    }

    public Long getCurrentEnv() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && (authentication.getPrincipal() instanceof CustomUser)) {
            CustomUser userDetails = (CustomUser) authentication.getPrincipal();
            return userDetails.getTenant();
        }
        return null;
    }
}
