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
<title>Publisher Churn Analysis</title>
</head>
<body>
	<div id="header"></div>
	<div id="blank"></div>
	<span class="pageHeading">PUBLISHER CHURN ANALYSIS</span>
	<div id="narrowblank"></div>
	<div id="mainDiv" ng-app="churnApp" ng-controller="churnCtrl">
	
	<!-- Modal code -->
	<script type="text/ng-template" id="myModalContent.html">
        <div class="modal-header">
            <h3 class="modal-title">Month: {{ month }}</h3>
        </div>
        <div class="modal-body">
			Publishers start month: {{ category }}<br><br>
			<div id="spinner" ng-hide="showTable">
				<img class="spinningwheel" src="images/spinningwheel.gif" />
			</div>
			<table class="dataTable" style="width: 100%;" ng-show="showTable">
			<tr class="headerRow">
			<th>Publisher (Top 20)</th><th>{{ type.indexOf("reqs") > -1 ? "Requests" : "Revenue"}}</th>
			</tr>
			<tr ng-repeat="pub in data">
			<td>{{ pub.publisherName }}</td><td ng-show="reqs">{{ pub.requests | number:0 }}</td><td ng-hide="reqs">{{ pub.revenue | currency }}</td>
			</tr>
			</table>
        </div>
        <div class="modal-footer">
            <button class="btn btn-primary" type="button" ng-click="ok()">OK</button>
            <button class="btn btn-warning" type="button" ng-click="cancel()">Cancel</button>
        </div>
    </script>
    <!-- Modal code -->
    <div ng-show="hideRevLoading">
    <br><br>
    <table style="width:80%;text-align:center;">
    <tr><td>Nexage risk <span class="circle" ng-style="myStyle(nexRisk)" style="color:white;">{{ nexRisk | number:0 }}</span></td>
    <td>Mydas risk <span class="circle" ng-style="myStyle(mydasRisk)"  style="color:white;">{{ mydasRisk | number:0 }}</span></td></tr></table></div>
	<br><br>
		<div class="tabs">
			<ul class="tab-links">
				<li class="active"><a href="#tab1">By Requests</a></li>
				<li><a href="#tab2">By Revenue</a></li>
			</ul>

			<div class="tab-content">
				<div id="tab1" class="tab active">
				<br><br>
					<div id="spinner" ng-hide="hideNexLoading">
						<img class="spinningwheel" src="images/spinningwheel.gif" />
					</div>
					<div ng-show="hideNexLoading">
						<highchart id="nexPubChurnChart" config="nexPubChurnChart"></highchart>
					</div>
					<br> <br> <br>
					<div id="spinner" ng-hide="hideLoading">
						<img class="spinningwheel" src="images/spinningwheel.gif" />
					</div>
					<div ng-show="hideLoading">
						<highchart id="pubChurnChart" config="pubChurnChart"></highchart>
					</div>
				</div>

				<div id="tab2" class="tab">
					<br><br>
					<div id="spinner" ng-hide="hideNexRevLoading">
						<img class="spinningwheel" src="images/spinningwheel.gif" />
					</div>
					<div ng-show="hideNexRevLoading">
						<highchart id="nexPubChurnRevChart" config="nexPubChurnRevChart"></highchart>
					</div>
					<br> <br> <br>
					<div id="spinner" ng-hide="hideRevLoading">
						<img class="spinningwheel" src="images/spinningwheel.gif" />
					</div>
					<div ng-show="hideRevLoading">
						<highchart id="pubChurnRevChart" config="pubChurnRevChart"></highchart>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>