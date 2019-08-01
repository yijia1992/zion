package com.bizzbee.zion.service;

import java.util.List;

import com.bizzbee.zion.dto.ProductCategoryExecution;
import com.bizzbee.zion.entity.ProductCategory;

public interface ProductCategoryService {
	
	
	List<ProductCategory> getProductCategoryList(long shopId);
	
	/**
	 * 批量添加商品类别
	 * 
	 * @param productCategoryList
	 * @return
	 * @throws ProductCategoryOperationException
	 */
	ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList);
	
	/**
	 * 将此类别下的商品里的类别id置空，再删除该商品类别
	 * 
	 * @param productCategoryId
	 * @param shopId
	 * @return
	 * @throws ProductCategoryOperationException
	 */
	ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId);
			
	
	
	
}
