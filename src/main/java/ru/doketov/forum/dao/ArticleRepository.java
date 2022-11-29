package ru.doketov.forum.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.doketov.forum.model.Article;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Article getArticleById(Long id);

    List<Article> findByOrderByRatingDesc();

    List<Article> getArticlesByHeader(String header);
}
