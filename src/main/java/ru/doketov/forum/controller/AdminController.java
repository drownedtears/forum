package ru.doketov.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.doketov.forum.model.entity.Article;
import ru.doketov.forum.model.dto.FindArticle;
import ru.doketov.forum.model.dto.FindUser;
import ru.doketov.forum.model.entity.User;
import ru.doketov.forum.service.article.ArticleServiceImpl;
import ru.doketov.forum.service.user.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final ArticleServiceImpl articleService;

    @Autowired
    public AdminController(UserService userService, ArticleServiceImpl articleService) {
        this.userService = userService;
        this.articleService = articleService;
    }

    @GetMapping()
    public String mainPage(Principal principal, Model model) {
        model.addAttribute("curUser", userService.getUserByUsername(principal.getName()));
        return "admin-main";
    }

    @GetMapping("/users")
    public String userList(Model model, Principal principal) {
        model.addAttribute("allUsers", userService.getAllUsers());
        model.addAttribute("findUser", userService.getFindUser());
        model.addAttribute("curUser", userService.getUserByUsername(principal.getName()));
        return "all-users-admin";
    }

    @PostMapping("/users")
    public String deleteUser(@ModelAttribute User user,
                              Model model, Principal principal) {
        model.addAttribute("userForDeletion", user);
        model.addAttribute("curUser", userService.getUserByUsername(principal.getName()));

        return "delete-user-confirm";
    }

    @PostMapping("/users/delete")
    public String deleteUserConfirm(@ModelAttribute User user) {
        userService.banOrUnbanUser(user);
        return "redirect:/admin/users";
    }

    @PostMapping("/users/find")
    public String getUserByUsername(@ModelAttribute FindUser findUser,
                                     Model model, Principal principal) {
        User user = userService.getUserByUsername(findUser.getUsername());
        model.addAttribute("curUser", userService.getUserByUsername(principal.getName()));
        model.addAttribute("findUser", userService.getFindUser());

        if (Objects.isNull(user)) {
            return "one-user-not-found";
        } else {
            model.addAttribute("oneUser", user);
            return "one-user";
        }
    }

    @GetMapping("/users/{userId}")
    public String  getUser(@PathVariable("userId") Long userId,
                           Model model, Principal principal) {
        User user = userService.getUserById(userId);
        model.addAttribute("curUser", userService.getUserByUsername(principal.getName()));
        model.addAttribute("findUser", userService.getFindUser());

        if (Objects.isNull(user)) {
            return "one-user-not-found";
        } else {
            model.addAttribute("oneUser", user);
            return "one-user";
        }
    }

    @GetMapping("/articles")
    public String articleList(Model model, Principal principal) {
        model.addAttribute("allArticles", articleService.getAllArticles());
        model.addAttribute("findArticle", articleService.getFindArticle());
        model.addAttribute("curUser", userService.getUserByUsername(principal.getName()));
        return "all-articles-admin";
    }

    @PostMapping("/articles")
    public String deleteArticle(@ModelAttribute Article article,
                                Model model, Principal principal) {
        model.addAttribute("articleForDeletion", article);
        model.addAttribute("curUser", userService.getUserByUsername(principal.getName()));

        return "delete-article-confirm";
    }

    @PostMapping("/articles/delete")
    public String deleteArticleConfirm(@ModelAttribute Article article) {
        articleService.deleteArticle(article);

        return "redirect:/admin/articles";
    }

    @PostMapping("/articles/find")
    public String getArticleByHeader(@ModelAttribute FindArticle findArticle,
                                     Model model, Principal principal) {
        List<Article> list = articleService.getArticlesByHeader(findArticle.getHeader());
        model.addAttribute("curUser", userService.getUserByUsername(principal.getName()));
        model.addAttribute("findArticle", articleService.getFindArticle());

        if (list.isEmpty()) {
            return "article-not-found";
        } else {
            model.addAttribute("filteredArticles", list);
            return "filtered-articles";
        }
    }

    @GetMapping("/articles/{articleId}")
    public String getArticleById(@PathVariable("articleId") Long id,
                                 Model model, Principal principal) {
        Article article = articleService.getArticleById(id);
        model.addAttribute("curUser", userService.getUserByUsername(principal.getName()));
        model.addAttribute("findArticle", articleService.getFindArticle());

        if (Objects.isNull(article)) {
            return "article-not-found";
        } else {
            List<Article> listOfOne = new ArrayList<>();
            listOfOne.add(article);
            model.addAttribute("filteredArticles", listOfOne);
            return "filtered-articles";
        }
    }

}
