var severurl = "https://mock.yonyoucloud.com/mock/122";


/**
 * 测试mock接口by房帅中
 */
var severUrlTest = "https://mock.yonyoucloud.com/mock/128/iuap-quickstart/";
/**
 * 档案列表数据地址 by 王金鹏
*/
var mockserverurl = "https://mock.yonyoucloud.com/mock/164/cloud"
/**
 * 92环境 树表模板url
 */
//var baseurl = "http://172.20.8.92:8080";
//var treetableurl = "/example";
var ctx = "http://10.6.197.111:8081/iuap_pap_quickstart";
/**
 * ajax封装
 * url 发送请求的地址
 * data 发送到服务器的数据，数组存储，如：{"username": "张三", "password": 123456}
 * succCallback 成功回调函数
 * errorCallback 失败回调函数
 * type 请求方式("POST" 或 "GET")， 默认已经设置为 "POST"
 * dataType 预期服务器返回的数据类型，常用的如：xml、html、json、text
 */

function $ajax(url, postData, succCallback, errorCallback, type, dataType) {
  var type = type || "post";
  var dataType = dataType || "json";
  $.ajax({
    type: type,
    url: url,
    data: postData,
    dataType: dataType,
    contentType: "application/json;charset=utf-8",
    beforeSend: function() {
      //开始loading
      $(".js_loading").show();
    },
    success: function(res) {
      if (res.success) {
        if (succCallback) {
          succCallback(res);
        }
      } else {
        if (errorCallback) {
          errorCallback(res);
        }
      }
    },

    complete: function() {
      //结束loading
      //$(".js_loading").remove();
      $(".js_loading").hide();
    }
  });
}
/**
 * 使用案例
 *   var postData = {
                username: '张三',
                password: 123456
            };
            $ajax(
                'test.asp', 
                postData, 
                function(res){  //成功
                    $(".box").html(res.firstName);
                },
                function(res){  //失败
                    $(".box").html(res.fail);
                }
            );
        });
 */
