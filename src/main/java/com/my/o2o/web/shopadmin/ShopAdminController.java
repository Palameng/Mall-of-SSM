package com.my.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="shopadmin", method=RequestMethod.GET)
public class ShopAdminController {
    
    @RequestMapping(value="/shop-operation")
    public String shopOperation(){
        //这里这么配置是因为在spring-web.xml中设置了视图解析器
        return "shop/shop-operation";
    }
    
    @RequestMapping(value="/shop-list")
    public String shopList(){
        //这里这么配置是因为在spring-web.xml中设置了视图解析器
        return "shop/shop-list";
    }
    
    @RequestMapping(value="/shopmanagement")
    public String shopManagement(){
        //这里这么配置是因为在spring-web.xml中设置了视图解析器
        return "shop/shopmanagement";
    }
    
    @RequestMapping(value="/productcategorymanagement")
    public String productCategoryManagement(){
        //这里这么配置是因为在spring-web.xml中设置了视图解析器
        return "shop/productcategorymanagement";
    }
}
