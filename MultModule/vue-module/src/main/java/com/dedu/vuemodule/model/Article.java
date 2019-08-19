package com.dedu.vuemodule.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_article")
public class Article {
    private Long id;
    private String name;
    private String content;
}
