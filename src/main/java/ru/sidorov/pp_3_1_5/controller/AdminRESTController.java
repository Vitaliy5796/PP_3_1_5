package ru.sidorov.pp_3_1_5.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sidorov.pp_3_1_5.model.User;
import ru.sidorov.pp_3_1_5.service.RoleService;
import ru.sidorov.pp_3_1_5.service.UserService;
import ru.sidorov.pp_3_1_5.util.BindingResultErrorMessages;
import ru.sidorov.pp_3_1_5.util.UserErrorResponse;
import ru.sidorov.pp_3_1_5.util.UserNotCreatedException;
import ru.sidorov.pp_3_1_5.util.UserNotFoundException;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/admin")
public class AdminRESTController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminRESTController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "")
    public List<User> showAllUsers() {
        return userService.getUsersList();
    }

    @PostMapping(value = "")
    public ResponseEntity<HttpStatus> addNewUser(@RequestBody @Valid User user, BindingResult bindingResult) {
        BindingResultErrorMessages errorMessages = new BindingResultErrorMessages();
        errorMessages.getBindingResultErrorMessages(bindingResult);
        User userFromDb = userService.findByEmail(user.getUsername());
        if (userFromDb != null) {
            throw new UserNotCreatedException("Пользователь с таким email уже существует.");
        }
        userService.addUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> updateUser(@PathVariable("id") Long id, @RequestBody @Valid User user,
                                                 BindingResult bindingResult) {
        user.setId(id);
        BindingResultErrorMessages errorMessages = new BindingResultErrorMessages();
        errorMessages.getBindingResultErrorMessages(bindingResult);
        User userFromDb = userService.findByEmail(user.getUsername());
        if (userFromDb != null && !userFromDb.getId().equals(user.getId())) {
            throw new UserNotCreatedException("Пользователь с таким email уже существует.");
        }
        userService.updateUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserNotCreatedException e) {
        UserErrorResponse response = new UserErrorResponse(e.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserNotFoundException e) {
        UserErrorResponse response = new UserErrorResponse("Пользователь с таким id не был найден.",
                System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
