package com.info.country.service;



import com.info.country.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    User save(User user);

    void delete(String userId);

    User findById(String id);

    Optional<User> findByUserName(String username);

    User update(String id, User user);

}
