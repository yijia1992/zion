package com.bizzbee.zion.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bizzbee.zion.entity.ProductCategory;

public interface ProductCategoryDao {
	/**
	 * 根据店铺id查询店铺商品列表
	 * 
	 * @param shopId
	 * @return
	 */
	List<ProductCategory> selectProductCategoryList(long shopId);
	
	/**
	 * 批量添加商品类别
	 * 
	 * @param productCategoryList 商品类别列表
	 * @return
	 */
	int batchInsertProductCategory(List<ProductCategory> productCategoryList);
	
	/**
	 * 删除商品类别
	 * 
	 * @param productCategoryId 商品类别Id
	 * @param shopId            店铺Id,使删除操作更安全
	 * @return
	 */
	int deleteProductCategory(@Param("productCategoryId") long productCategoryId, @Param("shopId") long shopId);
}
