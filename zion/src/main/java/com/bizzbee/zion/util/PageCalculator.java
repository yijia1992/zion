package com.bizzbee.zion.util;


/**
 * @author 逸嘉
 * 南通传智信息技术
 */
public class PageCalculator {
	public static int calculateRowIndex(int pageIndex,int pageSize){
		return (pageIndex>0)?(pageIndex-1)*pageSize:0;
	}
}
