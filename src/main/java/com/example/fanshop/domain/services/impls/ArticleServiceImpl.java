package com.example.fanshop.domain.services.impls;


import com.example.fanshop.domain.entities.Article;
import com.example.fanshop.domain.services.ArticleService;
import com.example.fanshop.domain.services.models.ArticleServiceModel;
import com.example.fanshop.repositories.ArticleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Article article = this.modelMapper.map(articleServiceModel , Article.class);

        return this.modelMapper.map(this.articleRepository.saveAndFlush(article),
                ArticleServiceModel.class);
    }
}
