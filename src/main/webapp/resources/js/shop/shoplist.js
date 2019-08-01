$(function(){
	
	getshoplist();

	function getshoplist() {
		$.ajax({
			url : "/zion/shopadmin/getshoplist",
			type : "get",
			dataType : "json",
			success : function(data) {
				if (data.success) {
				console.log(data);
					handleList(data.shopList);
					handleUser(data.user);
				}
			}
		});
	}

	// 显示名字
	function handleUser(data) {
		$('#user-name').text(data.name);
	}

	// 拼接店铺
	function handleList(data) {
		var shopListHtml = '';
		data.map(function(item, index) {
			shopListHtml += '<div class="row row-shop"><div class="col-40">'
					+ item.shopName + '</div><div class="col-20">'
					+ shopStatus(item.enableStatus)
					+ '</div><div class="col-20">'
					+ goShop(item.enableStatus, item.shopId) + '</div></div>'
		});
		$('.shop-wrap').html(shopListHtml);
	}

	function shopStatus(status) {
		if (status == 0) {
			return '审核中';
		} else if (status == 1) {
			return '审核通过';
		} else {
			return '店铺非法';
		}
	}

	
	function goShop(status, shopId) {
		if (status == 1) {
			return '<a href="/zion/admin/shopmanagement?shopId=' + shopId
					+ '">进入</a>';
		} else {
			return '';
		}
	}


})