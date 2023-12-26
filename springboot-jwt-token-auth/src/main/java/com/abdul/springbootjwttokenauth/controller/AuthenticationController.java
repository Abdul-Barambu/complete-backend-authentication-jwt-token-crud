package com.abdul.springbootjwttokenauth.controller;

import com.abdul.springbootjwttokenauth.dto.LoginRequest;
import com.abdul.springbootjwttokenauth.dto.RegistrationRequest;
import com.abdul.springbootjwttokenauth.entity.User;
import com.abdul.springbootjwttokenauth.repository.UserRepository;
import com.abdul.springbootjwttokenauth.service.Crud;
import com.abdul.springbootjwttokenauth.service.LoginService;
import com.abdul.springbootjwttokenauth.service.impl.AuthenticationService;
import com.abdul.springbootjwttokenauth.service.impl.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final LoginService loginService;
    private final UserService userService;
    private final Crud crud;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping(path = "/registerAdmin")
    public ResponseEntity<Object> registerAdmin(@RequestBody RegistrationRequest request) {
        return ResponseEntity.ok(authenticationService.registerAdmin(request));
    }

    @PostMapping(path = "/registerSchool")
    public ResponseEntity<Object> registerSchool(@RequestBody RegistrationRequest request) {
        return ResponseEntity.ok(authenticationService.registerSchool(request));
    }

    @PostMapping(path = "/registerPayer")
    public ResponseEntity<Object> registerPayer(@RequestBody RegistrationRequest request) {
        return ResponseEntity.ok(authenticationService.registerPayer(request));
    }

    @GetMapping(path = "/confirm")
    public ResponseEntity<Object> confirmToken(@RequestParam("token") String confirmToken) {
        return authenticationService.confirmToken(confirmToken);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Object> signin(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(loginService.signIn(loginRequest));
    }


//    CRUD operations

    //    get All Users
    @GetMapping(path = "/getAllUsers")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    //    update users
    @PutMapping(path = "/update/user/{id}")
    public String updateUser(@PathVariable Long id, @RequestBody Map<String, Object> updates) {

//        update single fields

        User existingUser = crud.getUserById(id);

        // Check if the user exists
        if (existingUser == null) {
            return "User not found";
        }

        // Iterate through the updates and apply them to the user
        for (Map.Entry<String, Object> entry : updates.entrySet()) {
            String field = entry.getKey();
            Object value = entry.getValue();

            // Update the specified field
            switch (field) {
                case "name":
                    existingUser.setName((String) value);
                    break;
                case "email":
                    existingUser.setEmail((String) value);
                    break;
                case "password":
                    // Encode the new password before updating
                    String newPassword = (String) value;
                    if (newPassword != null && !newPassword.isEmpty()) {
                        String encodedPassword = bCryptPasswordEncoder.encode(newPassword);
                        existingUser.setPassword(encodedPassword);
                    }
                    break;
                // Add more cases for other fields if needed
            }
        }

        // Update the user
        crud.updateUser(existingUser);

        return "Success";



//        UPDATE ALL FIELDS

//        User existingUser = crud.getUserById(id);
//
//        existingUser.setId(id);
//        existingUser.setName(user.getName());
//        existingUser.setEmail(user.getEmail());
//
//        // Encode the new password before updating
//        String newPassword = user.getPassword();
//        if (newPassword != null && !newPassword.isEmpty()) {
//            String encodedPassword = bCryptPasswordEncoder.encode(newPassword);
//            existingUser.setPassword(encodedPassword);
//        }
//
//        crud.updateUser(existingUser);
//
//        return "Success";
    }


//    delete a user
    @DeleteMapping(path = "/deleteUser/{id}")
    public String deleteUser(@PathVariable Long id){
        crud.deleteUser(id);

        return "Deleted";
    }

}
