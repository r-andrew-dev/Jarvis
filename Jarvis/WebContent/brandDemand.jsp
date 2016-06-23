<!DOCTYPE html>
<%@page import="java.text.Format"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="styles.css">
<link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Open+Sans:400,300,700">
<link rel="icon" type="image/png" href="images/logo.png">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>
<script src="http://code.highcharts.com/highcharts.js"></script>
<script src="highcharts-ng.js"></script>
<script src="scripts.js"/></script>
<script src="myApp.js"/></script>
<script src="myCtrl.js"/></script>
<script>
$(document).ready(function(){
   $("#header").load("header.html");
   
   jQuery('.tabs .tab-links a').on('click', function(e)  {
       var currentAttrValue = jQuery(this).attr('href');

       // Show/Hide Tabs
       jQuery('.tabs ' + currentAttrValue).show().siblings().hide();

       // Change/remove current tab to active
       jQuery(this).parent('li').addClass('active').siblings().removeClass('active');

       e.preventDefault();
   });
   
});
</script>
<title>Brand Demand</title>
</head>
<body>
<div id="header"></div>
<div id="blank"></div>
<span class="pageHeading">BRAND DEMAND</span>
<div id="narrowblank"></div>
<div id="mainDiv" ng-app="brandDataApp">
<div id="leftDiv" style="float: left; width: 56%;" ng-controller="brandDataCtrl"> 
<table class="dataTable" style="max-width: 100%;" ng-init="accounts.total = {}; predicate = 'yestSpend'; reverse = true;">
<tr class="headerRow">
<th><a href="#" ng-click="order('accountName')">ACCOUNT</a><span class="sortorder" ng-show="predicate === 'accountName'" ng-class="{reverse:reverse}"></span></th>
<th><a href="#" ng-click="order('platform')">PLATFORM</a><span class="sortorder" ng-show="predicate === 'platform'" ng-class="{reverse:reverse}"></span></th>
<th><a href="#" ng-click="order('yestSpend')">YESTERDAY SPEND</a><span class="sortorder" ng-show="predicate === 'yestSpend'" ng-class="{reverse:reverse}"></span></th>
<th><a href="#" ng-click="order('prevSpend')">DAY BEFORE SPEND</a><span class="sortorder" ng-show="predicate === 'prevSpend'" ng-class="{reverse:reverse}"></span></th>
<th><a href="#" ng-click="order('last7Spend')">LAST 7 DAY AVG. SPEND</a><span class="sortorder" ng-show="predicate === 'last7Spend'" ng-class="{reverse:reverse}"></span></th>
</tr>
<tr ng-repeat="x in accounts | orderBy: predicate : reverse">
<td style="width:230px;"><a href="#" ng-click="addAccountId(x.accountId, x.accountName,  x.platform)">{{ x.accountName }}</a></td>
<td>{{ x.platform }}</td>
<td ng-init="accounts.total.yestTotal = accounts.total.yestTotal + x.yestSpend">{{ x.yestSpend | currency }}</td>
<td ng-init="accounts.total.prevTotal = accounts.total.prevTotal + x.prevSpend">{{ x.prevSpend | currency }}</td>
<td ng-init="accounts.total.lastTotal = accounts.total.lastTotal + x.last7Spend">{{ x.last7Spend | currency }}</td>
</tr>
<tr ng-show="loading" style="font-weight: 800;">
<td>TOTAL</td><td></td>
<td>{{ accounts.total.yestTotal | currency }}</td><td>{{ accounts.total.prevTotal | currency }}</td><td>{{ accounts.total.lastTotal | currency }}</td>
</tr>
</table>
<div id="spinner" ng-hide="loading"><img class="spinningwheel" src="images/spinningwheel.gif"/></div>
</div>
<div id="rightDiv" style="float: left; width: 38%;" ng-controller="brandAccCtrl" ng-hide="hideBrandDiv"> 
Account Name: <span class="emphasize">{{ accountName }}</span><span style="padding-left:80px;font-size:12px;"><!--a href="">View details >>></a--></span><br>
Account ID: <span class="emphasize">{{ accountId }}</span><br>
Account Platform: <span class="emphasize">{{ accountPlatform }}</span><br><br>

<div class="tabs">
    <ul class="tab-links">
        <li class="active"><a href="#tab1">Spend</a></li>
        <li><a href="#tab2">Impressions</a></li>
        <li><a href="#tab3">Clicks</a></li>
        <li><a href="#tab4">Conversions</a></li>
        <li><a href="#tab5">eCPM</a></li>
    </ul>
 
    <div class="tab-content" ng-controller="brandChartController">
        <div id="tab1" class="tab active">
            <div>
		        <highchart id="spendChart" config="spendChart"></highchart>
        	</div>
        </div>
 
        <div id="tab2" class="tab">
            <div>
		        <highchart id="impsChart" config="impsChart"></highchart>
        	</div>
        </div>
 
        <div id="tab3" class="tab">
            <div>
		        <highchart id="clicksChart" config="clicksChart"></highchart>
        	</div>
        </div>
 
        <div id="tab4" class="tab">
            <div>
		        <highchart id="convsChart" config="convsChart"></highchart>
        	</div>
        </div>
        
        <div id="tab5" class="tab">
            <div>
		        <highchart id="ecpmChart" config="ecpmChart"></highchart>
        	</div>
        </div>
    </div>
</div>
</div>
</div>
</body>
</html>