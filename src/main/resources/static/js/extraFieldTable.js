function initExtraTable() {
    $table.bootstrapTable('destroy').bootstrapTable({
        method: 'get',
        url: '/sys/generator/getExtraFields',
        height: 485,
        toggle: 'table',
        toolbar: "#toolbar",
        showFullscreen: true,
        clickToSelect: true,
        search: false,
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
            { field: 'extraFieldName', title: 'ExtraFieldValue', width: "300" },
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
function refresh() {
    $table.bootstrapTable('refresh');
};
function editExtraField(row) {
    alert("editExtraField success!");
    console.log(row);
};
function deleteExtraField(row) {
    alert("deleteExtraField success!");
    console.log(row);
};