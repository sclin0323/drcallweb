<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="com.sonet.somp.*,com.sonet.somp.SonetMainSoapServiceStub.ICPAuthOrderResult, java.util.*, java.net.*,java.io.*"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

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

/**
* So-net Micropayment的伺服器網址
* 正式環境
* frontHost = "https://mpay.so-net.net.tw/";
* apiHost = "https://mpapi.so-net.net.tw/";	
*/

//測試環境
String frontHost = "http://mpay-dev.so-net.net.tw/";
String apiHost = "http://mpapi-dev.so-net.net.tw/";

//您的商店代碼
String icpId = request.getParameter("icpId");

//您的訂單編號
String icpOrderId = (String) request.getAttribute("icpOrderId");

//您的商品代碼,必須先向So-net申請商品上架
String icpProdId = request.getParameter("icpProdId");

//您使用的小額付款方式
String mpId = request.getParameter("mpId");

//您的訂單備註
String memo = (String) request.getAttribute("memo");

//訂單的使用者代碼
String icpUserId = request.getParameter("icpUserId");

//訂單的回傳網址
String returnUrl = request.getParameter("returnUrl");

//信用卡商品名稱
String icpProdDesc = request.getParameter("icpProdDesc");

//信用卡訂單金額
String price = (String) request.getAttribute("price");


// System.out.println("=============================");
// System.out.println("icpId: "+icpId);
// System.out.println("icpOrderId: "+icpOrderId);
// System.out.println("icpProdId: "+icpProdId);
// System.out.println("mpId: "+mpId);
// System.out.println("memo: "+memo);
// System.out.println("price: "+price);
// System.out.println("icpProdDesc: "+icpProdDesc);
// System.out.println("returnUrl: "+returnUrl);
// System.out.println("icpUserId: "+icpUserId);
// System.out.println("icpId: "+icpId);
// System.out.println("=============================");


Properties p = new Properties();
p.setProperty("icpId", icpId);
p.setProperty("icpOrderId", icpOrderId);
p.setProperty("icpProdId", icpProdId);
p.setProperty("mpId", mpId);
p.setProperty("memo", memo);
p.setProperty("price", price);
p.setProperty("icpProdDesc", icpProdDesc);
p.setProperty("returnUrl", returnUrl);
p.setProperty("icpUserId", icpUserId);
p.setProperty("doAction","authOrder");


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
String resultCode = "";
String resultMesg = "";
String authCode = "";
String actionUrl = "";

SoRequest sr = new SoRequest();
switch(method){
	case SoRequest.METH_SOAP :
		url = apiHost + "microPaymentService.php";
		ICPAuthOrderResult rta = (ICPAuthOrderResult)sr.doRequest(method,p,url, NewenCoding);

		//System.out.println("SoRequest.METH_SOAP");
		resultCode = rta.getResultCode();
		resultMesg = rta.getResultMesg();
		authCode = rta.getAuthCode();
		actionUrl = "";
		break;
	case SoRequest.METH_POST :
		Properties r = (Properties)sr.doRequest(method,p,url, NewenCoding);
		String resultStr = new String(sr.getResultStr().getBytes(OldenCoding), NewenCoding);

		//System.out.println("SoRequest.METH_POST");
		resultCode = r.getProperty("resultCode");
		resultMesg = r.getProperty("resultMesg");
		authCode = r.getProperty("authCode");
		actionUrl = "";
		break;
	default :		
		resultCode = "99999";
		resultMesg = "方法錯誤!!";
		authCode = "";
		actionUrl = "";
		break;
}

System.out.println("resultCode:"+resultCode);
if(resultCode.equals("00000")){
	/**
	* So-net Micropayment的付款中心條款頁面，通常不用更改
	*/
	actionUrl = frontHost + "paymentRule.php";
}else{
	actionUrl = "authFail.jsp";
}

String jsString = "new Array()";
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>預約掛號紀錄</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<script type="text/javascript">
	
	var addFormInputElement = function(form,elementName,value,elementType){
		eval('var elem = form.' + elementName + ';');
		if(!elem){
			
			var obj = document.createElement("input");
			obj.value = value;
			obj.name = elementName;
			obj.type = elementType;
			form.appendChild(obj);
		}else{
			eval('form.' + elementName + '.value = "' + value + '";');
		}
	}
	
	var formSubmit = function(form,queryParams){
		for(i=0; i<queryParams.length;i = i + 2){
			var elName = queryParams[i];
			if(queryParams[i+1] == false){
				var elValue = '';
			}else{
				var elValue = queryParams[i+1];
			}
			addFormInputElement(form,elName,elValue,"hidden");
		}
		form.submit();
	}
	
	var postForm = function(formName,queryParams){
		eval("var form = document." + formName + "");
		
		formSubmit(form,queryParams);	
	}

	$(window).load(function() {
		postForm('CallAuth',<%=jsString%>);
	});
	
</script>

</head>

<body>

<table width="620" border="0" bordercolor="EEFCFF">
  <tr>
	<td align="center" valign="middle" height="200" scope="col">
		<img src="${pageContext.request.contextPath}/images/process.gif" />
	</td>
  </tr>
</table>

<form action="<%=actionUrl%>" method="post" enctype="application/x-www-form-urlencoded" name="CallAuth" id="CallAuth">
<input type="hidden" name="icpId" value="<%=icpId%>">
<input type="hidden" name="icpOrderId" value="<%=icpOrderId%>">
<input type="hidden" name="icpProdId" value="<%=icpProdId%>">
<input type="hidden" name="mpId" value="<%=mpId%>">
<input type="hidden" name="memo" value="<%=memo%>">
<input type="hidden" name="icpUserId" value="<%=icpUserId%>">
<input type="hidden" name="authCode" value="<%=authCode%>">
</form>

</body>
</html>