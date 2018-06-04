package com.my.o2o.dao;

import java.util.List;

import com.my.o2o.entity.ProductCategory;

public interface ProductCategoryDao {
    /**
     * 根据商店ID查询该商店里所拥有的商品类别列表
     * @param shopId
     * @return
     */
    List<ProductCategory> queryProductCategoryList(long shopId);
}
