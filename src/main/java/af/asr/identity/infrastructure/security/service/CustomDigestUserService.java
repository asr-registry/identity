package af.asr.identity.infrastructure.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import af.asr.identity.data.model.Group;
import af.asr.identity.data.model.Privilege;
import af.asr.identity.data.model.Role;
import af.asr.identity.data.model.User;
import af.asr.identity.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomDigestUserService{

    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), user.isActive(), true, true, true,
                //  getAuthorities(user.getGroups()));
                getAuthorities(user.getGroups()));

        return userDetails;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Group> groups) {
        List<Role> roles =  new ArrayList<>();
        for(Group group: groups) {

            roles.addAll(group.getRoles());
        }
        // make a hashset to remove the duplicated roles
        HashSet hs = new HashSet();
        hs.addAll(roles);
        roles.clear();
        roles.addAll(hs);

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
}
