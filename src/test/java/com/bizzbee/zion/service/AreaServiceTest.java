package com.bizzbee.zion.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bizzbee.zion.BaseTest;
import com.bizzbee.zion.entity.Area;

public class AreaServiceTest extends BaseTest{
	
	@Autowired
	private AreaService areaService;
	
	@Test
	public void testGetAreaList(){
		List<Area> areaList =areaService.getAreaList(); 
		System.out.println(areaList);
	}
	
}
