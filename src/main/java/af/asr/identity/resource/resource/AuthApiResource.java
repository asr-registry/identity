package af.asr.identity.resource.resource;

import af.asr.identity.data.model.User;
import af.asr.identity.infrastructure.security.data.CustomUser;
import af.asr.identity.infrastructure.security.service.CustomUserService;
import af.asr.identity.infrastructure.security.token.JwtTokenUtil;
import af.asr.identity.resource.handler.ResponseHandler;
import af.asr.identity.service.RoleService;
import af.asr.identity.service.TenantService;
import af.asr.identity.service.UserService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
@Slf4j
public class AuthApiResource extends ResponseHandler {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userAuthService;

    @Autowired
    private CustomUserService customUserService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @Autowired
    private RoleService roleService;

    @Autowired
    private TenantService tenantService;

    ObjectMapper mapper = new ObjectMapper();


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody Map<String, String> loginUser) throws AuthenticationException {
        final String username = loginUser.get("username");
        final String password = loginUser.get("password");

        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final CustomUser customUser = customUserService.loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(customUser);

        User authenticatedUser = userService.getLoggedInUser();

        Map<String, Object> result = new HashMap<>();
        result.put("token", token);

        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity logout(HttpServletRequest request) {
        return ResponseEntity.ok(true);
    }

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }


    @GetMapping(path = "/config")
    public ObjectNode config(Authentication authentication) throws JsonParseException, IOException, GeneralSecurityException {
        CustomUser userDetails = (CustomUser) authentication.getPrincipal();

        User user = userService.findByUsername(userDetails.getUsername());
        return getConfiguration(user, userDetails);
    }

    private ObjectNode getConfiguration(User user, CustomUser userDetails) throws JsonParseException, IOException, GeneralSecurityException {
        ObjectNode config = mapper.createObjectNode();

        // User Details
        config.put("id", user.getId());
        config.put("name", user.getName());
        config.put("username", userDetails.getUsername());
        config.put("authenticated", true);
        config.put("roles", user.getGroups().toString());
        config.put("isSysAdmin", userService.isAdmin());

        // User Autorities
        ArrayNode authorities = mapper.createArrayNode();
        for(GrantedAuthority auth : userDetails.getAuthorities()) {
            authorities.add(auth.getAuthority());
        }
        config.set("authorities", authorities);

        return config;
    }

    @RequestMapping("/token")
    public Map<String,String> token(HttpSession session) {
        return Collections.singletonMap("token", session.getId());
    }


    @PostMapping(value = "/forget-password", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Map<String, Object>> forgetPass(@Valid @RequestBody(required = true) String email) {
        Map<String, Object> data =new  HashMap<>();

        User user = userService.findByEmail(email);

        data.put("status", HttpStatus.OK);
        data.put("message", "User successfully created");

        return ResponseEntity.ok(data);
    }

}
