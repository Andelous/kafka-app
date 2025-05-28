
<%
String typeClient = (String) session.getAttribute("type-client");

outer : if (typeClient != null) {
	if (typeClient.equals("PRODUCER")) {
		response.sendRedirect("/producer.jsp");
	} else if (typeClient.equals("CONSUMER")) {
		response.sendRedirect("/consumer.jsp");
	} else {
		break outer;
	}

	return;
}
%>


<%@ include file="/header.jsp"%>


<div class="mt-3 d-flex justify-content-start align-items-center">
	<img alt="Logo de Kafka" src="favicon.ico" class="img-thumbnail me-3"
		style="width: 50px; height: 50px;">

	<h1>
		Simulador de <strong>Big Data</strong> con <strong
			class="text-secondary">Kafka</strong>
	</h1>
</div>

<hr>

<div class="container">
	<div class="row">
		<div class="col-md-3 p-2">
			<h4>Productores</h4>

			<div id="list-producers">
				<div class="card">
					<div class="card-body">Sin productores</div>
				</div>
			</div>
		</div>

		<div class="col-md-6 p-2">
			<h4>Eventos</h4>

			<div id="list-events">
				<div class="card">
					<div class="card-body">Sin eventos</div>
				</div>
			</div>
		</div>

		<div class="col-md-3 p-2">
			<h4>Consumidores</h4>

			<div id="list-consumers">
				<div class="card">
					<div class="card-body">Sin consumidores</div>
				</div>
			</div>
		</div>
	</div>
</div>

<hr>

<div class="card">
	<div class="card-body">
		<h6 class="mb-2">
			Agr&eacute;gate
			<code>:)</code>
		</h6>

		<form action="main" method="get">
			<div class="input-group input-group-sm mb-3">
				<span class="input-group-text">Nombre</span> <input type="text"
					class="form-control" name="name" required minlength=3 maxlength=10>
			</div>
			<input type="hidden" name="type" value="PRODUCER"></input>
			<button type="submit" class="btn btn-sm btn-primary">&iexcl;Quiero
				ser productor&excl;</button>
		</form>
	</div>
</div>

<div class="card">
	<div class="card-body">
		<h6>
			Agr&eacute;gate
			<code>:)</code>
		</h6>


		<form action="main" method="get">
			<div class="mb-3">
				<label for="name-consumer" class="form-label">Nombre</label> <input
					type="text" id="name-consumer" name="name" required minlength=3
					maxlength=10 class="form-control form-control-sm"></input>
			</div>
			<input type="hidden" name="type" value="CONSUMER"></input>
			<button type="submit" class="btn btn-primary">&iexcl;Quiero
				ser consumidor&excl;</button>
		</form>
	</div>
</div>

<div>abcdefg</div>

<script>
	async function fetchAllData() {
		let response = await
		fetch("/kafka/all-data", {
			method : "GET"
		});

		let json = await response.json();
		console.log(json);

		// Producers
		
		let producers = json.producers;
		let contents = "";
		
		for (let name in producers) {
			contents += '<div class="card"><div class="card-body">' + name + '</div></div>';
		}
		
		let divProducers = document.getElementById("list-producers");
		divProducers.innerHTML = contents;
		
		
		// Events 
		
		let events = json.events;
		contents = "";
		
		for (let pos in events) {
			contents += '<div class="card"><div class="card-body">' + events[pos] + '</div></div>';
		}
		
		let divEvents = document.getElementById("list-events");
		divEvents.innerHTML = contents;


		// Consumers 
		
		let consumers = json.consumers;
		contents = "";
		
		for (let name in consumers) {
			contents += '<div class="card"><div class="card-body">' + name + '</div></div>';
		}
		
		let divConsumers = document.getElementById("list-consumers");
		divConsumers.innerHTML = contents;

		setTimeout(fetchAllData, 2500);
	}

	fetchAllData();
</script>

<%@ include file="/footer.jsp"%>

