package com.example.fanshop.domain.services;


import com.example.fanshop.domain.services.models.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserServiceModel register(UserServiceModel user);

    UserServiceModel findUserByUserName(String username);

    UserServiceModel editUserProfile(UserServiceModel userServiceModel, String oldPassword);

}
