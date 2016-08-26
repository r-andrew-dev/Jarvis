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
<script
	src="https://rawgithub.com/eligrey/FileSaver.js/master/FileSaver.js"
	type="text/javascript"></script>
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
<title>Publisher Deals</title>
</head>
<body>
	<div id="header"></div>
	<div id="blank"></div>
	<span class="pageHeading">MOBILITYWARE</span>
	<div id="narrowblank"></div>
	<div id="mainDiv" ng-app="mobDealApp"
		ng-controller="mobDealCtrl">
		<div id="spinner" ng-hide="showRev">
			<img class="spinningwheel" src="images/spinningwheel.gif" />
		</div>
		<div ng-show="showRev">
		Revenue<br><hr><br>
		<table class="dataTable">
			<tr class="headerRow"><th>Nexage Gross Revenue</th><th>Nexage Pub Revenue</th><th>Mydas Gross Revenue</th>
			<th>Mydas Pub Revenue</th><th>Total Gross Revenue</th><th>Total Pub Revenue</th><th>Total Pub Projected Revenue</th>
			<th>Revenue Goal</th><th>Revenue Goal Status</th></tr>
			<tr>
			<td>{{ revData[0] | currency }}</td>
			<td>{{ revData[1] | currency }}</td>
			<td>{{ revData[3] | currency }}</td>
			<td>{{ revData[4] | currency }}</td>
			<td>{{ 1*revData[0]+1*revData[3] | currency }}</td>		
			<td>{{ 1*revData[1]+1*revData[4] | currency }}</td>
			<td>{{ 1*revData[2]+1*revData[5] | currency }}</td>
			<td>{{ revData[6] | currency }}</td>
			<td><span ng-if="1*revData[2]+1*revData[5] >= 1*revData[6]" style="color:green;">&#10004;</span><span ng-if="1*revData[2]+1*revData[5] < 1*revData[6]" style="color:red;">&#10060;</span></td>
			</tr>
		</table>
		</div>
		<br><br>
		<div id="spinner" ng-hide="showReqs">
			<img class="spinningwheel" src="images/spinningwheel.gif" />
		</div>
		<div ng-show="showReqs">
		Monthly Requests<br><hr><br>
		<table class="dataTable">
			<tr class="headerRow"><th>Country</th><th>FSI Requests Nexage</th><th>FSI Requests Mydas</th>
			<th>Total FSI Requests</th><th>FSI Requests Goal</th><th>FSI Goal Status</th>
			<th>Video Requests Nexage</th><th>Video Requests Mydas</th><th>Total Video Requests</th><th>Video Requests Goal</th>	
			<th>Video Goal Status</th></tr>
			<tr ng-repeat="country in reqsData">
			<td>{{ country.code }}</td>
			<td>{{ country.values[0] | number:0 }}</td>
			<td>{{ country.values[5] | number:0 }}</td>
			<td>{{ 1*country.values[0]+1*country.values[5] | number:0 }}</td>
			<td>{{ country.values[2] | number:0 }}</td>
			<td><span ng-if="1*country.values[0]+1*country.values[5] > country.values[2]" style="color:green;">&#10004;</span><span ng-if="1*country.values[0]+1*country.values[5] <= country.values[2]" style="color:red;">&#10060;</span></td>
			<td>{{ country.values[1] | number:0 }}</td>
			<td>{{ country.values[4] | number:0 }}</td>
			<td>{{ 1*country.values[1]+1*country.values[4] | number:0 }}</td>
			<td>{{ country.values[3] | number:0 }}</td>	
			<td><span ng-if="1*country.values[1]+1*country.values[4] >= country.values[3]" style="color:green;">&#10004;</span><span ng-if="1*country.values[1]+1*country.values[4] < country.values[3]" style="color:red;">&#10060;</span></td>
			</tr>
		</table>
		</div>
		<br><br>
		<div id="spinner" ng-hide="showEcpm">
			<img class="spinningwheel" src="images/spinningwheel.gif" />
		</div>
		<div ng-show="showEcpm">
		eCPM<br><hr><br>
		<table class="dataTable">
			<tr class="headerRow"><th>Country</th><th>FSI Gross eCPM</th><th>FSI Pub eCPM</th>
			<th>FSI eCPM Goal</th><th>FSI eCPM Goal Status</th>
			<th>Video Gross eCPM</th><th>Video Pub eCPM</th><th>Video eCPM Goal</th><th>Video eCPM Goal Status</th>	
			<tr ng-repeat="country in ecpmData">
			<td>{{ country.code }}</td>
			<td>{{ country.values[2] | currency }}</td>
			<td>{{ country.values[3] | currency }}</td>
			<td>{{ country.values[5] | currency }}</td>
			<td><span ng-if="country.values[3] >= country.values[5]" style="color:green;">&#10004;</span><span ng-if="country.values[3] < country.values[5]" style="color:red;">&#10060;</span></td>		
			<td>{{ country.values[0] | currency }}</td>
			<td>{{ country.values[1] | currency }}</td>
			<td>{{ country.values[4] | currency }}</td>
			<td><span ng-if="country.values[1] >= country.values[4]" style="color:green;">&#10004;</span><span ng-if="country.values[1] < country.values[4]" style="color:red;">&#10060;</span></td>	
			</tr>
		</table>
		</div>
	</div>
</body>
</html>