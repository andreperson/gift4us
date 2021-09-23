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
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<title>${title}</title>


<!-- Favicon -->
  <link href="../gitf4us/resources/assets/img/brand/favicon.png" rel="icon" type="image/png">
  <!-- Fonts -->
  <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700" rel="stylesheet">
  <!-- Icons -->
  <link href="../gift4us/resources/assets/vendor/nucleo/css/nucleo.css" rel="stylesheet">
  <link href="../gift4us/resources/assets/vendor/fortawesome/fontawesome-free/css/all.min.css" rel="stylesheet">
  <!-- Argon CSS -->
  <link type="text/css" href="../gift4us/resources/assets/css/argon.css?v=1.0.0" rel="stylesheet">


</head>
<body class="bg-default">
	<c:if test="${fluido}">
		<c:set value="container-fluid" var="tipoDeContainer" />
	</c:if>
	<c:if test="${not fluido}">
		<c:set value="container" var="tipoDeContainer" />
	</c:if>

	

  <div class="main-content">

			

			<jsp:doBody />
		</div>
	
	
</body>
</html>