app.controller("MyController", function($scope, $http) {

	$scope.getDataFromServer = function() {
		$http({
			method : 'GET',
			url : 'javaAngularJS'
		}).success(function(data, status, headers, config) {
			$scope.person = data;
		}).error(function(data, status, headers, config) {

		});

	};
});

perfApp.service('accService', function($rootScope) {
	var accId = '';
	var accName = '';
	var accPlatform = '';

	var addAccount = function(id, name, platform) {
		this.accId = id;
		this.accName = name;
		this.accPlatform = platform;
		$rootScope.$broadcast('account_clicked');
	};

	var getAccountId = function() {
		return this.accId;
	};

	var getAccountName = function() {
		return this.accName;
	};

	var getAccountPlatform = function() {
		return this.accPlatform;
	};

	return {
		addAccount : addAccount,
		getAccountId : getAccountId,
		getAccountName : getAccountName,
		getAccountPlatform : getAccountPlatform
	};
});

brandApp.service('brandAccService', function($rootScope) {
	var accId = '';
	var accName = '';
	var accPlatform = '';

	var addAccount = function(id, name, platform) {
		this.accId = id;
		this.accName = name;
		this.accPlatform = platform;
		$rootScope.$broadcast('brand_account_clicked');
	};

	var getAccountId = function() {
		return this.accId;
	};

	var getAccountName = function() {
		return this.accName;
	};

	var getAccountPlatform = function() {
		return this.accPlatform;
	};

	return {
		addAccount : addAccount,
		getAccountId : getAccountId,
		getAccountName : getAccountName,
		getAccountPlatform : getAccountPlatform
	};
});

perfApp.controller('perfAccCtrl', function($scope, $http, accService) {
	$scope.hideDiv = true;
	$scope.$on('account_clicked', function() {
		$scope.accountId = accService.getAccountId();
		$scope.accountName = accService.getAccountName();
		$scope.accountPlatform = accService.getAccountPlatform();
		$scope.hideDiv = false;
	});
});

perfApp
		.controller(
				'perfDataCtrl',
				function($scope, $http, accService) {
					$http({
						method : "GET",
						url : "perfDemandController"
					})
							.then(
									function mySuccess(response) {
										$scope.accounts = response.data;
										$scope.loading = true;
										$scope.yTotal = 0;
										$scope.pTotal = 0;
										$scope.lTotal = 0;
										$scope.order = function(predicate) {
											$scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse
													: false;
											$scope.predicate = predicate;
										};
										$scope.addAccountId = function(id,
												name, platform) {
											accService.addAccount(id, name,
													platform);
										};
									}, function myError(response) {
										$scope.accounts = response.statusText;
									});

					$scope.getYestTotal = function(accountName) {
						var total = 0;
						if (accountName == null) {
							for (var i = 0; i < $scope.accounts.length; i++) {
								var account = $scope.accounts[i];
								total += account.yestSpend;
							}
						} else {
							for (var i = 0; i < $scope.accounts.length; i++) {
								var account = $scope.accounts[i];
								if (account.accountName.toUpperCase().includes(
										accountName.toUpperCase()))
									total += account.yestSpend;
							}
						}
						return total;
					}

					$scope.getPrevTotal = function(accountName) {
						var total = 0;
						if (accountName == null) {
							for (var i = 0; i < $scope.accounts.length; i++) {
								var account = $scope.accounts[i];
								total += account.prevSpend;
							}
						} else {
							for (var i = 0; i < $scope.accounts.length; i++) {
								var account = $scope.accounts[i];
								if (account.accountName.toUpperCase().includes(
										accountName.toUpperCase()))
									total += account.prevSpend;
							}
						}
						return total;
					}

					$scope.getLastTotal = function(accountName) {
						var total = 0;
						if (accountName == null) {
							for (var i = 0; i < $scope.accounts.length; i++) {
								var account = $scope.accounts[i];
								total += account.last7Spend;
							}
						} else {
							for (var i = 0; i < $scope.accounts.length; i++) {
								var account = $scope.accounts[i];
								if (account.accountName.toUpperCase().includes(
										accountName.toUpperCase()))
									total += account.last7Spend;
							}
						}
						return total;
					}
				});

brandApp
		.controller(
				'brandDataCtrl',
				function($scope, $http, brandAccService) {
					$http({
						method : "GET",
						url : "brandDemandController"
					})
							.then(
									function mySuccess(response) {
										$scope.accounts = response.data;
										$scope.loading = true;
										$scope.yTotal = 0;
										$scope.pTotal = 0;
										$scope.lTotal = 0;
										$scope.order = function(predicate) {
											$scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse
													: false;
											$scope.predicate = predicate;
										};
										$scope.addAccountId = function(id,
												name, platform) {
											brandAccService.addAccount(id,
													name, platform);
										};
									}, function myError(response) {
										$scope.accounts = response.statusText;
									});
				});

brandApp.controller('brandAccCtrl', function($scope, $http, brandAccService) {
	$scope.hideBrandDiv = true;
	$scope.$on('brand_account_clicked', function() {
		$scope.accountId = brandAccService.getAccountId();
		$scope.accountName = brandAccService.getAccountName();
		$scope.accountPlatform = brandAccService.getAccountPlatform();
		$scope.hideBrandDiv = false;
	});
});

brandApp.controller('brandChartController', function($scope, $http,
		brandAccService) {
	$scope.$on('brand_account_clicked', function() {
		var id = brandAccService.getAccountId();
		var platform = brandAccService.getAccountPlatform();
		var data;
		var spend = [];
		var imps = [];
		var clicks = [];
		var convs = [];
		var date = [];
		var ecpm = [];
		$http({
			method : "GET",
			url : "brandTrendsController?id=" + id + "&platform=" + platform
		}).then(function mySuccess(response) {
			data = angular.fromJson(response.data);
			for (var i = 0; i < 7; i++) {
				spend.push(data[i].spend);
				convs.push(data[i].convs);
				imps.push(data[i].impressions);
				clicks.push(data[i].clicks);
				date.push(data[i].date);
				ecpm.push(data[i].spend / data[i].impressions * 1000);
			}
		}, function myError(response) {
			$scope.accounts = response.statusText;
		});

		$scope.spendChart = drawAreaChart(date, spend, 'Spend', '');

		$scope.impsChart = drawAreaChart(date, imps, 'Impressions', '');

		$scope.clicksChart = drawAreaChart(date, clicks, 'Clicks', '');

		$scope.convsChart = drawAreaChart(date, convs, 'Conversions', '');

		$scope.ecpmChart = drawAreaChart(date, ecpm, 'eCPM', '');
	});
});

testApp.service('productService', function($rootScope) {
	var productList = [];

	var addProduct = function(newObj) {
		productList.push(newObj);
		$rootScope.$broadcast('data_shared');
	};

	var getProducts = function() {
		return productList;
	};

	return {
		addProduct : addProduct,
		getProducts : getProducts
	};

});

perfApp.controller('chartController', function($scope, $http, accService) {
	$scope.$on('account_clicked', function() {
		var id = accService.getAccountId();
		var platform = accService.getAccountPlatform();
		var data;
		var spend = [];
		var imps = [];
		var clicks = [];
		var convs = [];
		var date = [];
		var ecpm = [];
		$http({
			method : "GET",
			url : "perfTrendsController?id=" + id + "&platform=" + platform
		}).then(function mySuccess(response) {
			data = angular.fromJson(response.data);
			for (var i = 0; i < 7; i++) {
				spend.push(data[i].spend);
				convs.push(data[i].convs);
				imps.push(data[i].impressions);
				clicks.push(data[i].clicks);
				date.push(data[i].date);
				ecpm.push(data[i].spend / data[i].impressions * 1000);
			}
		}, function myError(response) {
			$scope.accounts = response.statusText;
		});

		$scope.spendChart = drawAreaChart(date, spend, 'Spend', '');

		$scope.impsChart = drawAreaChart(date, imps, 'Impressions', '');

		$scope.clicksChart = drawAreaChart(date, clicks, 'Clicks', '');

		$scope.convsChart = drawAreaChart(date, convs, 'Conversions', '');

		$scope.ecpmChart = drawAreaChart(date, ecpm, 'eCPM', '');
	});
});

exchangeApp
		.controller(
				'exchangeCtrl',
				function($scope, $location, $http) {
					$scope.filterCondition = {
						exchangeId : -1,
					};

					$http({
						method : "GET",
						url : "exchangeController"
					}).then(function mySuccess(response) {
						$scope.data = angular.fromJson(response.data);
					}, function myError(response) {
						$scope.data = response.statusText;
					});

					$scope.ranksExist = false;
					var rawData;
					var exchangeCount;
					var businessData;
					var businessLineCount;
					var networkId;
					$scope.getRanks = function(id) {
						var selectedExchange = document
								.getElementById('exchangeSelect');
						$scope.selectedExchangeName = selectedExchange.options[selectedExchange.selectedIndex].text;
						networkId = selectedExchange.options[selectedExchange.selectedIndex].value;
						var path;
						if (!$scope.ranksExist) {
							if ($location.absUrl().indexOf('localhost') > -1)
								path = 'local';
							else if ($location.absUrl().indexOf('10.172.98.67') > -1)
								path = 'mac';
							else
								path = 'remote';
							document.getElementById('spinner').style.display = "block";
							$http(
									{
										method : "GET",
										url : "exchangeRanksController?id="
												+ id + "&path=" + path
									}).then(function mySuccess(response) {
								$scope.ranks = response.data;
								rawData = angular.fromJson(response.data);
								exchangeCount = Object.keys(rawData).length;
								$scope.ranksExist = true;
								$scope.$broadcast('got_ranks');
							}, function myError(response) {
								$scope.ranks = response.statusText;
							});
						}

						$http({
							method : "GET",
							url : "businessLineController?path=" + path
						}).then(
								function mySuccess(response) {
									$scope.businessLine = response.data;
									businessData = angular
											.fromJson(response.data);
									businessLineCount = Object
											.keys(businessData).length;
									$scope.$broadcast('got_business');
								}, function myError(response) {
									$scope.businessLine = response.statusText;
								});
					}

					$scope
							.$on(
									'got_ranks',
									function() {
										var pieData = [];
										var columnCategories = [];
										var columnSpend = [];
										var columnImpressions = [];
										var columnClicks = [];
										var columnConversions = [];
										var columnEcpm = [];
										var columnCtr = [];
										var columnCvr = [];
										for (var i = 0; i < exchangeCount; i++) {
											var pieObject = {};
											pieObject['name'] = rawData[i].exchangeName;
											columnCategories
													.push(rawData[i].exchangeName);
											pieObject['y'] = rawData[i].costs;
											columnSpend.push(rawData[i].costs);
											columnImpressions
													.push(rawData[i].impressions);
											columnClicks
													.push(rawData[i].clicks);
											columnConversions
													.push(rawData[i].conversions);
											columnEcpm.push(rawData[i].ecpm);
											columnCtr.push(rawData[i].ctr);
											columnCvr.push(rawData[i].cvr);
											pieData.push(pieObject);
										}

										$scope.spendChart = {
											options : {
												chart : {
													type : 'pie',
													width : 600,
													height : 300
												},
												tooltip : {
													pointFormatter : function() {
														return '<br>Last quarter spend: $'
																+ this.y;
													}
												},
												legend : {
													enabled : false
												},
												plotOptions : {
													pie : {
														allowPointSelect : true,
														cursor : 'pointer',
													}
												}
											},
											series : [ {
												name : 'Total Spend',
												data : pieData
											} ],
											title : {
												text : 'Total Spend by Exchange'
											},
											loading : false
										}

										$scope.rankSpendChart = drawColumnChart(
												columnCategories, columnSpend,
												'Spend ($)',
												'Total Spend by Exchange');
										$scope.rankImpsChart = drawColumnChart(
												columnCategories,
												columnImpressions,
												'Impressions',
												'Total Impressions by Exchange');
										$scope.rankClicksChart = drawColumnChart(
												columnCategories, columnClicks,
												'Clicks',
												'Total Clicks by Exchange');
										$scope.rankConvsChart = drawColumnChart(
												columnCategories,
												columnConversions,
												'Conversions',
												'Total Conversions by Exchange');
										$scope.rankEcpmChart = drawColumnChart(
												columnCategories, columnEcpm,
												'eCPM', 'eCPM by Exchange');
										$scope.rankCtrChart = drawColumnChart(
												columnCategories, columnCtr,
												'CTR', 'CTR by Exchange');
										$scope.rankCvrChart = drawColumnChart(
												columnCategories, columnCvr,
												'CVR', 'CVR by Exchange');

									});

					$scope
							.$on(
									'got_business',
									function() {
										var brandTotal = 0;
										var performanceTotal = 0;
										var exchangeBusinessData = [];
										var exchangeName;
										var brandColumnCategories = [];
										var brandColumnSpend = [];
										var brandColumnImpressions = [];
										var brandColumnClicks = [];
										var brandColumnConversions = [];
										var brandColumnEcpm = [];
										var brandColumnCtr = [];
										var brandColumnCvr = [];
										var perfColumnCategories = [];
										var perfColumnSpend = [];
										var perfColumnImpressions = [];
										var perfColumnClicks = [];
										var perfColumnConversions = [];
										var perfColumnEcpm = [];
										var perfColumnCtr = [];
										var perfColumnCvr = [];

										for (var j = 0; j < businessLineCount; j++) {
											if (businessData[j].lineOfBusiness == 'Brand') {
												brandTotal = brandTotal
														+ businessData[j].costs;
												brandColumnCategories
														.push(businessData[j].exchangeName);
												brandColumnSpend
														.push(businessData[j].costs);
												brandColumnImpressions
														.push(businessData[j].impressions);
												brandColumnClicks
														.push(businessData[j].clicks);
												brandColumnConversions
														.push(businessData[j].conversions);
												brandColumnEcpm
														.push(businessData[j].ecpm);
												brandColumnCtr
														.push(businessData[j].ctr);
												brandColumnCvr
														.push(businessData[j].cvr);
											} else {
												performanceTotal = performanceTotal
														+ businessData[j].costs;
												perfColumnCategories
														.push(businessData[j].exchangeName);
												perfColumnSpend
														.push(businessData[j].costs);
												perfColumnImpressions
														.push(businessData[j].impressions);
												perfColumnClicks
														.push(businessData[j].clicks);
												perfColumnConversions
														.push(businessData[j].conversions);
												perfColumnEcpm
														.push(businessData[j].ecpm);
												perfColumnCtr
														.push(businessData[j].ctr);
												perfColumnCvr
														.push(businessData[j].cvr);
											}
											if (businessData[j].exchangeId == networkId) {
												exchangeName = businessData[j].exchangeName;
												if (businessData[j].lineOfBusiness == 'Brand') {
													var exchangeBrandObject = {};
													exchangeBrandObject['name'] = 'Brand';
													exchangeBrandObject['y'] = businessData[j].costs;
													exchangeBusinessData
															.push(exchangeBrandObject);
												} else {
													var exchangePerfObject = {};
													exchangePerfObject['name'] = 'Performance';
													exchangePerfObject['y'] = businessData[j].costs;
													exchangeBusinessData
															.push(exchangePerfObject);
												}
											}
										}

										var businessPieData = [];
										var brandObject = {};
										brandObject['name'] = 'Brand';
										brandObject['y'] = brandTotal;
										businessPieData.push(brandObject);
										var performanceObject = {};
										performanceObject['name'] = 'Performance';
										performanceObject['y'] = performanceTotal;
										businessPieData.push(performanceObject);

										$scope.businessLineChart = {
											options : {
												chart : {
													type : 'pie',
													width : 600,
													height : 300
												},
												tooltip : {
													pointFormatter : function() {
														return '<br>Last quarter spend: $'
																+ this.y;
													}
												},
												legend : {
													enabled : false
												},
												plotOptions : {
													pie : {
														allowPointSelect : true,
														cursor : 'pointer',
													}
												}
											},
											series : [ {
												name : 'Total Spend',
												data : businessPieData
											} ],
											title : {
												text : 'Total Spend by Business Line'
											},
											loading : false
										}

										$scope.exchangeBusinessChart = {
											options : {
												chart : {
													type : 'pie',
													width : 600,
													height : 300
												},
												tooltip : {
													pointFormatter : function() {
														return '<br>Last quarter spend: $'
																+ this.y;
													}
												},
												legend : {
													enabled : false
												},
												plotOptions : {
													pie : {
														allowPointSelect : true,
														cursor : 'pointer',
													}
												}
											},
											series : [ {
												name : 'Total Spend',
												data : exchangeBusinessData
											} ],
											title : {
												text : exchangeName
														+ ' Spend by Business Line'
											},
											loading : false
										}

										$scope.brandSpendChart = drawColumnChart(
												brandColumnCategories,
												brandColumnSpend, 'Spend ($)',
												'Total Brand Spend by Exchange');
										$scope.brandImpsChart = drawColumnChart(
												brandColumnCategories,
												brandColumnImpressions,
												'Impressions',
												'Total Brand Impressions by Exchange');
										$scope.brandClicksChart = drawColumnChart(
												brandColumnCategories,
												brandColumnClicks, 'Clicks',
												'Total Brand Clicks by Exchange');
										$scope.brandConvsChart = drawColumnChart(
												brandColumnCategories,
												brandColumnConversions,
												'Conversions',
												'Total Brand Conversions by Exchange');
										$scope.brandEcpmChart = drawColumnChart(
												brandColumnCategories,
												brandColumnEcpm, 'eCPM',
												'Brand eCPM by Exchange');
										$scope.brandCtrChart = drawColumnChart(
												brandColumnCategories,
												brandColumnCtr, 'CTR',
												'Brand CTR by Exchange');
										$scope.brandCvrChart = drawColumnChart(
												brandColumnCategories,
												brandColumnCvr, 'CVR',
												'Brand CVR by Exchange');

										$scope.perfSpendChart = drawColumnChart(
												perfColumnCategories,
												perfColumnSpend, 'Spend ($)',
												'Total Performance Spend by Exchange');
										$scope.perfImpsChart = drawColumnChart(
												perfColumnCategories,
												perfColumnImpressions,
												'Impressions',
												'Total Performance Impressions by Exchange');
										$scope.perfClicksChart = drawColumnChart(
												perfColumnCategories,
												perfColumnClicks, 'Clicks',
												'Total Performance Clicks by Exchange');
										$scope.perfConvsChart = drawColumnChart(
												perfColumnCategories,
												perfColumnConversions,
												'Conversions',
												'Total Performance Conversions by Exchange');
										$scope.perfEcpmChart = drawColumnChart(
												perfColumnCategories,
												perfColumnEcpm, 'eCPM',
												'Performance eCPM by Exchange');
										$scope.perfCtrChart = drawColumnChart(
												perfColumnCategories,
												perfColumnCtr, 'CTR',
												'Performance CTR by Exchange');
										$scope.perfCvrChart = drawColumnChart(
												perfColumnCategories,
												perfColumnCvr, 'CVR',
												'Performance CVR by Exchange');
									});

				});

exchangeApp.filter('percentage', [ '$filter', function($filter) {
	return function(input, decimals) {
		return $filter('number')(input * 100, decimals) + '%';
	};
} ]);

exchangeApp.filter('rank', [ '$filter', function($filter) {
	var suffixes = [ "th", "st", "nd", "rd" ];
	return function(input) {
		var lastDigit = input % 10;
		if (input > 10 && input < 14)
			lastDigit = lastDigit + 4;
		var suffix = (lastDigit <= 3) ? suffixes[lastDigit] : suffixes[0];
		return input + suffix;
	};
} ]);

supplyApp
		.controller(
				"supplyCtrl",
				function($scope, $http, $uibModal, $log) {
					$scope.showLoading = true;

					$scope.filterCondition = {
						selectedAM : -1,
					};
					$scope.showAccounts = false;
					$scope.status = {
						open : false
					};
					$scope.got_ams = false;

					$http({
						method : "GET",
						url : "publisherAccountController?type=ams"
					}).then(function mySuccess(response) {
						$scope.ams = response.data;
						$scope.got_ams = true;
					}, function myError(response) {
						$scope.ams = response.statusText;
					});

					$scope.getAccounts = function(id) {
						$scope.showLoading = false;
						$scope.showAccounts = false;
						$http(
								{
									method : "GET",
									url : "publisherAccountController?type=accounts&amId="
											+ id
								}).then(function mySuccess(response) {
							$scope.accounts = response.data;
							$scope.showAccounts = true;
							$scope.showLoading = true;
						}, function myError(response) {
							$scope.accounts = response.statusText;
						});
					}

					$scope.getSiteData = function(id, site) {
						$http(
								{
									method : "GET",
									url : "publisherAccountController?type=siteData&siteId="
											+ id
								}).then(function mySuccess(response) {
							site.placements = response.data.placementsList;
							site.bundleId = response.data.bundleId;
							site.os = response.data.os;
						}, function myError(response) {
							site.placements = response.statusText;
						});
					}

					$scope.getPlacementData = function(id, dim, bundleId,
							placement) {
						$http(
								{
									method : "GET",
									url : "publisherAccountController?type=placementData&placementId="
											+ id
											+ "&dim="
											+ dim
											+ "&bundleId="
											+ bundleId
								})
								.then(
										function mySuccess(response) {

											if (!placement.showChart) {
												var data = response.data;
												$scope.rawData = data;
												var placementName = data.placementName;
												var placementId = data.placementId;
												var greenTrends = data.greenTrends;
												var nexTrends = data.nexTrends;
												var categories = [];
												var trendsData = [];
												var ecpmTrendsData = [];
												var greenDataObject = {};
												var greenEcpmDataObject = {};
												greenDataObject['name'] = 'Mydas';
												greenEcpmDataObject['name'] = 'Mydas';
												var greenValues = [];
												var greenEcpmValues = [];
												for (var i = 0; i < Object
														.keys(greenTrends).length; i++) {
													categories
															.push(greenTrends[i].date);
													greenValues
															.push(greenTrends[i].requests);
													greenEcpmValues
															.push(greenTrends[i].spend
																	/ greenTrends[i].impressions
																	* 1000);
												}
												greenDataObject['data'] = greenValues;
												greenEcpmDataObject['data'] = greenEcpmValues;
												trendsData
														.push(greenDataObject);
												ecpmTrendsData
														.push(greenEcpmDataObject);

												var nexDataObject = {};
												var nexEcpmDataObject = {};
												nexDataObject['name'] = 'Nexage';
												nexEcpmDataObject['name'] = 'Nexage';
												var nexValues = [];
												var nexEcpmValues = [];
												for (var i = 0; i < Object
														.keys(nexTrends).length; i++) {
													nexValues
															.push(nexTrends[i].requests);
													nexEcpmValues
															.push(nexTrends[i].spend
																	/ nexTrends[i].impressions
																	* 1000);
												}
												nexDataObject['data'] = nexValues;
												nexEcpmDataObject['data'] = nexEcpmValues;
												trendsData.push(nexDataObject);
												ecpmTrendsData
														.push(nexEcpmDataObject);

												placement.chart = drawStackedColumnChart(
														categories, trendsData,
														'Requests',
														'Requests Last 7 Days');
												placement.ecpmChart = drawLineChart(
														categories,
														ecpmTrendsData, 'eCPM',
														'eCPM Last 7 Days');
												placement.showChart = true;
												
											}
										},
										function myError(response) {
											$scope.siteData = response.statusText;
										});
					}
					
					$scope.showData = function() {

						var modalInstance = $uibModal
								.open({
									animation : $scope.animationsEnabled,
									templateUrl : 'supplyModalContent.html',
									controller : 'ModalInstanceCtrl',
									size : 'lg',
									resolve : {
										data : function() {
											return $scope.rawData;
										}
									}
								});

						modalInstance.result.then(function(selectedItem) {
							$scope.selected = selectedItem;
						}, function() {
							$log.info('Modal dismissed at: ' + new Date());
						});

					};
					
					$scope.exportData = function() {
					        var blob = new Blob([document.getElementById('exportable').innerHTML], {
					            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
					        });
					        saveAs(blob, "data.xls");
				    };
					
					
				});

supplyApp.controller('ModalInstanceCtrl', function($scope,
		$uibModalInstance, data) {

	$scope.showTable = false;
	var rawData = [];
	var greenTrends = data.greenTrends;
	var nexTrends = data.nexTrends;
	
	for (var i = 0; i < Object.keys(greenTrends).length; i++) {
		var dataObject = {};
		dataObject['date'] = greenTrends[i].date;
		dataObject['greenReqs'] = greenTrends[i].requests;
		dataObject['greenEcpm'] = greenTrends[i].spend/greenTrends[i].impressions*1000;
		dataObject['nexReqs'] = nexTrends[i].requests;
		dataObject['nexEcpm'] = nexTrends[i].spend/nexTrends[i].impressions*1000;
		rawData.push(dataObject);
	}
	var obj = {trends:rawData};
	$scope.data = obj;
	$scope.showTable = true;

	$scope.ok = function() {
		$uibModalInstance.close();
	};

	$scope.cancel = function() {
		$uibModalInstance.dismiss('cancel');
	};
	
	$scope.exportData = function() {
        var blob = new Blob([document.getElementById('exportable').innerHTML], {
            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
        });
        saveAs(blob, "data.xls");
};
});

auctionsApp
		.controller(
				"auctionsCtrl",
				function($scope, $http, $sce) {
					$scope.hideLoading = false;
					$http({
						method : "GET",
						url : "auctionsController?type=getSites&dateRange=7"
					}).then(function mySuccess(response) {
						$scope.sites = response.data;
						$scope.hideLoading = true;
					}, function myError(response) {
						$scope.sites = response.statusText;
					});

					$scope.status = {
						isopen : false
					};

					$scope.selectedDateRange = "Last 7 days";

					$scope.order = function(predicate) {
						$scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse
								: false;
						$scope.predicate = predicate;
					};

					$scope.dateRangeClick = function(dateRange) {
						$scope.selectedDateRange = "Last " + dateRange
								+ " days";
						$scope.hideLoading = false;
						$http(
								{
									method : "GET",
									url : "auctionsController?type=getSites&dateRange="
											+ dateRange
								}).then(function mySuccess(response) {
							$scope.sites = response.data;
							$scope.hideLoading = true;
						}, function myError(response) {
							$scope.sites = response.statusText;
						});
					};

					$scope.showWarning = function(margin) {
						warningHtml = "<span data-tooltip='Site has negative margin' data-tooltip-position='top' style='color:red;font-size:12px;'>&#9888;</span>";
						return margin < 0 ? $sce.trustAsHtml(warningHtml) : "";
					};

				});

tagAuctionsApp
		.controller(
				"tagAuctionsCtrl",
				function($scope, $http, $sce) {
					$scope.hideLoading = false;
					$scope
							.$watch(
									"siteId",
									function() {
										$http(
												{
													method : "GET",
													url : "auctionsController?type=getTags&siteId="
															+ $scope.siteId
															+ "&dateRange=7"
												})
												.then(
														function mySuccess(
																response) {
															$scope.tags = response.data;
															var trends = response.data.nexTrends;

															var categories = [];
															var requestsData = [];
															var costsData = [];
															var requestsDataObject = {};
															var auctionsDataObject = {};
															var servedDataObject = {};
															var costsDataObject = {};
															var revDataObject = {};
															requestsDataObject['name'] = 'Requests';
															auctionsDataObject['name'] = 'Auctions';
															servedDataObject['name'] = 'Ads Served';
															costsDataObject['name'] = 'Costs';
															revDataObject['name'] = 'Net Revenue';
															var requestsValues = [];
															var auctionsValues = [];
															var servedValues = [];
															var costsValues = [];
															var revValues = [];
															for (var i = 0; i < Object
																	.keys(trends).length; i++) {
																categories
																		.push(trends[i].date);
																requestsValues
																		.push(trends[i].requests);
																auctionsValues
																		.push(trends[i].auctions);
																servedValues
																		.push(trends[i].impressions);
																costsValues
																		.push(trends[i].costs);
																revValues
																		.push(trends[i].spend);
															}

															requestsDataObject['data'] = requestsValues;
															auctionsDataObject['data'] = auctionsValues;
															servedDataObject['data'] = servedValues;
															costsDataObject['data'] = costsValues;
															revDataObject['data'] = revValues;

															requestsData
																	.push(requestsDataObject);
															requestsData
																	.push(auctionsDataObject);
															requestsData
																	.push(servedDataObject);

															costsData
																	.push(costsDataObject);
															costsData
																	.push(revDataObject);

															$scope.auctionsChart = drawLineChartWide(
																	categories,
																	requestsData,
																	'Values',
																	'Requests vs Auctions vs Ads Served', 48);

															$scope.revChart = drawLineChartWide(
																	categories,
																	costsData,
																	'$',
																	'Net Revenue vs Costs', 48);

															var countryReqs = response.data.countryReqs;
															var carrierReqs = response.data.carrierReqs;
															var countryWins = response.data.countryWins;

															drawMap(
																	countryReqs,
																	'#countryReqsChart',
																	'Requests');
															drawMap(
																	countryWins,
																	'#countryWinsChart',
																	'Wins');

															var carrierCategories = [];
															var carrierReqsData = [];
															for (var i = 0; i < Object
																	.keys(carrierReqs).length; i++) {
																carrierCategories
																		.push(carrierReqs[i].code);
																carrierReqsData
																		.push(carrierReqs[i].z);
															}

															$scope.carrierReqsChart = drawColumnChartWide(
																	carrierCategories,
																	carrierReqsData,
																	'',
																	'Requests by Carrier (Top 10)');

															$scope.hideLoading = true;
														},
														function myError(
																response) {
															$scope.tags = response.statusText;
														});
									});

					$scope.order = function(predicate) {
						$scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse
								: false;
						$scope.predicate = predicate;
					};

					$scope.selectedDateRange = "Last 7 days";

					$scope.status = {
						isopen : false
					};

					$scope.dateRangeClick = function(dateRange) {
						$scope.selectedDateRange = "Last " + dateRange
								+ " days";
						$scope.hideLoading = false;
						$http(
								{
									method : "GET",
									url : "auctionsController?type=getTags&siteId="
											+ $scope.siteId
											+ "&dateRange="
											+ dateRange
								})
								.then(
										function mySuccess(response) {
											$scope.tags = response.data;
											var trends = response.data.nexTrends;

											var categories = [];
											var requestsData = [];
											var costsData = [];
											var requestsDataObject = {};
											var auctionsDataObject = {};
											var servedDataObject = {};
											var costsDataObject = {};
											var revDataObject = {};
											requestsDataObject['name'] = 'Requests';
											auctionsDataObject['name'] = 'Auctions';
											servedDataObject['name'] = 'Ads Served';
											costsDataObject['name'] = 'Costs';
											revDataObject['name'] = 'Net Revenue';
											var requestsValues = [];
											var auctionsValues = [];
											var servedValues = [];
											var costsValues = [];
											var revValues = [];
											for (var i = 0; i < Object
													.keys(trends).length; i++) {
												categories.push(trends[i].date);
												requestsValues
														.push(trends[i].requests);
												auctionsValues
														.push(trends[i].auctions);
												servedValues
														.push(trends[i].impressions);
												costsValues
														.push(trends[i].costs);
												revValues.push(trends[i].spend);
											}

											requestsDataObject['data'] = requestsValues;
											auctionsDataObject['data'] = auctionsValues;
											servedDataObject['data'] = servedValues;
											costsDataObject['data'] = costsValues;
											revDataObject['data'] = revValues;

											requestsData
													.push(requestsDataObject);
											requestsData
													.push(auctionsDataObject);
											requestsData.push(servedDataObject);

											costsData.push(costsDataObject);
											costsData.push(revDataObject);

											$scope.auctionsChart = drawLineChartWide(
													categories, requestsData,
													'Values',
													'Requests vs Auctions vs Ads Served');

											$scope.revChart = drawLineChartWide(
													categories, costsData, '$',
													'Net Revenue vs Costs');

											var countryReqs = response.data.countryReqs;
											var carrierReqs = response.data.carrierReqs;
											var countryWins = response.data.countryWins;

											drawMap(countryReqs,
													'#countryReqsChart',
													'Requests');
											drawMap(countryWins,
													'#countryWinsChart', 'Wins');

											var carrierCategories = [];
											var carrierReqsData = [];
											for (var i = 0; i < Object
													.keys(carrierReqs).length; i++) {
												carrierCategories
														.push(carrierReqs[i].code);
												carrierReqsData
														.push(carrierReqs[i].z);
											}

											$scope.carrierReqsChart = drawColumnChartWide(
													carrierCategories,
													carrierReqsData, '',
													'Requests by Carrier (Top 10)');

											$scope.hideLoading = true;
										}, function myError(response) {
											$scope.tags = response.statusText;
										});

					};

					$scope.showWarning = function(margin) {
						warningHtml = "<span data-tooltip='Tag has negative margin' data-tooltip-position='top' style='color:red;font-size:12px;'>&#9888;</span>";
						return margin < 0 ? $sce.trustAsHtml(warningHtml) : "";
					};

				});

churnApp
		.controller(
				"churnCtrl",
				function($scope, $http, $location, $uibModal, $log) {
					$scope.hideLoading = false;
					$scope.hideNexLoading = false;
					$scope.hideRevLoading = false;
					$scope.hideNexRevLoading = false;
					$scope.animationsEnabled = true;
					
					$scope.myStyle= function(value){
						if(value > 90)
							return {background: 'red'};
						else if(value <= 90 && value > 75)
							return {background: 'orange'};
						else
							return {background: 'green'};
					}

					$scope.showData = function(category, month, title,
							yAxisText) {

						var modalInstance = $uibModal
								.open({
									animation : $scope.animationsEnabled,
									templateUrl : 'myModalContent.html',
									controller : 'ModalInstanceCtrl',
									size : 'lg',
									resolve : {
										category : function() {
											return category;
										},
										month : function() {
											return month;
										},
										type : function() {
											if (title.toLowerCase().indexOf(
													"nexage") > -1) {
												if (yAxisText.toLowerCase()
														.indexOf("requests") > -1)
													return "nexdetailreqs";
												else if (yAxisText
														.toLowerCase().indexOf(
																"revenue") > -1)
													return "nexdetailrev";
											} else if (title.toLowerCase()
													.indexOf("mydas") > -1) {
												if (yAxisText.toLowerCase()
														.indexOf("requests") > -1)
													return "mydasdetailreqs";
												else if (yAxisText
														.toLowerCase().indexOf(
																"revenue") > -1)
													return "mydasdetailrev";

											}
										}
									}
								});

						modalInstance.result.then(function(selectedItem) {
							$scope.selected = selectedItem;
						}, function() {
							$log.info('Modal dismissed at: ' + new Date());
						});

					};

					if ($location.absUrl().indexOf('localhost') > -1)
						path = 'local';
					else if ($location.absUrl().indexOf('10.172.98.67') > -1)
						path = 'mac';
					else
						path = 'remote';

					$http({
						method : "GET",
						url : "pubChurnController?type=mydas&path=" + path
					})
							.then(
									function mySuccess(response) {
										var data = response.data;
										var currYear = new Date().getFullYear();
										var currMonth = new Date().getMonth() + 1;

										var categories = [];
										var trendsData = [];
										var oldDataObject = {};
										var y1m1DataObject = {};
										var y1m2DataObject = {};
										var y1m3DataObject = {};
										var y1m4DataObject = {};
										var y1m5DataObject = {};
										var y1m6DataObject = {};
										var y1m7DataObject = {};
										var y1m8DataObject = {};
										var y1m9DataObject = {};
										var y1m10DataObject = {};
										var y1m11DataObject = {};
										var y1m12DataObject = {};
										var y2m1DataObject = {};
										var y2m2DataObject = {};
										var y2m3DataObject = {};
										var y2m4DataObject = {};
										var y2m5DataObject = {};
										var y2m6DataObject = {};
										var y2m7DataObject = {};
										var y2m8DataObject = {};
										var y2m9DataObject = {};
										var y2m10DataObject = {};
										var y2m11DataObject = {};
										var y2m12DataObject = {};

										oldDataObject['name'] = currYear - 2
												+ ' and before';
										y1m1DataObject['name'] = currYear - 1
												+ '-1';
										y1m2DataObject['name'] = currYear - 1
												+ '-2';
										y1m3DataObject['name'] = currYear - 1
												+ '-3';
										y1m4DataObject['name'] = currYear - 1
												+ '-4';
										y1m5DataObject['name'] = currYear - 1
												+ '-5';
										y1m6DataObject['name'] = currYear - 1
												+ '-6';
										y1m7DataObject['name'] = currYear - 1
												+ '-7';
										y1m8DataObject['name'] = currYear - 1
												+ '-8';
										y1m9DataObject['name'] = currYear - 1
												+ '-9';
										y1m10DataObject['name'] = currYear - 1
												+ '-10';
										y1m11DataObject['name'] = currYear - 1
												+ '-11';
										y1m12DataObject['name'] = currYear - 1
												+ '-12';
										y2m1DataObject['name'] = currYear
												+ '-1';
										y2m2DataObject['name'] = currYear
												+ '-2';
										y2m3DataObject['name'] = currYear
												+ '-3';
										y2m4DataObject['name'] = currYear
												+ '-4';
										y2m5DataObject['name'] = currYear
												+ '-5';
										y2m6DataObject['name'] = currYear
												+ '-6';
										y2m7DataObject['name'] = currYear
												+ '-7';
										y2m8DataObject['name'] = currYear
												+ '-8';
										y2m9DataObject['name'] = currYear
												+ '-9';
										y2m10DataObject['name'] = currYear
												+ '-10';
										y2m11DataObject['name'] = currYear
												+ '-11';
										y2m12DataObject['name'] = currYear
												+ '-12';
										var oldValues = [];
										var y1m1Values = [];
										var y1m2Values = [];
										var y1m3Values = [];
										var y1m4Values = [];
										var y1m5Values = [];
										var y1m6Values = [];
										var y1m7Values = [];
										var y1m8Values = [];
										var y1m9Values = [];
										var y1m10Values = [];
										var y1m11Values = [];
										var y1m12Values = [];
										var y2m1Values = [];
										var y2m2Values = [];
										var y2m3Values = [];
										var y2m4Values = [];
										var y2m5Values = [];
										var y2m6Values = [];
										var y2m7Values = [];
										var y2m8Values = [];
										var y2m9Values = [];
										var y2m10Values = [];
										var y2m11Values = [];
										var y2m12Values = [];
										for (var i = 0; i < Object.keys(data).length; i++) {
											var cat = data[i].year + "-"
											+ data[i].month;
											categories.push(cat);
											oldValues.push(data[i].oldRequests);
											y1m1Values
													.push(data[i].y1m1requests);
											y1m2Values
													.push(data[i].y1m2requests);
											y1m3Values
													.push(data[i].y1m3requests);
											y1m4Values
													.push(data[i].y1m4requests);
											y1m5Values
													.push(data[i].y1m5requests);
											y1m6Values
													.push(data[i].y1m6requests);
											y1m7Values
													.push(data[i].y1m7requests);
											y1m8Values
													.push(data[i].y1m8requests);
											y1m9Values
													.push(data[i].y1m9requests);
											y1m10Values
													.push(data[i].y1m10requests);
											y1m11Values
													.push(data[i].y1m11requests);
											y1m12Values
													.push(data[i].y1m12requests);
											y2m1Values
													.push(data[i].y2m1requests);
											y2m2Values
													.push(data[i].y2m2requests);
											y2m3Values
													.push(data[i].y2m3requests);
											y2m4Values
													.push(data[i].y2m4requests);
											y2m5Values
													.push(data[i].y2m5requests);
											y2m6Values
													.push(data[i].y2m6requests);
											y2m7Values
													.push(data[i].y2m7requests);
											y2m8Values
													.push(data[i].y2m8requests);
											y2m9Values
													.push(data[i].y2m9requests);
											y2m10Values
													.push(data[i].y2m10requests);
											y2m11Values
													.push(data[i].y2m11requests);
											y2m12Values
													.push(data[i].y2m12requests);
										}
										oldDataObject['data'] = oldValues;
										y1m1DataObject['data'] = y1m1Values;
										y1m2DataObject['data'] = y1m2Values;
										y1m3DataObject['data'] = y1m3Values;
										y1m4DataObject['data'] = y1m4Values;
										y1m5DataObject['data'] = y1m5Values;
										y1m6DataObject['data'] = y1m6Values;
										y1m7DataObject['data'] = y1m7Values;
										y1m8DataObject['data'] = y1m8Values;
										y1m9DataObject['data'] = y1m9Values;
										y1m10DataObject['data'] = y1m10Values;
										y1m11DataObject['data'] = y1m11Values;
										y1m12DataObject['data'] = y1m12Values;
										y2m1DataObject['data'] = y2m1Values;
										y2m2DataObject['data'] = y2m2Values;
										y2m3DataObject['data'] = y2m3Values;
										y2m4DataObject['data'] = y2m4Values;
										y2m5DataObject['data'] = y2m5Values;
										y2m6DataObject['data'] = y2m6Values;
										y2m7DataObject['data'] = y2m7Values;
										y2m8DataObject['data'] = y2m8Values;
										y2m9DataObject['data'] = y2m9Values;
										y2m10DataObject['data'] = y2m10Values;
										y2m11DataObject['data'] = y2m11Values;
										y2m12DataObject['data'] = y2m12Values;

										if (currMonth == 12)
											trendsData.push(y2m12DataObject);
										if (currMonth >= 11)
											trendsData.push(y2m11DataObject);
										if (currMonth >= 10)
											trendsData.push(y2m10DataObject);
										if (currMonth >= 9)
											trendsData.push(y2m9DataObject);
										if (currMonth >= 8)
											trendsData.push(y2m8DataObject);
										if (currMonth >= 7)
											trendsData.push(y2m7DataObject);
										if (currMonth >= 6)
											trendsData.push(y2m6DataObject);
										if (currMonth >= 5)
											trendsData.push(y2m5DataObject);
										if (currMonth >= 4)
											trendsData.push(y2m4DataObject);
										if (currMonth >= 3)
											trendsData.push(y2m3DataObject);
										if (currMonth >= 2)
											trendsData.push(y2m2DataObject);
										if (currMonth >= 1)
											trendsData.push(y2m1DataObject);
										trendsData.push(y1m12DataObject);
										trendsData.push(y1m11DataObject);
										trendsData.push(y1m10DataObject);
										trendsData.push(y1m9DataObject);
										trendsData.push(y1m8DataObject);
										trendsData.push(y1m7DataObject);
										trendsData.push(y1m6DataObject);
										trendsData.push(y1m5DataObject);
										trendsData.push(y1m4DataObject);
										trendsData.push(y1m3DataObject);
										trendsData.push(y1m2DataObject);
										trendsData.push(y1m1DataObject);
										trendsData.push(oldDataObject);

										$scope.pubChurnChart = drawStackedColumnChartWide(
												categories, trendsData,
												'Requests',
												'Mydas Publisher Churn');

										$scope.hideLoading = true;
									}, function myError(response) {
										$scope.sites = response.statusText;
									});

					$http({
						method : "GET",
						url : "pubChurnController?type=mydasrev&path=" + path
					})
							.then(
									function mySuccess(response) {
										var data = response.data;
										var currYear = new Date().getFullYear();
										var currMonth = new Date().getMonth() + 1;

										var categories = [];
										var trendsData = [];
										var oldDataObject = {};
										var y1m1DataObject = {};
										var y1m2DataObject = {};
										var y1m3DataObject = {};
										var y1m4DataObject = {};
										var y1m5DataObject = {};
										var y1m6DataObject = {};
										var y1m7DataObject = {};
										var y1m8DataObject = {};
										var y1m9DataObject = {};
										var y1m10DataObject = {};
										var y1m11DataObject = {};
										var y1m12DataObject = {};
										var y2m1DataObject = {};
										var y2m2DataObject = {};
										var y2m3DataObject = {};
										var y2m4DataObject = {};
										var y2m5DataObject = {};
										var y2m6DataObject = {};
										var y2m7DataObject = {};
										var y2m8DataObject = {};
										var y2m9DataObject = {};
										var y2m10DataObject = {};
										var y2m11DataObject = {};
										var y2m12DataObject = {};

										oldDataObject['name'] = currYear - 2
												+ ' and before';
										y1m1DataObject['name'] = currYear - 1
												+ '-1';
										y1m2DataObject['name'] = currYear - 1
												+ '-2';
										y1m3DataObject['name'] = currYear - 1
												+ '-3';
										y1m4DataObject['name'] = currYear - 1
												+ '-4';
										y1m5DataObject['name'] = currYear - 1
												+ '-5';
										y1m6DataObject['name'] = currYear - 1
												+ '-6';
										y1m7DataObject['name'] = currYear - 1
												+ '-7';
										y1m8DataObject['name'] = currYear - 1
												+ '-8';
										y1m9DataObject['name'] = currYear - 1
												+ '-9';
										y1m10DataObject['name'] = currYear - 1
												+ '-10';
										y1m11DataObject['name'] = currYear - 1
												+ '-11';
										y1m12DataObject['name'] = currYear - 1
												+ '-12';
										y2m1DataObject['name'] = currYear
												+ '-1';
										y2m2DataObject['name'] = currYear
												+ '-2';
										y2m3DataObject['name'] = currYear
												+ '-3';
										y2m4DataObject['name'] = currYear
												+ '-4';
										y2m5DataObject['name'] = currYear
												+ '-5';
										y2m6DataObject['name'] = currYear
												+ '-6';
										y2m7DataObject['name'] = currYear
												+ '-7';
										y2m8DataObject['name'] = currYear
												+ '-8';
										y2m9DataObject['name'] = currYear
												+ '-9';
										y2m10DataObject['name'] = currYear
												+ '-10';
										y2m11DataObject['name'] = currYear
												+ '-11';
										y2m12DataObject['name'] = currYear
												+ '-12';
										var oldValues = [];
										var y1m1Values = [];
										var y1m2Values = [];
										var y1m3Values = [];
										var y1m4Values = [];
										var y1m5Values = [];
										var y1m6Values = [];
										var y1m7Values = [];
										var y1m8Values = [];
										var y1m9Values = [];
										var y1m10Values = [];
										var y1m11Values = [];
										var y1m12Values = [];
										var y2m1Values = [];
										var y2m2Values = [];
										var y2m3Values = [];
										var y2m4Values = [];
										var y2m5Values = [];
										var y2m6Values = [];
										var y2m7Values = [];
										var y2m8Values = [];
										var y2m9Values = [];
										var y2m10Values = [];
										var y2m11Values = [];
										var y2m12Values = [];
										

										$scope.mydasRisk = data[Object.keys(data).length-1].risk;

										
										for (var i = 0; i < Object.keys(data).length-1; i++) {
											categories.push(data[i].year + "-"
													+ data[i].month);
											oldValues.push(data[i].oldrevenue);
											y1m1Values
													.push(data[i].y1m1revenue);
											y1m2Values
													.push(data[i].y1m2revenue);
											y1m3Values
													.push(data[i].y1m3revenue);
											y1m4Values
													.push(data[i].y1m4revenue);
											y1m5Values
													.push(data[i].y1m5revenue);
											y1m6Values
													.push(data[i].y1m6revenue);
											y1m7Values
													.push(data[i].y1m7revenue);
											y1m8Values
													.push(data[i].y1m8revenue);
											y1m9Values
													.push(data[i].y1m9revenue);
											y1m10Values
													.push(data[i].y1m10revenue);
											y1m11Values
													.push(data[i].y1m11revenue);
											y1m12Values
													.push(data[i].y1m12revenue);
											y2m1Values
													.push(data[i].y2m1revenue);
											y2m2Values
													.push(data[i].y2m2revenue);
											y2m3Values
													.push(data[i].y2m3revenue);
											y2m4Values
													.push(data[i].y2m4revenue);
											y2m5Values
													.push(data[i].y2m5revenue);
											y2m6Values
													.push(data[i].y2m6revenue);
											y2m7Values
													.push(data[i].y2m7revenue);
											y2m8Values
													.push(data[i].y2m8revenue);
											y2m9Values
													.push(data[i].y2m9revenue);
											y2m10Values
													.push(data[i].y2m10revenue);
											y2m11Values
													.push(data[i].y2m11revenue);
											y2m12Values
													.push(data[i].y2m12revenue);
										}
										oldDataObject['data'] = oldValues;
										y1m1DataObject['data'] = y1m1Values;
										y1m2DataObject['data'] = y1m2Values;
										y1m3DataObject['data'] = y1m3Values;
										y1m4DataObject['data'] = y1m4Values;
										y1m5DataObject['data'] = y1m5Values;
										y1m6DataObject['data'] = y1m6Values;
										y1m7DataObject['data'] = y1m7Values;
										y1m8DataObject['data'] = y1m8Values;
										y1m9DataObject['data'] = y1m9Values;
										y1m10DataObject['data'] = y1m10Values;
										y1m11DataObject['data'] = y1m11Values;
										y1m12DataObject['data'] = y1m12Values;
										y2m1DataObject['data'] = y2m1Values;
										y2m2DataObject['data'] = y2m2Values;
										y2m3DataObject['data'] = y2m3Values;
										y2m4DataObject['data'] = y2m4Values;
										y2m5DataObject['data'] = y2m5Values;
										y2m6DataObject['data'] = y2m6Values;
										y2m7DataObject['data'] = y2m7Values;
										y2m8DataObject['data'] = y2m8Values;
										y2m9DataObject['data'] = y2m9Values;
										y2m10DataObject['data'] = y2m10Values;
										y2m11DataObject['data'] = y2m11Values;
										y2m12DataObject['data'] = y2m12Values;

										if (currMonth == 12)
											trendsData.push(y2m12DataObject);
										if (currMonth >= 11)
											trendsData.push(y2m11DataObject);
										if (currMonth >= 10)
											trendsData.push(y2m10DataObject);
										if (currMonth >= 9)
											trendsData.push(y2m9DataObject);
										if (currMonth >= 8)
											trendsData.push(y2m8DataObject);
										if (currMonth >= 7)
											trendsData.push(y2m7DataObject);
										if (currMonth >= 6)
											trendsData.push(y2m6DataObject);
										if (currMonth >= 5)
											trendsData.push(y2m5DataObject);
										if (currMonth >= 4)
											trendsData.push(y2m4DataObject);
										if (currMonth >= 3)
											trendsData.push(y2m3DataObject);
										if (currMonth >= 2)
											trendsData.push(y2m2DataObject);
										if (currMonth >= 1)
											trendsData.push(y2m1DataObject);
										trendsData.push(y1m12DataObject);
										trendsData.push(y1m11DataObject);
										trendsData.push(y1m10DataObject);
										trendsData.push(y1m9DataObject);
										trendsData.push(y1m8DataObject);
										trendsData.push(y1m7DataObject);
										trendsData.push(y1m6DataObject);
										trendsData.push(y1m5DataObject);
										trendsData.push(y1m4DataObject);
										trendsData.push(y1m3DataObject);
										trendsData.push(y1m2DataObject);
										trendsData.push(y1m1DataObject);
										trendsData.push(oldDataObject);

										$scope.pubChurnRevChart = drawStackedColumnChartWide(
												categories, trendsData,
												'Revenue ($)',
												'Mydas Publisher Churn');

										$scope.hideRevLoading = true;
									}, function myError(response) {
										$scope.sites = response.statusText;
									});

					$http({
						method : "GET",
						url : "pubChurnController?type=nexrev&path=" + path
					})
							.then(
									function mySuccess(response) {
										var data = response.data;
										var currYear = new Date().getFullYear();
										var currMonth = new Date().getMonth() + 1;

										var categories = [];
										var trendsData = [];
										var oldDataObject = {};
										var y1m1DataObject = {};
										var y1m2DataObject = {};
										var y1m3DataObject = {};
										var y1m4DataObject = {};
										var y1m5DataObject = {};
										var y1m6DataObject = {};
										var y1m7DataObject = {};
										var y1m8DataObject = {};
										var y1m9DataObject = {};
										var y1m10DataObject = {};
										var y1m11DataObject = {};
										var y1m12DataObject = {};
										var y2m1DataObject = {};
										var y2m2DataObject = {};
										var y2m3DataObject = {};
										var y2m4DataObject = {};
										var y2m5DataObject = {};
										var y2m6DataObject = {};
										var y2m7DataObject = {};
										var y2m8DataObject = {};
										var y2m9DataObject = {};
										var y2m10DataObject = {};
										var y2m11DataObject = {};
										var y2m12DataObject = {};

										oldDataObject['name'] = currYear - 2
												+ ' and before';
										y1m1DataObject['name'] = currYear - 1
												+ '-1';
										y1m2DataObject['name'] = currYear - 1
												+ '-2';
										y1m3DataObject['name'] = currYear - 1
												+ '-3';
										y1m4DataObject['name'] = currYear - 1
												+ '-4';
										y1m5DataObject['name'] = currYear - 1
												+ '-5';
										y1m6DataObject['name'] = currYear - 1
												+ '-6';
										y1m7DataObject['name'] = currYear - 1
												+ '-7';
										y1m8DataObject['name'] = currYear - 1
												+ '-8';
										y1m9DataObject['name'] = currYear - 1
												+ '-9';
										y1m10DataObject['name'] = currYear - 1
												+ '-10';
										y1m11DataObject['name'] = currYear - 1
												+ '-11';
										y1m12DataObject['name'] = currYear - 1
												+ '-12';
										y2m1DataObject['name'] = currYear
												+ '-1';
										y2m2DataObject['name'] = currYear
												+ '-2';
										y2m3DataObject['name'] = currYear
												+ '-3';
										y2m4DataObject['name'] = currYear
												+ '-4';
										y2m5DataObject['name'] = currYear
												+ '-5';
										y2m6DataObject['name'] = currYear
												+ '-6';
										y2m7DataObject['name'] = currYear
												+ '-7';
										y2m8DataObject['name'] = currYear
												+ '-8';
										y2m9DataObject['name'] = currYear
												+ '-9';
										y2m10DataObject['name'] = currYear
												+ '-10';
										y2m11DataObject['name'] = currYear
												+ '-11';
										y2m12DataObject['name'] = currYear
												+ '-12';
										var oldValues = [];
										var y1m1Values = [];
										var y1m2Values = [];
										var y1m3Values = [];
										var y1m4Values = [];
										var y1m5Values = [];
										var y1m6Values = [];
										var y1m7Values = [];
										var y1m8Values = [];
										var y1m9Values = [];
										var y1m10Values = [];
										var y1m11Values = [];
										var y1m12Values = [];
										var y2m1Values = [];
										var y2m2Values = [];
										var y2m3Values = [];
										var y2m4Values = [];
										var y2m5Values = [];
										var y2m6Values = [];
										var y2m7Values = [];
										var y2m8Values = [];
										var y2m9Values = [];
										var y2m10Values = [];
										var y2m11Values = [];
										var y2m12Values = [];
										
										$scope.nexRisk = data[Object.keys(data).length-1].risk;
										
										for (var i = 0; i < Object.keys(data).length-1; i++) {
											categories.push(data[i].year + "-"
													+ data[i].month);
											oldValues.push(data[i].oldrevenue);
											y1m1Values
													.push(data[i].y1m1revenue);
											y1m2Values
													.push(data[i].y1m2revenue);
											y1m3Values
													.push(data[i].y1m3revenue);
											y1m4Values
													.push(data[i].y1m4revenue);
											y1m5Values
													.push(data[i].y1m5revenue);
											y1m6Values
													.push(data[i].y1m6revenue);
											y1m7Values
													.push(data[i].y1m7revenue);
											y1m8Values
													.push(data[i].y1m8revenue);
											y1m9Values
													.push(data[i].y1m9revenue);
											y1m10Values
													.push(data[i].y1m10revenue);
											y1m11Values
													.push(data[i].y1m11revenue);
											y1m12Values
													.push(data[i].y1m12revenue);
											y2m1Values
													.push(data[i].y2m1revenue);
											y2m2Values
													.push(data[i].y2m2revenue);
											y2m3Values
													.push(data[i].y2m3revenue);
											y2m4Values
													.push(data[i].y2m4revenue);
											y2m5Values
													.push(data[i].y2m5revenue);
											y2m6Values
													.push(data[i].y2m6revenue);
											y2m7Values
													.push(data[i].y2m7revenue);
											y2m8Values
													.push(data[i].y2m8revenue);
											y2m9Values
													.push(data[i].y2m9revenue);
											y2m10Values
													.push(data[i].y2m10revenue);
											y2m11Values
													.push(data[i].y2m11revenue);
											y2m12Values
													.push(data[i].y2m12revenue);
										}
										oldDataObject['data'] = oldValues;
										y1m1DataObject['data'] = y1m1Values;
										y1m2DataObject['data'] = y1m2Values;
										y1m3DataObject['data'] = y1m3Values;
										y1m4DataObject['data'] = y1m4Values;
										y1m5DataObject['data'] = y1m5Values;
										y1m6DataObject['data'] = y1m6Values;
										y1m7DataObject['data'] = y1m7Values;
										y1m8DataObject['data'] = y1m8Values;
										y1m9DataObject['data'] = y1m9Values;
										y1m10DataObject['data'] = y1m10Values;
										y1m11DataObject['data'] = y1m11Values;
										y1m12DataObject['data'] = y1m12Values;
										y2m1DataObject['data'] = y2m1Values;
										y2m2DataObject['data'] = y2m2Values;
										y2m3DataObject['data'] = y2m3Values;
										y2m4DataObject['data'] = y2m4Values;
										y2m5DataObject['data'] = y2m5Values;
										y2m6DataObject['data'] = y2m6Values;
										y2m7DataObject['data'] = y2m7Values;
										y2m8DataObject['data'] = y2m8Values;
										y2m9DataObject['data'] = y2m9Values;
										y2m10DataObject['data'] = y2m10Values;
										y2m11DataObject['data'] = y2m11Values;
										y2m12DataObject['data'] = y2m12Values;

										if (currMonth == 12)
											trendsData.push(y2m12DataObject);
										if (currMonth >= 11)
											trendsData.push(y2m11DataObject);
										if (currMonth >= 10)
											trendsData.push(y2m10DataObject);
										if (currMonth >= 9)
											trendsData.push(y2m9DataObject);
										if (currMonth >= 8)
											trendsData.push(y2m8DataObject);
										if (currMonth >= 7)
											trendsData.push(y2m7DataObject);
										if (currMonth >= 6)
											trendsData.push(y2m6DataObject);
										if (currMonth >= 5)
											trendsData.push(y2m5DataObject);
										if (currMonth >= 4)
											trendsData.push(y2m4DataObject);
										if (currMonth >= 3)
											trendsData.push(y2m3DataObject);
										if (currMonth >= 2)
											trendsData.push(y2m2DataObject);
										if (currMonth >= 1)
											trendsData.push(y2m1DataObject);
										trendsData.push(y1m12DataObject);
										trendsData.push(y1m11DataObject);
										trendsData.push(y1m10DataObject);
										trendsData.push(y1m9DataObject);
										trendsData.push(y1m8DataObject);
										trendsData.push(y1m7DataObject);
										trendsData.push(y1m6DataObject);
										trendsData.push(y1m5DataObject);
										trendsData.push(y1m4DataObject);
										trendsData.push(y1m3DataObject);
										trendsData.push(y1m2DataObject);
										trendsData.push(y1m1DataObject);
										trendsData.push(oldDataObject);

										$scope.nexPubChurnRevChart = drawStackedColumnChartWide(
												categories, trendsData,
												'Revenue ($)',
												'Nexage Publisher Churn');

										$scope.hideNexRevLoading = true;
									}, function myError(response) {
										$scope.sites = response.statusText;
									});

					$http({
						method : "GET",
						url : "pubChurnController?type=nex&path=" + path
					})
							.then(
									function mySuccess(response) {
										var data = response.data;
										var currYear = new Date().getFullYear();
										var currMonth = new Date().getMonth() + 1;

										var categories = [];
										var trendsData = [];
										var oldDataObject = {};
										var y1m1DataObject = {};
										var y1m2DataObject = {};
										var y1m3DataObject = {};
										var y1m4DataObject = {};
										var y1m5DataObject = {};
										var y1m6DataObject = {};
										var y1m7DataObject = {};
										var y1m8DataObject = {};
										var y1m9DataObject = {};
										var y1m10DataObject = {};
										var y1m11DataObject = {};
										var y1m12DataObject = {};
										var y2m1DataObject = {};
										var y2m2DataObject = {};
										var y2m3DataObject = {};
										var y2m4DataObject = {};
										var y2m5DataObject = {};
										var y2m6DataObject = {};
										var y2m7DataObject = {};
										var y2m8DataObject = {};
										var y2m9DataObject = {};
										var y2m10DataObject = {};
										var y2m11DataObject = {};
										var y2m12DataObject = {};

										oldDataObject['name'] = currYear - 2
												+ ' and before';
										y1m1DataObject['name'] = currYear - 1
												+ '-1';
										y1m2DataObject['name'] = currYear - 1
												+ '-2';
										y1m3DataObject['name'] = currYear - 1
												+ '-3';
										y1m4DataObject['name'] = currYear - 1
												+ '-4';
										y1m5DataObject['name'] = currYear - 1
												+ '-5';
										y1m6DataObject['name'] = currYear - 1
												+ '-6';
										y1m7DataObject['name'] = currYear - 1
												+ '-7';
										y1m8DataObject['name'] = currYear - 1
												+ '-8';
										y1m9DataObject['name'] = currYear - 1
												+ '-9';
										y1m10DataObject['name'] = currYear - 1
												+ '-10';
										y1m11DataObject['name'] = currYear - 1
												+ '-11';
										y1m12DataObject['name'] = currYear - 1
												+ '-12';
										y2m1DataObject['name'] = currYear
												+ '-1';
										y2m2DataObject['name'] = currYear
												+ '-2';
										y2m3DataObject['name'] = currYear
												+ '-3';
										y2m4DataObject['name'] = currYear
												+ '-4';
										y2m5DataObject['name'] = currYear
												+ '-5';
										y2m6DataObject['name'] = currYear
												+ '-6';
										y2m7DataObject['name'] = currYear
												+ '-7';
										y2m8DataObject['name'] = currYear
												+ '-8';
										y2m9DataObject['name'] = currYear
												+ '-9';
										y2m10DataObject['name'] = currYear
												+ '-10';
										y2m11DataObject['name'] = currYear
												+ '-11';
										y2m12DataObject['name'] = currYear
												+ '-12';
										var oldValues = [];
										var y1m1Values = [];
										var y1m2Values = [];
										var y1m3Values = [];
										var y1m4Values = [];
										var y1m5Values = [];
										var y1m6Values = [];
										var y1m7Values = [];
										var y1m8Values = [];
										var y1m9Values = [];
										var y1m10Values = [];
										var y1m11Values = [];
										var y1m12Values = [];
										var y2m1Values = [];
										var y2m2Values = [];
										var y2m3Values = [];
										var y2m4Values = [];
										var y2m5Values = [];
										var y2m6Values = [];
										var y2m7Values = [];
										var y2m8Values = [];
										var y2m9Values = [];
										var y2m10Values = [];
										var y2m11Values = [];
										var y2m12Values = [];
										for (var i = 0; i < Object.keys(data).length; i++) {
											categories.push(data[i].year + "-"
													+ data[i].month);
											oldValues.push(data[i].oldRequests);
											y1m1Values
													.push(data[i].y1m1requests);
											y1m2Values
													.push(data[i].y1m2requests);
											y1m3Values
													.push(data[i].y1m3requests);
											y1m4Values
													.push(data[i].y1m4requests);
											y1m5Values
													.push(data[i].y1m5requests);
											y1m6Values
													.push(data[i].y1m6requests);
											y1m7Values
													.push(data[i].y1m7requests);
											y1m8Values
													.push(data[i].y1m8requests);
											y1m9Values
													.push(data[i].y1m9requests);
											y1m10Values
													.push(data[i].y1m10requests);
											y1m11Values
													.push(data[i].y1m11requests);
											y1m12Values
													.push(data[i].y1m12requests);
											y2m1Values
													.push(data[i].y2m1requests);
											y2m2Values
													.push(data[i].y2m2requests);
											y2m3Values
													.push(data[i].y2m3requests);
											y2m4Values
													.push(data[i].y2m4requests);
											y2m5Values
													.push(data[i].y2m5requests);
											y2m6Values
													.push(data[i].y2m6requests);
											y2m7Values
													.push(data[i].y2m7requests);
											y2m8Values
													.push(data[i].y2m8requests);
											y2m9Values
													.push(data[i].y2m9requests);
											y2m10Values
													.push(data[i].y2m10requests);
											y2m11Values
													.push(data[i].y2m11requests);
											y2m12Values
													.push(data[i].y2m12requests);
										}
										oldDataObject['data'] = oldValues;
										y1m1DataObject['data'] = y1m1Values;
										y1m2DataObject['data'] = y1m2Values;
										y1m3DataObject['data'] = y1m3Values;
										y1m4DataObject['data'] = y1m4Values;
										y1m5DataObject['data'] = y1m5Values;
										y1m6DataObject['data'] = y1m6Values;
										y1m7DataObject['data'] = y1m7Values;
										y1m8DataObject['data'] = y1m8Values;
										y1m9DataObject['data'] = y1m9Values;
										y1m10DataObject['data'] = y1m10Values;
										y1m11DataObject['data'] = y1m11Values;
										y1m12DataObject['data'] = y1m12Values;
										y2m1DataObject['data'] = y2m1Values;
										y2m2DataObject['data'] = y2m2Values;
										y2m3DataObject['data'] = y2m3Values;
										y2m4DataObject['data'] = y2m4Values;
										y2m5DataObject['data'] = y2m5Values;
										y2m6DataObject['data'] = y2m6Values;
										y2m7DataObject['data'] = y2m7Values;
										y2m8DataObject['data'] = y2m8Values;
										y2m9DataObject['data'] = y2m9Values;
										y2m10DataObject['data'] = y2m10Values;
										y2m11DataObject['data'] = y2m11Values;
										y2m12DataObject['data'] = y2m12Values;

										if (currMonth == 12)
											trendsData.push(y2m12DataObject);
										if (currMonth >= 11)
											trendsData.push(y2m11DataObject);
										if (currMonth >= 10)
											trendsData.push(y2m10DataObject);
										if (currMonth >= 9)
											trendsData.push(y2m9DataObject);
										if (currMonth >= 8)
											trendsData.push(y2m8DataObject);
										if (currMonth >= 7)
											trendsData.push(y2m7DataObject);
										if (currMonth >= 6)
											trendsData.push(y2m6DataObject);
										if (currMonth >= 5)
											trendsData.push(y2m5DataObject);
										if (currMonth >= 4)
											trendsData.push(y2m4DataObject);
										if (currMonth >= 3)
											trendsData.push(y2m3DataObject);
										if (currMonth >= 2)
											trendsData.push(y2m2DataObject);
										if (currMonth >= 1)
											trendsData.push(y2m1DataObject);
										trendsData.push(y1m12DataObject);
										trendsData.push(y1m11DataObject);
										trendsData.push(y1m10DataObject);
										trendsData.push(y1m9DataObject);
										trendsData.push(y1m8DataObject);
										trendsData.push(y1m7DataObject);
										trendsData.push(y1m6DataObject);
										trendsData.push(y1m5DataObject);
										trendsData.push(y1m4DataObject);
										trendsData.push(y1m3DataObject);
										trendsData.push(y1m2DataObject);
										trendsData.push(y1m1DataObject);
										trendsData.push(oldDataObject);

										$scope.nexPubChurnChart = drawStackedColumnChartWide(
												categories, trendsData,
												'Requests',
												'Nexage Publisher Churn');

										$scope.hideNexLoading = true;
									}, function myError(response) {
										$scope.sites = response.statusText;
									});
				});

churnApp.controller('ModalInstanceCtrl', function($scope, $http,
		$uibModalInstance, category, month, type) {

	$scope.category = category;
	$scope.month = month;
	$scope.type = type;
	$scope.showTable = false;
	if(type.indexOf("reqs") > -1)
		$scope.reqs = true;
	else
		$scope.reqs = false;

	$http(
			{
				method : "GET",
				url : "pubChurnController?type=" + type + "&path=" + path
						+ "&category=" + category + "&month=" + month
			}).then(function mySuccess(response) {
		$scope.data = response.data;
		$scope.showTable = true;
	}, function myError(response) {
		$scope.sites = response.statusText;
	});

	$scope.ok = function() {
		$uibModalInstance.close();
	};

	$scope.cancel = function() {
		$uibModalInstance.dismiss('cancel');
	};
});

ooApp.controller('ooCtrl', function($scope, $http) {
	$scope.showTable = false;
	$scope.type = 'Current Month'
	
	$http({
		method : "GET",
		url : "ooController?type=mtd"
	}).then(function mySuccess(response) {
		$scope.data = response.data;
		$scope.showTable = true;
	}, function myError(response) {
		$scope.sites = response.statusText;
	});
	
	$scope.getLastMonth = function() {
		$scope.showTable = false;
		$http({
			method : "GET",
			url : "ooController?type=lmtd"
		}).then(function mySuccess(response) {
			$scope.type = 'Previous Month'
			$scope.data = response.data;
			$scope.showTable = true;
		}, function myError(response) {
			$scope.sites = response.statusText;
		});
	};
});

ooApp.filter('percentage', [ '$filter', function($filter) {
	return function(input, decimals) {
		return $filter('number')(input * 100, decimals) + '%';
	};
} ]);

ooSiteApp.config(function($locationProvider) {
	$locationProvider.html5Mode(true);
});

ooSiteApp.controller('ooSiteCtrl', function($scope, $http, $location) {
	$scope.showSiteTable = false;
	var id = $location.search().siteId;
	
	$scope.showTags = function() {
		window.location.href = 'ownedTag.jsp?siteId='+id;
	};
	
	$http({
		method : "GET",
		url : "ooController?type=site&id=" + id
	}).then(function mySuccess(response) {
		$scope.sitedata = response.data;
		$scope.showSiteTable = true;
	}, function myError(response) {
		$scope.sites = response.statusText;
	});
});

ooSiteApp.filter('percentage', [ '$filter', function($filter) {
	return function(input, decimals) {
		return $filter('number')(input * 100, decimals) + '%';
	};
} ]);

ooTagApp.config(function($locationProvider) {
	$locationProvider.html5Mode({
		  enabled: true,
		  requireBase: false
		});
});

ooTagApp.controller('ooTagCtrl', function($scope, $http, $location) {
	$scope.showTable = false;
	var id = $location.search().siteId;
	
	$scope.site = id;
	
	$scope.showTagDaily = function(tagId) {
		window.location.href = 'ownedTagDaily.jsp?tagId='+tagId;
	};
	
	$http({
		method : "GET",
		url : "ooController?type=tagmtd&id="+id
	}).then(function mySuccess(response) {
		$scope.data = response.data;
		$scope.showTable = true;
	}, function myError(response) {
		$scope.sites = response.statusText;
	});
});

ooTagApp.filter('percentage', [ '$filter', function($filter) {
	return function(input, decimals) {
		return $filter('number')(input * 100, decimals) + '%';
	};
} ]);

ooTagDailyApp.config(function($locationProvider) {
	$locationProvider.html5Mode(true);
});

ooTagDailyApp.controller('ooTagDailyCtrl', function($scope, $http, $location) {

	$scope.showSiteTable = false;
	var id = $location.search().tagId;

	$http({
		method : "GET",
		url : "ooController?type=tagdaily&id=" + id
	}).then(function mySuccess(response) {
		$scope.sitedata = response.data;
		$scope.showSiteTable = true;
	}, function myError(response) {
		$scope.sites = response.statusText;
	});
});

ooTagDailyApp.filter('percentage', [ '$filter', function($filter) {
	return function(input, decimals) {
		return $filter('number')(input * 100, decimals) + '%';
	};
} ]);

newTagsApp.controller('newTagsCtrl', function($scope, $http) {

	$scope.showTable = false;

	$http({
		method : "GET",
		url : "newTagsController"
	}).then(function mySuccess(response) {
		$scope.tagdata = response.data;
		$scope.showTable = true;
	}, function myError(response) {
		$scope.sites = response.statusText;
	});
	
	$scope.order = function(predicate) {
		$scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse
				: false;
		$scope.predicate = predicate;
	};
});

tierOptApp.controller('tierOptCtrl', function($scope, $http) {

	$scope.showData = false;

	$http({
		method : "GET",
		url : "tierOptController"
	}).then(function mySuccess(response) {
		$scope.sites = response.data;
		$scope.showData = true;
	}, function myError(response) {
		$scope.sites = response.statusText;
	});
	
	$scope.order = function(predicate) {
		$scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse
				: false;
		$scope.predicate = predicate;
	};
});

viewabilityApp.controller('viewabilityCtrl', function($scope, $http, $location) {

	$scope.showData = false;
	
	if ($location.absUrl().indexOf('localhost') > -1)
		path = 'local';
	else if ($location.absUrl().indexOf('10.172.98.67') > -1)
		path = 'mac';
	else
		path = 'remote';

	$http({
		method : "GET",
		url : "viewabilityController?path="+path
	}).then(function mySuccess(response) {
		$scope.data = response.data;
		$scope.showData = true;
	}, function myError(response) {
		$scope.sites = response.statusText;
	});
	
	$scope.exportData = function(type) {
		if(type == 'IASexch') {
	        var blob = new Blob([document.getElementById('exportable1').innerHTML], {
	            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
	        });
	        saveAs(blob, "IAS_Exchange.xls");
		} else if(type == 'MOATexch') {
			var blob = new Blob([document.getElementById('exportable2').innerHTML], {
	            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
	        });
	        saveAs(blob, "MOAT_Exchange.xls");
		} else if(type == 'IASNet') {
			var blob = new Blob([document.getElementById('exportable3').innerHTML], {
	            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
	        });
	        saveAs(blob, "IAS_Network.xls");
		} else if(type == 'MOATnet') {
			var blob = new Blob([document.getElementById('exportable4').innerHTML], {
	            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
	        });
	        saveAs(blob, "MOAT_Network.xls");
		}
    };
	
	$scope.order = function(predicate) {
		$scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse
				: false;
		$scope.predicate = predicate;
	};
});

viewabilityApp.filter('percentage', [ '$filter', function($filter) {
	return function(input, decimals) {
		return $filter('number')(input * 100, decimals) + '%';
	};
} ]);

sdkApp.controller('sdkCtrl', function($scope, $http, $location) {

	$scope.showData = false;
	
	if ($location.absUrl().indexOf('localhost') > -1)
		path = 'local';
	else if ($location.absUrl().indexOf('10.172.98.67') > -1)
		path = 'mac';
	else
		path = 'remote';

	$http({
		method : "GET",
		url : "sdkController?path="+path
	}).then(function mySuccess(response) {
		var data = response.data;
		
		var categories = [];
		var trendsData = [];

		var ios6DataObject = {};
		var android6DataObject = {};
		var ios5DataObject = {};
		var android5DataObject = {};
		var below4_6DataObject = {};
		var jsDataObject = {};
		var win_s2sDataObject = {};

		ios6DataObject['name'] = 'iOS 6.0';
		android6DataObject['name'] = 'Android 6.0';
		ios5DataObject['name'] = 'iOS 5.0';
		android5DataObject['name'] = 'Android 5.0';
		below4_6DataObject['name'] = '4.6 and below';
		jsDataObject['name'] = 'JS Tag';
		win_s2sDataObject['name'] = 'Windows and S2S';

		var ios6Values = [];
		var android6Values = [];
		var ios5Values = [];
		var android5Values = [];
		var below4_6Values = [];
		var jsValues = [];
		var win_s2sValues = [];

		for (var i = 0; i < Object
				.keys(data).length; i++) {
			categories
					.push(data[i].month);
			ios6Values
					.push(data[i].ios6);
			android6Values
					.push(data[i].android6);
			ios5Values
					.push(data[i].ios5);
			android5Values
					.push(data[i].android5);
			below4_6Values
					.push(data[i].below4_6);
			jsValues
					.push(data[i].js);
			win_s2sValues
					.push(data[i].win_s2s);

		}
		ios6DataObject['data'] = ios6Values;
		android6DataObject['data'] = android6Values;
		ios5DataObject['data'] = ios5Values;
		android5DataObject['data'] = android5Values;
		below4_6DataObject['data'] = below4_6Values;
		jsDataObject['data'] = jsValues;
		win_s2sDataObject['data'] = win_s2sValues;

		trendsData
				.push(ios6DataObject);
		trendsData
				.push(android6DataObject);
		trendsData
				.push(ios5DataObject);
		trendsData
				.push(android5DataObject);
		trendsData
				.push(below4_6DataObject);
		trendsData
				.push(jsDataObject);
		trendsData
				.push(win_s2sDataObject);

		$scope.sdkChart = drawStackedColumnChartWide(
				categories, trendsData,
				'Adoption Rate',
				'SDK Adoption');
		$scope.showData = true;
		
	}, function myError(response) {
		$scope.sites = response.statusText;
	});

});


sdkApp.controller('nativeCtrl', function($scope, $http) {

	$scope.showNativeData = false;

	$http({
		method : "GET",
		url : "nativeController"
	}).then(function mySuccess(response) {
		var trends = response.data;
		
		var categories = [];
		var data = [];
		var revdata = [];

		var requestsDataObject = {};
		var revenueDataObject = {};

		requestsDataObject['name'] = 'Inbound Requests';
		revenueDataObject['name'] = 'Revenue';

		var requestsValues = [];
		var revenueValues = [];
		
		for (var i = 0; i < Object
				.keys(trends).length; i++) {
			categories
					.push(trends[i].date);
			requestsValues
					.push(trends[i].requests);
			revenueValues
					.push(trends[i].spend);
			
		}

		requestsDataObject['data'] = requestsValues;
		revenueDataObject['data'] = revenueValues;


		data.push(requestsDataObject);
		revdata.push(revenueDataObject);


		$scope.nativeReqsChart = drawLineChartWide(
				categories,
				data,
				'Requests',
				'Daily Inbound Requests', 1);
		
		$scope.nativeRevChart = drawLineChartWide(
				categories,
				revdata,
				'Revenue ($)',
				'Daily Revenue', 0);
		
		$scope.showNativeData = true;
		
	}, function myError(response) {
		$scope.sites = response.statusText;
	});

});

adqualityApp.controller('adqualityCtrl', function($scope, $http) {

	$scope.showData = false;
	
	$http({
		method : "GET",
		url : "adQualityController?type=bidders"
	}).then(function mySuccess(response) {
		$scope.data = response.data;
		//$scope.showData = true;
	}, function myError(response) {
		$scope.sites = response.statusText;
	});
	
	$http({
		method : "GET",
		url : "adQualityController?type=daily"
	}).then(function mySuccess(response) {
		var trends = response.data;
		
		var categories = [];
		var data = [];

		var verifiedDataObject = {};
		var unverifiedDataObject = {};

		verifiedDataObject['name'] = 'Verified';
		unverifiedDataObject['name'] = 'Unverifiable';

		var verifiedValues = [];
		var unverifiedValues = [];
		
		for (var i = 0; i < Object
				.keys(trends).length; i++) {
			categories
					.push(trends[i].date);
			verifiedValues
					.push(trends[i].verified);
			unverifiedValues
					.push(trends[i].unverified);
			
		}

		verifiedDataObject['data'] = verifiedValues;
		unverifiedDataObject['data'] = unverifiedValues;


		data.push(verifiedDataObject);
		data.push(unverifiedDataObject);


		$scope.theoremChart = drawLineChartWide(
				categories,
				data,
				'Values',
				'Daily Verified vs Unverifiable', 1);
		
		$scope.showData = true;
		
	}, function myError(response) {
		$scope.sites = response.statusText;
	});
	
	$scope.order = function(predicate) {
		$scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse
				: false;
		$scope.predicate = predicate;
	};
});

adqualityApp.filter('percentage', [ '$filter', function($filter) {
	return function(input, decimals) {
		return $filter('number')(input * 100, decimals) + '%';
	};
} ]);

adqualityBidderApp.config(function($locationProvider) {
	$locationProvider.html5Mode({
			  enabled: true,
			  requireBase: false
	});
});

adqualityBidderApp.controller('adqualityBidderCtrl', function($scope, $http, $location) {

	$scope.showData = false;
	
	$scope.bidderName = $location.search().bidder; 
	
	$http({
		method : "GET",
		url : "adQualityController?type=bidder&bidder="+$scope.bidderName
	}).then(function mySuccess(response) {
		$scope.data = response.data;
		var trends = $scope.data;
		
		var categories = [];
		var data = [];

		var verifiedDataObject = {};
		var unverifiedDataObject = {};

		verifiedDataObject['name'] = 'Verified';
		unverifiedDataObject['name'] = 'Unverifiable';

		var verifiedValues = [];
		var unverifiedValues = [];
		
		for (var i = 0; i < Object
				.keys(trends).length; i++) {
			categories
					.push(trends[i].date);
			verifiedValues
					.push(trends[i].verified);
			unverifiedValues
					.push(trends[i].unverified);
			
		}

		verifiedDataObject['data'] = verifiedValues;
		unverifiedDataObject['data'] = unverifiedValues;


		data.push(verifiedDataObject);
		data.push(unverifiedDataObject);


		$scope.theoremChart = drawLineChartWide(
				categories,
				data,
				'Values',
				'Daily Verified vs Unverifiable', 0);

		$scope.showData = true;
	}, function myError(response) {
		$scope.sites = response.statusText;
	});
	
	$scope.order = function(predicate) {
		$scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse
				: false;
		$scope.predicate = predicate;
	};
});



