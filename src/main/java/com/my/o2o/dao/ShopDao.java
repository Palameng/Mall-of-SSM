package com.my.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
    
    /**
     * 分页查询店铺
     * @param shopCondition
     * @param rowIndex 从第几行查询数据
     * @param pageSize 要返回多少行数据
     * @return
     */
    List<Shop> queryShopList(
            @Param("shopCondition") Shop shopCondition, 
            @Param("rowIndex") int rowIndex, 
            @Param("pageSize") int pageSize
            );
    
    /**
     * 返回queryShopList总数
     * @param shopCondition
     * @return
     */
    int queryShopCount(@Param("shopCondition") Shop shopCondition);
}
