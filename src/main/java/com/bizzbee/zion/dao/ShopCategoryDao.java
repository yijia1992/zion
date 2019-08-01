package com.bizzbee.zion.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bizzbee.zion.entity.ProductCategory;
import com.bizzbee.zion.entity.ShopCategory;

public interface ShopCategoryDao {
	List<ShopCategory> queryShopCategories(@Param("category") ShopCategory shopCategory);
	
	
	
	
	
}
