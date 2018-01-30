<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>编辑成果</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link href="/achievement/source/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" media="screen" />

<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script type="text/javascript" src="/achievement/source/jquery-3.2.1/jquery.js"></script>
<script src="/achievement/source/bootstrap/js/bootstrap.js"></script>
<script src="/achievement/source/bootstrap/js/bootstrap.min.js"></script>
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
<style type="text/css">
	#achievement_des{
		width:567px;
		height:150px;
		resize: none;
	}
</style>

<script type="text/javascript">
	Array.prototype.indexOf = function(val) {
		for (var i = 0; i < this.length; i++) {
			if (this[i] == val)
				return i;
		}
		return -1;
	};

	Array.prototype.remove = function(val) {
		var index = this.indexOf(val);
		if (index > -1) {
			this.splice(index, 1);
		}
	};
	function deleteAch(achId) {

		saveDataAry = [];
		saveDataAry.push(achId);
		console.log(saveDataAry);
		$.ajax({
			type : 'post',
			url : '/achievement/back/user/achievement/delete',
			dataType : 'json',
			contentType : "application/json",
			data : JSON.stringify(saveDataAry),
			success : function(data, statue) {
				//window.open("/achievement/back/user?achStatus=0", "_self");
			}
		});
	}
	saveModAry = [];
	function addCheck(modId) {
		saveModAry.push(modId);
		console.log(saveModAry);
	}
	function removeCheck(modId) {
		saveModAry.remove(modId);
		console.log(saveModAry);
	}
	function deleteModule() {
		console.log(saveModAry);
		$.ajax({
			type : 'post',
			url : '/achievement/back/user/deleteModule',
			dataType : 'json',
			contentType : "application/json",
			data : JSON.stringify(saveModAry),
			success : function(data, statue) {
				window.open("/achievement/back/user/achievement/modify?achId="
						+ '${achievement.achId }', "_self");
			}
		});
	}
	function bounce(t) {
		
		var form = new FormData(document.getElementById("achievementForm"));
		$.ajax({
			url : '/achievement/back/user/achievement/saveAjax',
			type : "post",
			data : form,
			/* 执行执行的是dom对象 ，不需要转化信息*/
			processData : false,
			contentType : false,
			/* 指定返回类型为json */
			dataType : 'json',
			xhr : function() {
				myXhr = $.ajaxSettings.xhr();
				if (myXhr.upload) {
					myXhr.upload.addEventListener('progress', function(e) {
						if (e.lengthComputable) {
							var percent = Math.floor(e.loaded / e.total * 100);
							if (percent <= 100) {
								$("#bar").html("正在上传。。。。"+percent);
								$("#bar").css("width", percent + "%");
							}
							if (percent >= 100) {
								$("#bar").html("成功上传");

							}
						}
					}, false);
				}
				return myXhr;
			},
			success : function() {
				
				if (t == "mod") {
					url = "/achievement/back/user/addModule?achId="
							+ '${achievement.achId }';
					
				} else{
					url = "/achievement/back/user/achievement/modify?achId="
						+ '${achievement.achId }';
				}
				window.open(url, "_self");
			},
			error : function(e) {
				console.log("失败");
			}
		});

	}
	function prepare(obj) {
		var file = $(obj);
		var reader = new FileReader();
		reader.readAsDataURL(file[0].files[0]);
		if (file.prop("name") == "image") {
			console.log("image");
			reader.onload = function() {
				$("#achievmentImage").prop("src", reader.result);
			};
		} else {
			console.log("video");
			reader.onload = function() {
				$("#achievmentVideo").prop("src", reader.result);
			};
		}

	}
</script>
</head>

<body>

	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse"> <span class="icon-bar"></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span>
				</a> <a class="brand" href="/achievement/front/index" target="_blank">首页</a>


			</div>
		</div>
	</div>

	<div style="height: 60px;"></div>
	<div class="container">

		<div class="col-md-10">
			<ul id="mytab" class="nav nav-tabs">
				<li class="active"><a href="#xw1" data-toggle='tab'>基本信息</a></li>
				<li><a href="#xw2" data-toggle='tab'>栏目信息</a></li>
			</ul>
		</div>
		<div class="tab-content">
			<div class="tab-pane active fade in" id="xw1">

				<form action="/achievement/back/user/achievement/save"
					id="achievementForm" enctype="multipart/form-data" method="post">
					<div class="col-md-12">
						<span>成果名字：</span> <input value="${achievement.achName }"
							type="text" name="achName">
					</div>
					<div class="kb-input-content col-md-12 col-sm-12 " style="margin: 10px 0px 10px 0px;">
						<span>成果描述：</span> <textarea 
							 name="achDescribe" id="achievement_des" maxlength="200">${achievement.achDescribe }</textarea>
				            <span class="input-number-tip">0~200个字</span>
					</div>
					<div class="col-md-12 col-sm-12" style="margin: 10px 0px 10px 0px;">
						<sapn>成果图片：</sapn>
						<input type="file" name="image" onchange="prepare(this);"
							id="achievementImg">

					</div>
					<div class="col-md-6 col-sm-6" style="margin: 10px 0px 10px 75px;">
						<img id="achievmentImage" alt=""
							src="${achievement.achImagePath }" width="640" height="480">
					</div>
					<div class="col-md-12 col-sm-6" style="margin: 10px 0px 10px 0px;">
						<span>成果视频：</span> <input type="file" name="video"
							onchange="prepare(this);" id="achievementVid">
					</div>
					<div class="col-md-6 col-sm-6" style="margin: 10px 0px 10px 75px;">
						<video width="640" height="480" id="achievmentVideo"
							src="${achievement.achVideoPath }" controls> </video>
					</div>
					<div style="maigin: 10px 0px 10px 0px;">
						<span>成果分类：</span> <select name="achClassify">
							<option value="科研">科研</option>
							<option value="生活">生活</option>
						</select>
					</div>
					<input type="hidden" value="${achievement.achId }" name="achId">
					<input value="保存" type="button " class="btn btn-success"
						onclick="bounce('achievement');"> <input value="退出" id="back"
						type="button" class="btn btn-warning"
						<c:if test="${operator == 'add'}"> onclick="deleteAch('${achievement.achId }');" </c:if>
						<c:if test="${operator == 'modify'}"> onclick="window.open('/achievement/back/user?achStatus=0','_self');"</c:if> >
				</form>
			</div>
			<div class="col-md-10">
			<span>上传进度条：</span>
				<div class="progress progress-striped progress-success active"
					id="progress">
					<div style='width: 0%' class='bar' id="bar"></div>
				</div>
			</div>

			<div class="tab-pane fade in" id="xw2">

				<input type="button" value="添加栏目" class="btn btn-success"
					onclick="bounce('mod');"> <input type="button" value="删除栏目" class="btn btn-danger"
					onclick="deleteModule();">
				<table class="table table-hover">
					<thead>
						<tr>
							<th><input type="checkbox" /></th>
							<th>栏目简称</th>
							<th colspan="2">栏目描述</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="module" items="${modules }">

							<tr>
								<td><input type="checkbox"
									onclick="if(this.checked == true) {addCheck('${module.modId }');} else { removeCheck('${module.modId }'); }" /></td>
								<td>${module.modName }</td>
								<td colspan="2">${module.modDescribe }</td>
								<td><input type="button"
									class="btn btn-warning addSlideShow" value='修改'
									onclick="window.open('/achievement/back/user/modifyModule?achId=${achievement.achId }&modId='+'${module.modId }','_self');">
									</button></td>
							</tr>
						</c:forEach>

					</tbody>
				</table>
			</div>
		</div>
</body>
<script type="text/javascript">
	// 中文字符判断(计算字符数)
function getStrLength(str) { 
    var len = str.length; 
    var reLen = 0; 
    for (var i = 0; i < len; i++) {        
        if (str.charCodeAt(i)  >= 0  || str.charCodeAt(i) <= 128) { 
            // 全角    
            reLen += 1; 
        } else { 
            reLen+=2; 
        }
    } 
    return reLen;    
}
 
//计算字符数(200个字内，用于回复框)
function count_str(value,total_count){
    var len = getStrLength(value);
    count = "";
    count = total_count-len; 
    return '已经输入<span>'+len+'</span>字,'+'还可以输入<span>'+count+'</span>字';
}
 
  /* 文本区域字数检测 */
$("#achievement_des").on("keyup mouseup",function(event){
    var count_html=count_str($(this).val(),200);
    $(".kb-input-content").find(".input-number-tip").html(count_html);
});

</script>
</html>
