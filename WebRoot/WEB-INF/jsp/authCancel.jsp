<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*"%>
<%

String OldenCoding = "ISO8859-1";
String NewenCoding = "utf-8";

request.setCharacterEncoding(NewenCoding);

String resultCode = request.getParameter("resultCode");
String resultMesg = new String(request.getParameter("resultMesg").getBytes(OldenCoding), NewenCoding);
String icpId = request.getParameter("icpId");
String orderNo = request.getParameter("orderNo");

%>
<SCRIPT Language='JavaScript'>
var errMsg = "<%=resultMesg%>";
if(errMsg != ""){
	alert(errMsg);
}
</SCRIPT>