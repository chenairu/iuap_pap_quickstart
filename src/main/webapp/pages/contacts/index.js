define(['text!./contacts.html',
    "./viewModel.orgTree.js",
    "./viewModel.contact.js",
    "css!../../style/common.css",
    'css!./contacts.css',
    "../../config/sys_const.js",
], function (template, orgTree, contact) {
    function init(element, cookie) {
        var ctx = cookie.appCtx;
        $(element).html(template);
        orgTree.viewModel.event.pageInit();
        contact.viewModel.event.pageInit();
        if (u.isIE8) {
            $(".ie8-bg").css("display", "block");
        }
        //  调整div高度适应满屏，树的高度固定后才会有滚动条
        $("#myLayout").height(document.body.scrollHeight);
        $("#orgTree").height(document.body.scrollHeight - 100);
    };
    return {
        template: template,
        init: init
    }
});
