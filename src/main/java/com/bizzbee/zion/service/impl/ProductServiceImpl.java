package com.bizzbee.zion.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bizzbee.zion.dao.ProductDao;
import com.bizzbee.zion.dao.ProductImgDao;
import com.bizzbee.zion.dto.ProductExecution;
import com.bizzbee.zion.entity.Product;
import com.bizzbee.zion.entity.ProductImg;
import com.bizzbee.zion.enums.OperationStatusEnum;
import com.bizzbee.zion.enums.ProductStateEnum;
import com.bizzbee.zion.service.ProductService;
import com.bizzbee.zion.util.ImgUtil;
import com.bizzbee.zion.util.PathUtil;
@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductDao productDao;

	@Autowired
	private ProductImgDao productImgDao;
	
	@Override
	public ProductExecution addProduct(Product product,
			MultipartFile productImg, List<MultipartFile> productImgList) {
		
				// 空值判断
				if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
					// 给商品设置默认属性
					product.setCreateTime(new Date());
					// 默认上架状态
					//product.setEnableStatus(EnableStatusEnum.AVAILABLE.getState());
					// 若商品缩略图不为空则添加
					if (productImg != null) {
						addProductImg(product, productImg);
					}
					// 创建商品信息
					try {
						int effectNum = productDao.insertProduct(product);
						if (effectNum <= 0) {
							throw new RuntimeException(ProductStateEnum.EDIT_ERROR.getStateInfo());
						}
					} catch (Exception e) {
						throw new RuntimeException(ProductStateEnum.EDIT_ERROR.getStateInfo() + e.toString());
					}
					// 若商品详情图列表不为空则添加
					if (productImgList != null && !productImgList.isEmpty()) {
						addProductImgList(product, productImgList);
					}
					return new ProductExecution(OperationStatusEnum.SUCCESS, product);
				} else {
					// 参数为空则返回空值错误信息
					return new ProductExecution(ProductStateEnum.EMPTY);
				}
	}
	
	/**
	 * 添加商品缩略图
	 * 
	 * @param product    商品
	 * @param productImg 商品缩略图
	 */
	private void addProductImg(Product product, MultipartFile productImg) {
		// 获取图片存储路径，将图片放在相应店铺的文件夹下
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		String productImgAddr = ImgUtil.generateThumbnail(productImg, dest);
		product.setImgAddr(productImgAddr);
	}

	/**
	 * 添加商品详情图
	 * 
	 * @param product        商品
	 * @param productImgList 商品详情图列表
	 */
	private void addProductImgList(Product product, List<MultipartFile> productImgList) {
		// 获取图片存储路径，将图片放在相应店铺的文件夹下
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		List<ProductImg> productImgs = new ArrayList<>();
		// 遍历商品详情图，并添加到productImg中
		for (MultipartFile multipartFile : productImgList) {
			String imgAddr = ImgUtil.generateThumbnail(multipartFile, dest);
			ProductImg productImg = new ProductImg();
			productImg.setProductId(product.getProductId());
			productImg.setImgAddr(imgAddr);
			productImg.setCreateTime(new Date());
			productImgs.add(productImg);
		}

		// 存入有图片，就执行批量添加操作
		if (productImgs.size() > 0) {
			try {
				int effectNum = productImgDao.batchInsertProductImg(productImgs);
				if (effectNum <= 0) {
					throw new RuntimeException(OperationStatusEnum.PIC_UPLOAD_ERROR.getStateInfo());
				}
			} catch (Exception e) {
				throw new RuntimeException(OperationStatusEnum.PIC_UPLOAD_ERROR.getStateInfo() + e.toString());
			}
		}
	}


}
