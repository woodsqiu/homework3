<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SpringMVC 测试页</title>


    <script type="text/javascript" src="/js/jquery.min.js"></script>


    <style>
        div{
            padding:10px 10px 0 10px;
        }
    </style>
</head>
<body>


    <div>
        <fieldset>
            <p>注册</p>

            <form method="post" action="access/registry">
                用户名:<input type="text" name="name"/>
                密码:<input type="password" name="password"/>
                <input type="submit" value="注册"/>
            </form>

        </fieldset>
    </div>





</body>
</html>
