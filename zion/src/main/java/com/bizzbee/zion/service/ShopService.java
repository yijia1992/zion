package com.bizzbee.zion.service;


import org.springframework.web.multipart.MultipartFile;

import com.bizzbee.zion.dto.ShopExecution;
import com.bizzbee.zion.entity.Shop;

public interface ShopService {
	
	/**
	 * 获取店铺分页列表
	 * 
	 * @param shopCondition 店铺查询条件
	 * @param pageIndex     第几页
	 * @param pageSize      每页条数
	 * @return
	 * @throws ShopOperationException
	 */
	ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);
	
	Shop getByShopId(long shopId);
	
	ShopExecution modifyShop(Shop shop,MultipartFile shopImg);
	
	ShopExecution addShop(Shop shop,MultipartFile shopImg);
}
