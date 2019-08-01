package com.bizzbee.zion.dao;

import java.util.List;

import com.bizzbee.zion.entity.ProductImg;

public interface ProductImgDao {
	/**
	 * 批量插入商品图片
	 * 
	 * @param productImgList 商品图片列表
	 * @return
	 */

	int batchInsertProductImg(List<ProductImg> productImgList);
}
