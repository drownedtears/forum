package ru.doketov.forum.service.article;

import ru.doketov.forum.model.entity.Article;
import ru.doketov.forum.model.dto.FindArticle;

import java.util.List;

public interface ArticleService {

    List<Article> getAllArticles();

    void deleteArticle(Article article);

    Article getArticleById(Long id);

    List<Article> getArticlesByHeader(String header);

    FindArticle getFindArticle();

    void saveArticle(Article article);
}
