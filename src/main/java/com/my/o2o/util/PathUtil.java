package com.my.o2o.util;

public class PathUtil {
    
    private static String separator = System.getProperty("file.separator");
    
    /**
     * 获取图片上传后保存的基本地址
     * @return
     */
    public static String getImgBasePath(){
        String oString = System.getProperty("os.name");
        String basePath = "";
        if(oString.toLowerCase().startsWith("win")){
            basePath = "C:/Users/Administrator/Desktop/imageOfSSM/save";
        } else {
            basePath = "/home/my/image";
        }
        //因为windows下的路径分隔符为\，所以需要进行转换
        basePath = basePath.replace("/", separator);
        return basePath;
    }
    
    public static  String getShopImagePath(long shopId){
        //windows的还是要加这个/
        String imagePath = "/upload/item/shop/" + shopId + "/";
        return imagePath.replace("/", separator);
    }
}
