/**
 * Created by wtw on 2018/1/17.
 */

EchartOption.option = (function(){
    return {
        //饼图
        pieOption: function () {
            return {
                title: {
                    text: '',
                    x: 'center',

                },
                grid: {
                    bottom:5,
                    top:5

                },
                tooltip: {
                    trigger: 'item',
                    //formatter: "{a} <br/>{b}: {c} ({d}%)"
                    formatter: "{a}"
                },
                legend: {
                    orient: 'vertical',
                    data: []
                },
                series: [{
                    name: '',
                    type: 'pie',
                    radius: '',
                    itemStyle: {
                        normal: {
                            color: function (params) {
                                // build a color map as your need.
                                /*var colorList = [
                                 '#C1232B', '#B5C334', '#FCCE10', '#E87C25', '#27727B',
                                 '#FE8463', '#9BCA63', '#FAD860', '#F3A43B', '#60C0DD',
                                 '#D7504B', '#C6E579', '#F4E001', '#F0805A', '#26C0C0'
                                 ];*/
                                var colorList = [
                                    '#F3647C', '#8E6EF5', '#6E9DF5', '#8E9AAC', '#8E9AAC',
                                    '#64BFF3', '#64F3F2', '#64F3A7', '#F3ED64', '#EB9A44',
                                    '#E9744B', '#F364B3', '#F4E001', '#F0805A', '#26C0C0'
                                ];
                                return colorList[params.dataIndex]
                            },

                        }
                    },

                    data: []

                }]


            }

        },
        //日柱状图
        dayBarOption: function () {
            return {

                title: {
                    text: ''

                },
                grid: {
                    left: '3%',
                    right:'3%',

                    bottom: '6%',

                    containLabel: true
                },
                xAxis: {
                    name: '日',
                    nameGap:'8',
                    //  type:'category',
                    axisLabel: {
                        inside: false,
                        textStyle: {
                            color: '#333'
                        }
                    },
                    axisTick: {
                        show: false
                    },
                    axisLine: {
                        show: true
                    }

                },
                yAxis: {
                    name: '单位：KB',
                    axisLine: {
                        show: false
                    },
                    axisTick: {
                        show: false
                    },
                    axisLabel: {
                        textStyle: {
                            color: '#333'
                        }
                    }
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                        type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                dataZoom: [

                    {
                        type: 'inside',
                        filterMode: 'empty',
                        start: 10,
                        end: 90,


                    }
                ],

                series: [

                    {
                        type: 'bar',
                        barMaxWidth:'40',
                        itemStyle: {
                            normal: {
                                color: new echarts.graphic.LinearGradient(
                                    0, 0, 0, 1,
                                    [
                                        {offset: 0, color: '#188df0'},
                                        {offset: 1, color: '#188df0'}

                                    ]
                                )
                            },
                            emphasis: {
                                color: new echarts.graphic.LinearGradient(
                                    0, 0, 0, 1,
                                    [

                                        {offset: 0, color: '#2378f7'},
                                        {offset: 0.7, color: '#2378f7'},
                                    ]
                                )
                            }
                        },
                        data: []
                    }
                ]


            }

        },
        //月柱状图
        monthBarOption: function () {
            return {

                title: {
                    text: ''

                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '6%',
                    containLabel: true
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                        type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                xAxis: {
                    name: '月',
                    nameGap:'8',
                    axisLabel: {
                        inside: false,
                        textStyle: {
                            color: '#333'
                        }
                    },
                    axisTick: {
                        show: false
                    },
                    axisLine: {
                        show: true
                    }

                },
                yAxis: {
                    name: '单位：MB',
                    axisLine: {
                        show: false
                    },
                    axisTick: {
                        show: false
                    },
                    axisLabel: {
                        textStyle: {
                            color: '#333'
                        }
                    }
                },
                series: [

                    {
                        type: 'bar',
                        barMaxWidth:'40',
                        itemStyle: {
                            normal: {
                                color: new echarts.graphic.LinearGradient(
                                    0, 0, 0, 1,
                                    [
                                        /*{offset: 0, color: '#ff5a60'},
                                         {offset: 0.5, color: '#ff0711'},
                                         {offset: 1, color: '#ff1241'}*/
                                        {offset: 0, color: '#188df0'},
                                        {offset: 1, color: '#188df0'}
                                    ]
                                )
                            },
                            emphasis: {
                                color: new echarts.graphic.LinearGradient(
                                    0, 0, 0, 1,
                                    [
                                        /* {offset: 0, color: '#ff0711'},
                                         {offset: 0.7, color: '#ff5a60'},
                                         {offset: 1, color: '#ff0711'}*/
                                        {offset: 0, color: '#2378f7'},
                                        {offset: 0.7, color: '#2378f7'},
                                    ]
                                )
                            }
                        },
                        data: []
                    }
                ]


            }

        },

        //排行柱状图
        rankBarOption: function () {
            return {

                title: {
                    text: ''

                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                        type: 'line'        // 默认为直线，可选为：'line' | 'shadow'
                    },
                    formatter: '{b}'
                },
                grid: {
                    top: 40,
                    left:'10%',
                    bottom: 30
                },
                xAxis: {
                    type: 'value',
                    position: 'top',
                    max:'dataMax',
                    axisTick: {show: false},
                    axisLabel: {show: false},
                    axisLine: {show: false},
                    splitLine: {show: false},

                },
                yAxis: {
                    type: 'category',
                    axisLine: {show: false},


                    axisTick: {show: false},
                    splitLine: {show: false},
                    data: []
                },
                series: [
                    { // For shadow
                        type: 'bar',
                        itemStyle: {
                            normal: {color: 'rgba(0,0,0,0.05)'}
                        },
                        barGap: '-100%',
                        barMaxWidth:'40',
                        //   barWidth :50,//柱图宽度
                        barCategoryGap: '40%',
                        data: [],
                        animation: false
                    },
                    {
                        name: '',
                        type: 'bar',
                        barMaxWidth:'40',
                        //  barWidth :30,//柱图宽度
                        itemStyle: {
                            normal: {
                                color: function (params) {
                                    // build a color map as your need.
                                    /*var colorList = [
                                     '#C1232B', '#B5C334', '#FCCE10', '#E87C25', '#27727B',
                                     '#FE8463', '#9BCA63', '#FAD860', '#F3A43B', '#60C0DD',
                                     '#D7504B', '#C6E579', '#F4E001', '#F0805A', '#26C0C0'
                                     ];*/
                                    var colorList = [
                                        '#F3647C', '#8E6EF5','#CE64F3', '#6E9DF5', '#8E9AAC',
                                        '#64BFF3', '#64F3F2', '#64F3A7', '#F3ED64', '#EB9A44',
                                        '#E9744B', '#F364B3', '#F4E001', '#F0805A', '#26C0C0'
                                    ];
                                    return colorList[params.dataIndex]
                                },

                            }
                        },

                        label: {
                            normal: {
                                show: true,
                                formatter: '{c}M',
                                position: 'right',
                                right: 0
                            }

                        },
                        data: []
                    }
                ]
            }
        }
    }
})();