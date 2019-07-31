package com.bizzbee.zion.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bizzbee.zion.entity.Area;


public interface AreaService {

    List<Area> getAreaList();
}