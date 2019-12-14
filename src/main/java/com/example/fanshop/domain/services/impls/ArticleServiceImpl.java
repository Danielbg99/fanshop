package com.example.fanshop.domain.services.impls;


import com.example.fanshop.domain.entities.Article;
import com.example.fanshop.domain.services.ArticleService;
import com.example.fanshop.domain.services.models.ArticleServiceModel;
import com.example.fanshop.domain.services.models.SearchServiceModel;
import com.example.fanshop.repositories.ArticleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ModelMapper modelMapper;
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImpl(ModelMapper modelMapper, ArticleRepository articleRepository) {
        this.modelMapper = modelMapper;
        this.articleRepository = articleRepository;
    }

    @Override
    public ArticleServiceModel createArticle(ArticleServiceModel articleServiceModel) {
        Article article = this.modelMapper.map(articleServiceModel, Article.class);

        return this.modelMapper.map(this.articleRepository.saveAndFlush(article),
                ArticleServiceModel.class);
    }

    @Override
    public List<ArticleServiceModel> findAllArticles() {
        return this.articleRepository.findAll()
                .stream()
                .map(ca -> this.modelMapper.map(ca, ArticleServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleServiceModel> findAllArticles(SearchServiceModel searchServiceModel) {
        return this.articleRepository.findAll()
                .stream()
                .filter(a -> {
                    if (searchServiceModel.getName() != null) {
                        return a.getName().startsWith(searchServiceModel.getName())
                                || a.getName().endsWith(searchServiceModel.getName());
                    }
                    return true;
                })
                .map(a -> this.modelMapper.map(a, ArticleServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ArticleServiceModel findArticleById(String id) {
        return this.articleRepository.findById(id)
                .map(q -> this.modelMapper.map(q, ArticleServiceModel.class))
                .orElseThrow(() -> new ExpressionException("ARTICLE_ID_NOT_FOUND"));
    }
}
