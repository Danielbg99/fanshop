package com.example.fanshop.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "fan_shop")
public class FanShop extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "fan_shop_id", referencedColumnName = "id")
    private Article article;
    @ManyToOne
    @JoinColumn(columnDefinition = "fan_shop_id", referencedColumnName = "id")
    private User selledBy;
    @Column(name = "expiration_date", nullable = false)
    private String expirationDate;

    public FanShop() {
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public User getSelledBy() {
        return selledBy;
    }

    public void setSelledBy(User selledBy) {
        this.selledBy = selledBy;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}
