package ru.doketov.forum.service;

import ru.doketov.forum.model.Article;
import ru.doketov.forum.model.FindArticle;

import java.util.List;

public interface ArticleService {

    List<Article> getAllArticles();

    void deleteArticle(Article article);

    Article getArticleById(Long id);

    List<Article> getArticlesByHeader(String header);

    FindArticle getFindArticle();

    void saveArticle(Article article);
}
