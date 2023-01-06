package com.atguigu.gmall.model.to;

import lombok.Data;

import java.util.List;

/**
 * 三级分类的to，无限套
 * */
@Data
public class CategoryTreeTo {
    private Long categoryId;

    private String categoryName;

    private List<CategoryTreeTo> categoryChild;
}
