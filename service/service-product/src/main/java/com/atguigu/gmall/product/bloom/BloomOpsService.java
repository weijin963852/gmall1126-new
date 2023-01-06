package com.atguigu.gmall.product.bloom;

public interface BloomOpsService {
    void rebuildBloomFilter(String bloomFilterName, BloomDataQueryService bloomDataQueryService);
}
