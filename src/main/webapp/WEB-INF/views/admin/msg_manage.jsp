<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.threebody.com/cp" prefix="cp" %>
<!DOCTYPE HTML>
<html>
<head></head>
<body>
<form action="admin/roleManage" method="post" >
	<div class="tab-search">
		<ul>
			<li>消息名称：<input type="text" name="name" value="${name}"/></li>
			<li>消息类型：<cp:dictSelect dictCode="d_messageType" name="type" value="${type}"/></li>
			<li>
				<a href="javascript:void(0);" class="easyui-linkbutton" 
						data-options="iconCls:'icon-search'" onclick="$css.tabSearch(this)">查询</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" 
						data-options="iconCls:'icon-clear'" onclick="$css.form_reset(this)">重置</a>
			</li>
		</ul>
	</div>
</form>
<div class="btn-header">
	<a href="page/addOrUpdateMsg" class="easyui-linkbutton addMsg" data-options="iconCls:'icon-add'" >创建消息</a>
</div>
<table class="bordered" id="msgList" >
	<tr>
		<th style="width:15%;">消息名称</th>
		<th style="width:15%;">消息类型</th>
		<th style="width:50%;">消息内容</th>
		<th style="width:20%;">操作</th>
	</tr>
	<c:forEach var="msg" items="${page.result}" >
	<tr>
		<td>${msg.name}</td>
		<td><cp:dictType dictCode="d_messageType" clauseCode="${msg.type}"/></td>
		<td>${msg.str}</td>
		<td>
			<a href="page/addOrUpdateMsg?id=${msg.id}" class="editMsg fa fa-edit" >
				<span>编辑</span>
			</a>
			<a href="msg/delete?id=${msg.id}" class="delMsg fa fa-trash" >
				<span>删除</span>
			</a>
		</td>
	</tr>
	</c:forEach>
</table>

<div class="pageSplit">
	<cp:pageSplit page="${page}" />
</div>
<script>
$(function(){
	$("#msgList").next(".pageSplit").find("a.page_btn").on("click",$css.jumpPage);
	$("a.addMsg").on("click",{tabName:"创建消息"},$css.editRecord);
	$("#msgList")
	.on("click","a.delMsg",{url:"admin/msgManage"},$css.delRecord)
	.on("click","a.editMsg",{tabName:"编辑消息"},$css.editRecord);
	
});
</script>
</body>
</html>