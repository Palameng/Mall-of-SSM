package com.my.o2o.service.impl;

import java.io.InputStream;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.my.o2o.dao.ShopDao;
import com.my.o2o.dto.ShopExecution;
import com.my.o2o.entity.Shop;
import com.my.o2o.enums.ShopStateEnum;
import com.my.o2o.exceptions.ShopOperationException;
import com.my.o2o.service.ShopService;
import com.my.o2o.util.ImageUtil;
import com.my.o2o.util.PathUtil;

@Service
public class ShopServiceImpl implements ShopService{

    @Autowired
    private ShopDao shopDao;
    
    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName) {
        //空值判断
        if (shop == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        
        try{
            //给店铺信息赋初始值
            shop.setEnableStatus(0);    //审核中
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            
            //添加店铺信息
            int effectedNum = shopDao.insertShop(shop);
            
            if (effectedNum <= 0) {
                //使用ShopOperationException可以使得事务得到回滚
                throw new ShopOperationException("create fail!");
            }else {
                if (shopImgInputStream != null) {
                    try {
                        addShopImg(shop, shopImgInputStream, fileName);
                    } catch (Exception e) {
                       throw new ShopOperationException("addShopImg error:" + e.getMessage());
                    }
                    //更新图片的地址
                    effectedNum = shopDao.updateShop(shop);
                    if (effectedNum <= 0) {
                        throw new ShopOperationException("update img address error");
                    }
                }
            }
        }catch(Exception e){
            throw new ShopOperationException("addShop error:" + e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.CHECK, shop);
    }

    private void addShopImg(Shop shop, InputStream shopImgInputStream, String fileName) {
        //获取shop图片目录的相对值路径
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(shopImgInputStream, fileName, dest);
        shop.setShopImg(shopImgAddr);
    }

    @Override
    public Shop getByShopId(long shopId) {
        // TODO Auto-generated method stub
        return shopDao.queryByShopId(shopId);
    }

    @Override
    public ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String fileName)
            throws ShopOperationException {
        if(shop == null || shop.getShopId() == null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }else {
            
            try {
                // 1 判断是否需要处理图片
                if (shopImgInputStream != null && fileName != null && !"".equals(fileName)) {
                    Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                    if (tempShop.getShopImg() != null) {
                        ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                    }
                    addShopImg(shop, shopImgInputStream, fileName);
                }
                
                //2 更新店铺信息
                shop.setLastEditTime(new Date());
                int effectedNum = shopDao.updateShop(shop);
                if (effectedNum <= 0) {
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                }else {
                    shop = shopDao.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS, shop);
                }              
            } catch (Exception e) {
                // TODO: handle exception
                throw new ShopOperationException("modify error:" + e.getMessage());
            }
        }
    }

}
