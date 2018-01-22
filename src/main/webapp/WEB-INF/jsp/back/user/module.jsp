<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>完整demo</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script type="text/javascript" charset="utf-8"
	src="/achievement/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="/achievement/ueditor/ueditor.all.min.js">
	
</script>

<link rel="stylesheet" type="text/css"
	href="/achievement/source/vendors/bootstrap-wysihtml5/src/bootstrap-wysihtml5.css"></link>
<link href="/achievement/source/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" media="screen">
<link href="/achievement/source/assets/styles.css" rel="stylesheet"
	media="screen">
<script src="/achievement/source/vendors/jquery-1.9.1.min.js"></script>
<script src="/achievement/source/bootstrap/js/bootstrap.min.js"></script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8"
	src="/achievement/ueditor/lang/zh-cn/zh-cn.js"></script>

</head>
<body>


	<form class="container-fluid" method="post" 
	<c:if test="${modOpera == 'add' }">
	action="/achievement/back/user/saveModule"
	</c:if>
	<c:if test="${modOpera == 'modify' }">
	action="/achievement/back/user/saveModifyModule"
	</c:if>
	 method="post">
		<div class="row-fluid " role="form">
				<div class="form-group">
					<label for="name">模块简称</label> <input type="text" name="modName"
					value="${module.modName }"		class="form-control" placeholder="请输入2~4个汉字或字符"
					maxlength="4" minlength="2">
				</div>
				<div class="form-group">
					<label for="name">模块简述</label> <input type="text" name="modDescribe"
				value="${module.modDescribe }"		class="form-control" placeholder="模块简述">
				</div>
		</div>
		<div class="row-fluid">
			<div class="span12" id="content">
				<div class="row-fluid">
					<!-- block -->
					<div class="block">
						<div class="navbar navbar-inner block-header">
							<div class="muted pull-left">模块内容编辑</div>
						</div>
						<div class="block-content collapse in">
								<script id="editor" type="text/plain" name="modContent"
									style="width:100%;height:700px;">
									${module.modContent }
								</script>
								<input type="submit" value="提交">
								<input type="hidden" value='${achId }' name="achId">
								<input type="hidden" value='${module.modId }' name="modId">
						</div>
					</div>
					<!-- /block -->
				</div>
			</div>
		</div>
	</form>

	<script type="text/javascript">
		var ue = UE.getEditor('editor');
	</script>

</body>
</html>
