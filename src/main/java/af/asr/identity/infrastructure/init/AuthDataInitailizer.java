package af.asr.identity.infrastructure.init;


import af.asr.identity.data.model.Group;
import af.asr.identity.data.model.Privilege;
import af.asr.identity.data.model.Role;
import af.asr.identity.data.model.User;
import af.asr.identity.infrastructure.util.Constants;
import af.asr.identity.service.GroupService;
import af.asr.identity.service.PrivilegeService;
import af.asr.identity.service.RoleService;
import af.asr.identity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class AuthDataInitailizer {

    @Autowired
    private PrivilegeService privilegeService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TenantDataInitializer tenantDataInitializer;

    @Autowired
    private GroupService groupService;

    @PostConstruct
    public void init()
    {
        initAuthData();
    }

    private void initAuthData()
    {
        Map<String, List<Group>> groups = createGroupsIfNotExist();

        if(userService.findAll().size() <1)
        {
            User admin = new User();
            admin.setEmail("admin@asrpay.af");
            admin.setActive(true);
            admin.setName("Admin of System");
            admin.setUsername("admin");
            admin.setPhone("0794035544");
            admin.setGroups(groups.get("defaultGroups"));
            admin.setPassword("admin1235");
            admin.setActive(true);
            admin.setTenant(tenantDataInitializer.createCoreTenantsIfNotExist());


            User user =  userService.save(admin);

            User defaultUser = new User();
            defaultUser.setEmail("user@asrpay.af");
            defaultUser.setActive(true);
            defaultUser.setName("Default User");
            defaultUser.setUsername("user");
            defaultUser.setPhone("0798786754");
            defaultUser.setGroups(groups.get("defaultGroups"));
            defaultUser.setPassword("user1235");
            defaultUser.setCore(true);
            defaultUser.setTenant(tenantDataInitializer.createCoreTenantsIfNotExist());


            User createdDefaultUser =  userService.save(defaultUser);


        }
    }

    private Map<String, List<Privilege>> getAllPrivileges(){
        List<Privilege> privileges = new ArrayList<>();
        List<Privilege> userPrivileges= new ArrayList<>();
        Map<String, List<Privilege>> data = new HashMap<>();

        if (privilegeService.findAll().size() < 1)
        {
            Privilege sysAdminPriv = new Privilege("SYS_ADMIN");

            Privilege createRolePriv = new Privilege("CREATE_ROLE");
            Privilege readRolePriv = new Privilege("READ_ROLE");
            Privilege updateRolePriv = new Privilege("UPDATE_ROLE");
            Privilege deleteRolePriv = new Privilege("DELETE_ROLE");
            Privilege viewRolePriv = new Privilege("VIEW_ROLE");



            Privilege createUserPriv = new Privilege("CREATE_USER");
            Privilege readUserPriv = new Privilege("READ_USER");
            Privilege updateUserPriv = new Privilege("UPDATE_USER");
            Privilege deleteUserPriv = new Privilege("DELETE_USER");
            Privilege viewUserPriv = new Privilege("VIEW_USER");




            Privilege createIntegrationPriv = new Privilege("CREATE_INTEGRATION");
            Privilege readIntegrationPriv = new Privilege("READ_INTEGRATION");
            Privilege updateIntegrationPriv = new Privilege("UPDATE_INTEGRATION");
            Privilege deleteIntegrationPriv = new Privilege("DELETE_INTEGRATION");
            Privilege viewIntegrationPriv = new Privilege("VIEW_INTEGRATION");
            Privilege pauseIntegrationPriv = new Privilege("PAUSE_INTEGRATION");
            Privilege resumeIntegrationPriv = new Privilege("RESUME_INTEGRATION");
            Privilege restartIntegrationPriv = new Privilege("RESTART_INTEGRATION");



            Privilege mailSendPriv = new Privilege("SEND_MAIL");
            Privilege smsSendPriv = new Privilege("SEND_SMS");


            Privilege balanceInquiryPriv = new Privilege("BALANCE_INQUIRY");
            Privilege fundTransferPriv = new Privilege("FUND_TRANSFER");
            Privilege payForServicePriv = new Privilege("PAY_FOR_SERVICE");
            Privilege viewTransactionsWithReponsePriv = new Privilege("VIEW_TRANSACTIONS_WITH_RESPONSE");
            Privilege viewTransactionsWithoutReponsePriv = new Privilege("VIEW_TRANSACTIONS_WITHOUT_RESPONSE");
            Privilege generateQrCodePriv = new Privilege("GENERATE_QRCODE");
            Privilege createCardPriv = new Privilege("CREATE_CARD");
            Privilege readCardPriv = new Privilege("READ_CARD");
            Privilege updateCardPriv = new Privilege("UPDATE_CARD");
            Privilege deleteCardPriv = new Privilege("DELETE_CARD");
            Privilege viewCardPriv = new Privilege("VIEW_CARD");
            Privilege editProfilePriv = new Privilege("EDIT_PROFILE");
            Privilege viewProfilePriv = new Privilege("VIEW_PROFILE");

            privilegeService.save(balanceInquiryPriv);
            privilegeService.save(fundTransferPriv);
            privilegeService.save(payForServicePriv);
            privilegeService.save(viewTransactionsWithoutReponsePriv);
            privilegeService.save(viewTransactionsWithReponsePriv);
            privilegeService.save(generateQrCodePriv);
            privilegeService.save(createCardPriv);
            privilegeService.save(readCardPriv);
            privilegeService.save(updateCardPriv);
            privilegeService.save(deleteCardPriv);
            privilegeService.save(viewCardPriv);
            privilegeService.save(viewProfilePriv);
            privilegeService.save(editProfilePriv);


            privilegeService.save(sysAdminPriv);

            privilegeService.save(createRolePriv);
            privilegeService.save(readRolePriv);
            privilegeService.save(updateRolePriv);
            privilegeService.save(deleteRolePriv);
            privilegeService.save(viewRolePriv);

            privilegeService.save(createUserPriv);
            privilegeService.save(readUserPriv);
            privilegeService.save(updateUserPriv);
            privilegeService.save(deleteUserPriv);
            privilegeService.save(viewUserPriv);


            privilegeService.save(createIntegrationPriv);
            privilegeService.save(readIntegrationPriv);
            privilegeService.save(updateIntegrationPriv);
            privilegeService.save(deleteIntegrationPriv);
            privilegeService.save(viewIntegrationPriv);
            privilegeService.save(resumeIntegrationPriv);
            privilegeService.save(pauseIntegrationPriv);
            privilegeService.save(restartIntegrationPriv);

            privilegeService.save(mailSendPriv);
            privilegeService.save(smsSendPriv);


            privileges = Arrays.asList(
                    sysAdminPriv,
                createRolePriv, readRolePriv, updateRolePriv, deleteRolePriv, viewRolePriv,
                    createUserPriv, readUserPriv, updateUserPriv, deleteUserPriv, viewUserPriv,
                    createIntegrationPriv, readIntegrationPriv, updateIntegrationPriv, deleteIntegrationPriv, viewIntegrationPriv,
                    resumeIntegrationPriv, pauseIntegrationPriv, restartIntegrationPriv, smsSendPriv, mailSendPriv,

                    readCardPriv, createCardPriv, updateCardPriv, deleteCardPriv, viewCardPriv,
                    balanceInquiryPriv, fundTransferPriv, payForServicePriv, viewTransactionsWithoutReponsePriv, viewTransactionsWithReponsePriv,
                    viewProfilePriv, editProfilePriv
            );

            userPrivileges= Arrays.asList(
                    readCardPriv, createCardPriv, updateCardPriv, deleteCardPriv, viewCardPriv,
                    balanceInquiryPriv, fundTransferPriv, payForServicePriv, viewTransactionsWithoutReponsePriv, viewTransactionsWithReponsePriv,
                    viewProfilePriv, editProfilePriv
            );
            data.put("adminPrivileges", privileges);
            data.put("userPrivileges", userPrivileges);

        }

        return  data;
    }

    private Map< String, List<Role>> createRolesIfNotExist()
    {
        List<Role> adminRoles = new ArrayList<>();
        List<Role> userRoles = new ArrayList<>();
        Map<String, List<Privilege>> privs = getAllPrivileges();

        Map<String, List<Role>> roleData= new HashMap<>();

        if(roleService.findAll().size() < 1){
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            adminRole.setDescription("ROLE FOR ADMIN");
            adminRole.setPrivileges(privs.get("adminPrivileges"));
            adminRole.setCore(true);

            roleService.save(adminRole);

            Role userRole =new Role();
            userRole.setName(Constants.DEFAULT_ROLE_NAME);
            userRole.setDescription("ROLE FOR DEFAULT USER");
            userRole.setPrivileges(privs.get("userPrivileges"));
            userRole.setCore(true);
            roleService.save(userRole);

            //assign admin roles
            adminRoles = Arrays.asList(
                adminRole
            );

            //assign user roles
            userRoles =Arrays.asList(
                    userRole
            );

            roleData.put("adminRoles", adminRoles);
            roleData.put("userRoles", userRoles);

        }

        return roleData;
    }


    private Map<String, List<Group>> createGroupsIfNotExist()
    {

        Map<String, List<Role>> roles = createRolesIfNotExist();
        List<Role> adminroles = roles.get("userRoles");

        Map<String, List<Group>> groups = new HashMap<>();

        List<Group> defaultGroups = new ArrayList<>();

        if(roleService.findAll().size() < 1){
            Group defaul_group = Group.builder()
                    .core(true)
                    .name(Constants.DEFAILT_GROUP_NAME)
                    .description("This is the defaul group")
                    .active(true)
                    .roles(adminroles)
                    .build();
            groupService.save(defaul_group);

            defaultGroups.add(defaul_group);
        }
        groups.put("defaultGroups", defaultGroups);

        return groups;

    }
}
