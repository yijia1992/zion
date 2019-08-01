package com.bizzbee.zion.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bizzbee.zion.entity.Shop;

public interface ShopDao {
	
	/**
	 * 
	 * 带有分页功能的查询商铺列表 。 可输入的查询条件：商铺名（要求模糊查询） 区域Id 商铺状态 商铺类别 owner
	 * (注意在sqlmapper中按照前端入参拼装不同的查询语句)
	 * 
	 * @param shopConditionShop
	 * @param rowIndex：从第几行开始取
	 * @param pageSize：返回多少行数据（页面上的数据量）
	 *            比如 rowIndex为1,pageSize为5 即为 从第一行开始取，取5行数据
	 * @return
	 */
	List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition, @Param("rowIndex") int rowIndex,
			@Param("pageSize") int pageSize);

	/**
	 * 按照条件查询 符合前台传入的条件的商铺的总数
	 * 
	 * @param shopCondition
	 * @return
	 */
	int queryShopCount(@Param("shopCondition") Shop shopCondition);
	
	
	Shop queryByShopId(long shopId);
	
	int insertShop(Shop shop);
	
	int updateShop(Shop shop);

}
