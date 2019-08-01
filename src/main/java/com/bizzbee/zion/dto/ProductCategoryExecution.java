package com.bizzbee.zion.dto;

import java.util.List;

import com.bizzbee.zion.entity.ProductCategory;
import com.bizzbee.zion.enums.OperationStatusEnum;
import com.bizzbee.zion.enums.ProductCategoryStateEnum;

public class ProductCategoryExecution {
	private int state;
	private String stateInfo;

	// 因为是批量操作,所以使用List
	private List<ProductCategory> productCategoryList;

	private int count;

	/**
	 * 
	 * 
	 * @Title:ProductCategoryExecution
	 * 
	 * @Description:空的构造函数
	 */
	public ProductCategoryExecution() {
		super();
	}

	/**
	 * 
	 * @Title:ProductCategoryExecution
	 * 
	 * @Description:操作成功的时候使用的构造函数,返回操作状态和ProductCategory集合,以及操作成功的行数
	 * 
	 * @param productCategoryStateEnum
	 * @param productCategoryList
	 * @param count
	 */
	public ProductCategoryExecution(OperationStatusEnum operationStatusEnum,
			List<ProductCategory> productCategoryList, int count) {
		this.state = operationStatusEnum.getState();
		this.stateInfo = operationStatusEnum.getStateInfo();
		this.productCategoryList = productCategoryList;
		this.count = count;
	}

	/**
	 * @Title:ProductCategoryExecution
	 * 
	 * @Description:操作失败的时候返回的信息，仅包含状态和状态描述即可
	 * 
	 * @param productCategoryStateEnum
	 */
	public ProductCategoryExecution(ProductCategoryStateEnum productCategoryStateEnum) {
		this.state = productCategoryStateEnum.getState();
		this.stateInfo = productCategoryStateEnum.getStateInfo();
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public List<ProductCategory> getProductCategoryList() {
		return productCategoryList;
	}

	public void setProductCategoryList(List<ProductCategory> productCategoryList) {
		this.productCategoryList = productCategoryList;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
