/**
 * 
 */
$(function(){

    // 获取shopId
    var shopId = getQueryString("shopId");
    
    $('#shopInfo').attr('href','/zion/admin/shopoperation?shopId=' + shopId);
    $('#productCategory').attr('href','/zion/admin/productcategorymanagement?shopId='+shopId);
       


});
