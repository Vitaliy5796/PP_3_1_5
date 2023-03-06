package ru.sidorov.pp_3_1_5.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.sidorov.pp_3_1_5.model.Role;


public interface RoleRepository extends JpaRepository<Role, Long> {
}
