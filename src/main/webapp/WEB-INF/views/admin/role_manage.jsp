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
			<li>角色名称：<input type="text" name="roleName" value="${roleName}"/></li>
			<li>
			创建时间：
			<input type="text" class="Wdate" name="createTimeStart" value="${createTimeStart}" id="roleCreateTimeStart"
					onclick="WdatePicker({maxDate:'#F{$dp.$D(\'roleCreateTimeEnd\')}'})" />
			至
			<input type="text" class="Wdate" name="createTimeEnd" value="${createTimeEnd}" id="roleCreateTimeEnd"
					onclick="WdatePicker({minDate:'#F{$dp.$D(\'roleCreateTimeStart\')}'})" />
			</li>
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
	<a href="page/addOrUpdateRole" class="easyui-linkbutton addRole" data-options="iconCls:'icon-add'" >创建角色</a>
</div>
<table class="bordered" id="roleList" >
	<tr>
		<th style="width:20%;">角色名称</th>
		<th style="width:25%;">创建时间</th>
		<th style="width:35%;">备注</th>
		<th style="width:20%;">操作</th>
	</tr>
	<c:forEach var="role" items="${page.result}" >
	<tr>
		<td>${role.roleName}</td>
		<td><fmt:formatDate value="${role.createTime}" type="date" pattern="yyyy年MM月dd日 HH:mm"/></td>
		<td>${role.remark}</td>
		<td>
			<a href="javascript:void(0);" class="role-menu fa fa-location-arrow"  roleid="${role.id}">
				<span>菜单配置</span>
			</a>
			<a href="page/addOrUpdateRole?id=${role.id}" class="editRole fa fa-edit" >
				<span>编辑</span>
			</a>
			<a href="role/delete?id=${role.id}" class="delRole fa fa-trash" >
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
	$("#roleList").next(".pageSplit").find("a.page_btn").on("click",$css.jumpPage);
	$("a.addRole").on("click",{tabName:"创建角色"},$css.editRecord);
	$("#roleList")
	.on("click","a.delRole",{url:"admin/roleManage"},$css.delRecord)
	.on("click","a.editRole",{tabName:"编辑角色"},$css.editRecord);
	var openRoleMenu = function(event){
		var roleId = $(event.currentTarget).attr("roleid");
		var save = $css.buildDialogSave({formId : "role_menu",dialogId : "roleMenu"});
		$("<div></div>").dialog({
		    title: "配置角色权限",
			width: 600,
			height: 400,
			closed: false,
			cache: false,
			modal: true,
			href: "menu/menuList?id="+roleId,
			onClose : function() {
                $(this).dialog("destroy");
            },
			buttons:[{
				text:"保存",
				handler:save,
				iconCls:"icon-save"
			},{
				text:"取消",
				handler:function(){$("#roleMenu").dialog("close");},
				iconCls:"icon-cancel"
			}]
		});
	};
	$("#roleList").on("click","a.role-menu",openRoleMenu);
});
</script>
</body>
</html>