package com.abdul.springbootjwttokenauth.service;

import com.abdul.springbootjwttokenauth.entity.User;

public interface Crud {

    User updateUser(User user);

    User getUserById(Long id);

    void deleteUser(Long id);
}
