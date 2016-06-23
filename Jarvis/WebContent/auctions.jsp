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
<link rel="icon" type="image/png" href="images/logo.png">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>
<script
	src="//ajax.googleapis.com/ajax/libs/angularjs/1.5.0/angular-animate.js"></script>
<script
	src="//angular-ui.github.io/bootstrap/ui-bootstrap-tpls-1.2.5.js"></script>
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
<title>Nexage Multiple Auctions</title>
</head>
<body>
	<div id="header"></div>
	<div id="blank"></div>
	<span class="pageHeading">NEXAGE MULTIPLE AUCTIONS</span>
	<div id="narrowblank"></div>
	<div id="mainDiv" ng-app="auctionsApp" ng-controller="auctionsCtrl">
		<div id="spinner" ng-hide="hideLoading">
			<img class="spinningwheel" src="images/spinningwheel.gif" />
		</div>
		<div ng-show="hideLoading">
			<table>
				<tr>
					<td width="400px">
						<div
							style="border: 2px solid grey; padding: 1px 10px 1px 2px; width: 350px;">
							<i class="fa fa-search"></i> <input type="text" name="filter"
								ng-model="filter.siteName"
								style="border: 0px solid #000000; width: 300px; height: 20px; font-size: 15px;"
								placeholder="Filter sites...">
						</div>
					</td>
					<td>Date range:&nbsp;&nbsp;
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
				</tr>
			</table>
			<br>
			<table class="dataTable" style="max-width: 100%;"
				ng-init="predicate = 'ratio'; reverse = true;">
				<tr class="headerRow">
					<th><a href="#" ng-click="order('siteName')">SITE</a><span
						class="sortorder" ng-show="predicate === 'siteName'"
						ng-class="{reverse:reverse}"></span></th>
					<th><a href="#" ng-click="order('ratio')">OUTBOUND/INBOUND</a><span
						class="sortorder" ng-show="predicate === 'ratio'"
						ng-class="{reverse:reverse}"></span></th>
					<th><a href="#" ng-click="order('rev')">NET REVENUE</a><span
						class="sortorder" ng-show="predicate === 'rev'"
						ng-class="{reverse:reverse}"></span></th>
					<th><a href="#" ng-click="order('cost')">COST</a><span
						class="sortorder" ng-show="predicate === 'cost'"
						ng-class="{reverse:reverse}"></span></th>
					<th><a href="#" ng-click="order('margin')">MARGIN</a><span
						class="sortorder" ng-show="predicate === 'margin'"
						ng-class="{reverse:reverse}"></span></th>
					<th><a href="#" ng-click="order('totalImpressions')">IMPRESSIONS</a><span
						class="sortorder" ng-show="predicate === 'totalImpressions'"
						ng-class="{reverse:reverse}"></span></th>
					<th><a href="#" ng-click="order('avgEcpm')">ECPM</a><span
						class="sortorder" ng-show="predicate === 'avgEcpm'"
						ng-class="{reverse:reverse}"></span></th>
				</tr>
				<tr
					ng-repeat="site in sites | orderBy: predicate : reverse | filter: filter">
					<td><a
						href="tagAuctions.jsp?type=getTags&siteId={{site.siteId}}">{{
							site.siteName }}</a>&nbsp;&nbsp;&nbsp;<span
						ng-bind-html="showWarning(site.margin)"></span></td>
					<td>{{ site.ratio | number:1 }}</td>
					<td>{{ site.rev | currency }}</td>
					<td>{{ site.cost | currency }}</td>
					<td>{{ site.margin | currency }}</td>
					<td>{{ site.totalImpressions | number:0 }}</td>
					<td>{{ site.avgEcpm | currency }}</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>