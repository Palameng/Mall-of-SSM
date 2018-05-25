package com.my.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.my.o2o.BaseTest;
import com.my.o2o.entity.Area;
import com.my.o2o.entity.PersonInfo;
import com.my.o2o.entity.Shop;
import com.my.o2o.entity.ShopCategory;
import com.my.o2o.enums.ShopStateEnum;

public class ShopDaoTest extends BaseTest{
    @Autowired
    private ShopDao shopDao;
    
    @Test
    @Ignore
    public void testInsertShop(){
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        String forTest = "test1";
        
        owner.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("店铺1");
        shop.setShopDesc(forTest);
        shop.setShopAddr(forTest);
        shop.setPhone(forTest);
        //shop.setShopImg(forTest);
        shop.setCreateTime(new Date());
        //shop.setLastEditTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");
        
        int effectedNum = shopDao.insertShop(shop);
        assertEquals(1, effectedNum);
    }
    
    @Test
    @Ignore
    public void testUpdateShop(){
        Shop shop = new Shop();
//        String forTest = "test";
        String updateTest = "update";
        shop.setShopId(1L);
        
        shop.setShopDesc(updateTest);
        shop.setShopAddr(updateTest);
        shop.setPhone(updateTest);
//        shop.setShopImg(updateTest);
//        shop.setCreateTime(new Date());
        shop.setLastEditTime(new Date());
//        shop.setEnableStatus(1);
//        shop.setAdvice("审核中");
        
        int effectedNum = shopDao.updateShop(shop);
        assertEquals(1, effectedNum);        
    }
    
    @Test
    public void testQueryByShopId(){
        long shopId = 1L;
        Shop shop = shopDao.queryByShopId(shopId);
        System.out.println("areaId:" + shop.getArea().getAreaId());
        System.out.println("areaName:" + shop.getArea().getAreaName());
        System.out.println("shopCategoryId:" + shop.getShopCategory().getShopCategoryId());
        System.out.println("areaName:" + shop.getShopCategory().getShopCategoryName());       
    }
}
