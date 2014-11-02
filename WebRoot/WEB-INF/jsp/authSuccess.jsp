<%@ page contentType="text/html; charset=utf-8" %>
<%
/**
* 載入Somp的Class,此範例WebService Client的實作部份是以Axis2實作,各CP可自行實作這一段
* com.sonet.somp.SonetMainSoapServiceStub是透過Axis2提供的產生器產生的代理
* wsdl2java.bat -uri https://mpapi.so-net.net.tw/xml/microPaymentServiceProd.wsdl -o /to/your/path -p yourPackageName
*/
%>
<%@ page import="com.sonet.somp.*,com.sonet.somp.SonetMainSoapServiceStub.ICPConfirmOrderResult,java.util.*"%>
<%

/**
* 設定API的運作方法為，可以是post或是soap模式
*/
int method = SoRequest.METH_POST;
//int method = SoRequest.METH_SOAP;
String OldenCoding = "ISO8859-1";
/**
* 設定內容為UTF-8的編碼
*/
String NewenCoding = "utf-8";

request.setCharacterEncoding(NewenCoding);

/**
* So-net Micropayment的伺服器網址
* 正式環境
* frontHost = "https://mpay.so-net.net.tw/";
* apiHost = "https://mpapi.so-net.net.tw/";	
*/

//測試環境
String frontHost = "http://mpay-dev.so-net.net.tw/";
String apiHost = "http://mpapi-dev.so-net.net.tw/";
String resultCode = request.getParameter("resultCode");
String resultMesg = new String(request.getParameter("resultMesg").getBytes(OldenCoding), NewenCoding);
String icpId = request.getParameter("icpId");
String icpOrderId = request.getParameter("icpOrderId");
String sonetOrderNo = request.getParameter("sonetOrderNo");
String errMsg = "";

String price = (String) request.getAttribute("price");
String point = (String) request.getAttribute("point");
String orderDate = (String) request.getAttribute("orderDate");


/**
* 判斷回傳結果值,進行後續處理
*/
if(resultCode.equals("ok")){
	Properties p = new Properties();
	p.setProperty("icpId", icpId);
	p.setProperty("icpOrderId", icpOrderId);
	p.setProperty("sonetOrderNo", sonetOrderNo);

	p.setProperty("doAction","confirmOrder");
		
	/**
	* So-net Micropayment的Api Post網址
	* apiHost . "microPaymentPost.php"
	* wsdl 模式
	* So-net Micropayment的Api Soap網址
	* //正式機
	* apiHost . "xml/microPaymentServiceProd.wsdl"
	* //測試機
	* apiHost . "xml/microPaymentServiceProdDev.wsdl"
	* nonWsdl 模式
	* apiHost + "microPaymentService.php"
	*
	*/
	String url = apiHost + "microPaymentPost.php";
		
		
		
	SoRequest sr = new SoRequest();
	switch(method){
	case SoRequest.METH_SOAP :
		url = apiHost + "microPaymentService.php";
		ICPConfirmOrderResult r = (ICPConfirmOrderResult)sr.doRequest(method,p,url, NewenCoding);

		resultCode = r.getResultCode();
		resultMesg = r.getResultMesg();
		//out.println("SOAP: "+resultCode + resultMesg);
		break;
	case SoRequest.METH_POST :
		Properties result = (Properties)sr.doRequest(method,p,url, NewenCoding);
		
		resultCode = result.getProperty("resultCode");
		
		String resultStr = new String(sr.getResultStr().getBytes(OldenCoding), NewenCoding);
		//out.println("POST: "+resultStr);
		break;
	default :		
		resultCode = "99999";
		resultMesg = "方法錯誤!!";
		//out.println(resultCode + resultMesg);
		break;
	}
	
	// check success if resultCode == 0000
	//errMsg = resultMesg;
	//Properties r = hr.getResult(result);
	

}else{
	errMsg = resultMesg;
}

%>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootbox-4.2.0/bootbox.min.js"></script>
<SCRIPT Language='JavaScript'>
	var resultCode = "<%=resultCode%>";
	var price = "<%=price%>";
	var point = "<%=point%>";
	var date = "<%=orderDate%>";
	

// 	bootbox.alert("resultCode:"+resultCode+" price:"+price+" point:"+point+" date:"+date, function() {
//   		window.close();
// 	});
	
	if(resultCode == '00000'){
	
		bootbox.dialog({
		  message: "<div class='content-text'><b>消費金額:"+price+"<br>儲值點數:"+point+"<br>時間:"+date+"</b></div>",
		  title: "<h4 class='content-title'>儲值成功</h4>",
		  buttons: {
		    success: {
		      label: "OK",
		      className: "btn-success",
		      callback: function() {
		        window.close();
		      }
		    }
		  }
		});
		
	} else {
		bootbox.alert("<h4 class='content-title'>Oops!! 儲值失敗!!</h4><br><div class='content-text'>請重新儲值或留言來信告知，DR.CALL將儘快為您服務，感謝您。</div>", function() {
  			window.close();
		});
	}
	
	
	
	
	
	
	
	
	
	//bootbox.alert("success...");

</SCRIPT>