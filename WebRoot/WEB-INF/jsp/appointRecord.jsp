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

<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootbox-4.2.0/bootbox.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/autocomplete-1.2.7/jquery.autocomplete.min.js"></script>
<script type="text/javascript">

	function changeOnclik(appointId) {
	
		$("#changeAppointForm").find("#appointId").val(appointId);
		$("#changeAppointModal").modal('show');
	
	}
	

	function cancelOnclik(appointId) {
	
			bootbox.confirm("<h4 class='content-title'>預約掛號取消</h4><div class='content-text'><br>"
				+"<b>預約掛號取消，DrCall將收取一點手續費，請再次確認是否取消?</b><br>", function(result) {
				
					if(result == true){
					
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

			}); 

	}


	$(window).ready(function() {
		$("#menuRecord").addClass( "active");
		
		 // 設定醫院清單自動查詢
            var hospital_list_json = JSON.parse('${hospital_list}');
            
	  		$('#autocomplete').autocomplete({
	    		lookup: hospital_list_json,
	    		onSelect: function (suggestion) {    		
	    			$("#autocomplete").attr("hospitalId",suggestion.hospitalId);    			
	    			$("#hospitalId").attr("value",suggestion.hospitalId);	
	    		}
	  		});
	  		
	  		
	  		// ================== 診別查詢 ==================
            $("#search_btn").click(function () {

                $.ajax({
                    type: "POST",
                    url: "findDivisionsByHospital",
                    async: true,
                    data: {
                        hospitalId: $("#autocomplete").attr('hospitalId')
                    },
                    beforeSend: function (data) {
                        if (typeof $("#autocomplete").attr('hospitalId') === 'undefined') {
                            bootbox.alert("院所輸入有誤，請重新輸入。");
                            return false;
                        }
						
						$("#ajaxWaiting").show();
                    },
                    complete:function(){
                    	$("#ajaxWaiting").hide();
                	},
                    success: function (data) {
                        var divisions = data.divisions;

                        $('#select_devision option').remove();

                        $('#select_devision').append($("<option></option>").attr("value", "").text("請選擇科別"));
                        $.each(divisions, function (i, division) {
                            $('#select_devision')
                                .append($("<option></option>")
                                    .attr("value", division.divisionId)
                                    .text(division.cnName));
                        });

                        $("#searchSchedule").show();
                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                        alert(xhr.status);
                        alert(thrownError);
                    }
                });

            });
            
            // ===== CHANGE APPOINT =======
            $("#changeAppointForm").on("submit", function (event) {
            	event.preventDefault();
   
   				
   
            	var hospitalId = $("#autocomplete").attr('hospitalId');
                var divisionId = $("#select_devision").val();
                var shift = $("#select_shift").val();
                var date = new Date($("#select_date").val());
                
            	
            	// CHECK INPUT IS_CORRECT
            	$("#changeAppointForm").find("#caf_note1").hide();
            	if(hospitalId == null || divisionId == '' || shift == '' || date == ''){
            		$("#changeAppointForm").find("#caf_note1").fadeIn( "slow", function() {
    					$(this).css('color', '#008080');
  					});
  					return;
            	}
            		
            		// FETCH SCHEDULE 
            		 $.ajax({
                    	type: "POST",
                    	url: "findScheduleBDate",
                    	async: false,
                    	data: {
                        	hospitalId: hospitalId,
                        	divisionId: divisionId,
                        	date: date,
                        	selectShift: shift
                    	}, success: function (data) {
                    		var shifts = JSON.parse(data.schedule_shift);
							$("#confirmChangeAppointModal").find("#queryAppointList div").remove();
	                        $.each(shifts, function (j, shift) {
	
	                            var doctorName = shift.doctor_name;
	                            var maxNumber = shift.max_number;
	                            var currentNumber = shift.current_number;
	                            var scheduleId = shift.scheduleId;
	                            var status = shift.status;
	                            
	                      		
	                           	if(status == true){
	                           		var scheduleHTML = "<div class='form-group'>"+
	                        		"<input class='col-md-2' type='radio' name='queryAppointListSelect' value='"+scheduleId+"' doctorName='"+doctorName+"' currentNumber='"+currentNumber+"' appointNumber='"+maxNumber+"'>"+
	                        		"<label class='col-md-2 control-label'>有</label>"+
	                        		"<label class='col-md-2 control-label'>"+doctorName+"</label>"+
	                        		"<label class='col-md-3 control-label'>"+currentNumber+"</label>"+
	                        		"<label class='col-md-3 control-label'>"+maxNumber+"</label></div>";
	                           	} else if(status == false){
	                           		var scheduleHTML = "<div class='form-group'>"+
	                        		"<input class='col-md-2' disabled='true' type='radio' name='queryAppointListSelect' value='"+scheduleId+"' doctorName='"+doctorName+"' currentNumber='"+currentNumber+"' appointNumber='"+maxNumber+"'>"+
	                        		"<label class='col-md-2 control-label'>無</label>"+
	                        		"<label class='col-md-2 control-label'>"+doctorName+"</label>"+
	                        		"<label class='col-md-3 control-label'>0</label>"+
	                        		"<label class='col-md-3 control-label'>0</label></div>";
	                           	}
	                                      	
	                        	
	                    	
	                        	
	                            $("#confirmChangeAppointModal").find("#queryAppointList").append(scheduleHTML);
	                        });
	                        
	                        var date = $("#select_date").val();
							var hospitalName = $("#autocomplete").val();
							var divisionName = $("#select_devision :selected").text();
							var shiftName = $("#select_shift :selected").text();
							var appointId = $("#changeAppointForm").find("#appointId").val();							
													
											
							$("#confirmChangeAppointModal").find("#date").html(date);
							$("#confirmChangeAppointModal").find("#hospitalName").html(hospitalName);
							$("#confirmChangeAppointModal").find("#divisionName").html(divisionName);
							$("#confirmChangeAppointModal").find("#shiftName").html(shiftName);
							$("#confirmChangeAppointModal").find("#shift").val(shift);
							$("#confirmChangeAppointModal").find("#appointId").val(appointId);
							
							
							$('#changeAppointModal').modal('hide');
	                        $('#confirmChangeAppointModal').modal('show');
	                        
                    	
                    	}, error: function (xhr, ajaxOptions, thrownError) {
	                        alert(xhr.status);
	                        alert(thrownError);
	                    }
                    });
            	
            });
            
            $("#appointResultModal").blur(function() {
				window.location.reload();
			});
            

			// RETURN CHANGE APPOINT FORM            
            $("#confirmChangeAppointForm").find("#return").on("click", function (event) {
            
            	$('#changeAppointModal').modal('show');
            
            });
            
            // SUBMIT
	  		$("#confirmChangeAppointForm").on("submit", function (event) {
	  			 event.preventDefault();
	  			 
	  			var scheduleId = $("#confirmChangeAppointForm").find("#queryAppointList").find("input[type='radio']:checked").val();
	  			var shift = $("#confirmChangeAppointForm").find("#shift").val();
	  			var appointId = $("#confirmChangeAppointForm").find("#appointId").val();
	  			
	  			
	  			// CHANGE APPOINT
	  			$.ajax({
                    	type: "POST",
                    	url: "changeAppointment",
                    	async: false,
                    	data: {
                        	scheduleId: scheduleId,
                        	appointId: appointId,
                        	shift: shift
                    	}, success: function (data) {
                    		
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
		 					
							$('#confirmChangeAppointModal').modal('hide');
							$('#appointResultModal').modal('show');	
                    			
                    			
                    		
                    	}, error: function (xhr, ajaxOptions, thrownError) {
	                        alert(xhr.status);
	                        alert(thrownError);
	                    } 
                    	
                });
                    	
                    	
                    	
	  			
	  			
	  			
	  		
	  		});
		
		
	});
</script>

<style type="text/css">

.autocomplete-suggestions { border: 1px solid #999; background: #fff; cursor: default; overflow: auto; width:280px!important;}
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
		
							<div class="row">
								
								<div class="col-sm-12 widget stacked widget-table action-table">

									<div class="bs-callout bs-callout-info">
										    <h4 class="content-title">預約掛號紀錄</h4>
									</div>
									<!-- /widget-header -->

									<div class="widget-content">

										<table class="table table-striped table-bordered">
											<thead>
												<tr>
													<th>編號</th>
													<th>預約掛號時間</th>
													<th>看診日期</th>
													<th>班別</th>
													<th>候診人</th>
													<th>醫院</th>
													<th>醫師</th>
													<th>科</th>
													<th>候診號</th>
													<th>狀態</th>
													<th class="td-actions">變更預約</th>
													<th class="td-actions">取消</th>
												</tr>
											</thead>
											<tbody>
											
												<c:forEach var="appoint" items="${appoints}" varStatus="status">
													<tr>
														<td>${status.index + 1}</td>
														<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${appoint.crtTime}"/></td>
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
														
														<td>${appoint.appNumber}</td>
														
														<td>
															<c:choose>
															  <c:when test="${appoint.status == 0}">預約成功</c:when>
															  <c:when test="${appoint.status == 1}">預約成功_通知_第一次通知</c:when>
															  <c:when test="${appoint.status == 2}">看診前日_通知_第二次通知</c:when>
															  <c:when test="${appoint.status == 3}">準備提醒看診</c:when>
															  <c:when test="${appoint.status == 4}">完成通知看診 (第三通知)</c:when>
															  <c:when test="${appoint.status == 5}">看診完成</c:when>
															  <c:when test="${appoint.status == 6}">預約取消</c:when>
															  <c:when test="${appoint.status == 7}">預約失敗</c:when>
															  <c:otherwise>異常</c:otherwise>
															</c:choose>
														</td>
														
														<td class="td-actions">
															<button id="appointment_btn_change" type="button" class="btn btn-sm btn-info" value="${appoint.appointId}" onclick="changeOnclik(${appoint.appointId})">變更預約</button>
														</td>
														
														<td class="td-actions">
															<button id="appointment_btn_cancel" type="button" class="btn btn-sm btn-danger" value="${appoint.appointId}" onclick="cancelOnclik(${appoint.appointId})">取消</button>
														</td>
														
														
													</tr>
												</c:forEach>
											
											</tbody>
										</table>

									</div>
									<!-- /widget-content -->

								</div>


							</div>
		
	</div>


	<!-- Change Appoint -->
    <div class="modal fade" id="changeAppointModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">

                <form id="changeAppointForm" class="form-horizontal">

                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title content-title">預約掛號變更</h4>
                        
                        <br><b>預約掛號變更，消費者可以免費變更乙次。</b>
                        
                    </div>
                    <div class="modal-body">

                        <fieldset>
                        
                        	<div id="caf_note1" class="form-group" style="display:none">
								<div class="col-sm-12">
									<b>輸入欄位資料有誤，請重新確認並輸入完整資料。</b>
								</div>
							</div>
                        

                            <div class="form-group">
                                <label class="col-md-2 control-label" for="selectbasic">院所</label>
                                <div class="col-md-8">
                                    <div class="input-group">
                                        <input id="autocomplete" name="autocomplete" type="search" placeholder="*輸入院所" class="form-control input-md" x-webkit-speech lang="zh-TW">
                                            <span class="input-group-btn">
        										<button id="search_btn" class="btn btn-default" type="button">科別查詢</button>
      										</span>
                                    </div>
                                </div>
                            </div>

                            <!-- 輸入手機驗證碼-->
                            <div class="form-group">
                                <label class="col-md-2 control-label" for="textinput">科別</label>
                                <div class="col-md-8">
                                     <select id="select_devision" name="selectbasic" class="form-control">
                                            <option value="">*科別 (請先於「輸入院所」，並點擊「診別查詢」)</option>
                                     </select>
                                </div>
                            </div>     
                            
                            <div class="form-group">
                                <label class="col-md-2 control-label" for="textinput">日期</label>
                                <div class="col-md-8">
                                    <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd">
                                            <input id="select_date" class="form-control" size="16" type="text" value="" readonly>
                                            <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                                            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="col-md-2 control-label" for="textinput">診別</label>
                                <div class="col-md-8">
                                     <select id="select_shift" name="selectbasic" class="form-control">
                                            <option value="">*時段</option>
                                            <option value=0>早班 08:00-12:00</option>
                                            <option value=1>午班 13:00-17:00</option>
                                            <option value=2>晚班 18:00-21:00</option>
                                     </select>
                                </div>
                            </div>    

                        </fieldset>

                    </div>
                    <div class="modal-footer">
<!--                         <input id="shift" type="hidden"> -->
                        <input id="appointId" type="hidden">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary">下一步</button>
                    </div>

                </form>



            </div>


        </div>
    </div>
    
    <!-- Confirm Change Appoint -->
	<div class="modal fade" id="confirmChangeAppointModal">
		<div class="modal-dialog">
		
			<div class="modal-content">
			
				<form id="confirmChangeAppointForm">
			
				<div class="modal-header">
                	<button type="button" class="close" data-dismiss="modal">&times;</button>
                	<h4 class="modal-title content-title">變更預約掛號</h4>
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
          					<div class="col-md-12"><hr></div>
          				</div>
          
                        <div class="form-group">
                        		<label class="col-md-2 control-label" for="textinput"></label>
                        		<label class="col-md-2 control-label" for="textinput">是否看診</label>
                                <label class="col-md-2 control-label" for="textinput">醫生</label>
                                <label class="col-md-3 control-label" for="textinput">目前看診序號</label>
                                <label class="col-md-3 control-label" for="textinput">目前掛號人數</label>
                        </div>
                        
                        <div id="queryAppointList">
    
                        	
                        </div>
					</fieldset>
				</div>
				
				<div class="modal-footer">
                        <input id="appointId" type="hidden">    
                        <input id="shift" type="hidden">
                        <input id="scheduleId" type="hidden">           
                        <button id="return" type="button" class="btn btn-default" data-dismiss="modal">上一步</button>
                        <button id="queryAppointFormBtn" type="submit" class="btn btn-primary">確認變更</button>
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
                	<h4 class="modal-title content-title">變更預約掛號成功!</h4>
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