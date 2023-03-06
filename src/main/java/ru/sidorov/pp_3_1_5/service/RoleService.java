package ru.sidorov.pp_3_1_5.service;

import ru.sidorov.pp_3_1_5.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();

    void addRole(Role role);
}
