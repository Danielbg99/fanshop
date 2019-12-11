package com.example.fanshop.web.controllers;

import com.example.fanshop.domain.services.ArticleService;
import com.example.fanshop.domain.services.FanShopService;
import com.example.fanshop.domain.services.UserService;
import com.example.fanshop.repositories.ArticleRepository;
import com.example.fanshop.repositories.FanShopRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FanShopController {

    private final ModelMapper modelMapper;
    private final ArticleService articleService;
    private final FanShopService fanShopService;
    private final UserService userService;
    private final ArticleRepository articleRepository;
    private final FanShopRepository fanShopRepository;

    @Autowired
    public FanShopController(ModelMapper modelMapper, ArticleService articleService, FanShopService fanShopService, UserService userService, ArticleRepository articleRepository, FanShopRepository fanShopRepository) {
        this.modelMapper = modelMapper;
        this.articleService = articleService;
        this.fanShopService = fanShopService;
        this.userService = userService;
        this.articleRepository = articleRepository;
        this.fanShopRepository = fanShopRepository;
    }

    @GetMapping("/create")
    public ModelAndView createArticle(ModelAndView modelAndView, Authentication authentication) {
        modelAndView.setViewName("fanShop/createArticle");
        return modelAndView;
    }
    @GetMapping("/home")
    public ModelAndView home(ModelAndView modelAndView) {
        modelAndView.setViewName("home.html");
        return modelAndView;
    }
}
