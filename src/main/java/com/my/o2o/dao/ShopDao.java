package com.my.o2o.dao;

import com.my.o2o.entity.Shop;

public interface ShopDao {
    /**
     * 新增店铺
     * @param shop
     * @return
     */
    int insertShop(Shop shop);
    
    /**
     * 更新店铺
     * @param shop
     * @return
     */
    int updateShop(Shop shop);
    
    /**
     * 通过ID查询店铺
     * @param shopId
     * @return
     */
    Shop queryByShopId(long shopId);
}
