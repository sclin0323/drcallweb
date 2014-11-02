<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String hospital_list = (String) request.getAttribute("hospital_list");
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>預約掛號紀錄</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.bootpag.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootbox-4.2.0/bootbox.min.js"></script>
<script type="text/javascript">

	function cancelOnclik(appointId) {
		
			$.ajax({
				type : "POST",
				url : "submitCancel",
				async: false,
				data : {
					appointId : appointId
				},
				success : function(data) {
 					
 					if(data.status == 'true'){
 						bootbox.alert("<b>預約掛號記錄已經成功取消</b>", function() {
  							location.reload();
						});
 					} else {
 						bootbox.alert("<b>預約掛號記錄已無法取消!! 預約時間當診開始後無法取消預約。</b> ");
 					}

				},
				error : function(xhr, ajaxOptions, thrownError) {
					alert(xhr.status);
        			alert(thrownError);
				}
			});
		
    	
	}


	$(window).load(function() {
		$("#menuRecord").addClass( "active");
		
		
		// init bootpag
        $('#page-selection').bootpag({
            total: 5
        }).on("page", function(event, /* page number here */ num){
        
             //$("#content").html("Insert content"); // some ajax content loading...
             
             $.ajax({
				type : "POST",
				url : "getTableRow",
				async: true,
				data : {
					num : num
				},
				beforeSend : function(data){
					//$("#ajaxWaiting").show();
				},
				complete:function(){
                    //$("#ajaxWaiting").hide();
                },
				success : function(data) {
 					
 					console.log(data);
				},
				error : function(xhr, ajaxOptions, thrownError) {
					alert(xhr.status);
        			alert(thrownError);
				}
			});

             
             $(this).bootpag({total: 16, maxVisible: 10});
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
		
						<div class="row">
								
								<div class="col-sm-12 widget stacked widget-table action-table">

									<div class="bs-callout bs-callout-info">
										    <h4 class="content-title">掛號預約紀錄</h4>
									</div>
									<!-- /widget-header -->

									<div class="widget-content">

										<table class="table table-striped table-bordered">
											<thead>
												<tr>
													<th>編號</th>
													<th>預約看診日期</th>
													<th>班別</th>
													<th>候診人</th>
													<th>醫院</th>
													<th>醫師</th>
													<th>科</th>
													<th>掛號時間</th>
													<th>狀態</th>
													<th class="td-actions">取消</th>
												</tr>
											</thead>
											<tbody>
											
												<c:forEach var="appoint" items="${appoints}" varStatus="status">
													<tr>
														<td>${status.index+1}</td>
														<td>${appoint.schedule.date}</td>
														<td>
															<c:choose>
															  <c:when test="${appoint.shift == 0}">早班</c:when>
															  <c:when test="${appoint.shift == 1}">午班</c:when>
															  <c:when test="${appoint.shift == 2}">晚班</c:when>
															  <c:otherwise>異常</c:otherwise>
															</c:choose>	
														
														</td>
														<td>${appoint.name}</td>
														<td>${appoint.schedule.hospital.name}</td>
														<td>${appoint.schedule.doctor.name} 醫生</td>
														<td>${appoint.schedule.division.cnName}</td>
														<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${appoint.crtTime}"/></td>
														<td>
															<c:choose>
															  <c:when test="${appoint.status == 0}">預約成功</c:when>
															  <c:when test="${appoint.status == 1}">完成通知預約成功 (第一通知)</c:when>
															  <c:when test="${appoint.status == 2}">完成前一日看診通知 (第二通知)</c:when>
															  <c:when test="${appoint.status == 3}">準備通知看診</c:when>
															  <c:when test="${appoint.status == 4}">完成通知看診 (第三通知)</c:when>
															  <c:when test="${appoint.status == 5}">看診完成</c:when>
															  <c:when test="${appoint.status == 6}">預約取消</c:when>
															  <c:when test="${appoint.status == 7}">預約失敗</c:when>
															  <c:otherwise>異常</c:otherwise>
															</c:choose>
														</td>
														
														<td class="td-actions">
															<button id="appointment_btn_cancel" type="button" class="btn btn-sm btn-danger" value="${appoint.appointId}" onclick="cancelOnclik(${appoint.appointId})">取消</button>
														</td>
														
														
													</tr>
												</c:forEach>
											
											</tbody>
										</table>
										
										
<!-- 												<div class="clearfix"></div> -->
<!-- 												<div id="content">Dynamic Content goes here</div> -->
    											<div id="page-selection"></div>

									</div>
									<!-- /widget-content -->

								</div>


							</div>
		
	</div>





</body>
</html>