define(['text!./form.html',
    "css!../../style/common.css",
    'css!./form.css',
    '../../config/sys_const.js',
    "../../utils/utils.js",
    "../../utils/ajax.js",
    "../../utils/tips.js",
    "./viewModel.js"], function (template, cookie) {
        var ctx, listRowUrl, saveRowUrl, delRowUrl, getUrl, element;
        function init(element, cookie) {
            element = element;
            $(element).html(template);
            ctx = cookie.appCtx + "/example_dictionary";
            listRowUrl = ctx + "/list"; //列表查询URL
            saveRowUrl = ctx + "/save"; //新增和修改URL， 有id为修改 无id为新增
            delRowUrl = ctx + "/delete"; //刪除URL
            getUrl = ctx + "/get",
            window.csrfDefense();									//跨站请求伪造防御
            $(element).html(template);

        }
        viewModel.event = {

        }
        return {
            template: template,
            init: init
        };
    });
