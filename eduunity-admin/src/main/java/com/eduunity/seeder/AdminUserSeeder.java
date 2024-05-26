package com.eduunity.seeder;

import com.eduunity.dto.admin.AdminUser;
import com.eduunity.enums.Role;
import com.eduunity.repo.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AdminUserSeeder implements ApplicationRunner {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger log = LoggerFactory.getLogger(AdminUserSeeder.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(args.getOptionValues("seeder") != null){
            List<String> seeder = Arrays.asList(args.getOptionValues("seeder").get(0).split(","));
            if(seeder.contains("adminUser")) {
                seedAdminUser();
                log.info("Success run Admin User seeder");
            }
        }else{
            log.info("AdminUser seeder skipped");
        }
    }

    private void seedAdminUser() {
        List<AdminUser> adminUserList = new ArrayList<AdminUser>();
        AdminUser adminUser = new AdminUser();

        adminUser.setFirstName("chamud");
        adminUser.setLastName("sachintha");
        adminUser.setUserName("chamud123");
        adminUser.setPassword(passwordEncoder.encode("123"));
        adminUser.setRole(Role.ADMIN);

        adminUserList.add(adminUser);

        int index = 0;
        for (AdminUser adminUser1 : adminUserList) {
            adminRepository.save(adminUser1);
            log.info("Success run RoleSeeder {}",adminUserList.get(index));

            index++;
        }
    }
}
