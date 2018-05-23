package com.my.o2o.service;

import java.io.InputStream;
import com.my.o2o.dto.ShopExecution;
import com.my.o2o.entity.Shop;
import com.my.o2o.exceptions.ShopOperationException;

public interface ShopService {
    ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException;
}
