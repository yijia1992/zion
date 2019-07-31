package com.bizzbee.zion.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizzbee.zion.dao.ShopCategoryDao;
import com.bizzbee.zion.entity.ShopCategory;
import com.bizzbee.zion.service.ShopCategoryService;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService{
	@Autowired
	private ShopCategoryDao shopCategoryDao;
	
	@Override
	public List<ShopCategory> getShopCategoryList(ShopCategory shopCategory) {
		
		return shopCategoryDao.queryShopCategories(shopCategory);
	}

}
