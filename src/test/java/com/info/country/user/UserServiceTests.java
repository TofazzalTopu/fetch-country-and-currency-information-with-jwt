package com.info.country.user;

import com.info.country.model.User;
import com.info.country.repository.UserRepository;
import com.info.country.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTests {

    @InjectMocks private UserServiceImpl userService;
    @Mock private UserRepository userRepository;

    @Test
    public void save() {
        User user = new User();
        user.setId("1");
        user.setUsername("rana@gmail.com");

        Mockito.when(userRepository.save(user)).thenReturn(user);
        Assert.assertEquals(user, userRepository.save(user));
    }

    @Test
    public void findById() {
        String userId = "111111";
        User user = new User();
        user.setId(userId);
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        User userById = userService.findById(userId);
        Assert.assertEquals(userById, user);
    }

    @Test
    public void findByUsername() {
        String userName = "alex@gmail.com";
        User user = new User();
        user.setUsername(userName);
        Mockito.when(userRepository.findByUsername(userName)).thenReturn(Optional.of(user));
        Optional<User> userOptional = userService.findByUserName(userName);
        Assert.assertEquals(userOptional, Optional.of(user));
    }

    @Test
    public void findUserList() {
        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setId("1");
        user.setUsername("rana@gmail.com");
        userList.add(user);

        Mockito.when(userRepository.findAll()).thenReturn(userList);
        List<User> users = userService.findAll();
        Assert.assertEquals(1, users.size());
        Assert.assertEquals(user, users.get(0));
    }
}
