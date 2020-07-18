package com.dedu.vuemodule.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dedu.vuemodule.model.Article;
import com.dedu.vuemodule.service.ArticleServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
//@CrossOrigin
public class ArticleController {

    @Autowired
    ArticleServiceImpl articleService;

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @ApiOperation(value = "根据分页查询数据-dedu",notes = "分页查询Article")
    @GetMapping("/article")
    public IPage<Article> getArticleByKeywordPage(@RequestParam long pageNum, @RequestParam long pageSize, @RequestParam String keyword) {
        return articleService.getArticleByKeywordPage(pageNum, pageSize, keyword);
    }

    @ApiOperation(value = "根据Id查询数据-dedu",notes = "查询Article")
    @GetMapping("/article/{id}")
    public Article getArticleById(@NotNull @PathVariable Long id) {
        return articleService.getById(id);
    }

}
