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
        </div>
      </div>
    </nav>
    <!-- Header -->
    <div class="header bg-gradient-primary py-7 py-lg-8">
      <div class="container">
        <div class="header-body text-center mb-2">
          <div class="row justify-content-center">
            <div class="col-lg-5 col-md-6">
              <h1 class="text-white">Fale Conosco</h1>
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

              		<i class="fa fa-user-circle" aria-hidden="true"></i> Gift4Us
              
	            <c:if test="${not empty msg}">
					<br>
					<span style="color:#F44104;">
						<small><i class="fa fa-exclamation-circle" aria-hidden="true"></i>
							${msg}
						</small>
					</span>
				</c:if>
              </div>
              <c:url var="action" value="<%=ListaDeURLs.FALOUCONOSCO%>" />
        <section class="login" id="login">
             <form:form action="${action}" id="form-login" method="post">
                <div class="form-group mb-3">
                  <div class="input-group input-group-alternative">
                    <div class="input-group-prepend">
                      <span class="input-group-text"><i class="ni ni-email-83"></i></span>
                    </div>
                    <input id="nome" name="nome" class="form-control" placeholder="Nome" type="text" required autofocus >
                  </div>
                </div>
                <div class="form-group mb-3">
                  <div class="input-group input-group-alternative">
                    <div class="input-group-prepend">
                      <span class="input-group-text"><i class="ni ni-email-83"></i></span>
                    </div>
                    <input id="email" name="email" class="form-control" placeholder="E-mail" type="text" required autofocus >
                  </div>
                </div>
                <div class="form-group mb-3">
                  <div class="input-group input-group-alternative">
                    <div class="input-group-prepend">
                      <span class="input-group-text"><i class="ni ni-email-83"></i></span>
                    </div>
                    <input id="celular" name="celular" class="form-control" placeholder="ddd + celular" type="text">
                  </div>
                </div>
                <div class="form-group mb-3">
                  <div class="input-group input-group-alternative">
                    <div class="input-group-prepend">
                      <span class="input-group-text"><i class="ni ni-email-83"></i></span>
                    </div>
                    <textarea id="mensagem" name="mensagem" class="form-control" placeholder="Mensagem" required autofocus ></textarea>
                  </div>
                </div>
                <div>
                    <div style="font-size: 13px; color:gray;">
                    	<i class="ni ni-email-83"></i>&nbsp;&nbsp;Prefiro receber contato por:<br>
                    	<input id="whats" name="contato" placeholder="Whats App" value="whats" type="checkbox">&nbsp; Whats App &nbsp;&nbsp; 
                    	<input id="email" name="contato" placeholder="E-mail" value="email" type="checkbox"> &nbsp; E-mail &nbsp;&nbsp;
                    	<input id="ligacao" name="contato" placeholder="Ligação" value="ligacao" type="checkbox"> &nbsp; Ligação
                	</div>
                </div>
                <p>&nbsp;</p>
                <div>
                    <div style="font-size: 13px; color:gray;">
                    	<i class="ni ni-email-83"></i>&nbsp;&nbsp;Melhor período para contato:<br>
                    	<input id="manha" name="horario" placeholder="manhã" value="manha" type="checkbox">&nbsp; manhã &nbsp;&nbsp; 
                    	<input id="tarde" name="horario" placeholder="tarde" value="tarde" type="checkbox"> &nbsp; tarde &nbsp;&nbsp;
                    	<input id="almoco" name="horario" placeholder="almoço" value="almoco" type="checkbox"> &nbsp; horário do almoço
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