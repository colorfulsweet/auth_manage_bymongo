<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.threebody.com/cp" prefix="cp" %>
<!DOCTYPE HTML>
<html>
<head></head>
<body>
<div style="padding:10px;">
	<form class="addMsgForm" method="post" action="msg/save" onSubmit="return $css.ajaxSubmit(this,'admin/msgManage')">
		<input type="hidden" name="id" value="${msg.id}" />
		<table class="table-input">
			<tr>
				<th style="width:15%;">消息名称</th>
				<td style="width:35%;"><input type="text" name="name" value="${msg.name}" required="required"/></td>
				<th style="width:15%;">消息类型</th>
				<td style="width:35%;"><cp:dictSelect dictCode="d_messageType" name="type" value="${msg.type}"/></td>
			</tr>
			<tr>
				<th>消息内容</th>
				<td colspan="3"><textarea name="str" >${msg.str}</textarea></td>
			</tr>
			<tr>
				<th style="width:15%;">延迟时间</th>
				<td style="width:85%;" colspan="3"><input type="text" name="setTime" value="${msg.setTime}" />毫秒</td>
			</tr>
		</table>
		<div class="btn-box">
		<input class="btn green medium" type="submit" value="保存" />
		</div>
	</form>
</div>
</body>
</html>