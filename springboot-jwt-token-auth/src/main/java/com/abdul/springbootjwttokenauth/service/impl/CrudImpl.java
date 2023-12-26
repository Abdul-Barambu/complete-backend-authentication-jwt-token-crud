package com.abdul.springbootjwttokenauth.service.impl;

import com.abdul.springbootjwttokenauth.entity.User;
import com.abdul.springbootjwttokenauth.repository.UserRepository;
import com.abdul.springbootjwttokenauth.service.Crud;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrudImpl implements Crud {

    private final UserRepository userRepository;


    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
