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
<title>Tier Optimization</title>
</head>
<body>
	<div id="header"></div>
	<div id="blank"></div>
	<span class="pageHeading">TIER OPTIMIZATION REPORT</span><br>
	<span style="font-size:12px;">The following sites listed may have tags in need of optimizing between tiers</span><br>
	<div id="narrowblank"></div>
	<div id="mainDiv" ng-app="tierOptApp" ng-controller="tierOptCtrl">
		<div id="spinner" ng-hide="showData">
			<img class="spinningwheel" src="images/spinningwheel.gif" />
		</div>
		<div ng-show="showData">
			<div
				style="border: 2px solid grey; padding: 1px 10px 1px 2px; width: 25%;">
				<i class="fa fa-search"></i> <input type="text" name="filter"
					ng-model="filter.siteName"
					style="border: 0px solid #000000; width: 300px; height: 20px; font-size: 15px;"
					placeholder="Filter sites...">
			</div><br>
			<div id="panel">
				<div class="panelHeading"
				ng-repeat="site in sites | orderBy: 'siteName' | filter: filter"
				id="{{site.siteName}}">
					<a class="noDecor" ng-href="tierOpt.jsp#/{{site.siteName}}"
					ng-model="collapsed" ng-click="collapsed=!collapsed"><span
					ng-class="{'chevron right': !collapsed, 'chevron bottom': collapsed}">&nbsp;&nbsp;</span>
					{{site.siteName}}</a>
					
					<div class="panelContent" style="background-color:#F3F3F3;" ng-show="collapsed"
						ng-repeat="pos in site.positionsList">
					<a class="noDecor" ng-href="tierOpt.jsp#/{{site.siteName}}"
						ng-model="siteCollapsed"
						ng-click="siteCollapsed=!siteCollapsed"><span
						ng-class="{'chevron right': !siteCollapsed, 'chevron bottom': siteCollapsed}">&nbsp;&nbsp;</span>
						<span class="emphasize">Position: {{pos.positionName}}</span></a>
					
					<div class="panelContent" style="background-color:#F3F3F3;" ng-show="siteCollapsed">
						<table class="dataTable" style="width: 100%;">
							<tr class="headerRow">
								<th>Tag</th>
								<th>Tier</th>
								<th>eCPM</th>
								<th>Last 7 Day Revenue</th>
								<th>Last 7 Day Impressions</th>
							</tr>
							<tr ng-repeat="tag in pos.tagsList | orderBy: 'tier'">
								<td>{{ tag.placementName }}</td>
								<td>{{ tag.tier }}</td>
								<td>{{ tag.avgEcpm | currency }}</td>
								<td>{{ tag.rev | currency }}</td>
								<td>{{ tag.totalImpressions | number:0 }}</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
</body>
</html>