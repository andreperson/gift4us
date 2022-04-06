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

<!-- Favicon -->
<link href="${urlRecursos}resources-site/image/favicon.png" rel="icon"
	type="image/png">

<!-- CSS Part Start-->
<link rel="stylesheet" type="text/css"
	href="${urlRecursos}resources-site/css/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="${urlRecursos}resources-site/css/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css"
	href="${urlRecursos}resources-site/css/stylesheet.css" />
<link rel="stylesheet" type="text/css"
	href="${urlRecursos}resources-site/css/owl.carousel.css" />
<link rel="stylesheet" type="text/css"
	href="${urlRecursos}resources-site/css/owl.transitions.css" />
<link rel="stylesheet" type="text/css"
	href="${urlRecursos}resources-site/css/responsive.css" />
<link rel="stylesheet" type="text/css"
	href="${urlRecursos}resources-site/css/stylesheet-skin2.css" />
<link rel='stylesheet'
	href='http://fonts.googleapis.com/css?family=Droid+Sans'
	type='text/css'>
<!-- CSS Part End-->
</head>
<body>
	<div class="wrapper-wide">
		<c:import url="../header.jsp" />
		<jsp:doBody />
		<c:import url="../footer.jsp" />
	</div>
	<script type="text/javascript">
		var urlPadrao = '<c:url value="/"/>';
	</script>
	<!-- JS Part Start-->
	<script type="text/javascript"
		src="${urlRecursos}resources-site/js/jquery-2.1.1.min.js"></script>
		
	<script type="text/javascript"
		src="${urlRecursos}resources-site/js/js-cookie.js"></script>
		
	<script type="text/javascript"
		src="${urlRecursos}resources-site/js/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="${urlRecursos}resources-site/js/jquery.easing-1.3.min.js"></script>
	<script type="text/javascript"
		src="${urlRecursos}resources-site/js/jquery.dcjqaccordion.min.js"></script>
	<script type="text/javascript"
		src="${urlRecursos}resources-site/js/owl.carousel.min.js"></script>
	<script type="text/javascript" src="${urlRecursos}resources-site/js/custom.js"></script>
	<script type="text/javascript" src="${urlRecursos}resources-site/js/Index.js"></script>
	
	<script type="text/javascript" src="${urlRecursos}resources-site/js/checkout.js"></script>
	<!-- JS Part End-->

</body>
</html>