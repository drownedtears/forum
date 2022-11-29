package ru.doketov.forum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.doketov.forum.dao.ArticleRepository;
import ru.doketov.forum.model.Article;
import ru.doketov.forum.model.FindArticle;

import java.util.List;

@Service
public class ArticleService {

    private final FindArticle findArticle;
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, FindArticle findArticle) {
        this.findArticle = findArticle;
        this.articleRepository = articleRepository;
    }

    public List<Article> getAllArticles() {
        return articleRepository.findByOrderByRatingDesc();
    }

    public void deleteArticle(Article article) {
        articleRepository.delete(article);
    }

    public Article getArticleById(Long id) {
        return articleRepository.getArticleById(id);
    }

    public List<Article> getArticlesByHeader(String header) { return articleRepository.getArticlesByHeader(header); }

    public FindArticle getFindArticle() {
        return findArticle;
    }

    public void save(Article article) {
        articleRepository.save(article);
    }
}
