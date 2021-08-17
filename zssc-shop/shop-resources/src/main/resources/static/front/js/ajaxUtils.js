


// 发送一个请求携带token
function sendRequesToken(url,param,callback){
    $.ajax(
        {
            type:"POST",
            url:url,
            dataType:"JSON",
            data:param,
            beforeSend:function(XMLHttpRequest){ // 是在发送请求之前调用
                var token = localStorage.getItem('login-user');
                if(token){
                    XMLHttpRequest.setRequestHeader("Authorization", token);
                }
            },
            success:function(data){
                callback(data);
            },
            error:function(){
            }
        }
    );
}