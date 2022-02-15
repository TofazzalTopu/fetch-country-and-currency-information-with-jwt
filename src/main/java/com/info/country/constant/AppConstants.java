package com.info.country.constant;

import org.springframework.stereotype.Component;

/**
 * @author Tofazzal
 */

@Component
public class AppConstants {
    public static final String API_VERSION = "v1";

    public static final String JWT_SECRET = "SECUREOFFNOSECURITYSECUREOFFNOSECURITYSECUREOFFNOSECURITYSECUREOFFNOSECURITYSECUREOFFNOSECURITYSECUREOFFNOSECURITY";
    public static final String HEADER = "Authorization";
    public static final String PREFIX = "Bearer ";
    public static final String LOGIN_PATH = "/api/v1/users/login";
    public static final String REGISTRATION_PATH = "/api/v1/users/create";
    public static final String[] AUTH_WHITELIST = {
            "/v2/api-docs",
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "**/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/swagger-ui.html",
            "**/swagger-ui.html**",
            "**/swagger-ui.html/**",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/",
            LOGIN_PATH,
            REGISTRATION_PATH
    };

    public static final String USER_REGISTERED_SUCCESS = "User saved successfully.";
    public static final String USER_NOT_EXIST = "User not exist with the id: ";
    public static final String USER_NAME_ALREADY_EXIST = "User already exist with the username: ";
    public static final String USER_FETCH_SUCCESS = "User fetch successfully.";
    public static final String LOGOUT_SUCCESS = "You have been logged out successfully.";

    public static final String COUNTRY_NOT_FOUND_WITH_THE_GIVEN_ID = "Country not found with the given id: ";
    public static final String YOU_COULD_NOT_REQUEST_COUNTRY_API_MORE_THAN_THIRTY_TIMES_WITHIN_A_MINUTE = "You could not request country api more than 30 times within a minute!";
}
