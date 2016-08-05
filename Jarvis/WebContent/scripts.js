function openWindow(id) {
	if(id == 'perf')
		window.location.href = "demand.jsp";
	if(id == 'exchange')
		window.location.href = "exchange.jsp";
	if(id == 'supply')
		window.location.href = "supply.jsp";
	if(id == 'brand')
		window.location.href = "brandDemand.jsp";
	if(id == 'auctions')
		window.location.href = "auctions.jsp";
	if(id == 'pubchurn')
		window.location.href = "pubChurn.jsp";
	if(id == 'o&o')
		window.location.href = "owned.jsp";
	if(id == 'newTags')
		window.location.href = "newTags.jsp";
	if(id == 'tierOpt')
		window.location.href = "tierOpt.jsp";
	if(id == 'viewability')
		window.location.href = "adQualityHome.jsp";
	if(id == 'taco')
		window.location.href = "tacocorp.jsp";
	if(id == 'viewabilityreport')
		window.location.href = "viewability.jsp";
	if(id == 'adqualityreport')
		window.location.href = "adquality.jsp";
}

function drawColumnChart(categories, data, yAxisText, title) {
	var obj = {
			options: {
	            chart: {
	                type: 'column',
	                width: 700,
	                height: 300,
	                backgroundColor: '#F3F3F3'
	            },
	            tooltip: {
	            	
	            },
	            legend: {
	            	enabled: false
	            },
	            xAxis: {
	            	categories: categories
	            },
	            yAxis: {
	                min: 0,
	                title: {
	                    text: yAxisText
	                }
	            },
	            plotOptions: {
	            	column: {
	            		pointPadding: 0.2,
	                    borderWidth: 0,
	                    color: "#3366CC"
	            	}
	            }
	        },
	        series: [{
	            data: data
	        }],
	        title: {
	            text: title
	        },
	        loading: false
	}
	
	return obj;
}

function drawColumnChartWide(categories, data, yAxisText, title) {
	var obj = {
			options: {
	            chart: {
	                type: 'column',
	                width: 1380,
	                height: 300,
	                backgroundColor: '#F3F3F3'
	            },
	            tooltip: {
	            	
	            },
	            legend: {
	            	enabled: false
	            },
	            xAxis: {
	            	categories: categories
	            },
	            yAxis: {
	                min: 0,
	                title: {
	                    text: yAxisText
	                }
	            },
	            plotOptions: {
	            	column: {
	            		pointPadding: 0.2,
	                    borderWidth: 0,
	                    color: "#3366CC",
	                    dataLabels: {
	                        enabled: true
	                    }
	            	}
	            }
	        },
	        series: [{
	            data: data
	        }],
	        title: {
	            text: title
	        },
	        loading: false
	}
	
	return obj;
}

function drawStackedColumnChart(categories, data, yAxisText, title) {
	var obj = {
			options: {
	            chart: {
	                type: 'column',
	                width: 625,
	                height: 300,
	                backgroundColor: '#FFFFFF'
	            },
	            colors: ['#006600','#FF8000'],
	            tooltip: {
	            	
	            },
	            legend: {
	            	enabled: true
	            },
	            xAxis: {
	            	categories: categories
	            },
	            yAxis: {
	                min: 0,
	                title: {
	                    text: yAxisText
	                }
	            },
	            plotOptions: {
	            	column: {
	            		stacking: 'normal',
	            		pointPadding: 0.2,
	                    borderWidth: 0
	            	}
	            }
	        },
	        series: data,
	        title: {
	            text: title
	        },
	        loading: false
	}
	
	return obj;
}

function drawStackedColumnChartWide(categories, data, yAxisText, title) {
	var obj = {
			options: {
	            chart: {
	                type: 'column',
	                width: 1370,
	                height: 550,
	                backgroundColor: '#FFFFFF'
	            },
	            colors: ['#000000','#006600','#00ccff','#ff0000','#33cc33','#ffff00','#993300','#003366','#6600cc','#990099','#00cc00','#cc3300',
	                     '#00ffff','#666633','#669999','#ff00ff','#1a66ff','#ff9933','#66ff33','#ff8566','#663300','#006699','#ffccff','#00ccff','#000000'],
	            tooltip: {
	            	
	            },
	            legend: {
	            	enabled: true
	            },
	            xAxis: {
	            	categories: categories
	            },
	            yAxis: {
	                min: 0,
	                title: {
	                    text: yAxisText
	                },
	                labels: {
	                	enabled: true,
	                	formatter : function() {
	                		if(this.value > 1000000000)
	                			return this.value/1000000000+"B";
	                		else if(this.value > 1000000)
	                			return this.value/1000000+"M";
	                		else if(this.value > 1000)
	                			return this.value/1000+"K";
	                		else
	                			return this.value;
						}
	                }
	            },
	            tooltip: {
	                headerFormat: '<b>{point.x}</b><br/>',
	                pointFormat: '{series.name}: {point.y}<br/>{point.percentage:.1f}%'
	            },
	            plotOptions: {
	            	column: {
	            		stacking: 'normal',
	            		pointPadding: 0.2,
	                    borderWidth: 0
	            	},
	            	series: {
	                    point: {
	                        events: {
	                            click: function() {
	                            	angular.element(document.getElementById('mainDiv')).scope().showData(this.series.name, this.category, title, yAxisText);
	                            }
	                        }
	                    }
	                  }
	            }
	        },
	        series: data,
	        title: {
	            text: title
	        },
	        loading: false
	}
	
	return obj;
}

function drawLineChart(categories, data, yAxisText, title) {
	var obj = {
			options: {
	            chart: {
	                type: 'line',
	                width: 625,
	                height: 300,
	                backgroundColor: '#FFFFFF'
	            },
	            colors: ['#006600','#FF8000'],
	            tooltip: {
	            	
	            },
	            legend: {
	            	enabled: true
	            },
	            xAxis: {
	            	categories: categories
	            },
	            yAxis: {
	                min: 0,
	                title: {
	                    text: yAxisText
	                }
	            }
	        },
	        series: data,
	        title: {
	            text: title
	        },
	        loading: false
	}
	
	return obj;
}

function drawLineChartWide(categories, data, yAxisText, title, tickInterval) {
	var obj = {
			options: {
	            chart: {
	                type: 'line',
	                width: 1380,
	                height: 320,
	                backgroundColor: '#FFFFFF'
	            },
	            colors: ['#006600','#FF8000','#3366CC'],
	            tooltip: {
	            	
	            },
	            legend: {
	            	enabled: true
	            },
	            xAxis: {
	            	categories: categories,
	            	tickInterval: tickInterval
	            },
	            yAxis: {
	                min: 0,
	                title: {
	                    text: yAxisText
	                }
	            }
	        },
	        series: data,
	        title: {
	            text: title
	        },
	        loading: false
	}
	
	return obj;
}

function drawAreaChart(categories, data, yAxisText, title) {
	var obj = {

			options : {
				chart : {
					type : 'area',
					width : 600,
					height : 300,
					backgroundColor : '#F3F3F3'
				},
				tooltip : {
					formatter : function() {
						return categories[this.x] + '<br>' + yAxisText + ': ' + this.y;
					}
				},
				xAxis : {
					labels : {
						enabled : true,
						rotation : 315,
						formatter : function() {
							return categories[this.value];
						},
					},
				},
				legend : {
					enabled : false
				},
				yAxis : {
					title : {
						text : yAxisText
					}
				},
				plotOptions : {
					area : {
						color : "#3366CC"
					}
				}
			},
			series : [ {
				data : data
			} ],
			title : {
				text : title
			},
			loading : false
	}
	
	return obj;
}

function drawPieChartAndroidSDK() {
	var obj = {

			options : {
				chart : {
					type : 'pie',
					width : 1200,
					height : 600,
					backgroundColor : 'white'
				},
				tooltip : {
					pointFormat: '<b>{point.percentage:.2f}%</b>'
				},
				legend : {
					enabled : false
				},
				plotOptions : {
					pie : {
						dataLabels : {
		                    enabled: true,
		                    format: '<b>{point.name}</b>',
		                    style: {
		                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
		                    }
						}
					}
				}
			},
			series : [ {
				name: name,
				data : [{
	                name: '5.0',
	                y: 45693743
	            }, {
	                name: '5.0.1',
	                y: 220739362
	            }, {
	                name: '5.1.0',
	                y: 291704058
	            }, {
	                name: '5.1.1',
	                y: 214578360
	            }, {
	                name: '5.1.2',
	                y: 2733255
	            },  {
	                name: '5.2',
	                y: 397974435
	            }, {
	                name: '5.3',
	                y: 9444222246
	            }, {
	                name: '5.4',
	                y: 2130203251
	            }, {
	                name: '6.0',
	                y: 1111900377
	            }, {
	                name: '6.1',
	                y: 11701894267
	            }, {
	                name: '6.2',
	                y: 98154073
	            },{
	                name: '6.3',
	                y: 60961694,
	                sliced: true,
	                selected: true
	            }]
			} ],
			title : {
				text : 'Android SDK Adoption by Version'
			},
			loading : false
	}
	
	return obj;
}

function drawPieChartiOSSDK() {
	var obj = {

			options : {
				chart : {
					type : 'pie',
					width : 1200,
					height : 600,
					backgroundColor : 'white'
				},
				tooltip : {
					pointFormat: '<b>{point.percentage:.2f}%</b>'
				},
				legend : {
					enabled : false
				},
				plotOptions : {
					pie : {
						dataLabels : {
		                    enabled: true,
		                    format: '<b>{point.name}</b>',
		                    style: {
		                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
		                    }
						}
					}
				}
			},
			series : [ {
				name: name,
				data : [{
	                name: '5.0',
	                y: 7566973
	            }, {
	                name: '5.0.1',
	                y: 17633521
	            }, {
	                name: '5.1.0',
	                y: 15984003
	            }, {
	                name: '5.1.1',
	                y: 182231772
	            }, {
	                name: '5.1.2',
	                y: 5
	            },  {
	                name: '5.2',
	                y: 820738230
	            }, {
	                name: '5.3',
	                y: 191486287
	            }, {
	                name: '5.3.1',
	                y: 10324903
	            }, {
	                name: '5.4',
	                y: 52200955
	            }, {
	                name: '5.4.1',
	                y: 2173729314
	            }, {
	                name: '5.5',
	                y: 386842
	            }, {
	                name: '6.0',
	                y: 456
	            }, {
	                name: '6.0.1',
	                y: 193592480
	            }, {
	                name: '6.1',
	                y: 11282811769
	            }, {
	                name: '6.1.1',
	                y: 151961118
	            }, {
	                name: '6.2',
	                y: 351897628
	            },{
	                name: '6.3',
	                y: 57767,
	                sliced: true,
	                selected: true
	            }]
			} ],
			title : {
				text : 'iOS SDK Adoption by Version'
			},
			loading : false
	}
	
	return obj;
}


function drawPieChartTotalSDK() {
	var obj = {

			options : {
				chart : {
					type : 'pie',
					width : 1200,
					height : 600,
					backgroundColor : 'white'
				},
				tooltip : {
					pointFormat: '<b>{point.percentage:.2f}%</b>'
				},
				legend : {
					enabled : false
				},
				plotOptions : {
					pie : {
						dataLabels : {
		                    enabled: true,
		                    format: '<b>{point.name}</b>',
		                    style: {
		                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
		                    }
						}
					}
				}
			},
			series : [ {
				name: name,
				data : [{
	                name: '5.0',
	                y: 7566973+45693743
	            }, {
	                name: '5.0.1',
	                y: 17633521+220739362
	            }, {
	                name: '5.1.0',
	                y: 15984003+291704058
	            }, {
	                name: '5.1.1',
	                y: 182231772+214578360
	            }, {
	                name: '5.1.2',
	                y: 5+2733255
	            },  {
	                name: '5.2',
	                y: 820738230+397974435
	            }, {
	                name: '5.3',
	                y: 191486287+9444222246
	            }, {
	                name: '5.3.1',
	                y: 10324903
	            }, {
	                name: '5.4',
	                y: 52200955+2130203251
	            }, {
	                name: '5.4.1',
	                y: 2173729314
	            }, {
	                name: '5.5',
	                y: 386842
	            }, {
	                name: '6.0',
	                y: 456+1111900377
	            }, {
	                name: '6.0.1',
	                y: 193592480
	            }, {
	                name: '6.1',
	                y: 11282811769+11701894267
	            }, {
	                name: '6.1.1',
	                y: 151961118
	            }, {
	                name: '6.2',
	                y: 351897628+98154073
	            },{
	                name: '6.3',
	                y: 57767+60961694,
	                sliced: true,
	                selected: true
	            }]
			} ],
			title : {
				text : 'Total SDK Adoption by Version'
			},
			loading : false
	}
	
	return obj;
}

function drawMap(countryReqs, container, dataClass) {

	        var mapData = Highcharts.geojson(Highcharts.maps['custom/world']);

	        $.each(countryReqs, function () {
	            if (this.code === 'UK') {
	                this.code = 'GB';
	            }
	        });

	        $(container).highcharts('Map', {
	            chart : {
	                width : 670,
					height : 400,
					backgroundColor : '#F3F3F3'
	            },

	            title: {
	                text: dataClass + ' by Country'
	            },

	            subtitle : {
	                
	            },

	            legend: {
	                enabled: false
	            },

	            mapNavigation: {
	                enabled: false,
	                buttonOptions: {
	                    verticalAlign: 'bottom'
	                },
	                enableMouseWheelZoom: false
	            },

	            series : [{
	                name: 'Countries',
	                mapData: mapData,
	                nullColor: 'white',
	                enableMouseTracking: false
	            }, {
	                type: 'mapbubble',
	                mapData: mapData,
	                name: dataClass,
	                color: "#3366CC",
	                joinBy: ['iso-a3', 'code'],
	                data: countryReqs,
	                minSize: 4,
	                maxSize: '12%',
	                tooltip: {
	                    pointFormat: '{point.code}: {point.z}'
	                }
	            }]
	        });
}