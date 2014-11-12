<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String hospital_list = (String) request.getAttribute("hospital_list");
%>
<!DOCTYPE html>
<html>

<head>
	<title>DrCALL首頁</title>

	<!-- CSS STYLE -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/realperson-1.1.1/jquery.realperson.css">
    
    <!-- JAVASCRIPT -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootbox-4.2.0/bootbox.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/realperson-1.1.1/jquery.realperson.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/autocomplete-1.2.7/jquery.autocomplete.min.js"></script>
 
    <script type="text/javascript">
        $(document).ready(function () {

            $("#menuIndex").addClass("active");
            
            // 設定醫院清單自動查詢
            var hospital_list_json = JSON.parse('${hospital_list}');
	  		$('#autocomplete').autocomplete({
	    		lookup: hospital_list_json,
	    		onSelect: function (suggestion) {    		
	    			$("#autocomplete").attr("hospitalId",suggestion.hospitalId);    			
	    			$("#hospitalId").attr("value",suggestion.hospitalId);	
	    		}
	  		});
       
            // CHECK DEFAULT COOKIE 
            if($.cookie("isDefault") == 'true'){
         		$("#autocomplete").attr("hospitalId", $.cookie("hospitalId"));
         		$("#autocomplete").val($.cookie("hospitalName"));
             	$("#select_devision").append($("<option></option>").attr("value", $.cookie("divisionId")).text($.cookie("divisionName")));
             	$("#select_devision").val($.cookie("divisionId")).change();
             	$("#select_date").val($.formatDateTime('yy-mm-dd', new Date($.cookie("date"))));      	
             	$("#select_shift").val($.cookie("shift")).change();
             	
             	$.cookie('isDefault', null);
				$.cookie('hospitalId', null);
				$.cookie('hospitalName', null);
				$.cookie('divisionId', null);
				$.cookie('divisionName', null);
				$.cookie('shift', null);
				$.cookie('date', null);       
            }          

            // ================== 診別查詢 ==================
            $("#search_btn").click(function () {

                $.ajax({
                    type: "POST",
                    url: "app/index/findDivisionsByHospital",
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

            // ================== 看診答詢 ==================
            $("#searchQueryAppointModal").click(function () {
            
            	// check isLogin and disable the submit button if not login 
            	var isLogin = "hidden";
            	$.ajax({ type: "GET", url: "app/index/isLogin",async: false, 
            		success: function (data) {

                        if (data.isLogin == 'true') {
                        	$("#queryAppointModal").find("#queryAppointFormBtn").show();
                        	$("#queryAppointModal").find("#queryAppointFormLogin").hide();                 	
                        	isLogin = "visible";
                        } else {
                        	$("#queryAppointModal").find("#queryAppointFormBtn").hide();
                        	$("#queryAppointModal").find("#queryAppointFormLogin").show();
                        }

               		},
                    error: function (xhr, ajaxOptions, thrownError) {
                        alert(xhr.status);
                        alert(thrownError);
                    }
                });
                    
                var hospitalId = $("#autocomplete").attr('hospitalId');
                var divisionId = $("#select_devision").val();
                var shift = $("#select_shift").val();
                var date = new Date($("#select_date").val());

                $.ajax({
                    type: "POST",
                    url: "app/index/findScheduleBDate",
                    async: false,
                    data: {
                        hospitalId: hospitalId,
                        divisionId: divisionId,
                        date: date,
                        selectShift: shift
                    },
                    beforeSend: function (data) {

                        if (hospitalId == null) {
                        	$("#queryAppointNoteModal").find("#note").html("院所輸入錯誤請重新選擇!!");
                        	$("#queryAppointNoteModal").modal('show');
                            return false;
                        }

                        if (divisionId == '') {
                        	$("#queryAppointNoteModal").find("#note").html("診別輸入有誤請重新輸入!!");
                        	$("#queryAppointNoteModal").modal('show');
                            return false;
                        }

                        if (date == 'Invalid Date') {
                        	$("#queryAppointNoteModal").find("#note").html("日期有誤請重新選擇日期!!");
                        	$("#queryAppointNoteModal").modal('show');
                            return false;
                        }

                        if (shift == '') {
                        	$("#queryAppointNoteModal").find("#note").html("時段輸入有誤請重新選擇時段!!");
                        	$("#queryAppointNoteModal").modal('show');
                            return false;
                        }
                    },
                    success: function (data) {

                        var shifts = JSON.parse(data.schedule_shift);
						$("#queryAppointModal").find("#queryAppointList div").remove();
                        $.each(shifts, function (j, shift) {

                            var doctorName = shift.doctor_name;
                            var waitingNum = shift.waitingNum;
                            var callingNo = shift.callingNo;
                            var room = shift.room;
                            var scheduleId = shift.scheduleId;
                            var status = shift.status;
                            
                           // alert(callingNo+" "+waitingNum);
                      		
                           	if(status == true){
                           		var scheduleHTML = "<div class='form-group'>"+
                        		"<input class='col-md-2' style='visibility:"+isLogin+"' type='radio' name='queryAppointListSelect' value='"+scheduleId+"' doctorName='"+doctorName+"' callingNo='"+callingNo+"' waitingNum='"+waitingNum+"'>"+
                        		"<label class='col-md-2 control-label'>有</label>"+
                        		"<label class='col-md-2 control-label'>"+doctorName+"</label>"+
                        		"<label class='col-md-3 control-label'>"+callingNo+"</label>"+
                        		"<label class='col-md-3 control-label'>"+waitingNum+"</label></div>";
                           	} else if(status == false){
                           		var scheduleHTML = "<div class='form-group'>"+
                        		"<input class='col-md-2' disabled='true' style='visibility:"+isLogin+"' type='radio' name='queryAppointListSelect' value='"+scheduleId+"' doctorName='"+doctorName+"' callingNo='"+callingNo+"' waitingNum='"+waitingNum+"'>"+
                        		"<label class='col-md-2 control-label'>無</label>"+
                        		"<label class='col-md-2 control-label'>"+doctorName+"</label>"+
                        		"<label class='col-md-3 control-label'>0</label>"+
                        		"<label class='col-md-3 control-label'>0</label></div>";
                           	}
                                      	
                        	
                    	
                        	
                            $("#queryAppointModal").find("#queryAppointList").append(scheduleHTML);
                            
                            
                        });

						var date = $("#select_date").val();
						var hospitalName = $("#autocomplete").val();
						var divisionName = $("#select_devision :selected").text();
						var shiftName = $("#select_shift :selected").text();
													
										
						$("#queryAppointModal").find("#date").html(date);
						$("#queryAppointModal").find("#hospitalName").html(hospitalName);
						$("#queryAppointModal").find("#divisionName").html(divisionName);
						$("#queryAppointModal").find("#shiftName").html(shiftName);
						$("#queryAppointModal").find("#shift").val(shift);
						
						
                        $('#queryAppointModal').modal('show');
                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                        alert(xhr.status);
                        alert(thrownError);
                    }
                });
            });
            
            $("#queryAppointForm").on("submit", function (event) {
            	event.preventDefault();
            	
            	var date = $("#queryAppointModal").find("#date").html();
				var hospitalName = $("#queryAppointModal").find("#hospitalName").html();
				var divisionName = $("#queryAppointModal").find("#divisionName").html();
				var shiftName = $("#queryAppointModal").find("#shiftName").html();
            	var scheduleId = $("#queryAppointModal").find("#queryAppointList").find("input[type='radio']:checked").val();
            	var doctorName = $("#queryAppointModal").find("#queryAppointList").find("input[type='radio']:checked").attr("doctorName");
            	
            	var callingNo = $("#queryAppointModal").find("#queryAppointList").find("input[type='radio']:checked").attr("callingNo");
            	var waitingNum = $("#queryAppointModal").find("#queryAppointList").find("input[type='radio']:checked").attr("waitingNum");
            	
            	var appointNumber = $("#queryAppointModal").find("#queryAppointList").find("input[type='radio']:checked").attr("appointNumber");
            	var shift = $("#queryAppointModal").find("#shift").val();     
            	
            	//alert(callingNo+" "+waitingNum);
            	
            	$("#quickAppointModal").find("#date").html(date);
            	$("#quickAppointModal").find("#hospitalName").html(hospitalName);
            	$("#quickAppointModal").find("#divisionName").html(divisionName);
            	$("#quickAppointModal").find("#shiftName").html(shiftName);
            	$("#quickAppointModal").find("#doctorName").html(doctorName);
            	$("#quickAppointModal").find("#callingNo").html("目前看診序號: "+callingNo);
            	$("#quickAppointModal").find("#waitingNum").html("等候人數: "+waitingNum);
            	$("#quickAppointModal").find("#scheduleId").val(scheduleId);
            	$("#quickAppointModal").find("#shift").val(shift);
            	
            	$('#queryAppointModal').modal('hide');
            	$('#quickAppointModal').modal('show');
                
            });       
            
            $("#quickAppointForm").on("submit", function (event) {
                event.preventDefault();

                var idNumber = $("#quickAppointForm").find("#idNumber").val();
                var familyMemberId = $("#quickAppointForm").find("#familyMemberId").val();
                var shift = $("#quickAppointForm").find("#shift").val();
                var scheduleId = $("#quickAppointForm").find("#scheduleId").val();
				var isKeepId = $("#quickAppointForm").find('input[id="isKeepId"]').is(':checked');
				
                $.ajax({
                    type: "POST",
                    url: "app/index/submitQuickAppointment",
                    async: true,
                    data: {
                        idNumber: idNumber,
                        familyMemberId: familyMemberId,
                        scheduleId: scheduleId,
                        shift: shift,
                        isKeepId : isKeepId
                    },
                    beforeSend: function (data) {
                        $("#search_btn_waiting").remove();
                        
                        
                        // MOVE AJAX WAITING IMAGES
                        $("#ajaxWaiting").appendTo("#quickAppointForm");
                        $("#ajaxWaiting").show();
                    },
                    complete:function(){
                    	$("#ajaxWaiting").hide();
                	},
                    success: function (data) {

                        $('#quickAppointModal').modal('hide');
                        
                        var date = $("#quickAppointForm").find("#date").html();
						var hospitalName = $("#quickAppointForm").find("#hospitalName").html();
						var divisionName = $("#quickAppointForm").find("#divisionName").html();
						var shiftName = $("#quickAppointForm").find("#shiftName").html();
            			var doctorName = $("#quickAppointForm").find("#doctorName").html();
            			
                        
                        $("#quickAppointResultModal").find("#date").html(date);
            			$("#quickAppointResultModal").find("#hospitalName").html(hospitalName);
            			$("#quickAppointResultModal").find("#divisionName").html(divisionName);
            			$("#quickAppointResultModal").find("#shiftName").html(shiftName);
            			$("#quickAppointResultModal").find("#doctorName").html(doctorName);
            			$("#quickAppointResultModal").find("#appointNumber").html(data.appointNumber);
                         
                        $('#quickAppointResultModal').modal('show');
                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                        alert(xhr.status);
                        alert(thrownError);
                    }
                });
            });
            
            
            $("#quickAppointForm").find("#familyMemberId").change(function() {			
 				var idNumber = $("#quickAppointForm").find("#familyMemberId").find("option:selected").attr("idNumber");	
 				$("#quickAppointForm").find("#idNumber").val(idNumber);
			});
			
			
			 $("#queryAppointFormLogin").on("click", function (event) { 
 			 	var hospitalId = $("#autocomplete").attr('hospitalId');
 			 	var hospitalName = $("#autocomplete").val();
                var divisionId = $("#select_devision").val();
                var divisionName = $('#select_devision :selected').text();
                var shift = $("#select_shift").val();
                var date = new Date($("#select_date").val());         
               
				$.cookie("isDefault", true);
				$.cookie("hospitalId", hospitalId);
				$.cookie("hospitalName", hospitalName);	
				$.cookie("divisionId", divisionId);
				$.cookie("divisionName", divisionName);	
				$.cookie("shift", shift);
				$.cookie("date", date);
				
				window.location.href = "login.jsp";
			 });
			 
			// ================== 免費體驗 ==================
			 $("#experience_btn").on("click", function (event) {
			 	 //bootbox.alert("<h4 class='content-title'>免費體驗</h4><div class='content-text'><br>"+
			 	 //	"<b>免費一</b> <br>申請Dr. Call帳號，立即贈送DrCall點數一點，可立即享受快速掛號預約和通知看診服務。<br><br>"+
			 	 //	"<b>免費二</b> <br>下載Dr. Call APP登入系統，立即再贈送DrCall點數一點，可立即享受快速掛號預約和通知看診服務。<br>");
			 	 	
			 	 	
			 	 bootbox.dialog({
				  message: "<div class='content-text'><br>"+
				  	"<b>免費一</b> <br>申請Dr. Call帳號，立即贈送DrCall點數一點，可立即享受快速掛號預約和通知看診服務。<br><br>"+
				  	"<b>免費二</b> <br>下載Dr. Call APP登入系統，立即再贈送DrCall點數一點，可立即享受快速掛號預約和通知看診服務。<br>",
				  title: "<h4 class='content-title'>免費體驗</h4>",
				  buttons: {
				    success: {
				      label: "立即加入會員",
				      className: "btn-primary",
				      callback: function() {
				        	// redirect
				        	window.location.href = "login.jsp";
				      }
				    },
				    main: {
				      label: "返回",
				      className: "btn-primary",
				      callback: function() {
				        //Example.show("Primary button");
				      }
				    }
				  }
				});
			 
			 });
			 

			 

            // ================== 留言 ==================
            $("#message_form").on("submit", function (event) {
                event.preventDefault();

                var name = $("#name").val();
                var tel = $("#tel").val();
                var email = $("#email").val();
                var messageContent = $("#messageContent").val();

                $.ajax({
                    type: "POST",
                    url: "app/index/addMessage",
                    data: {
                        defaultReal: $("#defaultReal").val(),
                        defaultRealHash: $(".realperson-hash").val(),
                        name: name,
                        tel: tel,
                        email: email,
                        content: messageContent
                    },
                    beforeSend: function (data) {

                        if (messageContent == '') {
                            bootbox.alert("訊息內容不可為空!!");
                            return false;
                        }

                    },
                    success: function (data) {

                        if (data.isRealPerson == 'false') {
                            $("#defaultReal").val("");
                            bootbox.alert("認證碼輸入錯誤!!");
                        } else {

                            $("#name").val("");
                            $("#tel").val("");
                            $("#email").val("");
                            $("#messageContent").val("");
                            $("#defaultReal").val("");

                            bootbox.alert("訊息已送出!!");
                        }
                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                        alert(xhr.status);
                        alert(thrownError);

                    }
                });
            });

            $("#submit_order_experience").click(function () {
                $('#freeExperienceModal').modal('hide');
                $('#resultMessage').modal('show');
            });



            // Click About US
            $("#aboutUS").click(function () {
                bootbox.alert("<h4 class='content-title'>關於我們</h4><div class='content-text'><br>" +
                    "<h5><b>成立起因</b></h5><br>" +
                    "<span class='first-word'>這</span>創意的起因是六年級生面臨就診狀況，期許藉由此方式，改變就診現況，他不是醫療產業的從業人員，只是位平凡的工作者。於這年紀範圍的人，在懷孕到婦產科檢查時，都是依序於就診區等候。而醫生隨時有需進行接生，因此造成看診時間後延，這等的時間看產間狀況而定，直到醫生結束回來看診。而小朋友出生後，接續而來的是一連串的接種疫苗、小朋友生病…等，當下已不舒服，父員更怕與其它小朋友，有交互傳染的狀況，這不但苦了小朋友，更苦了照顧的父母啊!<br><br>" +
                    "以上的場景是否似曾相似??將診間看診訊息適時傳送出去，則可安心運用時間，並待通知再前往就診即可。此舉除可有效運用時間外，其相關費用(如停車費)、效益(交互感染)…等，更是可降低與避免。<br><br>" +
                    "創立Dr. Call就是為了將此訊息落差消除，讓消費者可以將等候的時間自行運用，可避免感染、降低成本…等好處。而這項服務需要不斷提昇通知準確性，故向有需求此服務的消費者收取通知服務費，讓我們可因此繼續研發，與提供更好的服務，這樣是消費者、院所與Dr. Call三贏的局面。<br><br>" +
                    "期待大家的支持，更盼給予團隊不足處的相關建議，讓團隊有動力，致力提昇更好的消費體驗，更精準的通知時間，而持續不斷的努力。</div>");
            });

            // 處理 MESSAGE REAL PERSON
            $(function () {
                $('#defaultReal').realperson();
            });

        });
    </script>

    <style type="text/css">
		.autocomplete-suggestions { border: 1px solid #999; background: #fff; cursor: default; overflow: auto; width:280px!important;}
		.autocomplete-suggestion { padding: 5px 5px; font-size: 1em; white-space: nowrap; overflow: hidden; }
		.autocomplete-selected { background: #f0f0f0; }
		.autocomplete-suggestions strong { font-weight: normal; color: #3399ff; }
    </style>


</head>

<body>

    <div class="container">
        <div class="row clearfix">
            <div class="col-md-12 column">
                <div class="row clearfix">
                    <div class="col-md-4 column">
                        <form id="appointment_form" class="form-horizontal">
                            <fieldset>

                                <!-- Form Name -->

                                <!-- Select Basic -->
                                <div class="form-group">

                                    <div class="col-md-12">
                                        <div class="input-group">
                                            <input id="autocomplete" name="autocomplete" type="search" placeholder="*輸入院所" class="form-control input-md" x-webkit-speech lang="zh-TW">
                                            <span class="input-group-btn">
        												<button id="search_btn" class="btn btn-default" type="button">科別查詢</button>
      												</span>
                                        </div>
                                    </div>

                                </div>

                                <!-- Select Basic -->
                                <div id="searchSchedule" class="form-group">
                                    <div class="col-md-12">
                                        <select id="select_devision" name="selectbasic" class="form-control">
                                            <option value="">*科別 (請先於「輸入院所」，並點擊「診別查詢」)</option>
                                        </select>
                                    </div>
                                </div>


                                <div id="searchSchedule" class="form-group">
                                    <div class="col-md-12">
                                        <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd">
                                            <input id="select_date" class="form-control" size="16" type="text" value="" readonly>
                                            <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                                            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                        </div>
                                    </div>
                                </div>

                                <div id="searchSchedule" class="form-group">
                                    <div class="col-md-12">
                                        <select id="select_shift" name="selectbasic" class="form-control">
                                            <option value="">*時段</option>
                                            <option value=0>早班 08:00-12:00</option>
                                            <option value=1>午班 13:00-17:00</option>
                                            <option value=2>晚班 18:00-21:00</option>
                                        </select>
                                    </div>
                                </div>


                                <!-- Select Basic -->
                                <div id="searchSchedule" class="form-group">
                                    <div class="col-md-12">
                                        <button id="searchQueryAppointModal" type="button" name="sing" class="btn btn-info btn-block">看診查詢</button>
                                    </div>
                                </div>

                                <div id="searchSchedule" class="form-group">
                                    <div class="col-md-12">
                                        <button id="experience_btn" type="button" name="sing" class="btn btn-success btn-block">免費體驗</button>
                                    </div>
                                </div>

                            </fieldset>
                        </form>

                    </div>
                    <div class="col-md-8 column">
                        <div class="jumbotron">
                            <h3 class="content-title">
							Welcome!! Dr. Call
						</h3>
                            <div class="content-text">
                                <p>即時查詢院所看診進度，線上預約掛號，即刻看診通知，看診無需等待..
                                </p>
                            </div>
                            <br>
                            
                            
                            <c:if test="${member.memberId == null }">
                            	<p><a class="btn btn-primary btn-large" href="login.jsp">立即加入會員</a></p>
                            </c:if>
                            
                            
                        </div>

                    </div>
                </div>

                <div class="media">
                    <h3 class="section-title text-left">Charitable activities 公益活動</h3>
                    <a href="#" class="pull-left">
                        <img src="http://lorempixel.com/64/64/" class="media-object" alt='' />
                    </a>
                    <div class="media-body">
                        <h4 class="media-heading content-title">
						Social Charity 社會公益
					</h4>
                        <div style="font-size: 14px;">&nbsp;</div>
                        <div class="content-text"><span class="first-word">D</span>r. Call了解自己是社會的一份子，本著取之於社會，用之於社會的精神外，讓社會大眾一起提昇，自己也才可以永續的營運，故應善盡社會公民的責任。Dr. Call深知除了將本業經營好，提供若干的就業機會外，對於社會的弱勢，更期盼可提供相關協助，讓這份大愛可被善用。Dr. Call更結合本身的長處，提供弱勢者需要的服務，藉由捐款給慈善單位，可將捐款向Dr. Call申請點數獎勵回饋，更可再將這份獎勵回饋，捐贈給弱勢的人，讓需要幫助的人，可以有效運用時間，來照顧需要的家人。在此謹代表Dr. Call團隊與受贈人，謝謝這份愛心與願意分享，讓這個社會更美好。</div>
                    </div>
                </div>


                <div class="row clearfix">



                    <div class="col-md-8 column">

                        <!-- 線上留言 -->
                        <form action="/" id="message_form" class="form-horizontal">
                            <fieldset>

                                <h3 class="section-title text-left">Online Message 線上留言</h3>

                                <div class="form-group">
                                    <label class="col-md-1 control-label" for="textinput">*稱呼</label>
                                    <div class="col-md-3">
                                        <input id="name" name="textinput" type="text" placeholder="姓名" class="form-control input-md" value="${member.name}" required>
                                    </div>

                                    <label class="col-md-1 control-label" for="textinput">電話</label>
                                    <div class="col-md-3">
                                        <input id="tel" name="textinput" type="tel" placeholder="0970888888" class="form-control input-md" pattern="^[\d]{10}$" value="${member.memberId}">
                                    </div>

                                    <label class="col-md-1 control-label" for="textinput">*Email</label>
                                    <div class="col-md-3">
                                        <input id="email" name="textinput" type="email" placeholder="Email" class="form-control input-md" value="${member.email}" required>
                                    </div>

                                </div>

                                <div class="form-group">
                                    <label class="col-md-1 control-label" for="textarea">*留言</label>
                                    <div class="col-md-11">
                                        <textarea class="form-control" id="messageContent" name="textarea"></textarea>
                                    </div>
                                </div>

                                <!-- Button -->
                                <div class="form-group">
                                    <!--   <label class="col-md-2 control-label" for="singlebutton">Single Button</label> -->
                                    <div class="col-sm-offset-1 col-md-3">
                                        <input id="defaultReal" name="defaultReal" type="text" class="form-control input-md">


                                    </div>


                                </div>

                                <div class="form-group">
                                    <div class="col-sm-offset-1 col-sm-3">
                                        <button type='submit' id='singlebutton' name='singlebutton' class='btn btn-primary btn-block'>送出</button>
                                    </div>
                                </div>

                            </fieldset>
                        </form>

                    </div>
                    
                   <div class="col-md-4 column">

                        <div class="panel-group" id="panel-1000">
                            <h3 class="section-title text-left">FAQS 常見問題</h3>


                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <a class="panel-title collapsed" data-toggle="collapse" data-parent="#panel-1000" href="#panel-element-1001">如何線上掛號預約</a>
                                </div>
                                <div id="panel-element-1001" class="panel-collapse collapse">
                                    <div class="panel-body">
                                        Anim pariatur cliche...
                                    </div>
                                </div>
                            </div>
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <a class="panel-title collapsed" data-toggle="collapse" data-parent="#panel-1000" href="#panel-element-1002">何謂掛號通知</a>
                                </div>
                                <div id="panel-element-1002" class="panel-collapse collapse">
                                    <div class="panel-body">
                                        Anim pariatur cliche...
                                    </div>
                                </div>
                            </div>
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <a class="panel-title collapsed" data-toggle="collapse" data-parent="#panel-1000" href="#panel-element-1003">如何購買點數</a>
                                </div>
                                <div id="panel-element-1003" class="panel-collapse collapse">
                                    <div class="panel-body">
                                        Anim pariatur cliche...
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>

                </div>





            </div>




        </div>
    </div>



    <footer>
        <div class="container">
            <div class="row clearfix">
                <!-- Contact US -->
                <div class="col-md-4 column">
                    <div class="headline">
                        <h3>Contact US 聯絡我們</h3>
                    </div>
                    <div class="content">
                        <p>地址 Address: 桃園縣xx區xxx路xxx號xF-x</p>
                        <p>電話 Tel: 03-012-1234</p>
                        <p>電子郵件 Email: <a href="#">admin@drcall.com</a>
                        </p>
                        </p>
                    </div>
                </div>
                <!-- Information 相關資料 -->
                <div class="col-sm-4">
                    <div class="headline">
                        <h3>Information 相關資料</h3>
                    </div>
                    <div class="content social">
                        <p><a href="#" id="aboutUS">About US 關於我們</a>
                        </p>
                        <p>About US 關於我們</p>
                        <div class="clearfix">test</div>
                        <div class="clearfix">test</div>
                    </div>
                </div>
                <!-- Download 下載 APP -->
                <div class="col-sm-4">
                    <div class="headline">
                        <h3>Download 下載 APP</h3>
                    </div>
                    <div class="content">
                        <p>App download address</p>

                        <p>
                            <img id="qrImg" src="/E8DPortal/style/images/app_download.png" alt="" width="70" height="70" class="icon-air">
                        </p>


                    </div>
                </div>
            </div>
        </div>
    </footer>

	<!-- AJAVX Waiting Image -->
	<div id="ajaxWaiting" class="ajaxWaiting" style="display: none;">
		<img src="images/ajax-loader.gif">
	</div>
	
    <!-- Free Experience Note -->
   	<div class="modal fade" id="freeExperienceNoteModal" tabindex="-1">
     	<div class="modal-dialog">
     		            <div class="modal-content">
                <form class="form-horizontal">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title content-title">表單尚未填寫完成</h4>
                        <br>
                        	<b>說明:</b> <br> 這是如同試吃的概念，我們也提供掛號通知的服務體驗，省下您的寶貴時間是我們的期望，更希望喜歡我們的服務。<br>
                        <br><b>流程:</b>
                        <br>1. 先於 DrCall首頁 輸入院所，並點擊「診別查詢」。
                        <br>2. 選擇「院所科別」、「看診日期」以及「看診時段」。
                        <br>3. 確認後點擊「免費體驗」。 
                    </div>
                    <div class="modal-body">
                        <fieldset>
							<b id="note"></b>
                        </fieldset>
                    </div>
                    <div class="modal-footer">
                        <input id="shift" type="hidden">
                        <button type="button" class="btn btn-default" data-dismiss="modal">返回</button>
                    </div>
                </form>
            </div>
     	</div>
    </div>
    
    <!-- Query Appoint Note -->
   	<div class="modal fade" id="queryAppointNoteModal" tabindex="-1">
     	<div class="modal-dialog">
     		            <div class="modal-content">
                <form class="form-horizontal">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title content-title">表單尚未填寫完成</h4>
                        <br>
                        	<b>說明:</b> <br>Drcall 提供醫療院所砍診進度查詢，並提供會員線上預約掛號<br>
                        <br><b>流程:</b>
                        <br>1. 先於 DrCall首頁 輸入「院所」，並點擊「診別查詢」。
                        <br>2. 選擇「院所科別」、「看診日期」以及「看診時段」。
                        <br>3. 確認後點擊「看診查詢」。 
                    </div>
                    <div class="modal-body">
                        <fieldset>
							<b id="note"></b>
                        </fieldset>
                    </div>
                    <div class="modal-footer">
                        <input id="shift" type="hidden">
                        <button type="button" class="btn btn-default" data-dismiss="modal">返回</button>
                    </div>
                </form>
            </div>
     	</div>
    </div>

	<!-- Query Appoint -->
	<div class="modal fade" id="queryAppointModal">
		<div class="modal-dialog">
		
			<div class="modal-content">
			
				<form id="queryAppointForm">
			
				<div class="modal-header">
                	<button type="button" class="close" data-dismiss="modal">&times;</button>
                	<h4 class="modal-title content-title">預約掛號查詢</h4>
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
                                <label id="" class="col-md-3 control-label" for="textinput">目前掛號人數</label>
                        </div>
                        
                        <div id="queryAppointList">
    
                        	
                        </div>
					</fieldset>
				</div>
				
				<div class="modal-footer">
                        <input id="shift" type="hidden">                
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button id="queryAppointFormBtn" type="submit" class="btn btn-primary">預約掛號</button>
                        <button id="queryAppointFormLogin" type="button" class="btn btn-danger">預約掛號需先登入系統</button>
                </div>
				
				
				</form>
				
			</div>
		</div>
	</div>
	

    <!-- Quick Appoint -->
    <div class="modal fade" id="quickAppointModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content ">

                <form id="quickAppointForm" class="form-horizontal">

                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title content-title" id="myModalLabel">預約掛號</h4>
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
	                                	<label id="callingNo" class="control-label" for="textinput"></label>
	                                </div>
	                                <div class="col-md-3">
	                                	<label id="waitingNum" class="control-label" for="textinput"></label>
	                                </div>
	                        </div>
	                        
	                        <div class="form-group">
          						<div class="col-md-12"><hr></div>
          					</div>



                            <!-- 掛號人 -->
                            <div class="form-group">
                                <label class="col-md-4 control-label" for="selectbasic">掛號人</label>
                                <div class="col-md-4">
                                    <select id="familyMemberId" name="selectbasic" class="form-control">
                                        <option idNumber="${member.idNumber}" value=0 >本人</option>
                                        <c:forEach var="family" items="${member.families}">
                                            <option idNumber="${family.idNumber}" value="${family.familyId}">${family.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            
                            
                            
                            <!-- 輸入身分證-->
                            <div class="form-group">
                                <label class="col-md-4 control-label" for="textinput">身分證字號(ID)</label>
                                <div class="col-md-4">
                                    <input id="idNumber" name="textinput" type="text" pattern="^[a-zA-Z]{1}[1-2]{1}[0-9]{8}$" placeholder="身分證字號" class="form-control input-md" value="${member.idNumber}" required>

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

                    </div>
                    <div class="modal-footer">
                        <input id="shift" type="hidden">
                        <input id="scheduleId" type="hidden">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary">確定掛號</button>                 
                    </div>

                </form>
            </div>


        </div>
    </div>


    <!-- Quick Appoint Result -->
    <div class="modal fade" id="quickAppointResultModal">
		<div class="modal-dialog">
		
			<div class="modal-content">
			
				<form id="queryAppointForm">
			
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
                        <button type="submit" class="btn btn-primary">完成</button>
                </div>
				
				
				</form>
				
			</div>
		</div>
	</div>


</body>

</html>