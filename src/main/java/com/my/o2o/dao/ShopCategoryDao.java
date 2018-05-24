package com.my.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.my.o2o.entity.ShopCategory;

public interface ShopCategoryDao {
    List<ShopCategory> queryShopCategory(
            @Param("shopCategoryCondition") 
            ShopCategory shopCategoryCondition
            );
}
