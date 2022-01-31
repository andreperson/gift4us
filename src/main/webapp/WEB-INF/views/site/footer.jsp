<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ page import="br.com.gift4us.urls.ListaDeURLs"%>
<c:url value="<%=ListaDeURLs.HOME%>" var="url" />
<!--Footer Start-->
<footer id="footer">
	<div class="fpart-first">
		<div class="container">
			<div class="row">
				<div class="contact col-lg-3 col-md-3 col-sm-12 col-xs-12">
					<h5>Gift4us</h5>
					<p>Trabalhamos para oferecer valor aos clientes que anunciam
						conosco. Nosso sistema esta em constante evolução para oferecer
						cada vez mais aos nossos clientes e aos clientes de nossos
						clientes.</p>
					<img alt="" class="img-responsive" src="image/logo-small.png">
				</div>
				<div class="column col-lg-2 col-md-2 col-sm-3 col-xs-12">
					<h5>INFORMAÇÕES</h5>
					<ul>
						<li><a href="quemsomos.html">Quem Somos</a></li>
						<li><a href="anuncie.html">Anuncie Conosco</a></li>
						<li><a href="processodecompra.html">Processo de Compra</a></li>
					</ul>
				</div>
				<div class="column col-lg-2 col-md-2 col-sm-3 col-xs-12">
					<h5>Fale Conosco</h5>
					<ul>
						<li><a href="contato.html">Formulário de Contato</a></li>
						<li><a href="##">Whats App</a></li>
						<li><a href="mailto:atendimento@gift4us.com.br">atendimento@gift4us.com.br</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="fpart-second">
		<div class="container">
			<div id="powered" class="clearfix">
				<div class="powered_text pull-left flip">
					<p>
						Gift4us © 2021 | São Paulo | Brasil
					</p>
				</div>
				<div class="social pull-right flip">
					<a href="#" target="_blank"> <img data-toggle="tooltip"
						src="image/socialicons/facebook.png" alt="Facebook"
						title="Facebook"></a> <a href="#" target="_blank"> <img
						data-toggle="tooltip" src="image/socialicons/twitter.png"
						alt="Instagram" title="Instagram">
					</a> 
				</div>
			</div>
			<div class="bottom-row">
				<div class="custom-text text-center">
					<p>
						Gift4us a nossa loja de brindes e variedades!
					</p>
				</div>
				
			</div>
		</div>
	</div>
	<div id="back-top">
		<a data-toggle="tooltip" title="Back to Top" href="javascript:void(0)"
			class="backtotop"><i class="fa fa-chevron-up"></i></a>
	</div>
</footer>
<!--Footer End-->
