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
<title>DSP Exchange Buying</title>
</head>
<body>
	<div id="header"></div>
	<div id="blank"></div>
	<span class="pageHeading">DSP EXCHANGE BUYING ANALYSIS</span>
	<div id="narrowblank"></div>
	<div id="mainDiv" ng-app="exchangeApp" ng-controller="exchangeCtrl">
		<span class="bodyText">Please select an exchange</span>&nbsp; <select
			name="exchangeSelect" id="exchangeSelect"
			ng-model="filterCondition.exchangeId"
			ng-change="getRanks(filterCondition.exchangeId)">
			<option value="-1">---Please select---</option>
			<!-- not selected / blank option -->
			<option ng-repeat="exchange in data" value="{{exchange.exchangeId}}">
				{{exchange.exchangeName}}</option>
		</select><br>
		<br>
		<br>
		<div id="spinner" ng-hide="ranksExist" style="display: none;">
			<img class="spinningwheel" src="images/spinningwheel.gif" />
		</div>
		<div ng-show="ranksExist">
			<span class="pageSubHeading">DSP Overview (Last Quarter)</span><br>
			<hr>
			<highchart id="spendChart" config="spendChart"></highchart>
		</div>
		<br>
		<br>
		<br>
		<div ng-show="ranksExist">
			<span class="pageSubHeading">{{ selectedExchangeName }} Ranks
				(Last Quarter)</span><br>
			<hr>
			<table class="inlineRow" style="width: 100%;">
				<tr ng-repeat="x in ranks | filter:filterCondition:true">
					<td>Spend<br>
					<span class="rank">{{ x.costsRank | rank }}</span></td>
					<td>Impressions<br>
					<span class="rank">{{ x.impsRank | rank }}</span></td>
					<td>Clicks<br>
					<span class="rank">{{ x.clicksRank | rank }}</span></td>
					<td>Conversions<br>
					<span class="rank">{{ x.convsRank | rank }}</span></td>
					<td>eCPM<br>
					<span class="rank">{{ x.ecpmRank | rank }}</span></td>
					<td>CTR<br>
					<span class="rank">{{ x.ctrRank | rank }}</span></td>
					<td>CVR<br>
					<span class="rank">{{ x.cvrRank | rank }}</span></td>
				</tr>
			</table>
			<div class="tabs">
				<ul class="tab-links">
					<li class="active"><a href="#tab1">Spend</a></li>
					<li><a href="#tab2">Impressions</a></li>
					<li><a href="#tab3">Clicks</a></li>
					<li><a href="#tab4">Conversions</a></li>
					<li><a href="#tab5">eCPM</a></li>
					<li><a href="#tab6">CTR</a></li>
					<li><a href="#tab7">CVR</a></li>
				</ul>

				<div class="tab-content">
					<div id="tab1" class="tab active">
						<div>
							<highchart id="rankSpendChart" config="rankSpendChart"></highchart>
						</div>
					</div>

					<div id="tab2" class="tab">
						<div>
							<highchart id="rankImpsChart" config="rankImpsChart"></highchart>
						</div>
					</div>

					<div id="tab3" class="tab">
						<div>
							<highchart id="rankClicksChart" config="rankClicksChart"></highchart>
						</div>
					</div>

					<div id="tab4" class="tab">
						<div>
							<highchart id="rankConvsChart" config="rankConvsChart"></highchart>
						</div>
					</div>

					<div id="tab5" class="tab">
						<div>
							<highchart id="rankEcpmChart" config="rankEcpmChart"></highchart>
						</div>
					</div>

					<div id="tab6" class="tab">
						<div>
							<highchart id="rankCtrChart" config="rankCtrChart"></highchart>
						</div>
					</div>

					<div id="tab7" class="tab">
						<div>
							<highchart id="rankCvrChart" config="rankCvrChart"></highchart>
						</div>
					</div>
				</div>
			</div>
		</div>
		<br>
		<br>
		<br>
		<div ng-show="ranksExist" style="float: left; width: 50%">
			<span class="pageSubHeading">Brand vs. Performance (Last
				Quarter)</span><br>
			<hr>
			<highchart id="chart2" config="businessLineChart"></highchart>
		</div>
		<div ng-show="ranksExist" style="float: left; width: 50%">
			<span class="pageSubHeading"></span><br>
			<hr>
			<highchart id="chart3" config="exchangeBusinessChart"></highchart>
		</div>
		<br>
		<br>
		<br>
		<div ng-show="ranksExist">
			<span class="pageSubHeading">{{ selectedExchangeName }} Brand
				Ranks (Last Quarter)</span><br>
			<hr>
			<table class="inlineRow" style="width: 100%;">
				<tr
					ng-repeat="x in businessLine | filter:filterCondition:true | filter:{'lineOfBusiness':'Brand'}:true">
					<td>Brand Spend<br>
					<span class="rank">{{ x.costsRank | rank }}</span></td>
					<td>Brand Impressions<br>
					<span class="rank">{{ x.impsRank | rank }}</span></td>
					<td>Brand Clicks<br>
					<span class="rank">{{ x.clicksRank | rank }}</span></td>
					<td>Brand Conversions<br>
					<span class="rank">{{ x.convsRank | rank }}</span></td>
					<td>Brand eCPM<br>
					<span class="rank">{{ x.ecpmRank | rank }}</span></td>
					<td>Brand CTR<br>
					<span class="rank">{{ x.ctrRank | rank }}</span></td>
					<td>Brand CVR<br>
					<span class="rank">{{ x.cvrRank | rank }}</span></td>
				</tr>
			</table>
			<div class="tabs">
				<ul class="tab-links">
					<li class="active"><a href="#tab8">Brand Spend</a></li>
					<li><a href="#tab9">Brand Impressions</a></li>
					<li><a href="#tab10">Brand Clicks</a></li>
					<li><a href="#tab11">Brand Conversions</a></li>
					<li><a href="#tab12">Brand eCPM</a></li>
					<li><a href="#tab13">Brand CTR</a></li>
					<li><a href="#tab14">Brand CVR</a></li>
				</ul>

				<div class="tab-content">
					<div id="tab8" class="tab active">
						<div>
							<highchart id="brandSpendChart" config="brandSpendChart"></highchart>
						</div>
					</div>

					<div id="tab9" class="tab">
						<div>
							<highchart id="brandImpsChart" config="brandImpsChart"></highchart>
						</div>
					</div>

					<div id="tab10" class="tab">
						<div>
							<highchart id="brandClicksChart" config="brandClicksChart"></highchart>
						</div>
					</div>

					<div id="tab11" class="tab">
						<div>
							<highchart id="brandConvsChart" config="brandConvsChart"></highchart>
						</div>
					</div>

					<div id="tab12" class="tab">
						<div>
							<highchart id="brandEcpmChart" config="brandEcpmChart"></highchart>
						</div>
					</div>

					<div id="tab13" class="tab">
						<div>
							<highchart id="brandCtrChart" config="brandCtrChart"></highchart>
						</div>
					</div>

					<div id="tab14" class="tab">
						<div>
							<highchart id="brandCvrChart" config="brandCvrChart"></highchart>
						</div>
					</div>
				</div>
			</div>
		</div>
		<br>
		<br>
		<br>
		<div ng-show="ranksExist">
			<span class="pageSubHeading">{{ selectedExchangeName }}
				Performance Ranks (Last Quarter)</span><br>
			<hr>
			<table class="inlineRow" style="width: 100%;">
				<tr
					ng-repeat="x in businessLine | filter:filterCondition:true | filter:{'lineOfBusiness':'Performance'}:true">
					<td>Perf Spend<br>
					<span class="rank">{{ x.costsRank | rank }}</span></td>
					<td>Perf Impressions<br>
					<span class="rank">{{ x.impsRank | rank }}</span></td>
					<td>Perf Clicks<br>
					<span class="rank">{{ x.clicksRank | rank }}</span></td>
					<td>Perf Conversions<br>
					<span class="rank">{{ x.convsRank | rank }}</span></td>
					<td>Perf eCPM<br>
					<span class="rank">{{ x.ecpmRank | rank }}</span></td>
					<td>Perf CTR<br>
					<span class="rank">{{ x.ctrRank | rank }}</span></td>
					<td>Perf CVR<br>
					<span class="rank">{{ x.cvrRank | rank }}</span></td>
				</tr>
			</table>
			<div class="tabs">
				<ul class="tab-links">
					<li class="active"><a href="#tab15">Performance Spend</a></li>
					<li><a href="#tab16">Performance Impressions</a></li>
					<li><a href="#tab17">Performance Clicks</a></li>
					<li><a href="#tab18">Performance Conversions</a></li>
					<li><a href="#tab19">Performance eCPM</a></li>
					<li><a href="#tab20">Performance CTR</a></li>
					<li><a href="#tab21">Performance CVR</a></li>
				</ul>

				<div class="tab-content">
					<div id="tab15" class="tab active">
						<div>
							<highchart id="perfSpendChart" config="perfSpendChart"></highchart>
						</div>
					</div>

					<div id="tab16" class="tab">
						<div>
							<highchart id="perfImpsChart" config="perfImpsChart"></highchart>
						</div>
					</div>

					<div id="tab17" class="tab">
						<div>
							<highchart id="perfClicksChart" config="perfClicksChart"></highchart>
						</div>
					</div>

					<div id="tab18" class="tab">
						<div>
							<highchart id="perfConvsChart" config="perfConvsChart"></highchart>
						</div>
					</div>

					<div id="tab19" class="tab">
						<div>
							<highchart id="perfEcpmChart" config="perfEcpmChart"></highchart>
						</div>
					</div>

					<div id="tab20" class="tab">
						<div>
							<highchart id="perfCtrChart" config="perfCtrChart"></highchart>
						</div>
					</div>

					<div id="tab21" class="tab">
						<div>
							<highchart id="perfCvrChart" config="perfCvrChart"></highchart>
						</div>
					</div>
				</div>
			</div>
		</div>
		<br>
		<br>
		<br>
	</div>
</body>
</html>