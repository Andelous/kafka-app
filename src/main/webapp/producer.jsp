<%@ page import="com.angeld.kafkaapp.KafkaObjects"%>
<%@ page import="com.angeld.kafkaapp.ProducerWrapper"%>

<%@ include file="/header.jsp"%>

<%
boolean shouldShow = Boolean.parseBoolean(request.getParameter("event"));

if (shouldShow) {
%>

<div class="alert alert-success alert-dismissible fade show mt-3"
	role="alert">
	<strong>&Eacute;xito</strong>. El evento se produjo correctamente.
	<button type="button" class="btn-close" data-bs-dismiss="alert"
		aria-label="Close"></button>
</div>

<%
}
%>

<div class="container">
	<div class="row justify-content-center">
		<div class="col-sm-9 col-md-8 col-lg-6 col-xl-4 ">

			<h4 class="mt-3">
				Productor <strong><%=((ProducerWrapper) session.getAttribute(KafkaObjects.PRODUCER)).getName()%></strong>
			</h4>

			<hr>

			<form action="kafka/produce" method="get" class="mb-3">
				<div class="input-group mb-3">
					<span class="input-group-text" id="temperature">Temperatura
						(celsius)</span> <input type="number" min="-273" class="form-control"
						aria-label="Username" name="temperature" required
						id="input-temperatura" aria-describedby="temperature">
				</div>

				<button type="submit" class="btn btn-success">&iexcl;Producir
					evento&excl;</button>
			</form>

			<form action="main" method="get">
				<button type="submit" class="btn btn-danger">Retirar
					productor</button>
			</form>

		</div>
	</div>
</div>



<script>
	document.getElementById("input-temperatura").focus();
</script>


<%@ include file="/footer.jsp"%>