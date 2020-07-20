<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>简历列表</title>
</head>
<body>
<div border="1px solid" width="900px">
    <form method="get" style=”float:right” action="/resume/redirectToSave">
        <input type="submit" value="新增"/>
    </form>
    <table border="1px solid" width="900px">
        <tr>
            <td>编号</td>
            <td>姓名</td>
            <td>手机</td>
            <td>地址</td>
            <td>编辑</td>
            <td>删除</td>
        </tr>
        <c:forEach var="resume" items="${list }">
            <tr>
                <td>${resume.id }</td>
                <td>${resume.name }</td>
                <td>${resume.phone }</td>
                <td>${resume.address }</td>
                <td>
                        <%--get请求根据id查询resume--%>
                    <form method="get" action="/resume/${resume.id}">
                        <input type="submit" value="编辑"/>
                    </form>
                </td>
                <td>
                        <%--根据id删除resume--%>
                    <form method="post" action="/resume/${resume.id}">
                        <input type="hidden" name="_method" value="delete"/>
                        <input type="submit" value="删除"/>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>
