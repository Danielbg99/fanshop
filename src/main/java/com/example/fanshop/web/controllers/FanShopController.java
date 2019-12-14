package com.example.fanshop.web.controllers;

import com.example.fanshop.domain.models.ArticleCreateBindingModel;
import com.example.fanshop.domain.models.SearchArticleBindingModel;
import com.example.fanshop.domain.services.ArticleService;
import com.example.fanshop.domain.services.FanShopService;
import com.example.fanshop.domain.services.UserService;
import com.example.fanshop.domain.services.models.ArticleServiceModel;
import com.example.fanshop.domain.services.models.SearchServiceModel;
import com.example.fanshop.repositories.ArticleRepository;
import com.example.fanshop.repositories.FanShopRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class FanShopController extends BaseController{

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
    @PreAuthorize("isAuthenticated()")
    public ModelAndView createArticle(ModelAndView modelAndView, Authentication authentication) {
        modelAndView.setViewName("fanShop/createArticle");
        return modelAndView;
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView createArticleConfirm(@ModelAttribute ArticleCreateBindingModel model) throws IOException {
        ArticleServiceModel article = this.modelMapper.map(model, ArticleServiceModel.class);

        this.articleService.createArticle(article);
        return super.redirect("/home");
    }
    @GetMapping("/home")
    public ModelAndView home(ModelAndView modelAndView) {
        modelAndView.setViewName("home.html");
        return modelAndView;
    }
    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView showAll(@ModelAttribute SearchArticleBindingModel searchArticleBindingModel, ModelAndView modelAndView){
        SearchServiceModel searchServiceModel = this.modelMapper.map(searchArticleBindingModel, SearchServiceModel.class);

        List<ArticleServiceModel> articles = null;

        if(searchServiceModel.getName() == null) {
            articles = this.articleService.findAllArticles()
                    .stream()
                    .map(q -> this.modelMapper.map(q, ArticleServiceModel.class))
                    .collect(Collectors.toList());
        } else {
            articles = this.articleService.findAllArticles(searchServiceModel)
                    .stream()
                    .map(q -> this.modelMapper.map(q, ArticleServiceModel.class))
                    .collect(Collectors.toList());
        }


        modelAndView.addObject("articles", articles);
        modelAndView.setViewName("fanShop/all-articles");
        return modelAndView;
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView detailsAnnouncement(@PathVariable String id, ModelAndView modelAndView) {
        modelAndView.addObject("article", this.modelMapper.map(this.articleService.findArticleById(id), ArticleServiceModel.class));
        modelAndView.setViewName("fanShop/details-article");
        return modelAndView;
    }
}
