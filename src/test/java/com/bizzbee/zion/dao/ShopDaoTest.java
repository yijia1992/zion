package com.bizzbee.zion.dao;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bizzbee.zion.BaseTest;
import com.bizzbee.zion.entity.Area;
import com.bizzbee.zion.entity.PersonInfo;
import com.bizzbee.zion.entity.Shop;
import com.bizzbee.zion.entity.ShopCategory;

public class ShopDaoTest extends BaseTest{
	
	@Autowired
	private ShopDao shopDao;
	
	@Test
	@Ignore
	public void testQueryShopList() {
		Shop shop = new Shop();
		PersonInfo owner = new PersonInfo();
		owner.setUserId(1L);
		shop.setOwner(owner);
		List<Shop> shopList = shopDao.queryShopList(shop,0,5);
		int count = shopDao.queryShopCount(shop);
		
		
	}
	
	
	@Test
	@Ignore
	public void testQueryShop() {
		Shop shop =shopDao.queryByShopId(1);
		System.out.println("areaName:" + shop.getArea().getAreaName());
		System.out.println("shopCategoryName:" + shop.getShopCategory().getShopCategoryName());
	}
	
	@Test
	@Ignore
	public void testInsertShop() {
		Shop shop = new Shop();
		PersonInfo owner = new PersonInfo();
		ShopCategory shopCategory = new ShopCategory();
		Area area = new Area();
		owner.setUserId(1L);
		area.setAreaId(1);
		shopCategory.setShopCategoryId(1L);
		shop.setOwner(owner);
		shop.setShopCategory(shopCategory);
		shop.setArea(area);
		shop.setShopAddr("test");
		shop.setShopName("test店铺");
		shop.setShopDesc("test");
		shop.setShopImg("test");
		shop.setPhone("test");
		shop.setPriority(1);
		shop.setCreateTime(new Date());
		shop.setLastEditTime(new Date());
		shop.setEnableStatus(1);
		shop.setAdvice("审核中");
		int effectNum = shopDao.insertShop(shop);
		System.out.println("effectNum：" + effectNum);
}
	@Test
	@Ignore
	public void testUpdateShop() {
		Shop shop = new Shop();
		shop.setShopId(1L);
		shop.setShopAddr("test@111");
		shop.setShopName("@test@111");
		shop.setShopDesc("test111");
		shop.setShopImg("test111");
		shop.setPhone("test111");
		shop.setLastEditTime(new Date());
		int effectNum = shopDao.updateShop(shop);
		System.out.println("effectNum：" + effectNum);
	}
}
