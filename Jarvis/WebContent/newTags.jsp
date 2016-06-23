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
<title>New Tags</title>
</head>
<body>
	<div id="header"></div>
	<div id="blank"></div>
	<span class="pageHeading">NEW TAGS - LAST 7 DAYS</span>
	<div id="narrowblank"></div>
	<div id="mainDiv" ng-app="newTagsApp" ng-controller="newTagsCtrl">
		<div id="spinner" ng-hide="showTable">
			<img class="spinningwheel" src="images/spinningwheel.gif" />
		</div>
		<div ng-show="showTable">
			<div
				style="border: 2px solid grey; padding: 1px 10px 1px 2px; width: 25%;">
				<i class="fa fa-search"></i> <input type="text" name="filter"
					ng-model="filter.siteName"
					style="border: 0px solid #000000; width: 300px; height: 20px; font-size: 15px;"
					placeholder="Filter sites...">
			</div><br>
			<table class="dataTable" style="width: 100%;" ng-init="predicate = 'date'; reverse = false;">
				<tr class="headerRow">
					<th style="width:45%;"><a href="#" ng-click="order('siteId')">Tag</a><span
							class="sortorder" ng-show="predicate === 'siteId'"
							ng-class="{reverse:reverse}"></span></th>
					<th style="width:45%;"><a href="#" ng-click="order('siteName')">Site</a><span
							class="sortorder" ng-show="predicate === 'siteName'"
							ng-class="{reverse:reverse}"></span></th>
					<th style="width:10%;"><a href="#" ng-click="order('date')">Created</a><span
							class="sortorder" ng-show="predicate === 'date'"
							ng-class="{reverse:reverse}"></span></th>
				</tr>
				<tr ng-repeat="site in tagdata | orderBy: predicate : reverse | filter: filter">
					<td>{{ site.siteId }}</td>
					<td>{{ site.siteName }}</td>
					<td>{{ site.date }}</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>