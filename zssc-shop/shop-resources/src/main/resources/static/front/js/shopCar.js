
$(function () {
    sendRequesToken("http://localhost/shop-car/carController/shoCarNum",null,function(data){
        $("#shoCarNum").text("("+data.data+")");
    });
})