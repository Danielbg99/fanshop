package com.example.fanshop.domain.services.models;

public class FanShopServiceModel {

    private ArticleServiceModel article;
    private UserServiceModel selledBy;
    private String expirationDate;

    public FanShopServiceModel() {
    }

    public ArticleServiceModel getArticle() {
        return article;
    }

    public void setArticle(ArticleServiceModel article) {
        this.article = article;
    }

    public UserServiceModel getSelledBy() {
        return selledBy;
    }

    public void setSelledBy(UserServiceModel selledBy) {
        this.selledBy = selledBy;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}
