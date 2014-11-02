<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String hospital_list = (String) request.getAttribute("hospital_list");
%>

<html lang="en">
<head>
<meta charset="utf-8">
<title>預約掛號</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootbox-4.2.0/bootbox.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/autocomplete-1.2.7/jquery.autocomplete.min.js"></script>

<script type="text/javascript">

	function appointOnclik(date, doctorName, hospitalName, divisionName, scheduleId, shift) {
			// shift 早班:0   午班:1  晚班:2
			if(shift == 0){
				var shiftName="早診";
			} else if(shift == 1){
				var shiftName="午診";
			} else if(shift == 2){
				var shiftName="晚診";
			}
 			
			$("#appointModal").find("#appointModalForm").find("#shift").val(shift);
    		$("#appointModal").find("#appointModalForm").find("#scheduleId").val(scheduleId);
    		$("#appointModal").find("#appointModalForm").find("#date").html(date);
    		$("#appointModal").find("#appointModalForm").find("#doctorName").html(doctorName);
    		$("#appointModal").find("#appointModalForm").find("#hospitalName").html(hospitalName);
    		$("#appointModal").find("#appointModalForm").find("#divisionName").html(divisionName);
    		$("#appointModal").find("#appointModalForm").find("#shiftName").html(shiftName);
    		
			$("#appointModal").modal('show');
	}

	function validateForm(form) {  

 	    var hospitalId= $("#autocomplete").attr('hospitalId');
 	    var divisionId = $("#select_devision").val();

	    if(hospitalId == ''){
	    	bootbox.alert("院所輸入有誤請重新輸入!!");
	    	return false;
	    } else if(divisionId == ''){
	    	bootbox.alert("科別輸入有誤請重新輸入!!");
	    	return false;
	    } else {
	    	return true;  
	    }
	}  


	$(document).ready(function() {
		$("#menuBooking").addClass( "active");


  		var hospital_list_json = JSON.parse('${hospital_list}');
  
  		$('#autocomplete').autocomplete({
    		lookup: hospital_list_json,
    		onSelect: function (suggestion) {
    		
    			$("#autocomplete").attr("hospitalId",suggestion.hospitalId);
    			
    			$("#hospitalId").attr("value",suggestion.hospitalId);

    		}
  		});

		$("#search_btn").click(function() {

			$.ajax({
				type : "POST",
				url : "division",
				async: true,
				data : {
					hospitalId : $("#autocomplete").attr('hospitalId')
				},
				beforeSend : function(data){
					if(typeof $("#autocomplete").attr('hospitalId') === 'undefined' || $("#autocomplete").attr('hospitalId') == ''){
						bootbox.alert("院所輸入有誤，請重新輸入。");
						return false;
					}

					$("#ajaxWaiting").show();
				},
				complete:function(){
                    	$("#ajaxWaiting").hide();
                },
				success : function(data) {
 					var divisions = data.divisions;	

  					$('#select_devision option').remove();	
  					$('#select_devision').append($("<option></option>").attr("value","").text("請選擇科別"));	
  					$.each(divisions, function(i, division) {   
      					$('#select_devision')
          				.append($("<option></option>")
         				.attr("value",division.divisionId)
          				.text(division.cnName)); 
  					});
				},
				error : function(xhr, ajaxOptions, thrownError) {
					alert(xhr.status);
        			alert(thrownError);
				}
			});

		});



		// 送出掛號預約
		$("#appointModalForm").on("submit", function(event) {
			event.preventDefault();

			var idNumber = $("#appointModalForm").find("#idNumber").val();
			var selectPatient = $("#appointModalForm").find("#selectPatient").val();
			var shift = $("#appointModalForm").find("#shift").val();
			var scheduleId = $("#appointModalForm").find("#scheduleId").val();
			var isKeepId = $("#appointModalForm").find('input[id="isKeepId"]').is(':checked');
			
			//alert(scheduleId);

			$.ajax({
				type : "POST",
				url : "submit",
				async: true,
				data : {
					idNumber : idNumber, selectPatient : selectPatient, scheduleId : scheduleId, shift : shift, isKeepId : isKeepId
				},
				beforeSend : function(data){
					$("#search_btn_waiting").remove();

					// MOVE AJAX WAITING IMAGES
                    $("#ajaxWaiting").appendTo("#appointModalForm");
                    $("#ajaxWaiting").show();
				},
				complete:function(){
                    $("#ajaxWaiting").hide();
                },
				success : function(data) {
 					
 					var date = data.date;
 					var hospitalName = data.hospitalName;
 					var divisionName = data.divisionName;
 					var doctorName = data.doctorName;
 					var appointNumber = data.appointNumber;
 					
 					$("#appointResultModal").find("#date").html(date);
 					$("#appointResultModal").find("#hospitalName").html(hospitalName);
 					$("#appointResultModal").find("#divisionName").html(divisionName);
 					$("#appointResultModal").find("#shiftName").html(shift);
 					$("#appointResultModal").find("#doctorName").html(doctorName);
 					$("#appointResultModal").find("#appointNumber").html(appointNumber);
 					
					$('#appointModal').modal('hide');
					$('#appointResultModal').modal('show');
				},
				error : function(xhr, ajaxOptions, thrownError) {
					alert(xhr.status);
        			alert(thrownError);
				}
			});

		});
		
		$("#appointModalForm").find("#selectPatient").change(function() {			
 			var idNumber = $("#appointModalForm").find("#selectPatient").find("option:selected").attr("idNumber");	
 			$("#appointModalForm").find("#idNumber").val(idNumber);
		});

		$("#appointResultModal").blur(function() {
			 window.location.href = "record";
		});


		$("#scheduleForm").on("submit", function(event) {
			event.preventDefault();

			var item = $("#radios").find(":selected").val();


			alert(item);

		});

	});
</script>

<style type="text/css">

.autocomplete-suggestions { border: 1px solid #999; background: #fff; cursor: default; overflow: auto; width:460px!important;}
.autocomplete-suggestion { padding: 5px 5px; font-size: 1em; white-space: nowrap; overflow: hidden; }
.autocomplete-selected { background: #f0f0f0; }
.autocomplete-suggestions strong { font-weight: normal; color: #3399ff; }

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
		<div class="row clearfix col-md-12">
								<form action="booking" class="form-horizontal" method="post" onsubmit="return validateForm()">
									<fieldset>
										<div class="bs-callout bs-callout-info">
										    <h4 class="content-title">線上預約掛號</h4>
										    <div class="content-text">Dr.Call 提供線上預約掛號服務...收費及服務內容...</div>
										</div>
										<!-- Form Name -->

										<!-- Select Basic -->
										<div class="form-group">
											<label class="col-md-2 control-label" for="autocomplete">*院所</label>
  											<div class="col-md-6">
  												<div class="input-group">
    												<input id="autocomplete" name="autocomplete" type="search" placeholder="輸入院所" class="form-control input-md" x-webkit-speech lang="zh-TW" hospitalid="${hospitalId}" value="${hospitalName}">
    												<input id="hospitalId" name="hospitalId" value="${hospitalId}" hidden=true>
    												<span class="input-group-btn">
        												<button id="search_btn" class="btn btn-default" type="button">診別查詢</button>
      												</span>
    											</div>
    										</div>

										</div>



										<!-- Select Basic -->
										<div id="searchSchedule" class="form-group" >

											<label class="col-md-2 control-label" for="selectbasic">*科別</label>
											<div class="col-md-4">
												<select id="select_devision" name="devisionId" class="form-control" >
													<option value="">科別  (請先於「輸入院所」，並點擊「診別查詢」)</option>
												</select>
											</div>

											<div class="col-md-2">
												<button id="schedule_btn" type="submit" name="sing"
													class="btn btn-info btn-block">取得排班表</button>
											</div>

										</div>

									</fieldset>
								</form>

							</div>

							<!-- schedule list -->
							<table class="table table-bordered table-condensed">
				              <thead>
				                <tr>
				                  <th class="col-md-1 text-center">日期 / 星期</th>
				                  <th class="col-md-2 text-center">醫生</th>
				                  <th class="col-md-3 text-center">早診</th>
				                  <th class="col-md-3 text-center">午診</th>
				                  <th class="col-md-3 text-center">晚診</th>
				                </tr>
				              </thead>
				              <tbody>

				              	<c:forEach var="scheduleDay" items="${scheduleDays}" >


				              		<c:forEach var="drcallSchedule" items="${scheduleDay.drcallSchedules}" varStatus="status">

				              			<tr class="text-center" >
				              				<c:if test="${status.index == 0}">
				              					<td rowspan="${fn:length(scheduleDay.drcallSchedules)}">${scheduleDay.date}</td>
				              				</c:if>
				                  			<td>${drcallSchedule.doctorName}  醫生  ${drcallSchedule.divisionName}</td>
				                  			<td>
				                  				<c:choose>
													<c:when test="${drcallSchedule.morningShift == true}">
														<button type="button" name='singlebutton' class='btn btn-primary btn-block' onclick="appointOnclik('${scheduleDay.date}','${drcallSchedule.doctorName}','${drcallSchedule.hospitalName}','${drcallSchedule.divisionName}','${drcallSchedule.scheduleId}', 0) ">掛號預約</button>
													</c:when>
													<c:otherwise><b>休診</b></c:otherwise>
												</c:choose>
				                  			</td>
				                  			<td>
				                  				<c:choose>
													<c:when test="${drcallSchedule.afternoonShift == true}">
														<button type="button" name='singlebutton' class='btn btn-primary btn-block' onclick="appointOnclik('${scheduleDay.date}','${drcallSchedule.doctorName}','${drcallSchedule.hospitalName}','${drcallSchedule.divisionName}','${drcallSchedule.scheduleId}', 1) ">掛號預約</button>
													</c:when>
													<c:otherwise><b>休診</b></c:otherwise>
												</c:choose>
				                  			</td>
				                  			<td>

				                  				<c:choose>
													<c:when test="${drcallSchedule.nightShift == true}">
														<button type="button" name='singlebutton' class='btn btn-primary btn-block' onclick="appointOnclik('${scheduleDay.date}','${drcallSchedule.doctorName}','${drcallSchedule.hospitalName}','${drcallSchedule.divisionName}','${drcallSchedule.scheduleId}', 2) ">掛號預約</button>
													</c:when>
													<c:otherwise><b>休診</b></c:otherwise>
												</c:choose>

				                  			</td>
				                		</tr>


				              		</c:forEach>
				              	</c:forEach>

				              </tbody>
				            </table>
	</div>


	<!-- AJAVX Waiting Image -->
	<div id="ajaxWaiting" class="ajaxWaiting" style="display: none;">
		<img src="${pageContext.request.contextPath}/images/ajax-loader.gif">
	</div>

	<!-- APPOINT　MODEL -->
	<div class="modal fade" id="appointModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">


				<form id="appointModalForm" class="form-horizontal">

				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="content-title">預約掛號</h4>
				</div>
				<div class="modal-body">

						<fieldset>
							<div class="form-group">
                                <label class="col-md-2 control-label" for="textinput">日期</label>
                                <div class="col-md-10">
                                	<label id="date" class="control-label" for="textinput" ></label>
                                </div>
	                        </div>
	                        <div class="form-group">
	                                <label class="col-md-2 control-label" for="textinput">院所</label>
	                                <div class="col-md-10">
	                                	<label id="hospitalName" class="control-label" for="textinput"></label>
	                                </div>
	                        </div>
	                        <div class="form-group">
	                                <label class="col-md-2 control-label" for="textinput">科別</label>
	                                <div class="col-md-10">
	                                	<label id="divisionName" class="control-label" for="textinput"></label>
	                                </div>
	                        </div>

	                        <div class="form-group">
	                                <label class="col-md-2 control-label" for="textinput">診別</label>
	                                <div class="col-md-10">
	                                	<label id="shiftName" class="control-label" for="textinput"></label>
	                                </div>
	                        </div>
                        
                        	<div class="form-group">
	                                <label class="col-md-2 control-label" for="textinput">醫師</label>
	                                <div class="col-md-2">
	                                	<label id="doctorName" class="control-label" for="textinput"></label>
	                                </div>
	                                <div class="col-md-3">
	                                	<label id="currentNumber" class="control-label" for="textinput"></label>
	                                </div>
	                                <div class="col-md-3">
	                                	<label id="appointNumber" class="control-label" for="textinput"></label>
	                                </div>
	                        </div>

	                        <div class="form-group">
          						<div class="col-md-12"><hr></div>
          					</div>

							<!-- 掛號人 -->
							<div class="form-group">
								<label class="col-md-4 control-label" for="selectbasic">掛號人</label>
								<div class="col-md-4">
									<select id="selectPatient" name="selectbasic" class="form-control">
										<option idNumber="${member.idNumber}" value=0>本人</option>
										<c:forEach var="family" items="${member.families}" >
											<option idNumber="${family.idNumber}" value=${family.familyId}>${family.name}</option>
										</c:forEach>

									</select>
								</div>
							</div>

							<!-- 輸入身分證-->
							<div class="form-group">
								<label class="col-md-4 control-label" for="textinput">身分證字號</label>
								<div class="col-md-4">
									<input id="idNumber" name="textinput" type="text" pattern="^[a-zA-Z]{1}[1-2]{1}[0-9]{8}$" placeholder="身分證字號" class="form-control input-md" value="${member.idNumber}" required >
								</div>
							</div>
							
							<!-- 是否儲存身分證字號-->
                            <div class="form-group">
                                <label class="col-md-4 control-label" for="textinput">儲存ID</label>
                                <div class="col-md-8">
									    <label class="checkbox-inline">
									      <input type="checkbox" name="checkKeepId" id="isKeepId" >(儲存ID僅於之後掛號時方便自動帶入使用)
									    </label>
                                </div>
                            </div>

							

						</fieldset>

						<input id="shift" type="hidden">
						<input id="scheduleId" type="hidden">


				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="submit" class="btn btn-primary">確定掛號</button>


				</div>

				</form>



			</div>


		</div>
	</div>


    <!-- Appoint Result -->
    <div class="modal fade" id="appointResultModal" tabindex="-1" >
		<div class="modal-dialog">

			<div class="modal-content">

				<div class="modal-header">
                	<button type="button" class="close" data-dismiss="modal">&times;</button>
                	<h4 class="modal-title content-title">預約掛號成功!</h4>
                </div>
				<div class="modal-body">
					<fieldset>
						<div class="form-group">
                                <label class="col-md-2 control-label" for="textinput">日期</label>
                                <div class="col-md-10">
                                	<label id="date" class="control-label" for="textinput" ></label>
                                </div>
                        </div>
                        <div class="form-group">
                                <label class="col-md-2 control-label" for="textinput">院所</label>
                                <div class="col-md-10">
                                	<label id="hospitalName" class="control-label" for="textinput"></label>
                                </div>
                        </div>
                        <div class="form-group">
                                <label class="col-md-2 control-label" for="textinput">科別</label>
                                <div class="col-md-10">
                                	<label id="divisionName" class="control-label" for="textinput"></label>
                                </div>
                        </div>
                        
                        <div class="form-group">
                                <label class="col-md-2 control-label" for="textinput">診別</label>
                                <div class="col-md-10">
                                	<label id="shiftName" class="control-label" for="textinput"></label>
                                </div>
                        </div>
          
          				<div class="form-group">
                                <label class="col-md-2 control-label" for="textinput">醫師</label>
                                <div class="col-md-10">
                                	<label id="doctorName" class="control-label" for="textinput"></label>
                                </div>
                        </div>
                        
                        <div class="form-group">
                                <label class="col-md-2 control-label" for="textinput">掛號序號</label>
                                <div class="col-md-10">
                                	<label id="appointNumber" class="control-label" for="textinput"></label>
                                </div>
                        </div>
                        
					</fieldset>
				</div>

				<div class="modal-footer">              
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary">完成</button>
                </div>


			</div>
		</div>
	</div>





</body>
</html>