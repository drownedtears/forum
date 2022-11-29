package ru.doketov.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.doketov.forum.model.Article;
import ru.doketov.forum.model.User;
import ru.doketov.forum.service.ArticleService;
import ru.doketov.forum.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@ResponseBody
public class UserController {
    private final UserService userService;
    private final ArticleService articleService;


    @Autowired
    public UserController(UserService userService, ArticleService articleService) {
        this.userService = userService;
        this.articleService = articleService;
    }

    @GetMapping("/user")
    public String mainPage(Principal principal, Model model) {
        model.addAttribute("curUser", userService.getUserByUsername(principal.getName()));
        return "user-main";
    }

    @GetMapping("/user/main")
    public String getForumPage(Model model, Principal principal) {

        setModelPage(model, principal);

        List<Article> list = articleService.getAllArticles();
        model.addAttribute("allArticles", list);
        model.addAttribute("curUser", userService.getUserByUsername(principal.getName()));
        return "main-forum-user";
    }

    @PostMapping("/user/main")
    public String addRating(@ModelAttribute Article article, Model model,
                            Principal principal) {

        setModelPage(model, principal);

        Article articleFromDb = articleService.getArticleById(article.getId());
        articleFromDb.setRating(article.getRating());
        articleService.save(articleFromDb);

        List<Article> list = articleService.getAllArticles();
        model.addAttribute("allArticles", list);
        model.addAttribute("curUser", userService.getUserByUsername(principal.getName()));

        return "main-forum-user";
    }

    @GetMapping("/user/add")
    public String addArticlePage(Model model, Principal principal) {
        Article article = new Article();
        model.addAttribute("newArticle", article);
        model.addAttribute("curUser", userService.getUserByUsername(principal.getName()));
        return "add-article";
    }

    @PostMapping("/user/add")
    public String addArticleConfirm(@ModelAttribute Article article,
                                    Principal principal, Model model) {
        setModelPage(model, principal);
        User curUser = userService.getUserByUsername(principal.getName());
        article.setUser(curUser);
        article.setRating(0);

        String timePattern = "dd:MM:YYYY";
        ZonedDateTime curTime = ZonedDateTime.now(ZoneId.of( "Europe/Moscow"));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(timePattern);
        article.setCre_date(curTime.format(dateTimeFormatter));

        articleService.save(article);

        List<Article> list = articleService.getAllArticles();
        model.addAttribute("allArticles", list);
        model.addAttribute("curUser", curUser);

        return "main-forum-user";
    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

    private void setModelPage(Model model, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        if (user.getRoles().size() > 1) {
            model.addAttribute("page", "admin");
        } else {
            model.addAttribute("page", "user");
        }
    }
}
