package com.bizzbee.zion.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping(value="admin",method=RequestMethod.GET)
public class ShopAdminController {
	@RequestMapping(value="/shopoperation")
	public String shopOperation(){
		return "shop/shopoperation";
	}
	
	@RequestMapping(value="/shoplist")
	public String shopList(){
		return "shop/shoplist";
	}
	
	/**
	 * 店铺管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/shopmanagement", method = RequestMethod.GET)
	public String shopManagement() {
		return "shop/shopmanagement";
	}
	
	
	
	/**
	 * 商品类别管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/productcategorymanagement", method = RequestMethod.GET)
	public String productCategoryManagement() {
		return "shop/productcategorymanagement";
	}
	
	@RequestMapping(value="/productmanage")
	public String productManage(){
		return "shop/productoperation";
	}
	
	
}	
	