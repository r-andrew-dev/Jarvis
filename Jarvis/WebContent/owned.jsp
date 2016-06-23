<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="styles.css">
<link rel="stylesheet" type="text/css"
	href="https://fonts.googleapis.com/css?family=Open+Sans:400,300,700">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<link href="bootstrap.css" rel="stylesheet">
<link rel="icon" type="image/png" href="images/logo.png">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.3/angular.min.js"></script>
<script
	src="//ajax.googleapis.com/ajax/libs/angularjs/1.5.3/angular-animate.js"></script>
<script
	src="//angular-ui.github.io/bootstrap/ui-bootstrap-tpls-1.3.2.js"></script>
<script src="http://code.highcharts.com/highcharts.js"></script>
<script src="highcharts-ng.js"></script>
<script src="scripts.js" /></script>
<script src="myApp.js" /></script>
<script src="myCtrl.js" /></script>
<script>
	$(document).ready(
			function() {
				$("#header").load("header.html");

				jQuery('.tabs .tab-links a').on(
						'click',
						function(e) {
							var currentAttrValue = jQuery(this).attr('href');

							// Show/Hide Tabs
							jQuery('.tabs ' + currentAttrValue).show()
									.siblings().hide();

							// Change/remove current tab to active
							jQuery(this).parent('li').addClass('active')
									.siblings().removeClass('active');

							e.preventDefault();
						});

			});
</script>
<title>O&O Analysis</title>
</head>
<body>
	<div id="header"></div>
	<div id="blank"></div>
	<span class="pageHeading">O&O ANALYSIS</span>
	<div id="narrowblank"></div>
	<div id="mainDiv" ng-app="ooApp" ng-controller="ooCtrl">
		<div id="spinner" ng-hide="showTable">
			<img class="spinningwheel" src="images/spinningwheel.gif" />
		</div>
		<div ng-show="showTable">
			<span class="pageSubHeading">RTB MTD Data - All Sites</span><br><hr><br>
			<table class="dataTable" style="width: 100%;">
				<tr class="headerRow">
					<th>Site</th>
					<th>Outbound Requests</th>
					<th>Cleared</th>
					<th>Impressions</th>
					<th>Clicks</th>
					<th>Revenue</th>
					<th>eCPM</th>
					<th>CTR</th>
				</tr>
				<tr ng-repeat="site in data | orderBy: 'rev' : true | filter:'RTB'">
					<td><a href="ownedSiteDaily.jsp?type=site&siteId={{site.siteId}}">{{ site.siteName }}</a></td>
					<td>{{ site.totalRequests | number }}</td>
					<td>{{ site.totalCleared | number }}</td>
					<td>{{ site.totalImpressions | number }}</td>
					<td>{{ site.totalClicked | number }}</td>
					<td>{{ site.rev | currency }}</td>
					<td>{{ site.rev/site.totalImpressions*1000 | currency }}</td>
					<td>{{ site.totalClicked/site.totalImpressions | percentage:2 }}</td>
				</tr>
			</table>
			<br>
			<br>
			<span class="pageSubHeading">Mediation MTD Data - All Sites</span><br><hr><br>
			<table class="dataTable" style="width: 100%;">
				<tr class="headerRow">
					<th>Site</th>
					<th>Outbound Requests</th>
					<th>Cleared</th>
					<th>Impressions</th>
					<th>Clicks</th>
					<th>Revenue</th>
					<th>eCPM</th>
					<th>CTR</th>
				</tr>
				<tr ng-repeat="site in data | orderBy: 'rev' : true | filter:'Mediation'">
					<td><a href="ownedSiteDaily.jsp?type=site&siteId={{site.siteId}}">{{ site.siteName }}</a></td>
					<td>{{ site.totalRequests | number }}</td>
					<td>{{ site.totalCleared | number }}</td>
					<td>{{ site.totalImpressions | number }}</td>
					<td>{{ site.totalClicked | number }}</td>
					<td>{{ site.rev | currency }}</td>
					<td>{{ site.rev/site.totalImpressions*1000 | currency }}</td>
					<td>{{ site.totalClicked/site.totalImpressions | percentage:2 }}</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>