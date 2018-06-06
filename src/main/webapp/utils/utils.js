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

