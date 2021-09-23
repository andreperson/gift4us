<%@ tag pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="title" required="true"%>
<%@ attribute name="fluido" required="true" type="java.lang.Boolean"%>
<%@ attribute name="cssFiles" required="false"%>
<%@ attribute name="jsFiles" required="false"%>
<%@ attribute name="refresh" required="false"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my"%>

<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<title>${title}</title>

<link rel="stylesheet" type="text/css"
	href="<c:url value="../resources-admin/assets/vendor/nucleo/css/nucleo.css"/>" media="screen" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="../resources-admin/assets/vendor/fortawesome/fontawesome-free/css/all.min.css"/>" media="screen" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="../resources-admin/assets/css/argon.css?v=1.0.0"/>" media="screen" />
	
	
<link href="<c:url value="/resources/imagens/sbc_logo.ico"/>" rel="icon" type="image/x-icon" />
<link href="<c:url value="/resources/imagens/sbc_logo.ico"/>" rel="shortcut icon" type="image/x-icon" />

<!-- 
<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/bootstrap.min.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/css/datetimepicker/jquery.datetimepicker.min.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/css/fontawesome/css/all.min.css"/>">
 -->

<c:forEach items="${cssFiles}" var="file">
	<c:url var="url" value="../resources-admin/assets/${file}" />
	<link rel="stylesheet" href="${url}">
</c:forEach>
	
	
	
</head>
<body>
	<c:if test="${fluido}">
		<c:set value="container-fluid" var="tipoDeContainer" />
	</c:if>
	<c:if test="${not fluido}">
		<c:set value="container" var="tipoDeContainer" />
	</c:if>

	<my:erros />
	<my:sucesso />
	<!-- Sidenav -->
	<nav
		class="navbar navbar-vertical fixed-left navbar-expand-md navbar-light bg-white"
		id="sidenav-main" style="zindex: 99999">
		<div class="container-fluid">
			<!-- Toggler -->
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#sidenav-collapse-main" aria-controls="sidenav-main"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>

			<!-- User -->
			<ul class="nav align-items-center d-md-none">
				<li class="nav-item dropdown"><a class="nav-link nav-link-icon"
					href="#" role="button" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false"> <i class="ni ni-bell-55"></i>
				</a>
					<div class="dropdown-menu dropdown-menu-arrow dropdown-menu-right"
						aria-labelledby="navbar-default_dropdown_1">
						<a class="dropdown-item" href="#">Action</a> <a
							class="dropdown-item" href="#">Another action</a>
						<div class="dropdown-divider"></div>
						<a class="dropdown-item" href="#">Something else here</a>
					</div></li>
				<li class="nav-item dropdown"><a class="nav-link" href="#"
					role="button" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false">
						<div class="media align-items-center">
							<span class="avatar avatar-sm rounded-circle"> <img
								alt="Image placeholder"
								src="../resources-admin/assets/img/theme/team-1-800x800.jpg">
							</span>
						</div>
				</a>
					<div class="dropdown-menu dropdown-menu-arrow dropdown-menu-right">
						<div class=" dropdown-header noti-title">
							<h6 class="text-overflow m-0">Welcome!</h6>
						</div>
						<a href="../resources-admin/examples/profile.html"
							class="dropdown-item"> <i class="ni ni-single-02"></i> <span>My
								profile</span>
						</a> <a href="../resources-admin/examples/profile.html"
							class="dropdown-item"> <i class="ni ni-settings-gear-65"></i>
							<span>Settings</span>
						</a> <a href="../resources-admin/examples/profile.html"
							class="dropdown-item"> <i class="ni ni-calendar-grid-58"></i>
							<span>Activity</span>
						</a> <a href="../resources-admin/examples/profile.html"
							class="dropdown-item"> <i class="ni ni-support-16"></i> <span>Support</span>
						</a>
						<div class="dropdown-divider"></div>
						<a href="#!" class="dropdown-item"> <i class="ni ni-user-run"></i>
							<span>Logout</span>
						</a>
					</div></li>
			</ul>
			<!-- Collapse -->
			<div class="collapse navbar-collapse" id="sidenav-collapse-main">
				<!-- Collapse header -->
				<div class="navbar-collapse-header d-md-none">
					<div class="row">
						<div class="col-6 collapse-brand">
							<a href="../resources-admin/index.html"> <img
								src="../resources-admin/assets/img/brand/blue.png">
							</a>
						</div>
						<div class="col-6 collapse-close">
							<button type="button" class="navbar-toggler"
								data-toggle="collapse" data-target="#sidenav-collapse-main"
								aria-controls="sidenav-main" aria-expanded="false"
								aria-label="Toggle sidenav">
								<span></span> <span></span>
							</button>
						</div>
					</div>
				</div>
				<!-- Form -->
				<form class="mt-4 mb-3 d-md-none">
					<div class="input-group input-group-rounded input-group-merge">
						<input type="search"
							class="form-control form-control-rounded form-control-prepended"
							placeholder="Search" aria-label="Search">
						<div class="input-group-prepend">
							<div class="input-group-text">
								<span class="fa fa-search"></span>
							</div>
						</div>
					</div>
				</form>
				<c:import url="../menu.jsp" />
				<hr class="my-3">
				<!-- Heading -->
				<h6 class="navbar-heading text-muted">Documentation</h6>
				<!-- Navigation -->
				<ul class="navbar-nav mb-md-3">
					<li class="nav-item"><a class="nav-link"
						href="https://demos.creative-tim.com/argon-dashboard/docs/getting-started/overview.html">
							<i class="ni ni-spaceship"></i> Getting started
					</a></li>
					<li class="nav-item"><a class="nav-link"
						href="https://demos.creative-tim.com/argon-dashboard/docs/foundation/colors.html">
							<i class="ni ni-palette"></i> Foundation
					</a></li>
					<li class="nav-item"><a class="nav-link"
						href="https://demos.creative-tim.com/argon-dashboard/docs/components/alerts.html">
							<i class="ni ni-ui-04"></i> Components
					</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="main-content">
		<jsp:doBody />
	</div>
	<!-- Default scripts -->
	<script
		src="<c:url value="../resources-admin/assets/vendor/jquery/dist/jquery.min.js"/>"></script>
	<script
		src="<c:url value="../resources-admin/assets/js/bootstrap/bootstrap.bundle.min.js"/>"></script>
	<script
		src="<c:url value="../resources-admin/assets/js/jquery/jquery.datetimepicker.full.min.js"/>"></script>
	<script
		src="<c:url value="../resources-admin/js/msbc-validator-1.8.0.min.js"/>"></script>
	<script 
		src="<c:url value="../resources-admin/js/funcoes.js"/>"></script>	


	<!-- Argon Scripts--> 
	<script
		src="<c:url value="../resources-admin/assets/vendor/chart.js/dist/Chart.min.js"/>"></script>
	<script
		src="<c:url value="../resources-admin/assets/vendor/chart.js/dist/Chart.extension.js"/>"></script>
	<script 
		src="<c:url value="../resources-admin/assets/js/argon.js?v=1.0.0"/>"></script>


	<c:forEach items="${jsFiles}" var="file">
		<c:url var="url" value="../resources-admin/${file}" />
		<script src="${url}"></script>
	</c:forEach>



</body>

</html>