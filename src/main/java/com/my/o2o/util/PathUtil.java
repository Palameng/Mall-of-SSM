package com.my.o2o.util;

public class PathUtil {
    
    private static String seperator = System.getProperty("file.seperator");
    
    public static String getImgBasePath(){
        String oString = System.getProperty("os.name");
        String basePath = "";
        if(oString.toLowerCase().startsWith("win")){
            basePath = "C:/Users/Administrator/Desktop/桌面壁纸";
        } else {
            basePath = "/home/my/image";
        }
        basePath = basePath.replace("/", seperator);
        return basePath;
    }
    
    public static  String getShopImagePath(long shopId){
        String imagePath = "/upload/item/shop/" + shopId + "/";
        return imagePath.replace("/", seperator);
    }
}
