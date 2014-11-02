<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//String family_account_list = (String) request.getAttribute("family_account_list");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>會員專區</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.png">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootbox-4.2.0/bootbox.min.js"></script>
<script type="text/javascript">

	function deleteFamily(familyId) {
    	
    	$.ajax({
				type : "POST",
				url : "deleteFamily",
				async: false,
				data : {
					familyId : familyId
				},
				success : function(data) {
					bootbox.alert("刪除成功");
					$('#'+familyId).remove();
				},
				error : function(data) {
					alert("error!!!");
				}
			});
    	
    	
	}

	$(document).ready(function() {
		$("#menuMember").addClass("active");
	
		$("#basic_info_form").on("submit", function(event) {
			event.preventDefault();
			var memberId = $("#basic_info_form").find('input[id="memberId"]').val();
			var idNumber = $("#basic_info_form").find('input[id="idNumber"]').val();
			var name = $("#basic_info_form").find('input[id="name"]').val();
			var email = $("#basic_info_form").find('input[id="email"]').val();
			var address = $("#basic_info_form").find('input[id="address"]').val();
			
			
			var gender = $("#basic_info_form").find('select[id="gender"]').val();
			var year = $("#basic_info_form").find('select[id="year"]').val();
			var month = $("#basic_info_form").find('select[id="month"]').val();
			var day = $("#basic_info_form").find('select[id="day"]').val();
			var birDate=new Date();
			birDate.setFullYear(year,month,day);
			
			$.ajax({
				type : "POST",
				url : "updateMember",
				async: false,
				data : {
					memberId : memberId, idNumber:idNumber, name : name, gender : gender, email : email, birDate : birDate, address : address
				},
				beforeSend : function(data){
					if(isNaN(year) || isNaN(month) || isNaN(day)){
						bootbox.alert("生日輸入有錯誤，請重新輸入。");
						return false;
					}
				},
				success : function(data) {
					
					bootbox.alert("Update OK!!");
 					
				},
				error : function(data) {
					alert("error!!!");

				}
			});
			
			
		});
	
		// 建立 Family Account
		$("#family_form").on("submit", function(event) {
			event.preventDefault();
			
			var name = $("#family_form").find('input[id="name"]').val();
			var gender = $("#family_form").find('select[id="gender"]').val();
			var tel = $("#family_form").find('input[id="tel"]').val();		
			var birthYear = $("#family_form").find('select[id="year"]').val();
			var birthMonth = $("#family_form").find('select[id="month"]').val();
			var birthDay = $("#family_form").find('select[id="day"]').val();
			var birthday = "民國  "+birthYear+"年  "+birthMonth+"月  "+birthDay+" 日";
			var idNumber = $("#family_form").find('input[id="idNumber"]').val();
			
			$.ajax({
				type : "POST",
				url : "addFamily",
				async: false,
				data : {
					name : name, tel : tel, birthYear : birthYear, birthMonth : birthMonth, birthDay : birthDay, gender : gender, idNumber : idNumber
				},
				beforeSend : function(data){
					if(isNaN(birthDay) || isNaN(birthMonth) || isNaN(birthYear)){
						bootbox.alert("生日輸入有錯誤，請重新輸入。");
						return false;
					}
					
					if(isNaN(gender)){
						bootbox.alert("性別輸入有錯誤，請重新輸入。");
						return false;
					}
					
				},
				success : function(data) {
					var isOverLimit = data.isOverLimit;
					
					if(isOverLimit == 'true'){
						bootbox.alert("會員親屬最多可以新增八位。已超過上限!!");
						return;
					}
					
					var familyMemberId = data.familyMemberId;
					var genderStr;
					if(gender == 0){
						genderStr = "女";
					} else {
						genderStr = "男";
					}
					
 					$("#delete_family_account").append("<tr id='"+familyMemberId+"'><td>"+tel+"</td><td>"+name+"</td><td>"+birthday+"</td><td>"+genderStr+"</td><td>"+idNumber+"</td><td class='td-actions'><a href='javascript:;' class='btn btn-sm btn-danger' onclick='deleteFamily("+familyMemberId+")'>刪除</a></td></tr>");
					
					// clean all fields in family form
					$("#family_form").find("#name").val("");
					$("#family_form").find("#idNumber").val("");
					$("#family_form").find("#tel").val("");
            		$("#family_form").find("#year").val("民國");
            		$("#family_form").find("#month").val("月");
            		$("#family_form").find("#day").val("日");

					bootbox.alert("新增成功");
					
				},
				error : function(data) {
					alert("error!!!");

				}
			});
		
		});
		
		$("#recommend_form").on("submit", function(event) {
			event.preventDefault();
			
			var email = $("#recommend_form").find('input[id="email"]').val();
			var name = $("#recommend_form").find("#name").val();
			var tel = $("#recommend_form").find("#tel").val();
			
			$.ajax({
				type : "POST",
				url : "addRecommend",
				async: true,
				data : {
					email : email, name : name, tel : tel
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
					var date = data.date;
					var deadline = data.deadline;
					var recommendId = data.recommendId;
					var status = data.status;
				
					bootbox.alert("<b>推薦email已送出，Dr. Call提醒您告知親友，於期限內下載或申請帳號都有免費的點數喔~~謝謝您的熱情推薦，別忘了去看您的帳戶喔~~</b>");
				
					var tr = "<tr><td>"+date+"</td><td>"+email+"</td><td>"+name+"</td><td>"+tel+"</td><td>"+deadline+"</td><td>"+recommendId+"</td><td>"+status+"</td></tr>";
					$("#recommend_table tbody").prepend(tr);
				},
				error : function(data) {
					alert("error!!!");

				}
			});
		});
	});
</script>

<style type="text/css">
.bs-callout {
	margin: 20px 0;
	padding: 20px;
	border-left: 3px solid #eee;
}
.bs-callout-info {
	background-color: #f4f8fa;
	border-color: #5bc0de;
}


</style>

</head>

<body>

	<div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div class="tabbable" id="tabs-970930">
					<ul class="nav nav-tabs">
						<li class="active"><a href="#panel-001" data-toggle="tab">基本資料</a>
						</li>
						<li><a href="#panel-002" data-toggle="tab">會員親屬管理</a>
						</li>
						<li><a href="#panel-004" data-toggle="tab">推薦他人專區</a>
						</li>
					</ul>
					<div class="tab-content">
						<!-- 基本資料 -->
						<div class="tab-pane active" id="panel-001">	
							<div class="row clearfix col-md-12">
								<div class="bs-callout bs-callout-info">
										    <h4 class="content-title">會員基本資料</h4>
										    <div class="content-text">Dr. Call將依會員資料進行預約掛號以及看診通知服務，再請務必填寫正確，以利掛號順利。</div>
								</div>
								<form id="basic_info_form" class="form-horizontal">
									<fieldset>
										
									
										<div class="form-group">
											<label class="col-md-2 control-label" for="selectbasic">*帳號</label>
											<div class="col-md-6">
												<input type="email" class="form-control" id="memberId" placeholder="帳號" name="memberId" value="${member.memberId}" disabled>
											</div>
										</div>
										
										<div class="form-group">
											<label class="col-md-2 control-label" for="selectbasic">*Email</label>
											<div class="col-md-6">
												<input type="email" class="form-control" id="email" placeholder="Email" name="email" value="${member.email}" required>
											</div>
										</div>
										
										<div class="form-group">
											<label class="col-md-2 control-label" for="selectbasic">身分證字號</label>
											<div class="col-md-6">
												<input type="text" pattern="^[a-zA-Z]{1}[1-2]{1}[0-9]{8}$" class="form-control" id="idNumber" placeholder="身分證字號" name="name" value="${member.idNumber}">
											</div>
										</div>

										<div class="form-group">
											<label class="col-md-2 control-label" for="selectbasic">*姓名</label>
											<div class="col-md-6">
												<input type="text" class="form-control" id="name" placeholder="姓名" name="name" value="${member.name}" required>
											</div>
										</div>									
										
										<div class="form-group">
											<label class="col-md-2 control-label" for="selectbasic">*出生日</label>
											
											<div class="col-sm-2">
												<select class="form-control" id="year">
													<option>民國</option>
													<option selected value=${member.birthYear}>${member.birthYear}</option>
													<c:forEach var="i" begin="1" end="103">
			            								<option value=${i} >${i}</option>
			          								</c:forEach>
												</select>
											</div>
											
											<div class="col-sm-2">
												<select class="form-control" id="month">
													<option>月</option>
													<option selected value=${member.birthMonth}>${member.birthMonth}</option>
													<c:forEach var="i" begin="1" end="12">
			            								<option value=${i}>${i}</option>
			          								</c:forEach>
												</select>
											</div>
			
											<div class="col-sm-2">
												<select class="form-control" id="day">
													<option>日</option>
													<option selected value=${member.birthDay}>${member.birthDay}</option>
													<c:forEach var="i" begin="1" end="31">
			            								<option value=${i}>${i}</option>
			          								</c:forEach>
												</select>
											</div>
										</div>
										
										<div class="form-group">
											<label class="col-md-2 control-label" for="selectbasic">*姓別</label>
											<div class="col-sm-6">
												<select class="form-control" id="gender" >
													<option <c:if test="${member.gender == 1}">selected</c:if> value=1>男</option>
													<option <c:if test="${member.gender == 0}">selected</c:if> value=0>女</option>
												</select>
											</div>
										</div>

										<div class="form-group">
											<label class="col-md-2 control-label" for="selectbasic">*地址</label>
											<div class="col-md-6">
												<input type="text" class="form-control" id="address" placeholder="住址" name="address" value="${member.address}" required>
											</div>
										</div>

										<!-- Button -->
										<div class="form-group">
											<div class="col-md-offset-2 col-md-6 ">
												<button type="submit" id="singlebutton" name="singlebutton" class="btn btn-primary btn-block">確認修改</button>
											</div>
										</div>



									</fieldset>
								</form>

							</div>


						</div>
						
						
						<!-- Family Account -->
						<div class="tab-pane" id="panel-002">
							<div class="col-md-12 row clearfix">
								<div class="bs-callout bs-callout-info">
										    <h4 class="content-title">建立親屬資料，以利協助家人親屬預約掛號並得到通知的服務</h4>
										    <div class="content-text">Dr. Call了解您想對家人好的心意與需求，更想讓家人享受這服務。Dr. Call在此提供建立家人清單資料，完成後就可供為家人進行預約掛號，並進行通知服務。</div>
										</div>
								<form id="family_form" class="col-md-12 form-horizontal">
									<fieldset>
										
										<div class="form-group">
											<label class="col-md-2 control-label" for="selectbasic">*行動電話</label>
											<div class="col-md-6">
												<input type="tel" pattern="^[\d]{10}$" class="form-control" id="tel" placeholder="行動電話格式09XXXXXX" required>
											</div>
										</div>

										<div class="form-group">
											<label class="col-md-2 control-label" for="selectbasic">*姓名</label>
											<div class="col-md-6">
												<input type="text" class="form-control" id="name" placeholder="Name" required>
											</div>
										</div>

										<div class="form-group">
											<label class="col-md-2 control-label" for="selectbasic">*生日</label>
											
											<div class="col-sm-2">
												<select class="form-control" id="year">
													<option>民國</option>
													<c:forEach var="i" begin="1" end="103">
			            								<option value=${i}>${i}</option>
			          								</c:forEach>
												</select>
											</div>
											
											<div class="col-sm-2">
												<select class="form-control" id="month" >
													<option>月</option>
														<c:forEach var="i" begin="1" end="12">
				            								<option value=${i}>${i}</option>
				          								</c:forEach>
												</select>
											</div>

											<div class="col-sm-2">
												<select class="form-control" id="day">
													<option>日</option>
													<c:forEach var="i" begin="1" end="31">
			            								<option value=${i}>${i}</option>
			          								</c:forEach>
												</select>
											</div>
										</div>
										
										<div class="form-group">
											<label class="col-md-2 control-label" for="selectbasic">*性別</label>
											<div class="col-sm-6">
												<select class="form-control" id="gender" >
													<option>性別</option>
													<option value=1>男</option>
													<option value=0>女</option>
												</select>
											</div>
										</div>
										
										<div class="form-group">
											<label class="col-md-2 control-label" for="selectbasic">身分證字號</label>
											<div class="col-md-6">
												<input type="text" pattern="^[a-zA-Z]{1}[1-2]{1}[0-9]{8}$" class="form-control" id="idNumber" placeholder="身分證字號" name="name">
											</div>
										</div>

										<div class="form-group">
											<div class="col-md-offset-2 col-md-6 ">
												<button type="submit" id="addFamilyAccount" name="singlebutton" class="btn btn-primary btn-block">新增</button>
											</div>
										</div>

									</fieldset>
								</form>	

								<table class="table table-striped table-bordered">
									<thead>
										<tr>
											<th>行動電話</th>
											<th>姓名</th>
											<th>生日</th>
											<th>性別</th>
											<th>身分證字號</th>									
											<th class="td-actions">DELETE</th>
										</tr>
									</thead>
									<tbody id="delete_family_account">
										<c:forEach var="family" items="${member.families}" >
											<tr id="${family.familyId}">
												<td>${family.tel}</td>
												<td>${family.name}</td>
												<td>民國 ${family.birthYear} 年 ${family.birthMonth} 月 ${family.birthDay} 日</td>
												<td>
													<c:choose>
														<c:when test="${family.gender == 0}">男</c:when>
														<c:when test="${family.gender == 1}">女</c:when>
													</c:choose>		
												</td>
												
												
												<td>${family.idNumber}</td>
												<td class="td-actions">
													<a href="javascript:;" class="btn btn-sm btn-danger" onclick="deleteFamily('${family.familyId}')">刪除</a> 
												</td>
											</tr>
										</c:forEach>
										
									</tbody>
								</table>



							</div>

						</div>
						
						<!-- 親友推薦 -->
						<div class="tab-pane" id="panel-004">
							<div class="col-md-12 row clearfix">
								<div class="bs-callout bs-callout-info">
										    <h4 class="content-title">好康!!推薦他人，免費贈點</h4>
										    <div class="content-text">Dr.call 提供會員推薦贈點活動，透過Email推薦他人，該推薦人於期限內加入Dr.Call會員並登入系統，推薦人以及被推薦人皆可免費獲得xx點。</div>
										</div>
								<form action="/" id="recommend_form" class="col-md-12 form-horizontal">
									<fieldset>
						
										<!-- INPUT NAME -->
										<div class="form-group">
											<label class="col-md-2 control-label" for="selectbasic">*姓名</label>
											<div class="col-md-6">
												<input type="text" class="form-control" id="name" placeholder="姓名" required>
											</div>
										</div>

										<!-- INPUT EMAIL -->
										<div class="form-group">
											<label class="col-md-2 control-label" for="selectbasic">*Email</label>
											<div class="col-md-6">
												<input type="email" class="form-control" id="email" placeholder="Email" required>
											</div>
										</div>

										<!-- INPUT TEL -->
										<div class="form-group">
											<label class="col-md-2 control-label" for="selectbasic">*手機電話</label>
											<div class="col-md-6">
												<input type="tel" pattern="^[\d]{10}$" class="form-control" id="tel" placeholder="行動電話格式09XXXXXX" required>
											</div>
										</div>

										<!-- Button -->
										<div class="form-group">
											<div class="col-md-offset-2 col-md-6 ">
												<button type="submit" id="addReference" name="singlebutton" class="btn btn-primary btn-block">確認推薦</button>
											</div>
										</div>



									</fieldset>
								</form>

								<table id="recommend_table" class="table table-striped table-bordered">
									<thead>
										<tr>
											<th>推薦時間</th>
											<th>推薦對象Email</th>
											<th>姓名</th>
											<th>手機電話</th>
											<th>推薦期限</th>
											<th>推薦驗證碼</th>
											<th>推薦狀態</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="recommend" items="${member.recommends}" >
											<tr>
												<td> <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${recommend.recommendDate}" /></td>
												<td>${recommend.email}</td>
												<td>${recommend.name}</td>
												<td>${recommend.tel}</td>
												<td><fmt:formatDate pattern="yyyy-MM-dd" value="${recommend.deadline}" /></td>
												<td>${recommend.recommendId}</td>
												<td>
													<c:choose>
													  <c:when test="${recommend.status == 0}">已送出推薦，等候中!</c:when>
													  <c:when test="${recommend.status == 1}">恭喜，推薦成功!</c:when>
													  <c:otherwise>狀態異常</c:otherwise>
													</c:choose>											
												</td>
												
												
												
											</tr>
										</c:forEach>
										
									</tbody>
								</table>



							</div>
						</div>

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


