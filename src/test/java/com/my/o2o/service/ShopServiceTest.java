package com.my.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.my.o2o.BaseTest;
import com.my.o2o.dto.ImageHolder;
import com.my.o2o.dto.ShopExecution;
import com.my.o2o.entity.Area;
import com.my.o2o.entity.PersonInfo;
import com.my.o2o.entity.Shop;
import com.my.o2o.entity.ShopCategory;
import com.my.o2o.enums.ShopStateEnum;
import com.my.o2o.exceptions.ShopOperationException;

public class ShopServiceTest extends BaseTest{
    @Autowired
    private ShopService shopService;
    
    @Test
    @Ignore
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
        ImageHolder imageHolder = new ImageHolder(shopImg.getName(), inputStream_shopImg);
        ShopExecution shopExecution = shopService.addShop(shop, imageHolder);
        
        assertEquals(ShopStateEnum.CHECK.getState(), shopExecution.getState());
    }
    
    @Test
    @Ignore
    public void testModifyShop() throws ShopOperationException, FileNotFoundException{
        Shop shop = new Shop();
        shop.setShopId(1L);
        shop.setShopName("修改了修改了");
        File shopImg = new File("C:/Users/Administrator/Desktop/桌面壁纸/dog.jpg");
        InputStream is = new FileInputStream(shopImg);
        ImageHolder thumbnail = new ImageHolder("dabai.jpg", is);
        ShopExecution shopExecution = shopService.modifyShop(shop,thumbnail);
        System.out.println("新的图片地址为：" + shopExecution.getShop().getShopImg());
    }
    
    @Test
    public void testGetShopList(){
        Shop shopCondition = new Shop();
        ShopCategory sc = new ShopCategory();
        ShopCategory parent = new ShopCategory();
        parent.setShopCategoryId(1L);
        sc.setParent(parent);
        shopCondition.setShopCategory(sc);
        ShopExecution shopExecution = shopService.getShopList(shopCondition, 0, 3);
        
        System.out.println("店铺列表数为: " + shopExecution.getShopList().size());
        System.out.println("店铺列表数为: " + shopExecution.getCount());
    }
}


