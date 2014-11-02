<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.*,java.text.*"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

Date date = new Date();
DateFormat dateformat;

dateformat = new SimpleDateFormat("yyyyMMddHHmmss");
String icpOrderId = dateformat.format(date);

//String OldenCoding = "ISO8859-1";
//String NewenCoding = "utf-8";

%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>線上儲值</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.png">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootbox-4.2.0/bootbox.min.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		
		$("#confirmPaymentForm").on("submit", function(event) {
		
			$("#confirmPaymentForm").find("#submit").prop('disabled', true);
		
			bootbox.dialog({
			  message: "<h5><b>請在新開網上儲蓄卡頁面完成付款後再選擇，儲值完成前請不要關閉此窗口。</b></h5>",
			  title: "<h4 class='content-title'>信用卡儲值</h4><div class='content-text'>",
			  buttons: {
			    success: {
			      label: "已完成付款",
			      className: "btn-success",
			      callback: function() {
			      	location.reload();
			      }
			    }
			    
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
						<li class="active"><a href="#panel-001" data-toggle="tab">點數儲值</a>
						</li>
						<li><a href="#panel-002" data-toggle="tab">線上帳戶</a>
						</li>
					</ul>
					<div class="tab-content">
						<!-- 儲值方式 -->
						<div class="tab-pane active" id="panel-001">	
							<div class="row clearfix col-md-12">
								<div class="bs-callout bs-callout-info">
										    <h4 class="content-title">信用卡快速儲值</h4>
										    <div class="content-text"></div>
								</div>
								
								<form id="confirmPaymentForm" class="form-horizontal" action="confirmPayment" method="post" target="_blank">
								
								<table class="table">
											<thead>
												<tr>
													<th class="col-md-1 text-right">選擇方案</th>
													<th class="text-center">點數</th>
													<th>金額</th>
													<th>單位成本計算</th>
												</tr>
											</thead>
											<tbody>
												
												<tr>
													<td class="col-md-1 text-right">
														<input type="radio" name="type" value="1" required> 
													
													</td>
													<td class="text-center">2點</td>
													<td>30元</td>
													<td>30元/2點</td>
												</tr>
												
												<tr>
													<td class="col-md-1 text-right">
														<input type="radio" name="type" value="2" required>
													
													</td>
													<td class="text-center">12點</td>
													<td>150元</td>
													<td>25元/2點</td>
												</tr>
												
												<tr>
													<td class="col-md-1 text-right">
														<input type="radio" name="type" value="3" required>
													
													</td>
													<td class="text-center">26點</td>
													<td>300元</td>
													<td>23元/2點</td>
												</tr>
												
												<tr>
													<td class="col-md-1 text-right">
														<input type="radio" name="type" value="4" required>
													
													</td>
													<td class="text-center">56點</td>
													<td>600元</td>
													<td>21.4元/2點</td>
												</tr>
												
											</tbody>
								</table>
								
								
								
								
									<fieldset>								
						

										<!-- Button -->
										<div class="form-group">
											<div class="col-md-offset-1 col-md-4 ">
												<button type="submit" id="submit" name="singlebutton" class="btn btn-primary btn-block">登入到信用卡中心付款</button>
																
												<!-- FOR PAYMENT -->
												<input type="hidden" name="mpId" value="CITI">
												<input type="hidden" name="icpId" id="icpId" value="yitai">
												<input type="hidden" name="memo" id="memo" value="test">
												<input type="hidden" name="icpProdDesc" id="icpProdDesc" value="DRCALL Momey">
												<input type="hidden" name="icpProdId" id="icpProdId" value="yta_0001">
												<input type="hidden" name="icpUserId" id="icpUserId" value="${member.memberId}">	
												<input type="hidden" name="icpOrderId" id="icpOrderId" maxlength="20" value="<%=icpOrderId%>">										
												
												<!--訂單交易結果回傳URL請自訂-->
												 <input type="hidden" name="returnUrl" id="returnUrl" value=<%=basePath+"app/payment/authSuccess"%>>
												
											</div>
										</div>



									</fieldset>
								</form>

							</div>


						</div>
						
						
						<!-- ACCOUNT RECORD -->
						<div class="tab-pane" id="panel-002">
							<div class="col-md-12 row clearfix">
								<div class="bs-callout bs-callout-info">
									<h4 class="content-title">DRCALL 個人帳戶</h4>
								</div>

								<table class="table">
											<thead>
												<tr>
													<th>交易時間</th>
													<th>存入</th>
													<th>支出</th>
													<th>點數餘額</th>
													<th>說明</th>
												</tr>
											</thead>
											<tbody>
												
												
												<c:forEach var="account" items="${accounts}" varStatus="status">
													<tr>
														<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${account.datetime}"/></td>
														<td>${account.deposit}</td>
														<td>${account.withdraw}</td>
														<td><b>${account.balance}</b></td>
														<td>
															<c:choose>
															  <c:when test="${account.type == 0}">網路儲值</c:when>
															  <c:when test="${account.type == 1}">APP儲值</c:when>
															  <c:when test="${account.type == 2}">網路預約掛號</c:when>
															  <c:when test="${account.type == 3}">APP預約掛號</c:when>
															  <c:when test="${account.type == 4}">預約掛號取消</c:when>
															  <c:when test="${account.type == 5}">推薦他人</c:when>
															  <c:when test="${account.type == 6}">被推薦人</c:when>
															  <c:when test="${account.type == 7}">新建帳戶</c:when>
															  <c:otherwise>異常</c:otherwise>
															</c:choose>
															${account.note}
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