package com.my.o2o.web.shopadmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.o2o.dto.ImageHolder;
import com.my.o2o.dto.ShopExecution;
import com.my.o2o.entity.Area;
import com.my.o2o.entity.PersonInfo;
import com.my.o2o.entity.Shop;
import com.my.o2o.entity.ShopCategory;
import com.my.o2o.enums.ShopStateEnum;
import com.my.o2o.exceptions.ShopOperationException;
import com.my.o2o.service.AreaService;
import com.my.o2o.service.ShopCategoryService;
import com.my.o2o.service.ShopService;
import com.my.o2o.util.CodeUtil;
import com.my.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
    
    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;
    
    /**
     * 获取商店类型下拉列表和区域下拉列表的内容，其中商店类型下拉列表显示的是非第一级的内容，区域为所有可选区域
     * @return
     */
    @RequestMapping(value="/getshopinitinfo", method=RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopInitInfo(){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<ShopCategory> shopCategories = new ArrayList<ShopCategory>();
        List<Area> areas = new ArrayList<Area>();
        try {
            //获取所有类别的列表
            shopCategories = shopCategoryService.getShopCategoryList(new ShopCategory());
            areas = areaService.getAreaList();
            
            modelMap.put("shopCategoryList", shopCategories);
            modelMap.put("areaList", areas);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        
        return modelMap;
    }
    
    /**
     * 店铺注册功能
     * 1 首先将前端传递下来的shopStr解析成实体类
     * 2 之后接收上传图片的File
     * 3 把所有信息加入addshop函数存储到持久层中
     * @param request 传递的请求参数
     * @return
     */
    @RequestMapping(value = "/registershop", method = RequestMethod.POST)
    private Map<String, Object> registerShop(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        
        //1 接收验证码信息
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码输入错误");
            return modelMap;
        }
        
        //2 接收并转化相应的参数，包括店铺的基本文字信息
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        
        //3 接收店铺图片信息
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        if(commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "上传图片不能为空!");
            return modelMap;
        }
        
        //4 注册店铺
        if (shop != null && shopImg != null) {
            PersonInfo owner = (PersonInfo)request.getSession().getAttribute("user");
            shop.setOwner(owner);
            ShopExecution sExecution;
            try {
                ImageHolder thumbnail = new ImageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
                sExecution = shopService.addShop(shop, thumbnail);
                if (sExecution.getState() == ShopStateEnum.CHECK.getState()) {
                    modelMap.put("success", true);
                    //该用户可以操作的店铺列表
                    @SuppressWarnings("unchecked")
                    List<Shop> shopList = (List<Shop>)request.getSession().getAttribute("shopList");
                        
                    if (shopList == null || shopList.size() == 0) {
                        shopList = new ArrayList<Shop>();
                    }
                    
                    shopList.add(sExecution.getShop());
                    request.getSession().setAttribute("shopList", shopList);
                }else{
                    modelMap.put("success", false);
                    modelMap.put("errMsg", sExecution.getStateInfo());
                }
            } catch (ShopOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
            return modelMap;
        }else{
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入店铺信息!");
            return modelMap;           
        }
    }
    
//    private static void inputStreamToFile(InputStream ins, File file){
//        FileOutputStream os = null;
//        try{
//            os = new FileOutputStream(file);
//            int bytesRead = 0;
//            byte[] buffer = new byte[1024];
//            //从输入流中读取内容存到buffer里，返回读取的内容长度
//            while((bytesRead = ins.read(buffer)) != -1){
//                //把buffer按照从指定offset偏移处开始写入输出流,长度为len-offset
//                os.write(buffer, 0, bytesRead);
//            }
//        }catch(Exception e){
//            throw new RuntimeException("调用inputStreamToFile产生异常：" + e.getMessage());
//        }finally {
//            try{
//                if (os != null) {
//                    os.close();
//                }
//                if (ins != null) {
//                    ins.close();
//                }
//            } catch(IOException e) {
//                throw new RuntimeException("调用inputStreamToFile关闭io产生异常：" + e.getMessage());
//            }
//        }
//    }
    
    @RequestMapping(value="/getshopbyid", method=RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopById(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        
        if (shopId > -1) {
            try {
                Shop shop = shopService.getByShopId(shopId);
                List<Area> areaList = areaService.getAreaList();
                
                modelMap.put("shop", shop);
                modelMap.put("areaList", areaList);
                modelMap.put("success", true);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId!");          
        }
        return modelMap;
    }
    
    /**
     * 编辑店铺信息并保存
     * @param request
     * @return
     */
    @RequestMapping(value = "/modifyshop", method = RequestMethod.POST)
    @ResponseBody 
    private Map<String, Object> modifyshop(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        
        //1 接收验证码信息
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码输入错误");
            return modelMap;
        }
        
        //2 接收并转化相应的参数，包括店铺的基本文字信息
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            //将上层打包的json信息解析成shop实体类
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        
        //3 接收店铺图片信息
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        if(commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }
        
        //4 更新编辑后的店铺信息
        if (shop != null && shop.getShopId() != null) {
            ShopExecution sExecution;
            
            try {
                //执行修改动作
                if (shopImg == null) {
                    sExecution = shopService.modifyShop(shop, null);
                }else {
                    ImageHolder thumbnail = new ImageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
                    sExecution = shopService.modifyShop(shop,thumbnail);
                }

                if (sExecution.getState() == ShopStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                }else{
                    modelMap.put("success", false);
                    modelMap.put("errMsg", sExecution.getStateInfo());
                }
                
            } catch (ShopOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
            return modelMap;
        }else{
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入店铺ID!");
            return modelMap;           
        }
    }
    
    @RequestMapping(value="/getshoplist", method=RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopList(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        PersonInfo user = new PersonInfo();
        user.setUserId(1L);
        user.setName("MengYuan");
        request.getSession().setAttribute("user", user);
        user = (PersonInfo)request.getSession().getAttribute("user");
        
        try {
            Shop shopCondition = new Shop();
            shopCondition.setOwner(user);
            ShopExecution shopExecution = shopService.getShopList(shopCondition, 0, 100);
            modelMap.put("shopList", shopExecution.getShopList());
            modelMap.put("user", user);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }
    
    @RequestMapping(value="/getshopmanagementinfo", method=RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopManagementInfo(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if (shopId <= 0) {
            Object currentShopObj = request.getSession().getAttribute("currentShop");
            if (currentShopObj == null) {
                modelMap.put("redirect", true);
                modelMap.put("url", "o2o/shopadmin/shoplist");
            }else {
                Shop currentShop = (Shop)currentShopObj;
                modelMap.put("redirect", false);
                modelMap.put("shopId", currentShop.getShopId());
            }
        }else{
            Shop currentShop = new Shop();
            currentShop.setShopId(shopId);
            request.getSession().setAttribute("currentShop", currentShop);
            modelMap.put("redirect", false);
        }
        return modelMap;
    }
}
