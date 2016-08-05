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
<script
	src="https://rawgithub.com/eligrey/FileSaver.js/master/FileSaver.js"
	type="text/javascript"></script>
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
<title>Supply Analysis</title>
</head>
<body>
	<div id="header"></div>
	<div id="blank"></div>
	<span class="pageHeading">SUPPLY ANALYSIS</span>
	<div id="narrowblank"></div>
	<div id="mainDiv" ng-app="supplyApp" ng-controller="supplyCtrl"
		ng-show="got_ams">
		<span class="bodyText">Please select an Account Manager</span>&nbsp; <select
			name="exchangeSelect" id="exchangeSelect"
			ng-model="filterCondition.selectedAM"
			ng-change="getAccounts(filterCondition.selectedAM)">
			<option value="-1">---Please select---</option>
			<option ng-repeat="am in ams" value="{{am.accountManagerId}}">
				{{am.accountManagerName}}</option>
		</select><br> <br> <br> <br>
		<div id="spinner" ng-hide="showLoading">
			<img class="spinningwheel" src="images/spinningwheel.gif" />
		</div>
		<div id="panel" ng-show="showAccounts">
			<div class="panelHeading"
				ng-repeat="account in accounts | orderBy: 'publisherName'"
				id="{{account.publisherId}}">
				<a class="noDecor" ng-href="supply.jsp#/{{account.publisherId}}"
					ng-model="collapsed" ng-click="collapsed=!collapsed"><span
					ng-class="{'chevron right': !collapsed, 'chevron bottom': collapsed}">&nbsp;&nbsp;</span>Publisher:
					{{account.publisherName}}</a>
				<div class="panelContent" style="background-color:#F3F3F3;" ng-show="collapsed"
					ng-repeat="site in account.sitesList | orderBy: 'siteName'">
					<a class="noDecor" ng-href="supply.jsp#/{{account.publisherId}}"
						ng-model="siteCollapsed"
						ng-click="getSiteData(site.siteId, site);siteCollapsed=!siteCollapsed"><span
						ng-class="{'chevron right': !siteCollapsed, 'chevron bottom': siteCollapsed}">&nbsp;&nbsp;</span>Site:
						<span class="emphasize">{{site.siteName}}</span></a>
					<div id="{{site.siteId}}" class="panelContent"
						ng-show="siteCollapsed" ng-repeat="placement in site.placements">

						<a class="noDecor" ng-href="supply.jsp#/{{site.siteId}}"
							ng-model="placementCollapsed"
							ng-click="getPlacementData(placement.placementId, placement.placementDimension, site.bundleId, placement);placementCollapsed=!placementCollapsed"><span
							ng-class="{'chevron right': !placementCollapsed, 'chevron bottom': placementCollapsed}">&nbsp;&nbsp;</span>Placement:
							<span class="emphasize">{{placement.placementName}}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Platform:
							<span class="emphasize">{{site.os}}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Size:
							<span class="emphasize">{{placement.placementDimension}}</span></a>
						<div id="{{placement.placementId}}" class="panelContent"
							ng-show="placementCollapsed">
							<hr>
							
							<!-- Modal code -->
	<script type="text/ng-template" id="supplyModalContent.html">
        <div class="modal-header">
            <h3 class="modal-title">Data</h3>
        </div>
        <div class="modal-body">
<div id="exportable">
			<table class="dataTable" style="width: 100%;" ng-show="showTable">
			<tr class="headerRow">
			<th>Date</th><th>Green requests</th><th>Green eCPM</th><th>Nexage requests</th><th>Nexage eCPM</th>
			</tr>
			<tr ng-repeat="day in data.trends">
			<td>{{ day.date }}</td><td>{{ day.greenReqs | number:0 }}</td><td>{{ day.greenEcpm | currency }}</td>
			<td>{{ day.nexReqs | number:0 }}</td><td>{{ day.nexEcpm | currency }}</td>
			</tr>
			</table>
</div>
        </div>
        <div class="modal-footer">
            <button class="btn btn-primary" type="button" ng-click="ok()">OK</button>
            <button class="btn btn-primary" ng-click="exportData()">Export</button>&nbsp;&nbsp;
        </div>
    </script>
    <!-- Modal code -->
							<span style="float:right;font-size:12px;" ng-show="placement.showChart"><a href="#" ng-click="showData()">Show data</a></span><br><br>
							
							<table>
								<tr>
									<td><highchart id="{{placement.placementId}}"
											config="placement.chart" ng-show="placement.showChart"></highchart>
									</td>
									<td><highchart id="{{placement.placementName}}"
											config="placement.ecpmChart" ng-show="placement.showChart"></highchart>
									</td>
								</tr>
								<tr>
									<td>
									<table class="dataTable" style="width: 100%;" ng-show="placement.showChart">
									<tr class="headerRow"><th>Country</th><th>Green Requests</th></tr>
									<tr ng-repeat="country in placement.greenCountryReqs">
										<td>{{ country.code }}</td>
										<td>{{ country.z | number:0 }}</td>
									</tr>
									</table>
									</td>
									<td>
									<table class="dataTable" style="width: 100%;" ng-show="placement.showChart">
									<tr class="headerRow"><th>Country</th><th>Nexage Requests</th></tr>
									<tr ng-repeat="country in placement.nexCountryReqs">
										<td>{{ country.code }}</td>
										<td>{{ country.z | number:0 }}</td>
									</tr>
									</table>
									</td>
								</tr>
							</table>
							<div ng-hide="placement.showChart">
								<img class="spinningwheel" src="images/spinningwheel.gif" />
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>