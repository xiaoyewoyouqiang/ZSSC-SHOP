<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org/">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<h3>这是Freemarker的演示</h3>
username:${username!''}<br>
age:${age}<br>
<hr>
<h3>演示获取对象属性</h3>
user.username:${user.username}<br>
user.sex:${user.sex}<br>
性别:${(user.sex == 1)?string('男','女')}<br>

<hr>
<h3>演示比遍历集合</h3>
    <#list userList as user>
            id:${user.id}<br>
            usernam:${user.username}<br>
            index:${user_index}<br>
    -----------------------------------<br>
    </#list>
</body>
<hr>
<h3>演示逻辑判断</h3>

<#if (age > 50)>
        老年人。。。。
    <#elseif (age > 30)>
            中年人。。。
    <#else>
        年轻人。。。
</#if>
</html>