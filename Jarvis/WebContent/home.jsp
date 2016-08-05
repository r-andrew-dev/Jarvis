<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="styles.css">
<link rel="stylesheet" type="text/css"
	href="https://fonts.googleapis.com/css?family=Open+Sans:400,300,700">
<link rel="icon" type="image/png" href="images/logo.png">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="scripts.js" /></script>
<script>
	$(document).ready(function() {
		$("#header").load("header.html");
	});
</script>
<title>Home</title>
</head>
<body>
	<div id="header"></div>
	<div id="blank"></div>
	<span class="pageHeading"></span>
	<div id="narrowblank"></div>
	<div id="wrapper">
		<div id="columns">
			<div id="perf" class="pinColumn" onclick="openWindow(this.id);">
				<p><h3>Performance Demand</h3></p>
			</div>
			<div id="brand" class="pinColumn" onclick="openWindow(this.id);">
				<p><h3>Brand Demand</h3></p>
			</div>
			<div id="supply" class="pinColumn" onclick="openWindow(this.id);">
				<p><h3>Supply Analysis</h3></p>		
			</div>
			<div id="exchange" class="pinColumn" onclick="openWindow(this.id);">
				<p><h3>Exchange Analysis</h3></p>	
			</div>
			<div id="auctions" class="pinColumn" onclick="openWindow(this.id);">
				<p><h3>Nexage Multiple Auctions</h3></p>			
			</div>
			<div id="pubchurn" class="pinColumn" onclick="openWindow(this.id);">
				<p><h3>Publisher Churn Analysis</h3></p>			
			</div>
			<div id="o&o" class="pinColumn" onclick="openWindow(this.id);">
				<p><h3>O&O Analysis</h3></p>				
			</div>
			<div id="newTags" class="pinColumn" onclick="openWindow(this.id);">
				<p><h3>New Tags</h3></p>				
			</div>
			<div id="tierOpt" class="pinColumn" onclick="openWindow(this.id);">
				<p><h3>Tier Optimization Report</h3></p>				
			</div>
			<div id="viewability" class="pinColumn" onclick="openWindow(this.id);">
				<p><h3>Ad Quality Reports</h3></p>				
			</div>
			<div id="taco" class="pinColumn" onclick="openWindow(this.id);">
				<p><h3>TacoCorp Reports</h3></p>				
			</div>
			<div id="deals" class="pinColumn" onclick="openWindow(this.id);">
				<p><h3>Deals Dashboard</h3></p>				
			</div>
		</div>
	</div>
</body>
</html>