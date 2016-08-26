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
<title>TacoCorp Reports</title>
</head>
<body>
	<div id="header"></div>
	<div id="blank"></div>
	<span class="pageHeading">TACOCORP REPORTS</span>
	<div id="narrowblank"></div>
	<div id="mainDiv" ng-app="sdkApp">

		<br> <br>
		<div class="tabs">
			<ul class="tab-links">
				<li class="active"><a href="#tab1">SDK Adoption</a></li>
				<li><a href="#tab2">Native</a></li>
				<li><a href="#tab3">OS Versions</a></li>
			</ul>

			<div class="tab-content">
				<div id="tab1" class="tab active">
					<div ng-controller="sdkCtrl">
						<br> <br>
						<div id="spinner" ng-hide="showData">
							<img class="spinningwheel" src="images/spinningwheel.gif"/>
						</div>
						
						<span style="float:right;">
						Select chart style<br>
						    Column:
						    <input id="colRadio" type="radio" ng-model="typeValue" value="col" ng-click="changeChart()"/>&nbsp;&nbsp;
						    Area:
						    <input id="areaRadio" type="radio" ng-model="typeValue" value="area" ng-click="changeChart()"/>&nbsp;&nbsp;
							<br><br>
						 </span>
						
						<div ng-show="showCol">
							<highchart id="sdkChart" config="sdkChart"></highchart>
						</div>
						<div ng-show="showArea">
							<highchart id="sdkAreaChart" config="sdkAreaChart"></highchart>
						</div>
						<br><br>
						<div>
							<highchart id="sdkBreakoutChartTotal" config="sdkBreakoutChartTotal"></highchart>
						</div>
						<br><br>
						<div>
							<highchart id="sdkBreakoutChartAndroid" config="sdkBreakoutChartAndroid"></highchart>
						</div>
						<br><br>
						<div>
							<highchart id="sdkBreakoutChartiOS" config="sdkBreakoutChartiOS"></highchart>
						</div>
					</div>
				</div>

				<div id="tab2" class="tab">
					<div ng-controller="nativeCtrl">
						<br> <br>
						<div id="spinner" ng-hide="showNativeData">
							<img class="spinningwheel" src="images/spinningwheel.gif" />
						</div>

						<div ng-show="showNativeData">
							<span class="pageSubHeading">Supply (Last 30 days):<br>
							<br></span>
							<highchart id="nativeReqsChart" config="nativeReqsChart"></highchart>
						</div>
						<br> <br>
						<div ng-show="showNativeData">
							<highchart id="nativeRevChart" config="nativeRevChart"></highchart>
						</div>
					</div>
					<div ng-controller="nativeDemandCtrl">
						<br> <br>
						<div id="spinner" ng-hide="showNativeDemandData">
							<img class="spinningwheel" src="images/spinningwheel.gif" />
						</div>
						<span class="pageSubHeading">Demand (Last 30 days):<br>
						<br></span>
						<table class="dataTable" ng-show="showNativeDemandData">
							<tr class="headerRow">
								<th>Bidder</th>
								<th>Served</th>
								<th>Viewed</th>
								<th>Revenue</th>
							</tr>
							<tr ng-repeat="bidder in data">
								<td>{{ bidder.name }}</td>
								<td>{{ bidder.served | number:0 }}</td>
								<td>{{ bidder.viewed | number:0 }}</td>
								<td>{{ bidder.revenue | currency }}</td>
						</table>
					</div>
				</div>
				<div id="tab3" class="tab">
					<div ng-controller="osCtrl">
					<br> <br>
					<div id="spinner" ng-hide="showOsData">
							<img class="spinningwheel" src="images/spinningwheel.gif" />
						</div>
						<div ng-show="showOsData">
						<span class="pageSubHeading">Nexage Requests by OS Versions - Last 30 days</span><br><br>
							<table class="dataTable" ng-show="showOsData">
							<tr class="headerRow">
								<th>OS Version</th>
								<th>Requests</th>
							</tr>
							<tr ng-repeat="osv in data">
								<td>{{ osv.code }}</td>
								<td>{{ osv.z | number:0 }}</td>
						</table>
						</div>
				</div> 
				</div>
			</div>
		</div>
	</div>
</body>
</html>