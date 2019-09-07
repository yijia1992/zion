package com.bizzbee.zion.dao;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.bizzbee.zion.BaseTest;
import com.bizzbee.zion.entity.ShopCategory;

public class ShopCategoryDaoTest extends BaseTest{
	@Autowired
	private ShopCategoryDao shopCategoryDao;
	@Test
	@Ignore
	public void testQueryShopCategory() {
		List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategories(new ShopCategory());
		System.out.println(shopCategoryList.size());
//		ShopCategory sonCategory = new ShopCategory();
//		ShopCategory parentCategory = new ShopCategory();
//		parentCategory.setShopCategoryId(1L);
//		sonCategory.setParent(parentCategory);
//		shopCategoryList = shopCategoryDao.queryShopCategories(sonCategory);
//		System.out.println(shopCategoryList.get(0).getShopCategoryName());
	}

}
