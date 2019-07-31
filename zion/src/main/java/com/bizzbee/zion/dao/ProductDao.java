package com.bizzbee.zion.dao;

import com.bizzbee.zion.entity.Product;

public interface ProductDao {
	/**
	 * 插入商品
	 * 
	 * @param product
	 * @return
	 */
	int insertProduct(Product product);
}
