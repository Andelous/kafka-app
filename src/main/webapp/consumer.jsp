<%@ page import="com.angeld.kafkaapp.KafkaObjects"%>
<%@ page import="com.angeld.kafkaapp.ConsumerWrapper"%>



<%@ include file="/header.jsp"%>


<div class="container">
	<div class="row justify-content-center">
		<div class="col-sm-10 col-md-9 col-lg-8 col-xl-7">

			<h4 class="mt-3">
				Consumidor <strong><%=((ConsumerWrapper) session.getAttribute(KafkaObjects.CONSUMER)).getName()%></strong>
			</h4>

			<hr>
			
			<div id="kafka-events"></div>

			<form action="main" method="get">
				<button type="submit" class="btn btn-danger">Retirar
					consumidor</button>
			</form>

		</div>
	</div>
</div>


<script>
	async function retrieveEvents() {
		var response = await fetch("/kafka/events", {
			method : "GET"
		});

		var json = await response.json();
		console.log(json);
		
		var contents = "";
		
		for (val in json) {
			contents += "<pre><code class='language-json'>" + json[val] + "</code></pre>";
		}

		var myDiv = document.getElementById("kafka-events");
		
		myDiv.innerHTML = contents;
		
		hljs.highlightAll();

		setTimeout(retrieveEvents, 2000);
	}

	retrieveEvents();
</script>

<%@ include file="/footer.jsp"%>