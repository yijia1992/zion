/**
 * 
 */

$(function(){
	
	var shopId = getQueryString("shopId");
	
	var getProductCategoryURL = '/zion/shopadmin/getproductcategorylist';
	
	var addProductCategoryURL = '/zion/shopadmin/addproductcategorys';
	
	var deleteProductCategoryUrl = "/zion/shopadmin/removeproductcategory"
	
	getProductCategoryList();
	function getProductCategoryList() {
		$.getJSON(
			getProductCategoryURL,
			function(data) {
				if (data.success) {
					console.log(data);
					var dataList = data.data;
					$('.product-categroy-wrap').html('');
					var tempHtml = '';
					dataList
							.map(function(item, index) {
								tempHtml += ''
										+ '<div class="row row-product-category now">'
										+ '<div class="col-33 product-category-name">'
										+ item.productCategoryName
										+ '</div>'
										+ '<div class="col-33">'
										+ item.priority
										+ '</div>'
										+ '<div class="col-33"><a href="#" class="button delete ok" data-id="'
										+ item.productCategoryId
										+ '">删除</a></div>'
										+ '</div>';
							});
					$('.product-categroy-wrap').append(tempHtml);
					regtrueDelete();
				}
		});
	}
	
	// 新增按钮的点击事件
	$('#new').click(function() {
		// 新增数据 以 temp 为标识，便于和库表中的数据区分开来
		var tempHtml = '<div class="row row-product-category temp">'
				+ '<div class="col-33"><input class="category-input category" type="text" placeholder="分类名"></div>'
				+ '<div class="col-33"><input class="category-input priority" type="number" placeholder="优先级"></div>'
				+ '<div class="col-33"><a href="#" class="button delete temp">删除</a></div>'
				+ '</div>';
		$('.product-categroy-wrap').append(tempHtml);
		regPageDelete();
	});

	$('#submit').click(function() {
		// 通过temp 获取新增的行
		var tempArr = $('.temp');
		// 定义数组接收新增的数据
		var productCategoryList = [];
		tempArr.map(function(index, item) {
			var tempObj = {};
			tempObj.productCategoryName = $(item).find('.category').val();
			tempObj.priority = $(item).find('.priority').val();
			tempObj.shopId = shopId;
			if (tempObj.productCategoryName && tempObj.priority) {
				productCategoryList.push(tempObj);
			}
		});
		$.ajax({
			url : addProductCategoryURL,
			type : 'POST',
			// 后端通过 @HttpRequestBody直接接收
			data : JSON.stringify(productCategoryList),
			contentType : 'application/json',
			success : function(data) {
				if (data.success) {
					$.toast('新增【' + data.effectNum + '】条成功！');
					// 重新加载数据
					getProductCategoryList();
				} else {
					$.toast(data.errMsg);
				}
			}
		});
	});
	
	// 一种是需要提交到后台的删除 now ，另外一种是 新增但未提交到数据库中的删除 temp
	function regtrueDelete(){
		$('.button.delete.ok').click(function(e) {
			var target = e.currentTarget;
			$.confirm('确定么?', function() {
				$.ajax({
					url : deleteProductCategoryUrl,
					type : 'POST',
					data : {
						productCategoryId : target.dataset.id,
						shopId:shopId
					},
					dataType : 'json',
					success : function(data) {
						if (data.success) {
							$.toast('删除成功！');
							// 重新加载数据
							getProductCategoryList();
						} else {
							$.toast('删除失败！');
						}
					}
				});
			});
		});
	}
	
	
	function regPageDelete(){
		$('.button.delete.temp').click(function(e) {
			console.log(this);
			$(this).parent().parent().remove();
		});
		
		
	}
	
	
	
	

})



