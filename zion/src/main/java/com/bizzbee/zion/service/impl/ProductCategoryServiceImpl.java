package com.bizzbee.zion.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bizzbee.zion.dao.ProductCategoryDao;
import com.bizzbee.zion.dto.ProductCategoryExecution;
import com.bizzbee.zion.entity.ProductCategory;
import com.bizzbee.zion.enums.OperationStatusEnum;
import com.bizzbee.zion.enums.ProductCategoryStateEnum;
import com.bizzbee.zion.service.ProductCategoryService;
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService{
	@Autowired
	private ProductCategoryDao productCategoryDao;
	
	@Override
	public List<ProductCategory> getProductCategoryList(long shopId) {
		
		return productCategoryDao.selectProductCategoryList(shopId);
	}

	@Override
	@Transactional
	public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) {
		// 列表不为空
				if (productCategoryList != null && !productCategoryList.isEmpty()) {
					try {
						int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
						if (effectedNum <= 0) {
							throw new RuntimeException("批量添加失敗");
						} else {
							return new ProductCategoryExecution(OperationStatusEnum.SUCCESS, productCategoryList, effectedNum);
						}
					} catch (Exception e) {
//						throw new ProductCategoryOperationException(
//								ProductCategoryStateEnum.EDIT_ERROR.getStateInfo() + e.getMessage());
						throw new RuntimeException(ProductCategoryStateEnum.EDIT_ERROR.getStateInfo()+e.getMessage());
						
					}
				} else {
//					return new ProductCategoryExecution(ProductCategoryStateEnum.EMPETY_LIST);
					return new ProductCategoryExecution(ProductCategoryStateEnum.EMPETY_LIST);
					
					
				}
	}

	@Override
	@Transactional
	public ProductCategoryExecution deleteProductCategory(
			long productCategoryId, long shopId) {
		// 删除商品类别
				try {
					int effectedNum = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
					if (effectedNum <= 0) {
						throw new RuntimeException(ProductCategoryStateEnum.DELETE_ERROR.getStateInfo());
					} else {
						return new ProductCategoryExecution(OperationStatusEnum.SUCCESS, null, effectedNum);
					}
				} catch (Exception e) {
					throw new RuntimeException("deleteProductCategory error" + e.getMessage());
				}
	}

}
