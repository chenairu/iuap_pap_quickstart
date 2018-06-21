define(['text!./form.html',
    "css!../../style/common.css",
    'css!./form.css',
    '../../config/sys_const.js',
    "../../utils/utils.js",
    "../../utils/ajax.js",
    "../../utils/tips.js",
    "./viewModel.js"], function (template) {
        var element;
        function init(element) {
            element = element;
            $(element).html(template);
            viewModel.event.pageinit(element);
            //撑满高度布局
            $("#myLayout").height(document.body.scrollHeight);
        }
        viewModel.event = {
            pageinit: function (element) {
                viewModel.app = u.createApp({
                    el: element,
                    model: viewModel
                });
                viewModel.event.initForm();//初始化表单
            },

            initForm: function () {
                var r1 = viewModel.formData1.createEmptyRow();
                r1.setValue('sys', "否");
                viewModel.formData1.setRowSelect(0);
                viewModel.formData1.on("sys.valueChange", function (e) {
                    alert(e.newValue);
                });

                var r2 = viewModel.formData2.createEmptyRow();
                r2.setValue('sex', "男");
                viewModel.formData2.setRowSelect(0);
                viewModel.formData2.on("sex.valueChange", function (e) {
                    alert(e.newValue);
                });
            }
        }
        return {
            template: template,
            init: init
        };
    });
