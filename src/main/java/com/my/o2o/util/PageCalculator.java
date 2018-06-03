package com.my.o2o.util;

public class PageCalculator {
    /**
     * 因为在DAO层中，封装的是针对数据库的行操作，而这里承接的是前端的页操作，需要把页转成行
     * @param pageIndex 第几页
     * @param pageSize 每页大小
     * @return 从第几行开始显示
     */
    public static int calculateRowIndex(int pageIndex, int pageSize){
        return(pageIndex>0) ? (pageIndex-1)*pageSize : 0;
    }
}
