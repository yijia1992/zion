package com.bizzbee.zion.web.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;




import com.bizzbee.zion.entity.Area;
import com.bizzbee.zion.service.AreaService;


@Controller
@RequestMapping("/admin")
public class AreaController {
	//sl4j的log
	Logger logger= LoggerFactory.getLogger(AreaController.class);
	@Autowired
	private AreaService areaService;
	
	@RequestMapping(value="/arealist",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> listArea(){
		logger.info("===開始===");
		Map<String,Object> result =  new HashMap<String,Object>();
		List<Area> list = new ArrayList<>();
		list = areaService.getAreaList();
		result.put("areaList",list);
		result.put("total",list.size());
		logger.error("error......biubiubiu");
		logger.info("===結束==");
		return result;
		
	}
	
}
