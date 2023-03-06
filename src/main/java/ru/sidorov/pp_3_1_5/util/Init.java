package ru.sidorov.pp_3_1_5.util;

import org.springframework.stereotype.Component;
import ru.sidorov.pp_3_1_5.model.Role;
import ru.sidorov.pp_3_1_5.model.User;
import ru.sidorov.pp_3_1_5.service.RoleService;
import ru.sidorov.pp_3_1_5.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class Init {
    private final UserService userService;
    private final RoleService roleService;

    public Init(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void initializeDB() {
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");

        Set<Role> roleAdminSet = new HashSet<>();
        Set<Role> roleUserSet = new HashSet<>();

        roleAdminSet.add(roleAdmin);
        roleUserSet.add(roleUser);

        User admin = new User("admin", "admin", 22,
                "admin@mail.ru", "admin", roleAdminSet);

        User user = new User("user", "user", 22,
                "user@mail.ru", "user", roleUserSet);

        roleService.addRole(roleAdmin);
        roleService.addRole(roleUser);
        userService.addUser(admin);
        userService.addUser(user);
    }
}
