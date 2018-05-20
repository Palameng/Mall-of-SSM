package com.my.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.my.o2o.BaseTest;
import com.my.o2o.entity.Area;

public class AreaDaoTest extends BaseTest {
    //Spring 2.5 引入了 @Autowired 注释，它可以对类成员变量、方法及构造函数进行标注，完成自动装配的工作。 
    //通过 @Autowired的使用来消除 set ，get方法。
    @Autowired
    private AreaDao areaDao;
    
    @Test
    public void testQueryArea(){
        List<Area> areaList = areaDao.queryArea();
        assertEquals(2, areaList.size());
    }
}
