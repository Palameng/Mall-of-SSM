package com.my.o2o.service;

import java.util.List;

import com.my.o2o.entity.ShopCategory;

public interface ShopCategoryService {
    List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
