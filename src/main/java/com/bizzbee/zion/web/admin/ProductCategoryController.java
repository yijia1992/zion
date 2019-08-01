package com.bizzbee.zion.web.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bizzbee.zion.dto.ProductCategoryExecution;
import com.bizzbee.zion.entity.ProductCategory;
import com.bizzbee.zion.entity.Shop;
import com.bizzbee.zion.enums.OperationStatusEnum;
import com.bizzbee.zion.enums.ProductCategoryStateEnum;
import com.bizzbee.zion.service.ProductCategoryService;
@Controller
@RequestMapping("/shopadmin")
public class ProductCategoryController {
	@Autowired
	private ProductCategoryService productCategoryService;

	/**
	 * 根据ShopId获取productCategory
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getproductcategorylist", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getProductCategoryList(HttpServletRequest request) {
		Map<String,Object> result = new HashMap<String, Object>();
		List<ProductCategory> productCategoryList;
		ProductCategoryStateEnum ps;
		Shop shop = new Shop();
		shop.setShopId(18L);
		
		if (shop != null && shop.getShopId() != null) {
			try {
				productCategoryList = productCategoryService.getProductCategoryList(shop.getShopId());
				result.put("success",true);
				result.put("data",productCategoryList);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				ps = ProductCategoryStateEnum.EDIT_ERROR;
				result.put("success",false);
				result.put("errorCode",ps.getState());
				result.put("errorMsg",ps.getStateInfo());
				return result;
			}
		} else {
			ps = ProductCategoryStateEnum.NULL_SHOP;
			result.put("success",false);
			result.put("errorCode",ps.getState());
			result.put("errorMsg",ps.getStateInfo());
			return  result;
		}
	}
	
	/**
	 * 添加商铺目录 ，使用@RequestBody接收前端传递过来的productCategoryList
	 * 
	 * @param productCategoryList
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addproductcategorys", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addProductCategorys(@RequestBody List<ProductCategory> productCategoryList,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		// 列表不为空
		if (productCategoryList != null && !productCategoryList.isEmpty()) {
			// 从session中获取店铺信息，尽量减少对前端的依赖
			//Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");			
			for (ProductCategory productCategory : productCategoryList) {
					//productCategory.setShopId(currentShop.getShopId());
				productCategory.setCreateTime(new Date());
			}
			
			try {
				// 批量插入
				ProductCategoryExecution productCategoryExecution = productCategoryService
						.batchAddProductCategory(productCategoryList);
				if (productCategoryExecution.getState() == OperationStatusEnum.SUCCESS.getState()) {
					result.put("success", true);
					// 同时也将新增成功的数量返回给前台
					result.put("effectNum", productCategoryExecution.getCount());
				} else {
					result.put("success", false);
					result.put("errMsg", productCategoryExecution.getStateInfo());
				}
			} catch (Exception e) {
				result.put("success", false);
				result.put("errMsg", e.getMessage());
				return result;
			}
		} else {
			result.put("success", false);
			result.put("errMsg", ProductCategoryStateEnum.EMPETY_LIST.getStateInfo());
		}
		return result;
	}
	
	
	/**
	 * 删除商品目录
	 * 
	 * @param productCategoryId
	 * @param request
	 */
	@RequestMapping(value = "/removeproductcategory", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> removeProductCategory(Long productCategoryId, Long shopId,HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (productCategoryId != null && productCategoryId > 0) {
			// 从session中获取shop的信息
			//Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
				try {
					// 删除
					//Long shopId = currentShop.getShopId();
					ProductCategoryExecution pc = productCategoryService.deleteProductCategory(productCategoryId,
							shopId);
					if (pc.getState() == OperationStatusEnum.SUCCESS.getState()) {
						modelMap.put("success", true);
					} else {
						modelMap.put("success", false);
						modelMap.put("errMsg", pc.getStateInfo());
					}
				} catch (Exception e) {
					e.printStackTrace();
					modelMap.put("success", false);
					modelMap.put("errMsg", e.getMessage());
					return modelMap;
				}
			
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", ProductCategoryStateEnum.EMPETY_LIST.getStateInfo());
		}
		return modelMap;
	}
	
	
	
	
	
}
