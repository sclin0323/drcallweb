<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>DrCALL登入</title>

<link rel="shortcut icon" href="img/favicon.png">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootbox-4.2.0/bootbox.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
	
		$("#forgetPasswordForm").on("submit", function(event) {
			event.preventDefault();
			
			var tel = $("#forgetPasswordForm").find("#tel").val();
			var email = $("#forgetPasswordForm").find("#email").val();
			
			//alert(tel+" "+email);

 			$.ajax({
 				type : "POST",
				url : "app/login/forgetPassword",
				async: true,
 				data : {
 					tel : tel, email : email
 				},
 				beforeSend : function(data){
 					// SHOW AJAX WAITING IMAGES
					$("#ajaxWaiting").show();
 				},
 				complete:function(){
					// HIDE AJAX WAITING IMAGES
                    $("#ajaxWaiting").hide();
                },
 				success : function(data) {
					
					if(data.status == 'true'){
						bootbox.alert("密碼變更完成，請至Email收取密碼變更信件。");
					} else if(data.status == 'false') {
						bootbox.alert("輸入的手機和Email檢查不正確，請重新輸入。");
					}

 				},
				error : function(xhr, ajaxOptions, thrownError) {
				 	alert(xhr.status);
         			alert(thrownError);
 				}
 			});
		
		});

	});
 </script>

</head>

<body>
	<br>
	<div class="container">
		<div class="row">
		
			<div class="bs-callout bs-callout-info col-md-9 col-md-offset-1">
				<h4 class="content-title">Dr.CALL 忘記密碼</h4>
				<div class="content-text">
					輸入「手機」和「Email」送出核對，確認無誤後，系統將發送新密碼於Email信箱。<br>
					(若手機和Email忘記無法登入，請來信或留言告知，DrCall將儘快為您服務。)<br>
				</div>
			</div>
		
			<div class="col-md-5 col-md-offset-1">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">FORGET 忘記密碼</h3>
					</div>
					<div class="panel-body">
					
						
						<form id="forgetPasswordForm" class="form-horizontal" action="#" method="post">
						
						

							<div class="form-group">
								<label for="inputEmail3" class="col-sm-2 control-label">*手機</label>
								<div class="col-sm-10">
									<input id="tel" type="text" name="tel" class="form-control" placeholder="手機號碼(ex:0910888888)" required/>

								</div>
							</div>
							<div class="form-group">
								<label for="inputPassword3" class="col-sm-2 control-label">*Email</label>
								<div class="col-sm-10">
									<input id="email" type="email" name="email" class="form-control" placeholder="Email" required>
								</div>
							</div>

							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-6">
									<button type="submit"class="btn btn-primary btn-block">確認送出</button> 
									
								</div>
							</div>
						</form>
						
						
					</div>
				</div>
			</div>
			
		</div>
	</div>
   
     <!-- AJAVX Waiting Image -->
	<div id="ajaxWaiting" class="ajaxWaiting" style="display: none;">
		<img src="${pageContext.request.contextPath}/images/ajax-loader.gif">
	</div>
  </body>
</html>