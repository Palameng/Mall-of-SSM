package com.my.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.my.o2o.entity.ProductCategory;

public interface ProductCategoryDao {
    /**
     * 根据商店ID查询该商店里所拥有的商品类别列表
     * @param shopId
     * @return
     */
    List<ProductCategory> queryProductCategoryList(long shopId);
    
    /**
     * 批量新增商品类别
     * @param productCategoryList
     * @return
     */
    int batchInsertProductCategory(List<ProductCategory> productCategoryList);
    
    /**
     * 删除商品类别
     * @param productCategoryId
     * @param shopId
     * @return
     */
    int deleteProductCategory(@Param("productCategoryId") long productCategoryId, @Param("shopId") long shopId);
}
