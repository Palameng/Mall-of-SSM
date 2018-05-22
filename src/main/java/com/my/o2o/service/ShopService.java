package com.my.o2o.service;

import java.io.File;

import com.my.o2o.dto.ShopExecution;
import com.my.o2o.entity.Shop;

public interface ShopService {
    ShopExecution addShop(Shop shop, File shopImg);
}
