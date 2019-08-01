package com.bizzbee.zion.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bizzbee.zion.dao.ShopDao;
import com.bizzbee.zion.dto.ShopExecution;
import com.bizzbee.zion.entity.Shop;
import com.bizzbee.zion.enums.ShopStateEnum;
import com.bizzbee.zion.service.ShopService;
import com.bizzbee.zion.util.ImgUtil;
import com.bizzbee.zion.util.PageCalculator;
import com.bizzbee.zion.util.PathUtil;


@Service
public class ShopServiceImpl implements ShopService {
	@Autowired
	private ShopDao shopDao;
	

	@Override
	public ShopExecution addShop(Shop shop, MultipartFile shopImg) {
		//1.验证
		if (shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP_INFO);
		}else{
			try {
				// 初始化赋值
				shop.setCreateTime(new Date());
				shop.setEnableStatus(0);
				// 2.添加店铺信息
				int effectedNum = shopDao.insertShop(shop);
				if(effectedNum<0){
					throw new RuntimeException("创建失败");
				}else{
					//3.开始存图片
					if(shopImg!=null){
						try {
							addImage(shop,shopImg);
						} catch (Exception e) {
							throw new RuntimeException("add shopImgError:"+e.getMessage());
						}
					}
					//4.更新存储后的图片地址
					effectedNum = shopDao.updateShop(shop);
					if (effectedNum <= 0) {
						throw new RuntimeException("更新图片地址失败");
						
					}
					
					
				}
			} catch (Exception e) {
				throw new RuntimeException("add shopError:"+e.getMessage());
			}
			
		}
		
		
		
		
		return new ShopExecution(ShopStateEnum.CHECK,shop);
	}
	
	/**
	 * 存储图片
	 * 
	 * @param shop
	 * @param shopImg
	 */
	private void addImage(Shop shop, MultipartFile shopImg) {
		String dest = PathUtil.getShopImagePath(shop.getShopId());
		String shopImgAddr = ImgUtil.generateThumbnail(shopImg, dest);
		// 将图片路径存储用于更新店铺信息
		shop.setShopImg(shopImgAddr);
	}

	@Override
	public Shop getByShopId(long shopId) {
		return shopDao.queryByShopId(shopId);
		
	}

	@Override
	@Transactional
	public ShopExecution modifyShop(Shop shop, MultipartFile shopImg) {
		// 判断店铺是否存在
				if (shop == null || shop.getShopId() == null) {
					return new ShopExecution(ShopStateEnum.NULL_SHOP_INFO);
				} else {
					try {
						// 判断是否要处理照片
						if (shopImg != null) {
							Shop tempShop = shopDao.queryByShopId(shop.getShopId());
							if (tempShop.getShopImg() != null) {
								// 删除原先图片
								ImgUtil.deleteFileOrPath(tempShop.getShopImg());
							}
							// 添加新照片
							addImage(shop, shopImg);
						}
						// 更新照片信息
						shop.setLastEditTime(new Date());
						int effectNum = shopDao.updateShop(shop);
						// 更新成功
						if (effectNum > 0) {
							shop = shopDao.queryByShopId(shop.getShopId());
							return new ShopExecution(ShopStateEnum.CHECK, shop);
						} else {
							return new ShopExecution(ShopStateEnum.EDIT_ERROR);
						}
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
					
				}
			}

	@Override
	public ShopExecution getShopList(Shop shopCondition, int pageIndex,
			int pageSize) {
		// 前台页面插入的pageIndex（第几页）， 而dao层是使用 rowIndex （第几行） ，所以需要转换一下
				int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
				List<Shop> shopList = new ArrayList<Shop>();
				ShopExecution se = new ShopExecution();
				// 查询带有分页的shopList
				shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
				// 查询符合条件的shop总数
				int count = shopDao.queryShopCount(shopCondition);
				// 将shopList和 count设置到se中，返回给控制层
				if (shopList != null) {
					se.setShopList(shopList);
					se.setCount(count);
				} else {
					se.setState(ShopStateEnum.EDIT_ERROR.getState());
				}
				return se;
	}
	
		}

