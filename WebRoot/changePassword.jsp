<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  

// FOR CHANGE PASSWORD
String memberId = request.getParameter("memberId");
String email = request.getParameter("email");
request.setAttribute("memberId", memberId);
request.setAttribute("email", email);
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
	

		if('${memberId}' == "" || '${email}' == ""){
			$("#changePasswordForm").remove();
			bootbox.alert("發生錯誤!! 請依照變更密碼流程進入。若有任何問題歡迎來新或留言告知，DrCall將儘快為您服務。");
		} else {
			$("#changePasswordForm").find("#memberId").val('${memberId}');
			$("#changePasswordForm").find("#email").val('${email}');
		
		}
		
		
		$("#changePasswordForm").on("submit", function(event) {
				event.preventDefault();
		
                var passwd = $("#changePasswordForm").find("#passwd").val();
                var repasswd = $("#changePasswordForm").find("#repasswd").val();
                var memberId = $("#changePasswordForm").find("#memberId").val();
                var email = $("#changePasswordForm").find("#email").val();
                
				
                $.ajax({
                    type: "POST",
                    url: "app/login/changePassword",
                    async: true,
                    data: {
                        passwd: passwd, memberId: memberId, email: email
                    },
                    beforeSend: function (data) {
                    
                       	if(passwd != repasswd){
							$("#changePasswordForm").find('input[id="passwd"]').val('');
							$("#changePasswordForm").find('input[id="repasswd"]').val('');
							bootbox.alert("密碼輸入不一致，請重新輸入。");
							return false;
						}
						// SHOW AJAX WAITING IMAGES
						$("#ajaxWaiting").show();
                    },
 					complete:function(){
						// HIDE AJAX WAITING IMAGES
                    	$("#ajaxWaiting").hide();
               	 	},
                    success: function (data) {

                        var status = data.status;
                       
                         if (status == "false") {
                            bootbox.alert("手機帳號以及Email資料錯誤。有任何問題歡迎來信或留言知，DrCall將儘快為您服務。");
                         } else if (status == "true") {                  
                         	bootbox.alert("密碼變更完成，即可重新登入，DrCall關心您的健康。",function() {
  								window.location.href = "login.jsp";           	
							});       
                         }

                    },
                    error: function (xhr, ajaxOptions, thrownError) {
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
		
			<div class="col-md-6 col-md-offset-3">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">CHANGE 變更密碼</h3>
					</div>
					<div class="panel-body">
					
						
						<form id="changePasswordForm" class="form-horizontal" method="post">
						
						

							<div class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">*新密碼</label>
								<div class="col-sm-9">
									<input type="password" class="form-control" id="passwd" placeholder="*密碼" required>
								</div>
							</div>
							<div class="form-group">
								<label for="inputPassword3" class="col-sm-3 control-label">*重覆新密碼</label>
								<div class="col-sm-9">
									<input type="password" class="form-control" id="repasswd" placeholder="*再次輸入密碼" required>
								</div>
							</div>

							<div class="form-group">
								<div class="col-sm-offset-3 col-sm-6">
									<button type="submit"class="btn btn-primary btn-block">確認送出</button> 
									<input id="memberId" type="hidden">
									<input id="email" type="hidden">
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