package com.bizzbee.zion.web.admin;

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
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.bizzbee.zion.dto.ProductExecution;
import com.bizzbee.zion.entity.Product;
import com.bizzbee.zion.entity.Shop;
import com.bizzbee.zion.enums.OperationStatusEnum;
import com.bizzbee.zion.enums.ProductStateEnum;
import com.bizzbee.zion.service.ProductCategoryService;
import com.bizzbee.zion.service.ProductService;
import com.bizzbee.zion.util.HttpServletRequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;



@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {
	@Autowired
	private ProductService productService;

	@Autowired
	private ProductCategoryService productCategoryService;
	
	/**
	 *  支持上传商品详情图的最大数量
	 */
	private static final int IMAGE_MAX_COUNT = 6;
	
	
	/**
	 * 添加商品
	 * 
	 *
	 * 1. 接收前端参数：包括 商品、 商品缩略图、商品详情图片实体类
	 * 
	 * 前端页面通过post方式传递一个包含文件上传的Form会以multipart/form-data请求发送给服务器，
	 * 需要告诉DispatcherServlet如何处理MultipartRequest，我们在spring-web.xml中定义了multipartResolver。
	 * 
	 * 如果某个Request是一个MultipartRequest，它就会首先被MultipartResolver处理， 然后再转发相应的Controller。
	 * 在Controller中，
	 * 将HttpServletRequest转型为MultipartHttpServletRequest，可以非常方便的得到文件名和文件内容
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addproduct", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addProduct(HttpServletRequest request) {
		
		Map<String, Object> modelMap = new HashMap<>();
		
		// Step2: 使用FastJson提供的api,实例化Product 构造调用service层的第一个参数
		String productStr = null;
		Product product = null;
		// 使用jackson-databind-->https://github.com/FasterXML/jackson-databind将json转换为pojo
		ObjectMapper mapper = new ObjectMapper(); 
		try {
			productStr = HttpServletRequestUtil.getString(request, "productStr");
			product = mapper.readValue(productStr, Product.class);
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		
		
		product.setEnableStatus(1);
		

		// Step3: 商品缩略图 和 商品详情图 构造调用service层的第二个参数和第三个参数
		MultipartFile productImg = null; // 图片缩略图
		List<MultipartFile> productDetailImgList = new ArrayList<>();// 商品详情图
		try {
			// 创建一个通用的多部分解析器
			MultipartResolver multipartResolver = new CommonsMultipartResolver(
					request.getSession().getServletContext());
			// 判断 request 是否有文件上传,即多部分请求
			if (multipartResolver.isMultipart(request)) {
				productImg = handleImage(request, productDetailImgList);
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", OperationStatusEnum.PIC_EMPTY.getStateInfo());
				return modelMap;
			}
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}

		// Step3 调用Service层
		if (product != null && productImg != null && productDetailImgList.size() > 0) {
			try {
				
//				int shopId = HttpServletRequestUtil.getInt(request,"shopId");
//				Shop shop = new Shop();
//				shop.setShopId((long)shopId);
//				product.setShop(shop);
				// 调用addProduct
				ProductExecution pe = productService.addProduct(product, productImg, productDetailImgList);
				if (pe.getState() == OperationStatusEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			} catch (Exception e) {
				e.printStackTrace();
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", ProductStateEnum.PRODUCT_EMPTY.getStateInfo());
		}
		return modelMap;
	}
	
	/**
	 * 处理图片私有方法
	 * 
	 * @param request
	 * @param productDetailImgList
	 * @return
	 */
	private MultipartFile handleImage(HttpServletRequest request, List<MultipartFile> productDetailImgList) {
		MultipartHttpServletRequest multipartRequest;
		MultipartFile productImg;
		// 与前端约定使用productImg，得到商品缩略图
		multipartRequest = (MultipartHttpServletRequest) request;
		productImg = (MultipartFile) multipartRequest.getFile("productImg");

		// 得到商品详情的列表，和前端约定使用productDetailImg + i 传递
		for (int i = 0; i < IMAGE_MAX_COUNT; i++) {
			MultipartFile productDetailImg = (MultipartFile) multipartRequest.getFile("productDetailImg" + i);
			if (productDetailImg != null) {
				productDetailImgList.add(productDetailImg);
			} else {
				// 如果从请求中获取的到file为空，终止循环
				break;
			}
		}
		return productImg;
	}
	
	
	
	
	
	
}
