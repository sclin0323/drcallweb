<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html>
<head>

<title><sitemesh:write property='title' /></title>

<meta name="viewport" content="width=device-width, initial-scale=1.0">


<!-- CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/drcall/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker/bootstrap-datetimepicker.min.css"  media="screen">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-3.1.1-dist/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-3.1.1-dist/css/bootstrap-theme.min.css">
<style type="text/css">
html,body {
	margin: 0;
	padding: 0;
	height: 100%;
}

#container {
	min-height: 100%;
	position: relative;
}

#header {
	background: #ff0;
	padding: 0px;
}

#body {
	padding: 0px;
	padding-bottom: 60px; /* Height of the footer */
}

#copyright {
	position: absolute;
	bottom: 0;
	width: 100%;
	height: 60px; /* Height of the footer */
	background: #3e4753;
	font-size: 12px;
	padding: 20px 0 7px;
}

#copyright p.copyright-space {
	color: #dadada;
}

#copyright a {
	margin: 0 5px;
	color: #72c02c;
}

.copyright a:hover {
	color: #a8f85f;
}

</style>

<!-- JavaScript -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-2.1.1/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.cookie-1.4.1/jquery.cookie.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.formatDateTime-1.0.11/jquery.formatDateTime.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap-3.1.1-dist/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap-datetimepicker/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap-datetimepicker/locales/bootstrap-datetimepicker.zh-TW.js" charset="UTF-8"></script>

<script type="text/javascript">



	$(document).ready(function() {
	
		$("#cooperation").click(function() {

			bootbox.alert("<h4 class='content-title'>院所合作提案</h4><div class='content-text'><br>Dr. Call自2013年起以無比的熱情投入院所端的預約掛號通知的服務，為了讓合作提案能快速且有系統性的評估，請希望與Dr. Call合作的廠商，儘量提供完整和正確的資訊，以利我們更有效率地評估雙方合作的進行方式。<br><br>"+
				"<h5>Dr. Call的合作效益</h5>● 持續不斷有效會員<br>● 每日平均使用人次：人次<br>● 資料的分析運用<br>● 多元的服務提供<br><br>"+
				"若您有任何整合行銷上的合作提案，歡迎與我們聯絡！異業行銷合作或刊登廣告e-mail：或電洽請附上企劃書及相關書面資料及聯絡方式，我們將儘快與您聯絡。<br><br>"+
				"聯絡人：吳秉倫<br>電話：0911xxxxxx<br>Email：van@xxx.com</div>");

		});

		$("#privacyPolicy").click(function() {

			bootbox.alert("<h4 class='content-title'>DrCall 隱私權政策</h4><div class='content-text'><br>Dr. Call是由「資訊股份有限公司」所經營；為了支持個人資料保護，維護個人隱私權，Dr. Call謹以下列聲明，向您說明Dr.Call蒐集個人資料之目的、類別、利用範圍及方式、以及您所得行使之權利等事項； 如果您對於Dr. Call的隱私權聲明、以下相關告知事項、或與個人資料保護有關之相關事項有任何疑問，可以和Dr. Call服務中心聯絡，Dr. Call將儘快回覆說明。<br><br>"+
				"<h5>■ 適用範圍</h5>Dr. Call隱私權聲明及其所包含之告知事項，僅適用於Dr. Call所擁有及經營的網站。Dr. Call網站內可能包含許多連結、或其他合作夥伴所提供的服務，關於該等連結網站或合作夥伴網站的隱私權聲明及與個人資料保護有關之告知事項，請參閱各該連結網站或合作夥伴網站。<br><br>"+
				"<h5>■ 個人資料蒐集之目的與類別 </h5>Dr. Call為了行銷、客戶管理與服務、提供網路購物及其他電子商務服務、履行法定或合約義務、保護當事人及相關利害關係人之權益、售後服務、以及經營合於營業登記項目或組織章程所定之業務等目的，依照各該服務之性質，可能蒐集您的姓名、連絡方式(包括但不限於電話、E-MAIL及地址等)、服務單位、職稱、為完成收款或付款所需之資料、ＩＰ位址、及其他得以直接或間接識別使用者身分之個人資料。<br>"+
				"此外，為提升服務品質，Dr. Call會依照所提供服務之性質，記錄使用者的IP位址、以及在Dr. Call相關網站內的瀏覽活動(例如，使用者所使用的軟硬體、所點選的網頁)等資料，但是這些資料僅供作流量分析和網路行為調查，以便於改善Dr. Call相關網站的服務品質，這些資料也只是總量上的分析，不會和特定個人相連繫。<br><br>"+
				"<h5>■ 個人資料的利用 </h5>Dr. Call所蒐集的足以識別使用者身分的個人資料，都僅供Dr. Call於其內部、依照蒐集之目的進行處理和利用，除非事先說明、或為完成提供服務或履行合約義務之必要、或依照相關法令規定或有權主管機關之命令或要求，否則Dr. Call不會將足以識別使用者身分的個人資料提供給第三人（包括境內及境外）、或移作蒐集目的以外之使用。在合約有效期間內，以及法令所定應保存之期間內，Dr. Call會持續保管、處理及利用相關資料。<br><br>"+
				"<h5>■ 資料安全</h5>Dr. Call將以合於產業標準之合理技術及程序，維護個人資料之安全。<br><br>"+
				"<h5>■ 資料當事人之權利</h5>資料當事人可以請求查詢、閱覽本人的個人資料或要求給予複本，但Dr. Call得酌收必要成本和費用。若您的個人資料有變更、或發現您的個人資料不正確，可以向Dr. Call要求修改或更正。當蒐集個人資料之目的消失或期限屆滿時，您可要求刪除、停止處理或利用個人資料。但因Dr. Call執行職務或業務所必須者，不在此限。<br><br>"+
				"<h5>■ Cookie</h5>為了便利使用者，Dr. Call網站可能使用cookie技術，以便於提供更適合使用者個人需要的服務；cookie是網站伺服器用來和使用者瀏覽器進行溝通的一種技術，它可能在使用者的電腦中儲存某些資訊，Dr. Call網站也會讀取儲存在使用者電腦中的cookie資料。使用者可以經由瀏覽器的設定，取消、或限制此項功能，但可能因此無法使用部份網站功能。若您想知道如何取消、或限制此項功能，請與Dr. Call服務中心聯絡。<br><br>"+
				"<h5>■ 影響</h5>依照各該服務之性質，為使相關服務得以順利提供、或使相關交易得以順利完成或履行完畢，若您不願意提供各該服務或交易所要求的相關個人資料予Dr. Call，並同意Dr. Call就該等個人資料依法令規定、以及本隱私權聲明及其相關告知內容為相關之個人資料蒐集、處理、利用及國際傳輸，Dr. Call將尊重您的決定，但依照各該服務之性質或條件，您可能因此無法使用該等服務或完成相關交易，Dr. Call並保留是否同意提供該等相關服務或完成相關交易之權利。<br><br>"+
				"<h5>■ 修訂之權利</h5>Dr. Call有權隨時修訂本隱私權聲明及相關告知事項，並得於修訂後公佈在Dr. Call網站上之適當位置，不另行個別通知，您可以隨時在Dr. Call網站上詳閱修訂後的隱私權聲明及相關告知事項。</div>");

		});

		$("#termsofService").click(function() {

			bootbox.alert("<h4 class='content-title'>DrCall 服務條款</h4>");

		});
		
		
		// FOR DATE
            $('.form_datetime').datetimepicker({
             //  language:  'fr',
                weekStart: 1,
                todayBtn: 1,
                autoclose: 1,
                todayHighlight: 1,
                startView: 2,
                forceParse: 0,
                showMeridian: 1
            });
            $('.form_date').datetimepicker({
                language: 'zh-TW',
                weekStart: 1,
                todayBtn: 1,
                autoclose: 1,
                todayHighlight: 1,
                startView: 2,
                minView: 2,
                forceParse: 0
            });
            $('.form_time').datetimepicker({
                language: 'fr',
                weekStart: 1,
                todayBtn: 1,
                autoclose: 1,
                todayHighlight: 1,
                startView: 1,
                minView: 0,
                maxView: 1,
                forceParse: 0
            });

	});
</script>



<sitemesh:write property='head' />
</head>

<body>
	<div id="container">
		
		<!--  -->
	
		<div class="navbar navbar-inverse navbar-default navbar-static-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="${pageContext.request.contextPath}/">Dr. Call</a>
        </div>
        <div class="collapse navbar-collapse">
        <ul class="nav navbar-nav">
        
        	<li class="dropdown">
        		<a href="#" class="dropdown-toggle" data-toggle="dropdown">預約掛號<span class="caret"></span></a>
        		<ul class="dropdown-menu">
            	
            	<li id="menuRecord"><a href="${pageContext.request.contextPath}/app/appoint/record">預約掛號紀錄</a></li>
				<li id="menuBooking"><a href="${pageContext.request.contextPath}/app/appoint/booking">線上預約掛號</a></li>
            	
          		</ul>
        		
        	</li>
        	
			<li id="menuMember"><a href="${pageContext.request.contextPath}/app/member/management">會員專區</a></li>
			<li id="menuBuy"><a href="${pageContext.request.contextPath}/app/payment/choosePayment">點數購買/記錄</a></li>
			<li id="menuBuy"><a href="${pageContext.request.contextPath}/app/charity/activies">公益專區</a></li>
			<li id="menu"><a href="#" id="cooperation">院所合作提案</a></li>
		</ul>

		  <ul class="nav navbar-nav navbar-right hidden-xs">
		    							<sec:authorize ifNotGranted="ROLE_MEMBER">
											<li><a
												href="${pageContext.request.contextPath}/login.jsp">登入 Dr Call</a>
											</li>
										</sec:authorize>

										<sec:authorize ifAllGranted="ROLE_MEMBER">
											<li><a href="#"><span class="glyphicon glyphicon-heart"> 點數餘額: ${balance}</span></a>
											</li>
											<li><a href="${pageContext.request.contextPath}/j_spring_security_logout">登出  Dr Call</a>
											</li>
										</sec:authorize>
		    
		    
		  </ul>
        </div>
      </div>
    </div>

		<!-- body -->
		<div id="body">
			<sitemesh:write property='body' />
		</div>


		<div id="copyright">
			<div class="container">
				<div class="row">
					<div class="col-md-6">
						<p class="copyright-space">
							2014 © Drcall. ALL Rights Reserved. 
							<a href="#" id="privacyPolicy">隱私權聲明 Privacy Policy</a>| 
							<a href="#" id="termsofService">服務條款 Terms of Service</a>
						</p>
					</div>
					<div class="col-md-6">
						<a href="index.html"> <img id="logo-footer"
							src="${pageContext.request.contextPath}/images/footer_logo.png" class="pull-right" alt="">
						</a>
					</div>
				</div>
				<!--/row-->
			</div>
		</div>
	</div>

</body>
</html>