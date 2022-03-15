<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags/tag-login" prefix="my"%>
<%@ page import="br.com.gift4us.urls.ListaDeURLs"%>


<my:template fluido="false"
	title="${mensagens.get('NomeDoProjeto').valor}" cssFiles="${cssFiles}"
	jsFiles="${jsFiles}">
	
    <!-- Navbar -->
    
    <nav class="navbar navbar-top navbar-horizontal navbar-expand-md navbar-dark">
      <div class="container px-4">
        <a class="navbar-brand" href="login">
          <img src="${urlRecursos}resources-admin/assets/img/brand/white.png" />
        </a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar-collapse-main" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbar-collapse-main">
          <!-- Collapse header -->
          <div class="navbar-collapse-header d-md-none">
            <div class="row">
              <div class="col-6 collapse-brand">
                <a href="../index.html">
                  <img src="/gift4us/resources/assets/img/brand/blue.png">
                </a>
              </div>
              <div class="col-6 collapse-close">
                <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbar-collapse-main" aria-controls="sidenav-main" aria-expanded="false" aria-label="Toggle sidenav">
                  <span></span>
                  <span></span>
                </button>
              </div>
            </div>
          </div>
          <!-- Navbar items -->
          <ul class="navbar-nav ml-auto">
            <li class="nav-item">
              <a class="nav-link nav-link-icon" href="faleconosco">
                <i class="ni ni-single-02"></i>
                <span class="nav-link-inner--text">Fale Conosco</span>
              </a>
            </li>
          </ul>
        </div>
      </div>
    </nav>
    <!-- Header -->
    <div class="header bg-gradient-primary py-7 py-lg-8">
      <div class="container">
        <div class="header-body text-center mb-2">
          <div class="row justify-content-center">
            <div class="col-lg-5 col-md-6">
              <h1 class="text-white">Alterando a Senha!</h1>
              <h5 class="text-white">${msg }</h5>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- Page content -->
    <div class="container mt--8 pb-5">
      <div class="row justify-content-center">
        <div class="col-lg-5 col-md-7">
          <div class="card bg-secondary shadow border-0">
            <div class="card-body px-lg-5 py-lg-5">
              <div class="text-center text-muted mb-4">

              		<i class="fa fa-user-secret" aria-hidden="true"></i> gift4Us
              
	            <c:if test="${not empty msg}">
					<br>
					<span style="color:#F44104;">
						<small><i class="fa fa-exclamation-circle" aria-hidden="true"></i>
							${msg}
						</small>
					</span>
				</c:if>
              </div>
              <c:url var="action" value="<%=ListaDeURLs.ALTERAR_A_SENHA%>" />
        <section class="login" id="login">
             <form:form action="${action}" id="form-login" method="post">
                <div class="form-group mb-3">
                  <div class="input-group input-group-alternative">
                    <div class="input-group-prepend">
                      <span class="input-group-text"><i class="ni ni-email-83"></i></span>
                    </div>
                    <input id="loginouemail" name="loginouemail" class="form-control" placeholder="Login ou Email" type="text" required autofocus >
                  </div>
                </div>
                
                <div class="form-group mb-3">
                  <div class="input-group input-group-alternative">
                    <div class="input-group-prepend">
                      <span class="input-group-text"><i class="ni ni-email-83"></i></span>
                    </div>
                    <input id="codigo" name="codigo" class="form-control" placeholder="Código" type="text" required autofocus >
                  </div>
                </div>
                
                <div class="form-group mb-3">
                  <div class="input-group input-group-alternative">
                    <div class="input-group-prepend">
                      <span class="input-group-text"><i class="ni ni-email-83"></i></span>
                    </div>
                    <input id="novasenha" name="novasenha" class="form-control" placeholder="Nova Senha" type="password" required autofocus >
                  </div>
                </div>
                
                <div class="form-group mb-3">
                  <div class="input-group input-group-alternative">
                    <div class="input-group-prepend">
                      <span class="input-group-text"><i class="ni ni-email-83"></i></span>
                    </div>
                    <input id="confirmanovasenha" name="confirmanovasenha" class="form-control" placeholder="Confirmação da Nova Senha" type="password" required autofocus >
                  </div>
                </div>
                
                <div class="text-center">
                  <button type="submit" class="btn btn-primary my-4">Enviar</button>
                </div>
              </form:form>
        </section>
            </div>
          </div>
          <div class="row mt-3">
            <div class="col-6">
              <a href="login" class="text-light"><small>voltar ao login</small></a>
            </div>
          </div>
        </div>
      </div>
    </div>


  <!-- Argon Scripts -->
  <!-- Core -->
  <script src="${urlRecursos}resources-admin/assets/vendor/jquery/dist/jquery.min.js"></script>
  <script src="${urlRecursos}resources-admin/assets/vendor/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
  <!-- Argon JS -->
  <script src="${urlRecursos}resources-admin/assets/js/argon.js?v=1.0.0"></script>
</body>
	
	
	
	
	
	
</my:template>