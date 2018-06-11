//搜索导航（查询、筛选）展开/收起
$("body").on("click", "#condition-toggle", function () {
  var text = $("#condition-text").html();
  if (text === "展开") {
    $("#search-modul").show();
    $("#condition-text").html("收起");
  } else {
    $("#search-modul").hide();
    $("#condition-text").html("展开");
  }
});


// 搜索区域展开与收叠
$("body").on("click", "#collapse", function () {
  if ($("#collapse-body").css("display") == "none") {
    $("#collapse-body").show();
  }
  else {
    $("#collapse-body").hide();
  }
});


function divShow(name) {
  var divObj = "#" + name;
  $(divObj).show();
};

function divHide(name) {
  var divObj = "#" + name;
  $(divObj).hide();
}



	/**
	 * 去除前后空格
	 */
	removeSpace = function(newStr){
		newStr = newStr.replace(/(^\s*)|(\s*$)/g, "");
		return newStr;
	}
	
	/**
	 * 去除所有空格（前后及中间）
	 */
	removeAllSpace = function(newStr){
		newStr = newStr.replace(/\s+/g, "");
		return newStr;
	}