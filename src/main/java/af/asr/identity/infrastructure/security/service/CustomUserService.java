package af.asr.identity.infrastructure.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import af.asr.identity.data.model.*;
import af.asr.identity.data.repository.UserRepository;
import af.asr.identity.infrastructure.security.data.CustomUser;
import af.asr.identity.service.GroupService;
import af.asr.identity.service.PrivilegeService;
import af.asr.identity.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupService groupService;

    @Autowired
    private PrivilegeService privilegeService;

    @Autowired
    private TenantService tenantService;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public CustomUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadUserByUsername(username, getCurrentTenant());
    }


    public CustomUser loadUserByUsername(String username, long tenantId) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        Tenant tenant = tenantService.findById(tenantId);
        Collection<Group> userGroups = new ArrayList<>();

        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

            Collection<Privilege> userPermissions = privilegeService.findAllByUserAndTenant(user.getId(), tenantId);
            return new CustomUser(user.getUsername(), user.getPassword(),
                    user.isActive(), true, true, true, getGrantedAuthorities(userPermissions), tenantId);


        // userGroups = user.getGroups();
//
//        CustomUser userDetails = new CustomUser(user.getUsername(), user.getPassword(),
//                user.isActive(), true, true, true, getAuthorities(userGroups), tenantId);
//
//
//        return userDetails;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Group> groups) {
        HashSet<Role> roles = new HashSet<>();
        for (Group group : groups) {
            roles.addAll(group.getRoles());
        }

        return getGrantedAuthorities(getPermissions(roles));
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

    public HashSet<Role> getRoles(Collection<Group> groups) {
        HashSet<Role> roles = new HashSet<>();
        for (Group group : groups) {
            roles.addAll(group.getRoles());
        }
        return roles;
    }

    public Long getCurrentTenant() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && (authentication.getPrincipal() instanceof CustomUser)) {
            CustomUser userDetails = (CustomUser) authentication.getPrincipal();
            return userDetails.getTenant();
        }
        return null;
    }
}
