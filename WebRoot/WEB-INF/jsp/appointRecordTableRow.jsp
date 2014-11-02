<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String hospital_list = (String) request.getAttribute("hospital_list");
%>

												<c:forEach var="appoint" items="${appoints}" varStatus="status">
													<tr>
														<td>${status.index}</td>
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