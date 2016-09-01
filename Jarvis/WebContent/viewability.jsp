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
<title>Viewability Report</title>
</head>
<body>
	<div id="header"></div>
	<div id="blank"></div>
	<span class="pageHeading">VIEWABILITY REPORT</span>
	<div id="narrowblank"></div>
	<div id="mainDiv" ng-app="viewabilityApp">
		<div>
			<div class="tabs">
				<ul class="tab-links">
					<li class="active"><a href="#tab1">IAS - Exchange</a></li>
					<li><a href="#tab2">MOAT - Exchange</a></li>
					<li><a href="#tab3">IAS - Network</a></li>
					<li><a href="#tab4">MOAT - Network</a></li>
				</ul>

				<div class="tab-content">

					<div id="tab1" class="tab active">
						<div ng-controller="iasExchCtrl">
							<div id="spinner" ng-hide="showData" style="float: left;">
								<br> <br> <img class="spinningwheel"
									src="images/spinningwheel.gif" />
							</div>
							<div ng-show="showData">
								<br> <span style="float: right;"><button
										class="btn btn-primary" ng-click="exportData('IASexch')">Export</button>&nbsp;&nbsp;</span><br>
								<br>
								<div id="exportable1">
									<table class="dataTable" style="width: 100%;"
										ng-init="predicate = 'nexSiteName'; reverse = false;">
										<tr class="headerRow">
											<!-- <th><a href="#" ng-click="order('acctMgr')">AM</a><span class="sortorder"
										ng-show="predicate === 'acctMgr'"
										ng-class="{reverse:reverse}"></span></th> -->
											<th><a href="#" ng-click="order('nexPubName')">Nexage
													Pub Name</a><span class="sortorder"
												ng-show="predicate === 'nexPubName'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('nexSiteName')">Nexage
													Site Name</a><span class="sortorder"
												ng-show="predicate === 'nexSiteName'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('nexAlias')">Nexage
													Alias</a><span class="sortorder"
												ng-show="predicate === 'nexAlias'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('dspSiteId')">mmDSP
													Site Id</a><span class="sortorder"
												ng-show="predicate === 'dspSiteId'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('creativeType')">Creative
													Type</a><span class="sortorder"
												ng-show="predicate === 'creativeType'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('creativeSize')">Creative
													Size</a><span class="sortorder"
												ng-show="predicate === 'creativeSize'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('os')">OS</a><span
												class="sortorder" ng-show="predicate === 'os'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('invType')">Inventory
													Type</a><span class="sortorder"
												ng-show="predicate === 'invType'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('impsAnalyzed')">IAS
													Impressions Analyzed</a><span class="sortorder"
												ng-show="predicate === 'impsAnalyzed'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('impsDelivered')">Nexage
													Impressions Delivered</a><span class="sortorder"
												ng-show="predicate === 'impsDelivered'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('impsDiscrepancy')">Impressions
													Discrepancy</a><span class="sortorder"
												ng-show="predicate === 'impsDiscrepancy'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('measuredRate')">Measured
													Rate</a><span class="sortorder"
												ng-show="predicate === 'measuredRate'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('viewScore')">Viewability
													Score</a><span class="sortorder"
												ng-show="predicate === 'viewScore'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('dailyAvails')">Daily
													Avails</a><span class="sortorder"
												ng-show="predicate === 'dailyAvails'"
												ng-class="{reverse:reverse}"></span></th>
											<!-- <th><a href="#" ng-click="order('dateLastTested')">Date
													Last Tested</a><span class="sortorder"
												ng-show="predicate === 'dateLastTested'"
												ng-class="{reverse:reverse}"></span></th> -->
										</tr>
										<tr
											ng-repeat="site in data | orderBy: predicate : reverse | filter: {partner:'IAS',platform:'Exchange'}">
											<!-- <td>{{ site.acctMgr }}</td> -->
											<td>{{ site.nexPubName }}</td>
											<td>{{ site.nexSiteName }}</td>
											<td>{{ site.nexAlias }}</td>
											<td>{{ site.dspSiteId }}</td>
											<td>{{ site.creativeType }}</td>
											<td>{{ site.creativeSize }}</td>
											<td>{{ site.os }}</td>
											<td>{{ site.invType }}</td>
											<td>{{ site.impsAnalyzed | number }}</td>
											<td>{{ site.impsDelivered | number }}</td>
											<td>{{ site.impsDiscrepancy }}</td>
											<td>{{ site.measuredRate }}</td>
											<td>{{ site.viewScore }}</td>
											<td>{{ site.dailyAvails | number }}</td>
											<!-- <td>{{ site.dateLastTested }}</td> -->
										</tr>
									</table>
								</div>
								<br> <span style="float: right;"><button
										class="btn btn-primary" ng-click="exportData('IASexch')">Export</button>&nbsp;&nbsp;</span><br>
								<br>
							</div>
						</div>
					</div>

					<div id="tab2" class="tab">
						<div ng-controller="moatExchCtrl">
							<div id="spinner" ng-hide="showData" style="float: left;">
								<br> <br> <img class="spinningwheel"
									src="images/spinningwheel.gif" />
							</div>
							<div ng-show="showData">
								<br> <span style="float: right;"><button
										class="btn btn-primary" ng-click="exportData('MOATexch')">Export</button>&nbsp;&nbsp;</span><br>
								<br>
								<div id="exportable2">
									<table class="dataTable" style="width: 100%;"
										ng-init="predicate = 'nexSiteName'; reverse = false;">
										<tr class="headerRow">
											<!-- <th><a href="#" ng-click="order('acctMgr')">AM</a><span class="sortorder"
										ng-show="predicate === 'acctMgr'"
										ng-class="{reverse:reverse}"></span></th> -->
											<th><a href="#" ng-click="order('nexPubName')">Nexage
													Pub Name</a><span class="sortorder"
												ng-show="predicate === 'nexPubName'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('nexSiteName')">Nexage
													Site Name</a><span class="sortorder"
												ng-show="predicate === 'nexSiteName'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('nexAlias')">Nexage
													Alias</a><span class="sortorder"
												ng-show="predicate === 'nexAlias'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('dspSiteId')">mmDSP
													Site Id</a><span class="sortorder"
												ng-show="predicate === 'dspSiteId'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('creativeType')">Creative
													Type</a><span class="sortorder"
												ng-show="predicate === 'creativeType'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('creativeSize')">Creative
													Size</a><span class="sortorder"
												ng-show="predicate === 'creativeSize'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('os')">OS</a><span
												class="sortorder" ng-show="predicate === 'os'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('invType')">Inventory
													Type</a><span class="sortorder"
												ng-show="predicate === 'invType'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('impsAnalyzed')">MOAT
													Impressions Analyzed</a><span class="sortorder"
												ng-show="predicate === 'impsAnalyzed'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('impsDelivered')">Nexage
													Impressions Delivered</a><span class="sortorder"
												ng-show="predicate === 'impsDelivered'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('impsDiscrepancy')">Impressions
													Discrepancy</a><span class="sortorder"
												ng-show="predicate === 'impsDiscrepancy'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('measuredRate')">Measured
													Rate</a><span class="sortorder"
												ng-show="predicate === 'measuredRate'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('viewScore')">Viewability
													Score</a><span class="sortorder"
												ng-show="predicate === 'viewScore'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('dailyAvails')">Daily
													Avails</a><span class="sortorder"
												ng-show="predicate === 'dailyAvails'"
												ng-class="{reverse:reverse}"></span></th>
											<!-- <th><a href="#" ng-click="order('dateLastTested')">Date
													Last Tested</a><span class="sortorder"
												ng-show="predicate === 'dateLastTested'"
												ng-class="{reverse:reverse}"></span></th> -->
										</tr>
										<tr
											ng-repeat="site in data | orderBy: predicate : reverse | filter:{partner:'MOAT',platform:'Exchange'}">
											<!-- <td>{{ site.acctMgr }}</td> -->
											<td>{{ site.nexPubName }}</td>
											<td>{{ site.nexSiteName }}</td>
											<td>{{ site.nexAlias }}</td>
											<td>{{ site.dspSiteId }}</td>
											<td>{{ site.creativeType }}</td>
											<td>{{ site.creativeSize }}</td>
											<td>{{ site.os }}</td>
											<td>{{ site.invType }}</td>
											<td>{{ site.impsAnalyzed | number }}</td>
											<td>{{ site.impsDelivered | number }}</td>
											<td>{{ site.impsDiscrepancy }}</td>
											<td>{{ site.measuredRate }}</td>
											<td>{{ site.viewScore }}</td>
											<td>{{ site.dailyAvails | number }}</td>
											<!-- <td>{{ site.dateLastTested }}</td> -->
										</tr>
									</table>
								</div>
								<br> <span style="float: right;"><button
										class="btn btn-primary" ng-click="exportData('MOATexch')">Export</button>&nbsp;&nbsp;</span><br>
								<br>
							</div>
						</div>
					</div>
					<div id="tab3" class="tab">
						<div ng-controller="iasNetCtrl">
							<div id="spinner" ng-hide="showData" style="float: left;">
								<br> <br> <img class="spinningwheel"
									src="images/spinningwheel.gif" />
							</div>
							<div ng-show="showData" style="width:100%">
								<br> <span style="float: right;"><button
										class="btn btn-primary" ng-click="exportData('IASnet')">Export</button>&nbsp;&nbsp;</span><br>
								<br>
								<div id="exportable3">
									<table class="dataTable" style="width: 100%;"
										ng-init="predicate = 'nexSiteName'; reverse = false;">
										<tr class="headerRow">
											<th><a href="#" ng-click="order('nexPubName')">Pub
													Name</a><span class="sortorder"
												ng-show="predicate === 'nexPubName'"
												ng-class="{reverse:reverse}"></span></th>

											<th><a href="#" ng-click="order('nexSiteName')">Site
													Name</a><span class="sortorder"
												ng-show="predicate === 'nexSiteName'"
												ng-class="{reverse:reverse}"></span></th>

											<th><a href="#" ng-click="order('placementName')">Placement
													Name</a><span class="sortorder"
												ng-show="predicate === 'placementName'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('apid')">APID</a><span
												class="sortorder" ng-show="predicate === 'apid'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('creativeType')">Creative
													Type</a><span class="sortorder"
												ng-show="predicate === 'creativeType'"
												ng-class="{reverse:reverse}"></span></th>
												<th><a href="#" ng-click="order('creativeSize')">Creative
													Size</a><span class="sortorder"
												ng-show="predicate === 'creativeSize'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('os')">OS</a><span
												class="sortorder" ng-show="predicate === 'os'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('invType')">Inventory
													Type</a><span class="sortorder"
												ng-show="predicate === 'invType'"
												ng-class="{reverse:reverse}"></span></th>
												<th><a href="#" ng-click="order('impsAnalyzed')">IAS
													Impressions Analyzed</a><span class="sortorder"
												ng-show="predicate === 'impsAnalyzed'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('impsDelivered')">Mydas
													Impressions Delivered</a><span class="sortorder"
												ng-show="predicate === 'impsDelivered'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('impsDiscrepancy')">Impressions
													Discrepancy</a><span class="sortorder"
												ng-show="predicate === 'impsDiscrepancy'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('measuredRate')">Measured
													Rate</a><span class="sortorder"
												ng-show="predicate === 'measuredRate'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('viewScore')">Viewability
													Score</a><span class="sortorder"
												ng-show="predicate === 'viewScore'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('dailyAvails')">Daily
													Avails</a><span class="sortorder"
												ng-show="predicate === 'dailyAvails'"
												ng-class="{reverse:reverse}"></span></th>
											<!-- <th><a href="#" ng-click="order('dateLastTested')">Date
													Last Tested</a><span class="sortorder"
												ng-show="predicate === 'dateLastTested'"
												ng-class="{reverse:reverse}"></span></th> -->
										</tr>
										<tr
											ng-repeat="site in data | orderBy: predicate : reverse | filter: {partner:'IAS',platform:'Network'}">
											<!-- <td>{{ site.acctMgr }}</td> -->
											<td>{{ site.nexPubName }}</td>
											<td>{{ site.nexSiteName }}</td>
											<td>{{ site.placementName }}</td>
											<td>{{ site.apid }}</td>
											<td>{{ site.creativeType }}</td>
											<td>{{ site.creativeSize }}</td>
											<td>{{ site.os }}</td>
											<td>{{ site.invType }}</td>
											<td>{{ site.impsAnalyzed | number }}</td>
											<td>{{ site.impsDelivered | number }}</td>
											<td>{{ site.impsDiscrepancy }}</td>
											<td>{{ site.measuredRate }}</td>
											<td>{{ site.viewScore }}</td>
											<td>{{ site.dailyAvails | number }}</td>
											<!-- <td>{{ site.dateLastTested }}</td> -->
										</tr>
									</table>
								</div>
								<br> <span style="float: right;"><button
										class="btn btn-primary" ng-click="exportData('IASnet')">Export</button>&nbsp;&nbsp;</span><br>
								<br>
							</div>
						</div>
					</div>
					<div id="tab4" class="tab">
						<div ng-controller="moatNetCtrl">
							<div id="spinner" ng-hide="showData" style="float: left;">
								<br> <br> <img class="spinningwheel"
									src="images/spinningwheel.gif" />
							</div>
							<div ng-show="showData">
								<br> <span style="float: right;"><button
										class="btn btn-primary" ng-click="exportData('MOATnet')">Export</button>&nbsp;&nbsp;</span><br>
								<br>
								<div id="exportable4">
									<table class="dataTable" style="width: 100%;"
										ng-init="predicate = 'nexSiteName'; reverse = false;">
										<tr class="headerRow">
											<!-- <th><a href="#" ng-click="order('acctMgr')">AM</a><span class="sortorder"
										ng-show="predicate === 'acctMgr'"
										ng-class="{reverse:reverse}"></span></th> -->
											<th><a href="#" ng-click="order('nexPubName')">Pub
													Name</a><span class="sortorder"
												ng-show="predicate === 'nexPubName'"
												ng-class="{reverse:reverse}"></span></th>
											<!-- <th><a href="#" ng-click="order('pubId')">Pub ID</a><span
										class="sortorder" ng-show="predicate === 'pubId'"
										ng-class="{reverse:reverse}"></span></th> -->
											<th><a href="#" ng-click="order('nexSiteName')">Site
													Name</a><span class="sortorder"
												ng-show="predicate === 'nexSiteName'"
												ng-class="{reverse:reverse}"></span></th>
											<!-- <th><a href="#" ng-click="order('dspSiteId')">Site ID</a><span
										class="sortorder" ng-show="predicate === 'dspSiteId'"
										ng-class="{reverse:reverse}"></span></th> -->
											<th><a href="#" ng-click="order('placementName')">Placement
													Name</a><span class="sortorder"
												ng-show="predicate === 'placementName'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('apid')">APID</a><span
												class="sortorder" ng-show="predicate === 'apid'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('creativeType')">Creative
													Type</a><span class="sortorder"
												ng-show="predicate === 'creativeType'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('creativeSize')">Creative
													Size</a><span class="sortorder"
												ng-show="predicate === 'creativeSize'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('os')">OS</a><span
												class="sortorder" ng-show="predicate === 'os'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('invType')">Inventory
													Type</a><span class="sortorder"
												ng-show="predicate === 'invType'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('impsAnalyzed')">MOAT
													Impressions Analyzed</a><span class="sortorder"
												ng-show="predicate === 'impsAnalyzed'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('impsDelivered')">Mydas
													Impressions Delivered</a><span class="sortorder"
												ng-show="predicate === 'impsDelivered'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('impsDiscrepancy')">Impressions
													Discrepancy</a><span class="sortorder"
												ng-show="predicate === 'impsDiscrepancy'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('measuredRate')">Measured
													Rate</a><span class="sortorder"
												ng-show="predicate === 'measuredRate'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('viewScore')">Viewability
													Score</a><span class="sortorder"
												ng-show="predicate === 'viewScore'"
												ng-class="{reverse:reverse}"></span></th>
											<th><a href="#" ng-click="order('dailyAvails')">Daily
													Avails</a><span class="sortorder"
												ng-show="predicate === 'dailyAvails'"
												ng-class="{reverse:reverse}"></span></th>
											<!-- <th><a href="#" ng-click="order('dateLastTested')">Date
													Last Tested</a><span class="sortorder"
												ng-show="predicate === 'dateLastTested'"
												ng-class="{reverse:reverse}"></span></th> -->
										</tr>
										<tr
											ng-repeat="site in data | orderBy: predicate : reverse | filter:{partner:'MOAT',platform:'Network'}">
											<!-- <td>{{ site.acctMgr }}</td> -->
											<td>{{ site.nexPubName }}</td>
											<!-- <td>{{ site.pubId }}</td> -->
											<td>{{ site.nexSiteName }}</td>
											<!-- <td>{{ site.dspSiteId }}</td> -->
											<td>{{ site.placementName }}</td>
											<td>{{ site.apid }}</td>
											<td>{{ site.creativeType }}</td>
											<td>{{ site.creativeSize }}</td>
											<td>{{ site.os }}</td>
											<td>{{ site.invType }}</td>
											<td>{{ site.impsAnalyzed | number }}</td>
											<td>{{ site.impsDelivered | number }}</td>
											<td>{{ site.impsDiscrepancy }}</td>
											<td>{{ site.measuredRate }}</td>
											<td>{{ site.viewScore }}</td>
											<td>{{ site.dailyAvails | number }}</td>
											<!-- <td>{{ site.dateLastTested }}</td> -->
										</tr>
									</table>

								</div>
								<br> <span style="float: right;"><button
										class="btn btn-primary" ng-click="exportData('MOATnet')">Export</button>&nbsp;&nbsp;</span><br>
								<br>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>