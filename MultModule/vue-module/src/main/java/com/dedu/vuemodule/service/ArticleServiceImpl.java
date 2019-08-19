package com.dedu.vuemodule.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dedu.vuemodule.dao.ArticleMapper;
import com.dedu.vuemodule.model.Article;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> {

    public void test() {
        IPage<Article> pageCondition = new Page(1, 10);
        IPage<Article> pageArticle = this.getBaseMapper().selectPage(pageCondition, new QueryWrapper<Article>().eq("id", 2));
        System.out.println(pageArticle.getRecords());
    }

    public IPage getArticleByKeywordPage(long current, long size, String keyword) {
        IPage<Article> pageCondition = new Page(current, size, true);
        IPage<Article> pageResult = this.page(pageCondition, new QueryWrapper<Article>().like("name", keyword));
        return pageResult;
    }
}
