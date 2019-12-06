function initExtraTable() {
    $table.bootstrapTable('destroy').bootstrapTable({
        method: 'post',
        url: '/sys/generator/getExtraFields',
        height: 485,
        toggle: 'table',
        toolbar: "#toolbar",
        showFullscreen: true,
        clickToSelect: true,
        search: false,
        queryParams: function(params) {
            var param = {
                extraFieldName: $('#queryParam').val()
            };
            console.log(param);
            return param;
        },
        showColumns: true,
        pageNumber : 1, //初始化加载第一页
        pagination : true,//是否分页
        sidePagination : 'client',//server:服务器端分页|client：前端分页
        pageSize : 50,//单页记录数
        pageList : [10, 20, 50 ,100, 200],//可选择单页记录数
        responseHandler: function(res) {
            return {
                "total": res.data.length,//总页数
                "rows": res.data   //数据
            };
        },
        formatLoadingMessage: function () {
            return "请稍等，正在加载中...";
        },
        formatNoMatches: function () {  //没有匹配的结果
            return '无符合条件的记录';
        },
        onLoadSuccess: function(){
            $("[data-toggle='tooltip']").tooltip();
        },
        showRefresh : true,//刷新按钮
        showFooter: false,
        columns: [
            { checkbox: true },
            { field: 'extraFieldType', title: 'ExtraFieldType', width: "150" },
            { field: 'extraFieldName', title: 'extraFieldName', width: "300" },
            { field: 'extraFieldValue', title: 'ExtraFieldValue', width: "600" },
            { field: 'operation', title: 'Operation', align: 'center',
                formatter:function(value,row,index){
                    var element =
                        "<button type='button' class='btn btn-warning btn-sm btn-xs' onclick='editExtraField(" + JSON.stringify(row) + ")'><i class=\"glyphicon glyphicon-edit\"></i> 编辑</button>"+
                        "<button type='button' style='margin-left: 15px;' class='btn btn-danger btn-sm btn-xs' onclick='deleteExtraField(" + JSON.stringify(row) + ")'><i class=\"glyphicon glyphicon-minus\"></i> 删除</button> ";
                    return element;
                }
            }
        ]
    })
};

function exportData() {
    window.location.href="/sys/generator/exportExtraField?filter=" + $('#queryParam').val();
};

function refreshTable() {
    $table.bootstrapTable('refresh');
};

function editExtraField(row) {
    alert("editExtraField success!");
    console.log(row);
};

function doDelete(arr) {
    $.ajax({
        type: "post",
        url: "/sys/generator/batchDeleteField",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(arr),
        dataType: "json",
        success: function (data) {
            refreshTable();
            if (data.code === 1) {
                $.globalMessenger().post({
                    message: "删除成功",
                    type: 'success',//消息类型。error、info、success
                    hideOnNavigate: true
                });
            } else {
                $.globalMessenger().post({
                    message: data.message,
                    type: 'error',//消息类型。error、info、success
                    hideOnNavigate: true
                });
            }
        }
    });
};

function deleteExtraField(row) {
    console.log(row);
    var arr = [];
    arr.push(row);
    doDelete(arr);
};
function batchDelete() {
    var arr = $table.bootstrapTable('getSelections');
    if (arr.length > 0) {
        doDelete(arr)
    } else {
        $.globalMessenger().post({
            message: "请至少选择一条记录",
            type: 'info',//消息类型。error、info、success
            hideOnNavigate: true
        });
    }
}