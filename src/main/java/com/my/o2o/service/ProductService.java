package com.my.o2o.service;

import java.util.List;

import com.my.o2o.dto.ImageHolder;
import com.my.o2o.dto.ProductExecution;
import com.my.o2o.entity.Product;
import com.my.o2o.exceptions.ProductOperationException;

public interface ProductService {
    /**
     * 添加商品信息以及图片处理
     * @param product
     * @param thumbnail
     * @param productImgList
     * @return
     * @throws ProductOperationException
     */
    ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) throws ProductOperationException;
    
    /**
     * 通过商品ID查询唯一的商品信息
     * @param productId
     * @return
     */
    Product getProductById(long productId);
    
    /**
     * 修改商品信息以及处理图片
     * @param product
     * @param thumbnail
     * @param productImg
     * @return
     */
    ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList) throws ProductOperationException;
}
