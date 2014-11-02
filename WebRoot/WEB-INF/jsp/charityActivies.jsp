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
<title>Bootstrap 3, from LayoutIt!</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!-- CSS -->
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

<!-- JAVASCRIPT -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootbox-4.2.0/bootbox.min.js"></script>

</head>

<body>
	
	
		<div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div class="tabbable" id="tabs-970930">
					<ul class="nav nav-tabs">
						<li class="active"><a href="#panel-001" data-toggle="tab">公益活動</a>
						</li>
						<li><a href="#panel-002" data-toggle="tab">受贈單位</a>
						</li>
					</ul>
					<div class="tab-content">
						<!-- Charity Main -->
						<div class="tab-pane active" id="panel-001">	
							<div class="row clearfix col-md-12">
								<div class="bs-callout bs-callout-info">
										    <h4 class="content-title">公益專區</h4>
										    <div class="content-text"><b>目前累積公益點數:</b></div>
								</div>

								<!-- The file upload form used as target for the file upload widget -->
				<div class="col-md-4">
					<div class="thumbnail">
						<img alt="300x200" src="http://lorempixel.com/600/200/people" />
						<div class="caption">
							<h3>
								Thumbnail label
							</h3>
							<p>
								Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.
							</p>
							<p>
								<a class="btn btn-primary" href="#">Action</a> <a class="btn" href="#">Action</a>
							</p>
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="thumbnail">
						<img alt="300x200" src="http://lorempixel.com/600/200/city" />
						<div class="caption">
							<h3>
								Thumbnail label
							</h3>
							<p>
								Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.
							</p>
							<p>
								<a class="btn btn-primary" href="#">Action</a> <a class="btn" href="#">Action</a>
							</p>
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="thumbnail">
						<img alt="300x200" src="http://lorempixel.com/600/200/sports" />
						<div class="caption">
							<h3>
								Thumbnail label
							</h3>
							<p>
								Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.
							</p>
							<p>
								<a class="btn btn-primary" href="#">Action</a> <a class="btn" href="#">Action</a>
							</p>
						</div>
					</div>
				</div>



							</div>


						</div>
						
						
						<!-- 受贈單位 -->
						<div class="tab-pane" id="panel-002">
							<div class="col-md-12 row clearfix">
								<div class="bs-callout bs-callout-info">
										    <h4 class="content-title">DrCall 免費提供看診通知服務</h4>
										    <div class="content-text">...</div>
								</div>

								<div class="col-md-4">
									<div class="thumbnail">
										<img alt="300x200" src="http://lorempixel.com/600/200/people" />
										<div class="caption">
											<h3>
												Thumbnail label
											</h3>
											<p>
												Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.
											</p>
											<p>
												<a class="btn btn-primary" href="#">Action</a> <a class="btn" href="#">Action</a>
											</p>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="thumbnail">
										<img alt="300x200" src="http://lorempixel.com/600/200/city" />
										<div class="caption">
											<h3>
												Thumbnail label
											</h3>
											<p>
												Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.
											</p>
											<p>
												<a class="btn btn-primary" href="#">Action</a> <a class="btn" href="#">Action</a>
											</p>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="thumbnail">
										<img alt="300x200" src="http://lorempixel.com/600/200/sports" />
										<div class="caption">
											<h3>
												Thumbnail label
											</h3>
											<p>
												Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.
											</p>
											<p>
												<a class="btn btn-primary" href="#">Action</a> <a class="btn" href="#">Action</a>
											</p>
										</div>
									</div>
								</div>



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
