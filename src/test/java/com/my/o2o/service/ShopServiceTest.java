package com.my.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.my.o2o.BaseTest;
import com.my.o2o.dto.ShopExecution;
import com.my.o2o.entity.Area;
import com.my.o2o.entity.PersonInfo;
import com.my.o2o.entity.Shop;
import com.my.o2o.entity.ShopCategory;
import com.my.o2o.enums.ShopStateEnum;

public class ShopServiceTest extends BaseTest{
    @Autowired
    private ShopService shopService;
    
    @Test
    public void testAddShop() throws FileNotFoundException{
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        String forTest = "test3";
        
        owner.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("店铺3");
        shop.setShopDesc(forTest);
        shop.setShopAddr(forTest);
        shop.setPhone(forTest);
        //shop.setShopImg(forTest);
        shop.setCreateTime(new Date());
        //shop.setLastEditTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");
        File shopImg = new File("C:/Users/Administrator/Desktop/桌面壁纸/timg.jpg");
        InputStream inputStream_shopImg = new FileInputStream(shopImg);
        ShopExecution shopExecution = shopService.addShop(shop, inputStream_shopImg, shopImg.getName());
        
        assertEquals(ShopStateEnum.CHECK.getState(), shopExecution.getState());
    }
}
