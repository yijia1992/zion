package com.bizzbee.zion.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizzbee.zion.dao.AreaDao;
import com.bizzbee.zion.entity.Area;
import com.bizzbee.zion.service.AreaService;
@Service
public class AreaServiceImpl implements AreaService{
	@Autowired
	private AreaDao areaDao;

	@Override
	public List<Area> getAreaList() {
		
		return areaDao.queryArea();
	}

}
