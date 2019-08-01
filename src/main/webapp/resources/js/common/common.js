/**
 * 根据属性名获取QueryString中所带的属性值
 * 南通传智信息技术--王逸嘉
 * @param name:属性名
 */
function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null) {
		return r[2];
	}
	return '';
}