package com.my.o2o.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.imageio.ImageIO;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.slf4j.Logger;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageUtil {
    // 获取当前图片的绝对路径
    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    //设置时间的格式
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    //创建一个随机数
    private static final Random r = new Random();
    //创建一个日志对象
    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);
    
    /**
     * 将CommonsMultipartFile转换成File
     * @param cFile
     * @return
     */
    public static File transferCommonsMultipartFileToFile(CommonsMultipartFile cFile){
        File newFile = new File(cFile.getOriginalFilename());
        try{
            cFile.transferTo(newFile);
        } catch(IllegalStateException e){
            logger.error(e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        
        return newFile;
    }
    
    /**
     * 处理缩略图，并返回新生成图片的相对值路径
     * @param thumbnail
     * @param targetAddr
     * @return
     */
    public static String generateThumbnail(InputStream thumbnailInputStream, String fileName, String targetAddr){
        //获取一个随机文件名用于压缩生成的新图片
        String realFileName = getRandomFileName();
        
        //获取到目标图片的扩展名以准备给新文件名使用
        String extension = getFileExtension(fileName);
        
        //为指定目标地址中未生成的文件夹生成文件夹
        makeDirPath(targetAddr);
        
        //新图片的相对路径，目标地址+文件名+格式
        String relativeAddr = targetAddr + realFileName + extension;
        logger.debug("current relativeAddr is:" + relativeAddr);
        
        //输出图片文件流，加上basepath变为绝对路径
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        logger.debug("current complete addr is:" + PathUtil.getImgBasePath() + relativeAddr);
        
        //压缩目标图片，加上水印，并输出新图片到指定位置
        try{
            Thumbnails.of(thumbnailInputStream)
            .size(200, 200)
            .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.jpg")), 0.25f)
            .outputQuality(0.8f)
            .toFile(dest);
        } catch(IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        
        return relativeAddr;
    }
    
    /**
     * 创建目标路径所涉及到的目录，即/home/my/xxx.jpg,那么这些文件夹都得创建出来
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        
        if(!dirPath.exists()){
            dirPath.mkdirs();
        }
        
    }

    /**
     * 获取输入文件流的扩展名
     * @param thumbnail
     * @return
     */
    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成随机文件名
     * @return
     */
    public static String getRandomFileName() {
        // 获取随机的五位数
        int rannum = r.nextInt(89999) + 10000;
        String nowTimeStr = sDateFormat.format(new Date());
        return nowTimeStr + rannum;
    }

    public static void main(String[] args) throws IOException{
        //String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        /**
         * of里的参数为获取目标处理图片的路径，然后压缩成多少乘多少的格式，之后加水印，水印接收的参数为 水印位置 水印图片存放的路径 
         * 透明度，之后的outputQuality为输出图片的质量，0到1,1最高，最后是tofile保存新图片的路径
         */
        Thumbnails.of(new File("C:\\Users\\Administrator\\Desktop\\桌面壁纸\\timg.jpg"))
        .size(200, 200)
        .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.jpg")), 0.25f)
        .outputQuality(0.8f)
        .toFile("C:\\Users\\Administrator\\Desktop\\桌面壁纸\\timgnew.jpg");
    }
}
