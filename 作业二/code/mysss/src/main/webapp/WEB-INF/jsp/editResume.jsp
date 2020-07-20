<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>新增学生</title>
</head>
<body>

<form method="post" action="/resume">
    <input type="hidden" name="_method" value="put"/>
    <input type="hidden" name="id" value="${resume.id}"/><br/>
    姓名：<input name="name" value="${resume.name}"/><br/>
    手机：<input name="phone" value="${resume.phone}"/><br/>
    地址：<input name="address" value="${resume.address}"/><br/>
    <input type="submit" value="更新"/><br/>
</form>
</body>
</html>