package com.info.country.service.impl;

import com.info.country.constant.AppConstants;
import com.info.country.exceptions.NotFoundException;
import com.info.country.model.User;
import com.info.country.repository.UserRepository;
import com.info.country.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Md. Tofazzal Hossain
 * Date : 15-02-2022
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<User> findAll() {
        List<User> userList = userRepository.findAll();
        return userList;
    }

    @Override
    public User save(User user) {
        try {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user = userRepository.save(user);
            user.setPassword(null);
        } catch (Exception e) {
            log.error("Exception {}", e.getMessage());
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void delete(String userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(AppConstants.USER_NOT_EXIST + id));
    }

    @Override
    public Optional<User> findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User update(String id, User user) {
        try {
            User existUser = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not exist with the id: " + id));
            existUser.setUsername(user.getUsername());
            user = userRepository.save(existUser);
        } catch (Exception e) {
            log.error("Exception {}", e.getMessage());
            e.printStackTrace();
        }
        return user;
    }

}
