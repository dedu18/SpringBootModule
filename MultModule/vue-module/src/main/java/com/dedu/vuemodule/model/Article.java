package com.dedu.vuemodule.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_article")
public class Article {
    private Long id;
    private String name;
    private String content;
    @TableField(value="share_user")
    private String shareuser;
    @TableField(value="share_url")
    private String shareurl;
    @TableField(value="password")
    private String sharecode;
}
