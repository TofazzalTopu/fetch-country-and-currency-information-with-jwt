package com.info.country.controller;

import com.info.country.constant.AppConstants;
import com.info.country.model.User;
import com.info.country.service.UserService;
import com.info.country.service.dto.responses.Response;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Slf4j
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/" + AppConstants.API_VERSION + "/users")
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "User registration")
    @PostMapping(value = "/create")
    public ResponseEntity<Response<User>> save(@RequestBody User user) throws URISyntaxException {
        if (userService.findByUserName(user.getUsername()).isPresent()) {
            log.error(AppConstants.USER_NAME_ALREADY_EXIST);
            return ResponseEntity.badRequest().body(new Response<>(HttpStatus.BAD_REQUEST.value(),
                    AppConstants.USER_NAME_ALREADY_EXIST + user.getUsername(), null));
        }
        return ResponseEntity.created(new URI("/api/v1/users")).body(new Response<>(HttpStatus.CREATED.value(),
                AppConstants.USER_REGISTERED_SUCCESS, userService.save(user)));
    }

    @ApiOperation(value = "Update user by id")
    @PatchMapping("/{id}")
    public ResponseEntity<Response<User>> update(@PathVariable String id, @RequestBody User user) {
        Optional<User> optionalUser = userService.findByUserName(user.getUsername());
        if (optionalUser.isPresent()) {
            if (user.getId() != optionalUser.get().getId()) {
                log.error(AppConstants.USER_NAME_ALREADY_EXIST);
                return ResponseEntity.badRequest().body(new Response<>(HttpStatus.BAD_REQUEST.value(),
                        AppConstants.USER_NAME_ALREADY_EXIST + user.getUsername(), null));
            }
        }

        return ResponseEntity.ok().body(new Response<>(HttpStatus.ACCEPTED.value(),
                AppConstants.USER_REGISTERED_SUCCESS, userService.update(id, user)));
    }

    @ApiOperation(value = "Fetch All users")
    @GetMapping
    public ResponseEntity<Response<List<User>>> list() {
        return ResponseEntity.ok().body(new Response<>(HttpStatus.OK.value(),
                AppConstants.USER_FETCH_SUCCESS, userService.findAll()));
    }

    @ApiOperation(value = "Fetch user by id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<User>> findById(@PathVariable String id) {
        return ResponseEntity.ok().body(new Response<>(HttpStatus.OK.value(),
                AppConstants.USER_FETCH_SUCCESS, userService.findById(id)));
    }

    @ApiOperation(value = "Delete user by id")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response<Void>> delete(@PathVariable String id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
