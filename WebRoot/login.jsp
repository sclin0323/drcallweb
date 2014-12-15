<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  

// FOR RECOMMEND
String isRecommend = request.getParameter("isRecommend");
String recommendCode = request.getParameter("recommendCode");
String tel = request.getParameter("tel");

if("true".equals(isRecommend)){
	request.setAttribute("isRecommend", true);
	request.setAttribute("recommendCode", recommendCode);
	request.setAttribute("tel", tel);
}

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
	
		if('${isRecommend}' == 'true'){
			$("#create_account_form").find("#isRecommend").prop("checked", true);
			$("#create_account_form").find("#referencesCode").prop("disabled",false);
    		$("#create_account_form").find("#referencesCode").val('${recommendCode}');
    		$("#create_account_form").find("#memberId").val('${tel}');
		}
	
		 //TEL CHECK 手機驗證
		 $("#create_account_form").find("#checkBtn").on("click", function (event) {
						
                var memberId = $("#create_account_form").find("#memberId").val();

                $.ajax({
                    type: "POST",
                    url: "app/login/checkMemberId",
                    async: false,
                    data: {
                        memberId: memberId
                    },
                    beforeSend: function (data) {

                        var re = /^[0-9]+$/;
                        if (!re.test(memberId) || memberId.length != 10) {
                            $("#create_account_form").find("#memberId").val('');
                            $("#create_account_form").find("#memberId").attr("placeholder", "格式錯誤ex:0970888888");
                            return false;
                        }
                    },
                    success: function (data) {

                        var status = data.status;

                         if (status == "false") {
                            bootbox.alert("此手機號已經存在申請使用，若有任何帳號申請問題，可來信告知，Dr.Call將儘快為您處理，感謝您!!");
                         } else if (status == "true") {
                                              
                         	bootbox.alert("該手機驗證成功，手機驗證碼將寄送簡訊至您的手機，收到後，輸入表單並完成其他欄位後送出。",function() {
  								$("#create_account_form").find("#memberId").prop('disabled', true);
                             	$("#create_account_form").find("#checkBtn").prop('disabled', true);
                             	$("#create_account_form").find("#submitBtn").prop('disabled', false);                         	
							});       
                         }

                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                        alert(xhr.status);
                        alert(thrownError);
                    }
                });

        	});
		
		
		// create new account
		$("#create_account_form").find("#memberId").focus(function() {
  			$("#create_account_form").find("#caf_note1").fadeIn( "slow", function() {
    			$(this).css('color', '#008080');
  			});
		});
		
		$("#create_account_form").find("#email").focus(function() {
  			$("#create_account_form").find("#caf_note2").fadeIn( "slow", function() {
    			$(this).css('color', '#008080');
  			});
		});
		
		$("#create_account_form").on("submit", function(event) {
			event.preventDefault();

			var isRecommend = $("#create_account_form").find('input[id="isRecommend"]').is(':checked');
			var recommendId = $("#create_account_form").find('input[id="referencesCode"]').val();
			
			var memberId = $("#create_account_form").find('input[id="memberId"]').val();
			var identifyCode = $("#create_account_form").find('input[id="identifyCode"]').val();	
			var email = $("#create_account_form").find('input[id="email"]').val();
			var name = $("#create_account_form").find('input[id="name"]').val();
			var gender = $("#create_account_form").find('select[id="gender"]').val();
			
			var address = $("#create_account_form").find('input[id="address"]').val();
			var passwd = $("#create_account_form").find('input[id="passwd"]').val();
			var repasswd = $("#create_account_form").find('input[id="repasswd"]').val();
			
			
			
			var birthMonth = $("#create_account_form").find('select[id="month"]').val();
			var birthDay = $("#create_account_form").find('select[id="day"]').val();
			var birthYear = $("#create_account_form").find('select[id="year"]').val();
			
			$.ajax({
				type : "POST",
				url : "app/login/createNewAccount",
				async: true,
				data : {
					memberId : memberId, identifyCode : identifyCode, email : email, name : name, address : address, passwd : passwd, isRecommend : isRecommend, recommendId : recommendId, birthYear : birthYear, birthMonth : birthMonth, birthDay : birthDay, gender : gender 
				},
				beforeSend : function(data){
					
					if(isNaN(birthDay) || isNaN(birthMonth) || isNaN(birthYear)){
						bootbox.alert("生日輸入有錯誤，請重新輸入。");
						return false;
					}
					
					if(passwd != repasswd){
						$("#create_account_form").find('input[id="passwd"]').val('');
						$("#create_account_form").find('input[id="repasswd"]').val('');
						bootbox.alert("密碼輸入不一致，請重新輸入。");
						return false;
					}
					
					if(gender == null){
						bootbox.alert("請輸入性別");
						return false;
					}
					
					// SHOW AJAX WAITING IMAGES
					$("#ajaxWaiting").show();
					
				},
				complete:function(){
					// HIDE AJAX WAITING IMAGES
                    $("#ajaxWaiting").hide();
                },
				success : function(data) {
				
					var status = data.status;
					
					if(status == '0') {
						$("#create_account_form").find('input[id="passwd"]').val('');
						$("#create_account_form").find('input[id="repasswd"]').val('');
						bootbox.alert("認證碼輸入不正確，請重新輸入，若有任何問題可於首頁留言告知，DrCall將儘快為您服務。");
					} else if(status == '1'){
						$("#create_account_form").find('input[id="passwd"]').val('');
						$("#create_account_form").find('input[id="repasswd"]').val('');
						bootbox.alert("查無此推薦碼，請重新輸入，若有任何問題可於首頁留言告知，DrCall將儘快為您服務。");
					} else if(status == '2'){
						$("#create_account_form").find('input[id="passwd"]').val('');
						$("#create_account_form").find('input[id="repasswd"]').val('');
						bootbox.alert("此推薦碼已經失效，無法使用，若有任何問題可於首頁留言告知，DrCall將儘快為您服務。");
					} else if(status == '3'){
						
						bootbox.alert("恭喜您完成用戶註冊!!親愛的用戶您好，立即輸入帳號密碼即可登入，感謝您的支持。",function() {
  							location.reload();
						});

					} else {
						bootbox.alert("帳號申請失敗，請重新申請，若有任何問題可於首頁留言告知，DrCall將儘快為您服務。",function() {
  							location.reload();
						});
					}				
				},
				error : function(xhr, ajaxOptions, thrownError) {
					alert(xhr.status);
        			alert(thrownError);
				}
			});
			
		
		});
		
		// focus account textfield
		$("#j_username").focus(function() {
			$("#checkPasswdError").hide();
  			//alert( "Handler for .focus() called." );
		});
		
		// 驗證帳號
		$("#identify_form").on("submit", function(event) {
			event.preventDefault();
			
			var j_username = $("#j_username").val();
			var identifyCode = $("#identify_form").find('input[id="identifyCode"]').val();
			
			$.ajax({
				type : "POST",
				url : "app/login/identifyAccount",
				async: false,
				data : {
					j_username : j_username, identifyCode : identifyCode
				},
				success : function(data) {
					// 驗證成功
					$("#identify_div").hide();
					bootbox.alert("帳號驗證成功");
					
				},
				error : function(xhr, ajaxOptions, thrownError) {
					alert(xhr.status);
        			alert(thrownError);
				}
			});
		
		});
		
		
		// 推薦人
		$("#isRecommend").click(function() {
 
   			if($("#isRecommend").prop("checked")) {
    			
    			$("#referencesCode").prop("disabled",false);
    			$("#referencesCode").prop("placeholder","輸入推薦序號");
    			$("#referencesCode").prop("required", true);
    			
    			
   			} else {
     			$("#referencesCode").prop("disabled",true);    
     			$("#referencesCode").prop("placeholder","是否有推薦人(有請打勾)");
   			}
		});

	});
	
	
	
	
 </script>
 
 
     <style type="text/css">
        .ajaxWaiting {vertical-align: middle; width: 46px; height: 38px; padding-top: 8px; float: center; text-align: center; position: fixed; top: 390px;  left: 50%; }
    </style>

</head>

<body>
	<div class="container">
		<div class="row">
			<div class="bs-callout bs-callout-info col-md-9 col-md-offset-1">
				<h4 class="content-title">Dr.CALL 申請會員帳號流程</h4>
				<div class="content-text">
					STEP1. 於申請會員帳號，手機欄位中，輸入「手機號碼」並點擊「手機驗證」 (DrCall將發送8碼驗證碼至您的手機)<br>
					STEP2. 於申請會員帳號，驗證碼欄位中，輸入「驗證碼」 以及 帳號申請所需相關欄位 (若有推薦人勾選推薦人欄位並輸入推薦序號)<br>
					STEP3. 確認所輸入資料後，點擊「建立新帳號」。<br>
					STEP4. 完成帳號建立，輸入手機及密碼即可登入系統。
				</div>
			</div>
		
			<div class="col-md-5 col-md-offset-1">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">LOGIN 會員登入</h3>
					</div>
					<div class="panel-body">
					
						
						<form id="login_form" class="form-horizontal" action="j_spring_security_check" method="post">
						
						

							<div class="form-group">
								<label for="inputEmail3" class="col-sm-2 control-label">*手機</label>
								<div class="col-sm-10">
									<input id="j_username" type="text" name="j_username" class="form-control" placeholder="手機"/>

								</div>
							</div>
							<div class="form-group">
								<label for="inputPassword3" class="col-sm-2 control-label">*密碼</label>
								<div class="col-sm-10">
									<input id="j_password" type="password" name="j_password" class="form-control" placeholder="密碼" value="">
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-10">
									<div class="checkbox">
										<label> <input name="_spring_security_remember_me" type="checkbox">記住我</label>
									</div>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-6">
									<button type="submit"class="btn btn-primary btn-block">登入</button> 
									
								</div>
								<div class="col-sm-3"><a href="forgetPassword.jsp" target="_self">忘記密碼</a></div>
							</div>
						</form>
						
						
					</div>
				</div>
							<c:if test="${param.error != null}">
								<div id="checkPasswdError" class="alert alert-danger">帳號密碼輸入錯誤! 請重新輸入</div>
							</c:if>

			</div>

			<!-- 申請會員 -->
			<div class="col-md-4 col-md-offset-0">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">CREATE ACCOUNT 申請會員帳號</h3>
					</div>
					<div class="panel-body">
						<form id="create_account_form" class="form-horizontal">
						
							<div id="caf_note1" class="form-group" style="display:none">
								<div class="col-sm-12">
									<b>手機即為登入帳號，手機驗證後取得並輸入驗証碼才可完成新帳號的設立。</b>
								</div>
							</div>
						
							<div class="form-group">
                                <div class="col-md-12">
                                    <div class="input-group">
                                        <input id="memberId" class="bootbox-input bootbox-input-text form-control" placeholder="手機  ex:0970123456" autocomplete="off" type="text">
                                        <span class="input-group-btn">
        									<button id="checkBtn" class="btn btn-default" type="button">手機驗證</button>
      									</span>
                                    </div>
                                </div>
                            </div>                   
						
						
							<div class="form-group">

								<div class="col-sm-12">
									<input type="text" class="form-control" id="identifyCode"
										placeholder="*驗證碼" required>
								</div>
							</div>
							
							<div id="caf_note2" class="form-group" style="display:none">
								<div class="col-sm-12">
									<b>請輸入會員真實姓名、性別、出生日，這是院所預約掛號所需的資訊。</b>
								</div>
							</div>
							
							<div class="form-group">

								<div class="col-sm-12">
									<input type="email" class="form-control" id="email"
										placeholder="*Email" required>
								</div>
							</div>
							<div class="form-group">

								<div class="col-sm-12">
									<input type="text" class="form-control" id="name"
										placeholder="*姓名" required>
								</div>
							</div>				

							<div class="form-group">
							
								<div class="col-sm-4">
									<select class="form-control" id="year" >
										<option>民國</option>
										<c:forEach var="i" begin="1" end="103">
				            				<option value=${i}>${i}</option>
				          				</c:forEach>
									</select>
								</div>
							
								<div class="col-sm-4">
									<select class="form-control" id="month">
										<option>月</option>
										
										<c:forEach var="i" begin="1" end="12">
				            				<option value=${i}>${i}</option>
				          				</c:forEach>
										
									</select>
								</div>

								<div class="col-sm-4">
									<select class="form-control" id="day">
										<option>日</option>
										<c:forEach var="i" begin="1" end="31">
				            				<option value=${i}>${i}</option>
				          				</c:forEach>
									</select>
								</div>
							</div>

							<div class="form-group">
								<div class="col-sm-12">
									<select class="form-control" id="gender" >
										<option>性別</option>
										<option value=1>男</option>
										<option value=0>女</option>
									</select>
								</div>
							</div>
							
							<div class="form-group">
								<div class="col-sm-12">
									<input type="text" class="form-control" id="address" placeholder="地址" >
								</div>
							</div>

							<div class="form-group">
								<div class="col-sm-12">
									<input type="password" class="form-control" id="passwd" placeholder="*密碼" required>
								</div>
							</div>

							<div class="form-group">
								<div class="col-sm-12">
									<input type="password" class="form-control" id="repasswd" placeholder="*再次輸入密碼" required>
								</div>
								
							</div>
							
							
							
							<div class="form-group">
								<div class="col-md-12">
							<div class="input-group">
      							<span class="input-group-addon">     
          						<input id="isRecommend" type="checkbox">     
      							</span>							
      							
     							<input id="referencesCode" name="prependedcheckbox" class="form-control" type="text" placeholder="是否有推薦人(有請打勾)" disabled>
    						</div>
    						</div>
							</div>

							<div class="form-group">
								<div class="col-sm-offset-0 col-sm-8">
									<button id="submitBtn" type="submit" class="btn btn-success btn-block" disabled>建立新帳號</button>
								</div>
							</div>
	
						</form>

					</div>
				
				</div>
				
				<div id="checkRepasswd" class="alert alert-danger" style="display:none">密碼輸入不一致!請重新輸入</div>
				
			</div>
			<!-- 申請會員 end -->
			
		</div>
	</div>
   
   	<!-- AJAVX Waiting Image -->
	<div id="ajaxWaiting" class="ajaxWaiting" style="display: none;">
		<img src="${pageContext.request.contextPath}/images/ajax-loader.gif">
	</div>
  </body>
</html>
