package com.bizzbee.zion.web.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.bizzbee.zion.dto.ShopExecution;
import com.bizzbee.zion.entity.Area;
import com.bizzbee.zion.entity.PersonInfo;
import com.bizzbee.zion.entity.Shop;
import com.bizzbee.zion.entity.ShopCategory;
import com.bizzbee.zion.enums.ShopStateEnum;
import com.bizzbee.zion.service.AreaService;
import com.bizzbee.zion.service.ShopCategoryService;
import com.bizzbee.zion.service.ShopService;
import com.bizzbee.zion.util.HttpServletRequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
@RequestMapping("/shopadmin")
public class ShopManageController {
	@Autowired
	private ShopService shopService;
	
	@Autowired
	private ShopCategoryService shopCategoryService;

	@Autowired
	private AreaService areaService;
	
	
	/**
	 * 获取店铺列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getshoplist", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getShopList(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		
		PersonInfo user = new PersonInfo();
		user.setUserId(1L);
		user.setName("查郁");
		try {
			Shop shopCondition = new Shop();
			shopCondition.setOwner(user);
			ShopExecution shopExecution = shopService.getShopList(shopCondition, 1, 100);
			result.put("shopList", shopExecution.getShopList());
			// 列出店铺成功之后，将店铺放入session中作为权限验证依据，即该帐号只能操作它自己的店铺
			//request.getSession().setAttribute("shopList", shopExecution.getShopList());
			result.put("shopList",shopExecution.getShopList());
			result.put("user", user);
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false);
			result.put("errMsg", e.getMessage());
		}
		return result;
	}
	
	

	/**
	 * 店铺信息初始化：店铺区域和店铺类别
	 * 
	 * @return
	 */
	@RequestMapping(value = "initinfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getShopInitInfo() {
		Map<String, Object> result = new HashMap<>();
		List<Area> areaList = new ArrayList<>();
		List<ShopCategory> shopCategoryList = new ArrayList<>();
		try {
			shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
			areaList = areaService.getAreaList();
			result.put("shopCategoryList", shopCategoryList);
			result.put("areaList", areaList);
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false);
			result.put("errorMsg", e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value="/regshop",method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> registerShop(HttpServletRequest request){
		Map<String, Object> result = new HashMap<String, Object>();
		//1.接收參數-->转化成shop对象
		String shopString = HttpServletRequestUtil.getString(request,"shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		
		try {
			shop = mapper.readValue(shopString,Shop.class);
		} catch (IOException e) {
			//参数接收问题
			result.put("success",false);
			result.put("errMsg",e.getMessage());
			return result;
		}
		//2.获取上传的文件
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		//如果文件流存在
		if(commonsMultipartResolver.isMultipart(request)){
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
			shopImg = (CommonsMultipartFile)multipartHttpServletRequest.getFile("shopImg");
		}else{
			//上传的文件流不存在
			result.put("success",false);
			result.put("errMsg","上传的图片为空");
			return result;
		}
		
		//3.开始注册店铺
		if (shop!=null&&shopImg!=null) {
			//补全shop必要信息
			PersonInfo owner = new PersonInfo();
			owner.setUserId(1L);
			shop.setOwner(owner);
			ShopExecution seExecution = shopService.addShop(shop, shopImg);
			//注册成功
			if(seExecution.getState()==ShopStateEnum.CHECK.getState()){
				result.put("success",true);
			}else{
				result.put("success",false);
				result.put("errMsg",seExecution.getStateInfo());
			}
			return result;
		}else{
			//没有上传店铺信息
			result.put("success",false);
			result.put("errMsg","没有店铺信息传入");
			return result;
		}
		
	}
	
	
	/**
	 * 根据id获取店铺信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getshopbyid", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getShopById(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		// 获取shopid
		long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		if (shopId > -1) {
			try {
				Shop shop = shopService.getByShopId(shopId);
				modelMap.put("shop", shop);
				// 获取区域列表
				List<Area> areaList = areaService.getAreaList();
				modelMap.put("areaList", areaList);
				modelMap.put("success", true);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", ShopStateEnum.NULL_SHOPID.getStateInfo());
		}
		return modelMap;
	}
	
	/**
	 * 修改店铺
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/modifyshop", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> modifyShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		
		// 1、接收并转化相应参数，包括店铺信息及图片信息
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper(); // create once, reuse（创建一次，可重用）
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}

		// 获取图片文件流
		MultipartHttpServletRequest multipartRequest = null;
		MultipartFile shopImg = null;
		MultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			multipartRequest = (MultipartHttpServletRequest) request;
			shopImg = (MultipartFile) multipartRequest.getFile("shopImg");
		}

		// 2、修改店铺，尽量不要依靠前端信息
		if (shop != null && shop.getShopId() > 0) {
			ShopExecution se = shopService.modifyShop(shop, shopImg);
			if (se.getState() == ShopStateEnum.CHECK.getState()) {
				modelMap.put("success", true);
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", se.getStateInfo());
			}
			return modelMap;
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", ShopStateEnum.NULL_SHOPID.getStateInfo());
			return modelMap;
	}
}

	
	
	
	
	
	
	
	
	
	
	
}
