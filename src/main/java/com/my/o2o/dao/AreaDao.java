package com.my.o2o.dao;

import java.util.List;

import com.my.o2o.entity.Area;

public interface AreaDao {
    /**
     * show the area list
     * @return areaList
     */
    List<Area> queryArea();
}
