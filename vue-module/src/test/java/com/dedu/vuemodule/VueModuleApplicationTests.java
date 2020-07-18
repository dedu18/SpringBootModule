package com.dedu.vuemodule;

import com.dedu.vuemodule.service.ArticleServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VueModuleApplicationTests {

    @Autowired
    ArticleServiceImpl articleService;

    @Test
    public void contextLoads() {
        articleService.test();
    }

}
