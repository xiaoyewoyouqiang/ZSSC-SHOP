
function submitForm(form,url) {

    // 1.把表单数据转成一个对象
    var param = formToObject(form);

    // 2.使用ajax把数据发送到后台
    sendRequest(url,param);

}

// 发送请求
function sendRequest(url,param){
    $.post(url,param,function(data){
        // 响应用户
        responseClinet(data);
    },"json");
}

function responseClinet(data){
    // 成功，提示用户操作成功，然后关闭添加的层，刷新一下查询列表
    if(data.status == 'success'){

        // 提示框1s后关闭会调用回调函数
        layer.msg('操作成功', {icon: 1,time:1000},function(){
            // 获取添加页面层索引
            var index = parent.layer.getFrameIndex(window.name);
            // 根据索引关闭这个层
            parent.layer.close(index);
        });

    }else{
        // 失败，提示用户操作失败，页面不动
        layer.msg('操作失败', {icon: 2,time:1000});
    }
}

function responseClinetFlush(data){
    // 成功，提示用户操作成功，然后关闭添加的层，刷新一下查询列表
    if(data.status == 'success'){
        // 提示框1s后关闭会调用回调函数
        layer.msg('操作成功', {icon: 1,time:1000},function(){
            location.reload();
        });

    }else{
        // 失败，提示用户操作失败，页面不动
        layer.msg(data.msg, {icon: 2,time:1000});
    }
}

function responceCLinetToLogin(data,callback){
    if(data.status == 'success'){
        layer.msg('操作成功', {icon: 1,time:1000},function(){
            callback();
            // location.href = "http://localhost/shop-home/toLoginUserPage";
        });
    }else{
        layer.msg(data.msg, {icon: 2,time:1000});
    }
}


// 把from对象转成一个对象
function formToObject(form){

    // 1.获取表单数据
    var formArray = form.serializeArray();

    // 2.封装成一个js对象
    var param = new Object();
//                param.id = 10; // 给param对象添加一个id属性，值是10
    for(var i =0;i<formArray.length;i++){
        var name = formArray[i].name; // 获取表单的name属性
        var value = formArray[i].value; // 获取表单的name属性

        // 给param德行添加属性
        param[name]=value; // param.id = 10
    }

    return param;
}
