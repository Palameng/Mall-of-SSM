package com.my.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.my.o2o.BaseTest;
import com.my.o2o.entity.ShopCategory;

public class ShopCategoryDaoTest extends BaseTest{
    @Autowired
    private ShopCategoryDao shopCategoryDao;
    
    @Test
    @Ignore
    public void testQueryShopCategory(){
        List<ShopCategory> shopCategories = shopCategoryDao.queryShopCategory(new ShopCategory());
        assertEquals(2, shopCategories.size());
        
        ShopCategory testCategory = new ShopCategory();
        ShopCategory parentCategory = new ShopCategory();
        parentCategory.setShopCategoryId(1L);
        testCategory.setParent(parentCategory);
        shopCategories = shopCategoryDao.queryShopCategory(testCategory);
        //assertEquals(1, shopCategories.size());
        System.out.println("------------------------------------------------------------");
        for (int i = 0; i < shopCategories.size(); i++) {
            System.out.println(shopCategories.get(i).getShopCategoryName());   
        }
    }
    
    @Test
    public void testQueryShopCategory2(){
        List<ShopCategory> shopCategories = shopCategoryDao.queryShopCategory(null);
        System.out.println(shopCategories.size());
    }
}
