<%@ page language="java" pageEncoding="UTF-8"%>

<html>
<head>
	<title>登陆超时</title>
</head>
<body>
<div id="error_info"></div>
<script type="text/javascript">
(function(){
	var error_info = "登陆超时，请重新登陆";
	try{
		new $.TipBox({
			type : "tip",
			str : error_info,
			setTime : 1500,
			hasMask : true,
			callBack : function(){
				location.reload(true);
			}
		});
	} catch (e) {
		document.getElementById("error_info").innerHTML = error_info;
		setTimeout(function(){
			document.location = "/${project_name}/index";
		},1000);
	}
})();
</script>
</body>
</html>