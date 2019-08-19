package com.dedu.vuemodule.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dedu.vuemodule.model.Article;
import com.dedu.vuemodule.service.ArticleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@CrossOrigin
public class HelloController {

    @Autowired
    ArticleServiceImpl articleService;

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/article")
    public IPage<Article> getArticleByKeywordPage(@RequestParam long pageNum, @RequestParam long pageSize, @RequestParam String keyword) {
        return articleService.getArticleByKeywordPage(pageNum, pageSize, keyword);
    }

}
