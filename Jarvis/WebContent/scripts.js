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