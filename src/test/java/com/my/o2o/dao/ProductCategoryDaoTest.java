package com.my.o2o.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
//import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.my.o2o.BaseTest;
import com.my.o2o.entity.ProductCategory;
import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductCategoryDaoTest extends BaseTest{
    @Autowired
    private ProductCategoryDao productCategoryDao;
    
    /**
     * 搜索商品类别列表
     * @throws Exception
     */
    @Test
    public void testA_QueryByShopId() throws Exception {
        long shopId = 1L;
        List<ProductCategory> productCategories = productCategoryDao.queryProductCategoryList(shopId);
        System.out.println("该店铺自定义类别数为:" + productCategories.size());
    }
    
    /**
     * 批量添加商品列表
     */
    @Test
    public void testB_BatchInsertProductCategory(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryName("烘焙类");
        productCategory.setPriority(1);
        productCategory.setCreateTime(new Date());
        productCategory.setShopId(1L);
        
        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setProductCategoryName("面食类");
        productCategory2.setPriority(2);
        productCategory2.setCreateTime(new Date());
        productCategory2.setShopId(1L);
        
        List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
        productCategoryList.add(productCategory);
        productCategoryList.add(productCategory2);
        
        int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
        assertEquals(2, effectedNum);
    }
    
    /**
     * UT回环，即上一个方法测试了批量增加，这里做删除操作
     * @throws Exception
     */
    @Test
    public void testC_DeleteProductCategory() throws Exception{
        long shopId = 1;
        List<ProductCategory> productCategories = productCategoryDao.queryProductCategoryList(shopId);
        
        for(ProductCategory pc : productCategories){
            if ("烘焙类".equals(pc.getProductCategoryName()) || "面食类".equals(pc.getProductCategoryName())) {
                int effectedNum = productCategoryDao.deleteProductCategory(pc.getProductCategoryId(), shopId);
                assertEquals(1, effectedNum);
            }
        }
    }
}
