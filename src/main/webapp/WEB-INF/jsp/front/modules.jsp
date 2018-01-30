<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>项目展示</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="/achievement/source/bootstrap-3.3.7-dist/css/bootstrap.css">
	<script type="text/javascript" src="/achievement/source/jquery-3.2.1/jquery.js"></script>
	<script type="text/javascript" src="/achievement/source/bootstrap-3.3.7-dist/js/bootstrap.js"></script>
	<script type="text/javascript">
	
	$(window).load(function(){
		 
	    $("#model_descript img").addClass("img-responsive center-block");
	 
	})
	</script>
	<style type="text/css">
		.model_name{
			
			border-color: #A1A7AD;
			border-width: 1px;
		}
		.model_descript{
			
			border-color: #A1A7AD;
			border-width: 1px;
		}
		.font{
			
			border-color: #A1A7AD;
			border-width: 1px;
		}
		.font_inner{
			height: 200px;
			margin-top:10px;
			
			border-color: #A1A7AD;
			border-width: 1px;
		}
		#model_descript{
			word-break: break-all;
			word-wrap: break-word;
		}
		#model_name{
			text-align: center;
			font-size: 2em;
		}
		.navbar-fixed-top{
			padding:0px;
			margin:0px;
			top:0;
			left:0;
		}
		.breadcrumb li a{
			font-size:1.5em;
		}
		
		.breadcrumb a{
		cursor:pointer;
		target:_self;
		font-size:1.5em;
	  	}
	</style>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
	<div class="navbar-fixed-top col-sm-12 col-md-12">
		<div class="navbar-inner">
			<div class="container-fluid">
				<ol class="breadcrumb">
					<li><a href="/achievement/front/index">首页</a> <span class="divider"></span></li>
					<li><a href="javascript:void(0);">展示</a></li>
					<a class="pull-right" herf="/achievement/back">登录</a>
				</ol>
			
			</div>
			
		</div>
	</div>
     
   	<div style="height: 60px;"></div>
    <div class="container col-md-12 col-sm-12">
      <div class="row">
        <div class="col-md-12">
          <ul id="mytab" class="nav nav-tabs">
            <li class="active">
              	<a href="#xw1" data-toggle='tab'>视频</a> 
            </li>
            <li>
            	<a href="#xw2" data-toggle='tab'>图片</a>
            </li>
            <c:forEach items="${modules }" var="module">
             <li>
              <a href='#${module.modId }' data-toggle='tab'>${module.modName }</a>
            </li>
            </c:forEach>
          </ul>
   
        <div class="tab-content">
            <div class="tab-pane active fade in" id="xw1">
              <div class="col-md-12 col-sm-12 font">
              		<h3>成果名称:${achievement.achName }</h3>
              </div>
              <div class="col-md-12 col-sm-12 font_inner" >
              	<div class="col-md-7 col-sm-7">
              		<video controls class="col-md-10 col-sm-10" autoplay>
						<source src="${achievement.achVideoPath }" >
					</video>
				</div>
				<div class="col-md-5 col-sm-5">
					<h4>成果描述:</h4>
					<div>${achievement.achDescribe }</div>
				</div>
              </div>
              </div>
           <div class="tab-pane fade in" id="xw2">
	           	<div class="col-md-12 font">
	              		<h3>成果名称:${achievement.achName }</h3>
	            </div>
	            <div class="col-md-12 col-sm-12 font_inner" >
	            <div class="col-md-2 col-sm-2"></div>
					<div class="col-md-8 col-sm-8">
						<img src="${achievement.achImagePath }" class="img-responsive">
					</div>
              	</div>
           </div>
          <c:forEach items="${modules }" var="module">
            <div class="tab-pane fade in" id="${module.modId }">
          	<div class="col-md-12 font">
            </div>
            <div class="col-md-12 " style="margin-top: 10px;">
              	<div class="col-md-12 " id="model_name">${module.modName }</div>
              	<div class="col-md-3"></div>
              	<div class="col-md-6" id="model_descript">${module.modContent }</div>
              	<div class="col-md-3"></div>
            </div>
        </div>
           </c:forEach>
       </div>
      </div>
     </div>
    </div>
  </body>
</html>
