<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="styles.css">
<link rel="stylesheet" type="text/css"
	href="https://fonts.googleapis.com/css?family=Open+Sans:400,300,700">
<link rel="icon" type="image/png" href="images/logo.png">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>
	<script
	src="//ajax.googleapis.com/ajax/libs/angularjs/1.5.0/angular-animate.js"></script>
<script
	src="//angular-ui.github.io/bootstrap/ui-bootstrap-tpls-1.2.5.js"></script>
<!-- <script src="http://code.highcharts.com/highcharts.js"></script> -->
<script src="https://code.highcharts.com/maps/highmaps.js"></script>
<script src="https://code.highcharts.com/maps/modules/data.js"></script>
<script src="https://code.highcharts.com/mapdata/custom/world.js"></script>
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
<title>Nexage Multiple Auctions</title>
</head>
<body>
	<div id="header"></div>
	<div id="blank"></div>
	<span class="pageHeading">NEXAGE MULTIPLE AUCTIONS</span>
	<div id="narrowblank"></div>
	<div id="mainDiv" ng-app="tagAuctionsApp"
		ng-controller="tagAuctionsCtrl"
		ng-init="siteId=<%=request.getParameter("siteId")%>">
		<div id="spinner" ng-hide="hideLoading">
			<img class="spinningwheel" src="images/spinningwheel.gif" />
		</div>
		<div ng-show="hideLoading">
		<table width="100%"><tr><td>
			<span class="pageSubHeading">Site name: {{ tags.siteName }}</span></td>
				<td>
				Date range:&nbsp;&nbsp;
						<div class="btn-group" uib-dropdown is-open="status.isopen">
							<button id="single-button" type="button" class="btn btn-primary"
								uib-dropdown-toggle ng-disabled="disabled">
								{{ selectedDateRange }}&nbsp;&nbsp;<span class="chevron bottom"></span>
							</button>
							<ul uib-dropdown-menu role="menu" aria-labelledby="single-button">
								<li role="menuitem" ng-click="dateRangeClick('7')"><a href="#">Last 7 days</a></li>
								<li role="menuitem" ng-click="dateRangeClick('14')"><a href="#">Last 14 days</a></li>
								<li role="menuitem" ng-click="dateRangeClick('30')"><a href="#">Last 30 days</a></li>
								<li role="menuitem" ng-click="dateRangeClick('60')"><a href="#">Last 60 days</a></li>
								<!-- <li role="menuitem" ng-click="dateRangeClick('90')"><a href="#">Last 90 days</a></li> -->
							</ul>
						</div>
				</td>
				<td><span
				style="float: right; font-size: 12px; padding-right: 10px;"><a
				href="javascript:history.back()">Go back to all sites</a></span></td></tr>
				</table>
				<br><br>
			<div>
				<highchart id="auctionsChart" config="auctionsChart"></highchart>
			</div>
			<br>
			<div>
				<highchart id="revChart" config="revChart"></highchart>
			</div>
			<br>
			<div id="table">
			<table class="dataTable" style="max-width: 100%;"
				ng-init="predicate = 'rev'; reverse = true;">
				<tr class="headerRow">
					<th><a href="#table" ng-click="order('placementName')">TAG</a><span
						class="sortorder" ng-show="predicate === 'placementName'"
						ng-class="{reverse:reverse}"></span></th>
					<th><a href="#table" ng-click="order('rev')">NET REVENUE</a><span
						class="sortorder" ng-show="predicate === 'rev'"
						ng-class="{reverse:reverse}"></span></th>
					<th><a href="#table" ng-click="order('cost')">COST</a><span
						class="sortorder" ng-show="predicate === 'cost'"
						ng-class="{reverse:reverse}"></span></th>
					<th><a href="#table" ng-click="order('margin')">MARGIN</a><span
						class="sortorder" ng-show="predicate === 'margin'"
						ng-class="{reverse:reverse}"></span></th>
						<th><a href="#table" ng-click="order('totalImpressions')">IMPRESSIONS</a><span
						class="sortorder" ng-show="predicate === 'totalImpressions'"
						ng-class="{reverse:reverse}"></span></th>
						<th><a href="#table" ng-click="order('avgEcpm')">ECPM</a><span
						class="sortorder" ng-show="predicate === 'avgEcpm'"
						ng-class="{reverse:reverse}"></span></th>
						<th><a href="#table" ng-click="order('position')">POSITION</a><span
						class="sortorder" ng-show="predicate === 'position'"
						ng-class="{reverse:reverse}"></span></th>
						<th><a href="#table" ng-click="order('tier')">TIER</a><span
						class="sortorder" ng-show="predicate === 'tier'"
						ng-class="{reverse:reverse}"></span></th>
				</tr>
				<tr
					ng-repeat="tag in tags.placementsList | orderBy: predicate : reverse">
					<td>{{ tag.placementName }}&nbsp;&nbsp;&nbsp;<span
						ng-bind-html="showWarning(tag.margin)"></span></td>
					<td>{{ tag.rev | currency }}</td>
					<td>{{ tag.cost | currency }}</td>
					<td>{{ tag.margin | currency }}</td>
					<td>{{ tag.totalImpressions | number:0 }}</td>
					<td>{{ tag.avgEcpm | currency }}</td>
					<td>{{ tag.position }}</td>
					<td>{{ tag.tier }}</td>
				</tr>
			</table>
			</div><br><br>
			<div id="countryReqsChart" style="float:left;"></div>
			<div id="countryWinsChart" style="float:left;margin-left:20px;">Wins chart</div>
			<div style="clear:both;">
				<br><br>
				<highchart id="carrierReqsChart" config="carrierReqsChart"></highchart>
			</div>
		</div>
	</div>
</body>
</html>