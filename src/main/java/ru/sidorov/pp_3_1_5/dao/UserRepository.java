package ru.sidorov.pp_3_1_5.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.sidorov.pp_3_1_5.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u JOIN FETCH u.roles where u.email = (:email)")
    User findByEmail(String email);
}
