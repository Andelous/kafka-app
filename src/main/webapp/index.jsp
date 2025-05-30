
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
			<div>
				<button type="button" class="btn btn-outline-success btn-sm" data-bs-toggle="modal"
					data-bs-target="#modalProducer">Agregarme +</button>
			</div>
		</div>

		<div class="col-md-4 p-2">
			<h4>Eventos</h4>

			<div id="list-events">
				<div class="card"><div class="card-body">Sin eventos</div></div>
			</div>
		</div>

		<div class="col-md-5 p-2">
			<h4>Consumidores</h4>

			<div id="list-consumers">
				<div class="card"><div class="card-body">Sin consumidores</div></div>
			</div>
			<div>
				<button type="button" class="btn btn-outline-success btn-sm" data-bs-toggle="modal"
					data-bs-target="#modalConsumer">Agregarme +</button>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="modalProducer" tabindex="-1"
	aria-labelledby="modalProducerLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				<h2 class="modal-title fs-5" id="modalProducerLabel">
					Agr&eacute;gate
					<code>:)</code>
				</h2>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<form action="main" method="get">
					<div class="input-group input-group-sm mb-3">
						<span class="input-group-text">Nombre</span> <input type="text"
							class="form-control" name="name" required minlength=3
							maxlength=10>
					</div>
					<input type="hidden" name="type" value="PRODUCER"></input>
					<div class="d-flex justify-content-end">
					<button type="submit" class="btn btn-sm btn-primary m-1">&iexcl;Quiero
						ser productor&excl;</button>
					<button type="button" class="btn btn-secondary btn-sm m-1"
						data-bs-dismiss="modal">Cerrar</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>


<div class="modal fade" id="modalConsumer" tabindex="-1"
	aria-labelledby="modalConsumerLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				<h2 class="modal-title fs-5" id="modalConsumerLabel">
					Agr&eacute;gate
					<code>:)</code>
				</h2>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<form action="main" method="get">
					<div class="input-group input-group-sm mb-3">
						<span class="input-group-text">Nombre</span> <input type="text"
							class="form-control" name="name" required minlength=3
							maxlength=10>
					</div>
					<input type="hidden" name="type" value="CONSUMER"></input>
					<div class="d-flex justify-content-end">
					<button type="submit" class="btn btn-sm btn-primary m-1">&iexcl;Quiero
						ser consumidor&excl;</button>
					<button type="button" class="btn btn-secondary btn-sm m-1"
						data-bs-dismiss="modal">Cerrar</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>



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
		
		let optionsDate = {
				hour12: false,
				hour: 'numeric',
				minute: 'numeric',
				second: 'numeric'
		};
		
		for (let name in producers) {
			let lastProducedMillis = producers[name].lastProduced;
			let lastProducedObject = lastProducedMillis ? new Date(lastProducedMillis) : undefined;
			let lastProducedText = lastProducedObject ? lastProducedObject.toLocaleDateString('es-MX', optionsDate) : 'No ha producido eventos';
			
			let bgColor = '';
			
			if (Date.now() - lastProducedMillis < 5000){ bgColor = 'bg-success-subtle'; }
			
			contents += '<div class="card mb-3"><h6 class="card-header">' + name + ' <span class="badge rounded-pill text-bg-success">' + producers[name].eventCount + ' eventos producidos</span></h6><div class="card-body ' + bgColor +'"><small class="text-secondary">' + lastProducedText + '</small></div></div>';
		}
		
		let divProducers = document.getElementById("list-producers");
		
		if (contents.length > 0)
			divProducers.innerHTML = contents;
		else
			divProducers.innerHTML = '<div class="card mb-3"><div class="card-body">Sin productores</div></div>';
		
		
		// Events 
		
		let events = json.events;
		contents = "";
		
		if (events.length > 10) {
			contents = '<pre><code class="language-json">...</code></pre>' + contents;
		}
		
		let initPos = 0;
		
		if (events.length > 10){
			initPos = events.length - 9;
		}
		
		for (let pos = initPos; pos < events.length; pos++) {
			contents = '<pre><code class="language-json">' + events[pos] + '</code></pre>'  + contents;
		}
		
		let divEvents = document.getElementById("list-events");
		
		if (events.length > 0)
			divEvents.innerHTML = contents;
		else
			divEvents.innerHTML = '<div class="card mb-3"><div class="card-body">Sin eventos</div></div>';


		// Consumers 
		
		let consumers = json.consumers;
		contents = "";
		
		for (let name in consumers) {
			let consumerEvents = consumers[name].events;
			let lastConsumedMillis = consumers[name].lastConsumed;
			let bgColor = '';
			
			if (Date.now() - lastConsumedMillis < 5000){ bgColor = 'bg-info-subtle'; }
			
			contents += '<div class="card mb-3"><h6 class="card-header">' + name + '  <span class="badge rounded-pill text-bg-info"><small>' + consumerEvents.length + ' eventos consumidos</small></span></h6><div class="card-body ' + bgColor + '">';
			
			let internalContents = '';
			
			if (consumerEvents.length > 3) {
				internalContents += '<pre><code class="language-json">...</code></pre>';
			}
			
			let initPos = 0;
			
			if (consumerEvents.length > 3){
				initPos = consumers[name].events.length - 2;
			}
			
			for (let pos = initPos; pos < consumerEvents.length; pos++) {
				internalContents = '<pre><code class="language-json">' + consumerEvents[pos] + '</code></pre>' + internalContents;
			}
			
			if (consumerEvents.length == 0) {
				internalContents = '<small class="text-secondary">No ha consumido eventos</small>';
			}
			
			contents += internalContents + '</div></div>';
		}
		
		let divConsumers = document.getElementById("list-consumers");
		
		if (contents.length > 0)
			divConsumers.innerHTML = contents;
		else
			divConsumers.innerHTML = '<div class="card mb-3"><div class="card-body">Sin consumidores</div></div>';

		hljs.highlightAll();
		
		setTimeout(fetchAllData, 2500);
	}

	fetchAllData();
</script>

<%@ include file="/footer.jsp"%>

