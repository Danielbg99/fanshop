package com.example.fanshop.web.controllers;

import com.example.fanshop.domain.models.UserEditBindingModel;
import com.example.fanshop.domain.models.UserRegisterBindingModel;
import com.example.fanshop.domain.models.view.UserProfileViewModel;
import com.example.fanshop.domain.services.UserService;
import com.example.fanshop.domain.services.models.UserServiceModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController{

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public ModelAndView register(ModelAndView modelAndView) {
        modelAndView.setViewName("user/register.html");
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@ModelAttribute UserRegisterBindingModel model, ModelAndView modelAndView) {
        if (!model.getPassword().equals(model.getConfirmPassword())) {
            modelAndView.setViewName("user/register");
            return modelAndView;
        }

        userService.register(modelMapper.map(model, UserServiceModel.class));
        return redirect("user/login");
    }
    @GetMapping("/login")
    public ModelAndView login(ModelAndView modelAndView) {
        modelAndView.setViewName("user/login.html");
        return modelAndView;
    }
    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView profile(Principal principal, ModelAndView modelAndView){
        modelAndView
                .addObject("model", this.modelMapper
                        .map(this.userService.findUserByUserName(principal.getName()), UserProfileViewModel.class));
        return super.view("user/profile.html", modelAndView);
    }
    @GetMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editProfile(Principal principal, ModelAndView modelAndView) {
        modelAndView
                .addObject("model", this.modelMapper.map
                        (this.userService.findUserByUserName(principal.getName()), UserProfileViewModel.class));
        modelAndView.setViewName("user/edit-profile.html");
        return modelAndView;
    }
    @PostMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editProfileConfirm(@ModelAttribute UserEditBindingModel model){
        if (!model.getPassword().equals(model.getConfirmPassword())){
            return super.view("user/edit-profile");
        }

        this.userService.editUserProfile(this.modelMapper.map(model, UserServiceModel.class), model.getOldPassword());

        return super.redirect("/users/profile");
    }
}
