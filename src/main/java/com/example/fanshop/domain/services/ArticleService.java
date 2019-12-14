package com.example.fanshop.domain.services;

import com.example.fanshop.domain.services.models.ArticleServiceModel;
import com.example.fanshop.domain.services.models.SearchServiceModel;

import java.util.List;

public interface ArticleService {

    ArticleServiceModel createArticle(ArticleServiceModel articleServiceModel);

    List<ArticleServiceModel> findAllArticles();

    List<ArticleServiceModel> findAllArticles(SearchServiceModel searchServiceModel);

    ArticleServiceModel findArticleById(String id);
}
