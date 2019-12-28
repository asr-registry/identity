package af.asr.identity.service;

import af.asr.identity.data.model.Role;
import af.asr.identity.data.model.User;
import af.asr.identity.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;

public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> findAll()
    {
        return userRepository.findAll();
    }
    public User findByUsername(String username)
    {
        return userRepository.findByUsername(username);
    }
    public User findByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }
    public User findByPhone(String phone)
    {
        return userRepository.findByPhone(phone);
    }


    public boolean delete(Long id)
    {
        User obj = findById(id);
        if (id == null || id < 1 || obj.isCore()) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }
    public User findById(Long id)
    {
        return userRepository.findById(id).get();
    }
    public User getLoggedInUser() {
        return getLoggedInUser(false);
    }

    public User getLoggedInUser(Boolean forceFresh) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.toString();

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        }
        return findByUsername(username);
    }
    public String getSecurityContextHolderUsername(Boolean forceFresh) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.toString();

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        }

        return username;
    }
    public boolean isAdmin() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<? extends GrantedAuthority> auths = ((UserDetails) principal).getAuthorities();
        if (auths.contains(new SimpleGrantedAuthority("SYS_ADMIN"))) {
            return true;
        }
        return false;
    }
}
