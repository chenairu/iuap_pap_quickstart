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





//存在问题，需要调整：涉及死循环
var inputDom = document.querySelectorAll("input");
var searchbtn = document.querySelector('[data-role="searchbtn"]');
var clearbtn = document.querySelector('[data-role="clearbtn"]');
var inputlen = inputDom.length;
var ifuse = false; //是否可用
var domshasvalue = function () {
  for (var i = 0; i < inputlen; i++) {
    if (inputDom[i].value.length > 0) {
      return true;
    }
  }
  return false;
};
if (inputlen > 0) {
  for (var i = 0; i < inputlen; i++) {
    u.on(inputDom[i], "blur", function () {
      ifuse = false;
      if (this.value && this.value.length > 0) {
        //如果本元素失去焦点时有value则按钮直接可用，
        ifuse = true;
      }
      if (!ifuse) {
        //如果离开时无value则查看其它框是否有值
        ifuse = domshasvalue();
      }
      if (ifuse) {
        //有值时去除不可用样式
        u.removeClass(searchbtn, "disable");
        u.removeClass(clearbtn, "disable");
      } else {
        //没值时添加不可用样式
        u.addClass(searchbtn, "disable");
        u.addClass(clearbtn, "disable");
      }
    });
  }
}
