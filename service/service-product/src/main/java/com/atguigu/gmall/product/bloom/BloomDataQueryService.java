package com.atguigu.gmall.product.bloom;

import java.util.List;

/**
 * 实现传详情页名字，查询详情页布隆过滤器的id;
 * 传入订单，查询订单布隆过滤器的id
 * */
public interface BloomDataQueryService {

    List<Long> selectAllId();
}
