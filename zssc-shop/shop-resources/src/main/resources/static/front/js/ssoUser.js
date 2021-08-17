$(function(){ // 页面加载完成后调用
    // 在首页显示用户的登录状态
    $.ajax(
        {
            type:"GET",
            url:"http://localhost/auth/getUserByToken",
            dataType:"JSON",
            beforeSend:function(XMLHttpRequest){ // 是在发送请求之前调用
                // 把tokne设置请求头中
                // 1.获取token
                var token = localStorage.getItem('login-user');

                if(token){
                    // 如果token有内容就携带到请求中
                    // 2.把token值写入到请求头中
                    XMLHttpRequest.setRequestHeader("Authorization", token);
                }
            },
            success:function(data){
                if(data.status == 'success'){
                    $("#logMsg").html("您好，欢迎【"+data.data+"】来到宅客微购，<a href='javascript:localStorage.removeItem(\"login-user\");location.reload();'>注销</a>");
                }else{
                    $("#logMsg").html("您还没有登录，请<a href='http://localhost/shop-sso/toLoginUserPage'>登录</a>，<a href='http://localhost/shop-sso/toRegisterUserPage'>免费注册</a>");
                }
            },
            error:function(){
            }
        }
    );
})