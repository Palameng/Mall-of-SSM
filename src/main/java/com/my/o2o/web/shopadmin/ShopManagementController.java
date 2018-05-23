package com.my.o2o.web.shopadmin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.o2o.dto.ShopExecution;
import com.my.o2o.entity.PersonInfo;
import com.my.o2o.entity.Shop;
import com.my.o2o.enums.ShopStateEnum;
import com.my.o2o.exceptions.ShopOperationException;
import com.my.o2o.service.ShopService;
import com.my.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
    
    @Autowired
    private ShopService shopService;
    
    @RequestMapping(value = "/registershop", method = RequestMethod.POST)
    private Map<String, Object> registerShop(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        
        //1 接收并转化相应的参数，包括店铺信息以及图片信息
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
        
        //1.1 接收图片信息
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
        
        //2 注册店铺
        if (shop != null && shopImg != null) {
            PersonInfo owner = new PersonInfo();
            //此处强制先指定店铺的拥有者都是id=1的
            owner.setUserId(1L);
            //TODO User Session
            
            shop.setOwner(owner);
//            File shopImgFile = new File(PathUtil.getImgBasePath() + ImageUtil.getRandomFileName());
//            try {
//                shopImgFile.createNewFile();
//            } catch (IOException e) {
//                modelMap.put("success", false);
//                modelMap.put("errMsg", e.getMessage());
//                return modelMap;
//            }
//            try {
//                inputStreamToFile(shopImg.getInputStream(), shopImgFile);
//            } catch (IOException e) {
//                modelMap.put("success", false);
//                modelMap.put("errMsg", e.getMessage());
//                return modelMap;
//            }
            ShopExecution sExecution;
            try {
                sExecution = shopService.addShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
                if (sExecution.getState() == ShopStateEnum.CHECK.getState()) {
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
}