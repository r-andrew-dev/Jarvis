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
<title>Ad Quality Home</title>
</head>
<body>
	<div id="header"></div>
	<div id="blank"></div>
	<span class="pageHeading"></span>
	<div id="narrowblank"></div>
	<div id="wrapper">
		<div id="columns">
			<div id="viewabilityreport" class="pinColumn" onclick="openWindow(this.id);">
				<p><h3>Viewability</h3></p>
			</div>
			<div id="adqualityreport" class="pinColumn" onclick="openWindow(this.id);">
				<p><h3>Ad Verification</h3></p>
			</div>
		</div>
	</div>
</body>
</html>