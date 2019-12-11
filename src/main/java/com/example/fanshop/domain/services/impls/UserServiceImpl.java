package com.example.fanshop.domain.services.impls;

import com.example.fanshop.domain.entities.User;
import com.example.fanshop.domain.services.RoleService;
import com.example.fanshop.domain.services.UserService;
import com.example.fanshop.domain.services.models.UserServiceModel;
import com.example.fanshop.repositories.RoleRepository;
import com.example.fanshop.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleService roleService;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder, RoleService roleService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleService = roleService;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserServiceModel register(UserServiceModel userServiceModel) {
        this.roleService.seedRolesInDB();
        User user = this.modelMapper.map(userServiceModel, User.class);
        if (this.userRepository.count() == 0) {
            user.setAuthorities(new LinkedHashSet<>(this.roleRepository.findAll()));
        } else {
            user.setAuthorities(new LinkedHashSet<>());

            user.getAuthorities().add(this.roleRepository.findByAuthority("USER"));
        }

        user.setPassword(this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()));
        return this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceModel.class);
    }

    @Override
    public UserServiceModel findUserByUserName(String username) {
        return this.userRepository.findByUsername(username)
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .orElseThrow(() -> new UsernameNotFoundException("USERNAME_NOT_FOUND"));
    }

    @Override
    public UserServiceModel editUserProfile(UserServiceModel userServiceModel, String oldPassword) {
        User user = this.userRepository.findByUsername(userServiceModel.getUsername())
                .orElseThrow(()-> new UsernameNotFoundException("USER_ID_NOT_FOUND"));

        if (!this.bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("PASSWORD_IS_INCORRECT");
        }

        user.setPassword(!"".equals(userServiceModel.getPassword()) ?
                this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()) :
                user.getPassword());
        user.setEmail(userServiceModel.getEmail());

        return this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceModel.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("USERNAME_NOT_FOUND"));
    }
}
