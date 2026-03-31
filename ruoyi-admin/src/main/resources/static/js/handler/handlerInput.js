function formValidate(formId) {
    if ($.common.isEmpty(formId)) {
        formId = "#form-sub-edit";
    }
    $(formId).validate({
        onkeyup: false,
        focusCleanup: true,
        handlePost: true,
        handleBy: true,
        rules: {
            handleBy: {
                required: function () {
                    // 检查 handleDept 和 handlePost 是否有值
                    return $.common.isNotEmpty($("input[name='handleDept']").val()) &&
                        $.common.isNotEmpty($("select[name='handlePost']").val());
                }
            }
        },
        messages: {
            handleBy: {
                required: "请选择处理人"
            }
        }
    });
}

$(document).ready(function () {
    queryDeptTree();
});

/**
 * 主动触发handleBy输入框的校验
 */
function validateHandleBy() {
    // 获取相关字段的值
    const handlePost = $("select[name='handlePost']").val();
    // 如果处理人部门或处理人岗位为空，则显示错误信息
    if ($.common.isEmpty(deptId) || $.common.isEmpty(handlePost)) {
        // 手动触发改字段的验证
        const validator = $("#form-sub-edit").validate();
        validator.showErrors({
            "handleBy": "请先选择处理人部门和处理人岗位"
        });
        return false;
    }
    return true;
}

let deptId;

function queryDeptTree() {
    let url = ctx + "system/user/allDeptTreeData";
    let tree;
    let options = {
        url: url,
        expandLevel: 2,
        onClick: zOnClick,
        callBack: function (data) {
            tree = data;
        }
    };
    $.tree.init(options);

    function zOnClick(event, treeId, treeNode) {
        let ancestors = [];
        let parent = treeNode.getParentNode();
        //获取祖先节点的名称
        while ($.common.isNotEmpty(parent)) {
            ancestors.unshift(parent);
            parent = parent.getParentNode();
        }
        //构建提示文本
        let innerText = '';
        for (let node of ancestors) {
            innerText += node.name + '>';
        }
        innerText += treeNode.name;
        deptId = parseInt(treeNode.id);
        //给用户的提示信息
        $('input[name="deptText"]').val(innerText);
        initHandlePost();
        initHandleBy();
    }
}

// 填充处理人部门下拉框
function initHandlePost() {
    $.ajax({
        url: ctx + "system/post/post-list",
        type: "post",
        dataType: "json",
        success: function (result) {
            if (result.code === web_status.SUCCESS) {
                let dom = $("select[name='handlePost']");
                dom.empty();
                let data = result.rows;
                for (let i = 0; i < data.length; i++) {
                    dom.append(
                        "<option value='" + data[i].postId + "'>" + data[i].postName + "</option>"
                    );
                }
            } else {
                $.modal.msgWarning(result.msg);
            }
        }
    })
}

// 填充处理人下拉框
function initHandleBy() {
    let handlePost = $("select[name='handlePost']").val();
    let dom = $("select[name='handleBy']");
    //缺少必要的参数
    if ($.common.isEmpty(deptId) || $.common.isEmpty(handlePost)) {
        dom.empty();
        dom.append(
            "<option value=''>" + "请先选择部门和岗位" + "</option>"
        );
        return;
    }
    handlePost = parseInt(handlePost);
    $.ajax({
        url: ctx + `system/user/dept-post-user?deptId=${deptId}&postId=${handlePost}`,
        type: "get",
        dataType: "json",
        success: function (result) {
            if (result.code === web_status.SUCCESS) {
                let data = result.rows;
                dom.empty();
                if (data.length <= 0) {
                    dom.append(
                        "<option value=''>" + "该部门无指定岗位员工" + "</option>"
                    );
                    return;
                }
                for (let i = 0; i < data.length; i++) {
                    dom.append(
                        "<option value='" + data[i].loginName + "'>" + data[i].userName + "</option>"
                    );
                }
            } else {
                $.modal.msgWarning(result.msg);
            }
        }
    })
}
