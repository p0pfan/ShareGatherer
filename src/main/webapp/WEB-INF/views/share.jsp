<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons"
      rel="stylesheet">
        <!-- Bootstrap -->
        <link rel = "stylesheet" type = "text/css" href ="<%=request.getContextPath() %>/resources/css/bootstrap.min.css">

        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/css/material/bootstrap-material-design.css">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/css/material/ripples.min.css">

<title>Share Our sharing</title>
</head>
<body>
	<div class="navbar navbar-default">
      <div class="container-fluid">
        <div class="navbar-header">
        <!--   <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-responsive-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button> -->
          <a class="navbar-brand" href="#">Share what you want to share</a>
        </div>
        <div class="navbar-collapse collapse navbar-responsive-collapse">
          <ul class="nav navbar-nav">
            <li class="active  ripple-effect"><a href="javascript:void(0)">Timeline</a></li>
          </ul>
          <form class="navbar-form navbar-left">
            <div class="form-group">
              <input type="text" class="form-control col-md-8" placeholder="Search">
            </div>
          </form>
        </div>
      </div>
    </div>
    <div class="container">
        <div class="col-md-12" id = "content">
                       
        </div>
    </div>
    
    <script src = "<%=request.getContextPath() %>/resources/js/jquery-3.0.0.min.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/material/material.min.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/material/ripples.min.js"></script>
	<script src="<%=request.getContextPath() %>/resources/js/base.js"></script>
	<script src="<%=request.getContextPath() %>/resources/js/getShare.js"></script>
    <script type="text/javascript">
        $( document ).ready(function() 
            {
                $(function(){
                    $.material.init();
                });
            });
        $.ajax({
    		url: basePath+'/share/all',
    		type: 'GET',
    		dataType: 'json',
    		success: function(data){
    			insert(data);
    			
    		}
    	});
       
    	
    </script>

	
</body>
</html>