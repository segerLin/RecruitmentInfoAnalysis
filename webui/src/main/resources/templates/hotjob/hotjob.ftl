<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <link rel="icon" href="/img/logo.png">

    <title>招聘信息分析</title>
    <!-- 引入 echarts.js -->
    <script src="/js/echarts.js"></script>
    <script src="/js/vintage.js"></script>
    <script src="/js/jquery.js"></script>
    <script src="/js/utils.js"></script>
    <script src="/js/distpicker.js"></script>
    <style type="text/css">
        html,body { height: 100%; margin: 0px; padding: 0px;background:#333333 }
    </style>

</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div style="height: 5%"></div>
<div id="chart1" style="height: 70%; overflow: auto"></div>
<div style="height: 5%"></div>
<div id="chart2" style="height: 100%"></div>
<div style="height: 2%"></div>
<div data-toggle="distpicker">
    <select data-province="---- 选择省 ----" id="job_province"></select>
    <select data-city="---- 选择市 ----" id="job_city"></select>
</div>
<div style="height: 2%"></div>
<div id="chart3" style="height: 70%; overflow: auto"></div>
<div style="height: 5%"></div>
<div id="chart4" style="height: 100%"></div>
<div style="height: 2%"></div>

<script type="text/javascript">
    $('#job_province').change(function () {
        var province = $('#job_province').val();
        if(province.length == 0)
            return;
        var changeArr = ['北京市','天津市','重庆市','上海市','香港特别行政区','澳门特别行政区'];
        for(var i =0; i < changeArr.length; i++){
            if(province == changeArr[i]){
                province = province.substring(0,2);
                break;
            }
        }
        myChart_3.showLoading();
        myChart_4.showLoading();

        $.get("/hot/province",{"province":province}).done(function(data){
            myChart_3.hideLoading();
            myChart_4.hideLoading();
            myChart_3.setOption({
                title: {
                    text: province  + ' 最热门职业',
                    subtext: '统计出' + province  + ' 热门的职业Top(10)\n\n下图显示的最热门职业及其招聘量',
                    x: "20%"
                },
                legend: {
                    type: 'scroll',
                    orient: 'vertical',
                    x:"65%"
                },
                tooltip: {},
                dataset: {
                    // 提供一份数据。
                    source: data.data
                },
                series: [
                    {
                        name: '招聘数量',
                        type: 'pie',
                        roseType : 'radius',
                        center: ['30%', '60%'],
                        encode:{
                            value: "hot",
                            itemName: "title"
                        }
                    }
                ]
            });

            myChart_4.setOption({
                title: {
                    text: province  + ' 最热门职业',
                    subtext: '统计出' + province  + ' 热门的职业Top(10)\n\n下图显示的最热门职位薪资和平均要求工作经验',
                    x: "20%"
                },
                tooltip : {
                    trigger: 'axis',
                    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                        type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                legend: {
                    // data: ['最低工资', '平均工资','最高工资']
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    top: '10%',
                    containLabel: true
                },
                xAxis:  [
                    {
                        type: 'value',
                        axisLabel: {
                            formatter: '{value} k'
                        },
                        max:150
                    },
                    {
                        type: 'value',
                        axisLabel: {
                            formatter: '{value} 年'
                        },
                        inverse:true,
                        max: 20
                    }
                ],
                yAxis: [
                    {
                        type: 'category'
                    },
                    {
                        type: 'category',
                        show: false,
                    }
                ],

                dataset: {
                    // 提供一份数据。
                    source: data.data
                },
                series: [
                    {
                        name: '最低工资',
                        type: 'bar',
                        stack: '总量',
                        label: {
                            normal: {
                                show: false,
                                position: 'insideRight'
                            }
                        },
                        encode:{
                            x: "minSalary",
                            y: "title"
                        }
                    },
                    {
                        name: '平均工资',
                        type: 'bar',
                        stack: '总量',
                        label: {
                            normal: {
                                show: false,
                                position: 'insideRight'
                            }
                        },
                        encode:{
                            x: "avgSalary",
                            y: "title"
                        }
                    },
                    {
                        name: '最高工资',
                        type: 'bar',
                        stack: '总量',
                        label: {
                            normal: {
                                show: true,
                                position: 'insideRight'
                            }
                        },
                        encode:{
                            x: "maxSalary",
                            y: "title"
                        }
                    },
                    {
                        name: '平均要求经验',
                        type: 'bar',
                        xAxisIndex : 1,
                        yAxisIndex : 1,
                        label: {
                            normal: {
                                show: false,
                                position: 'insideRight'
                            }
                        },
                        encode:{
                            x: "avgExperience",
                            y: "title"
                        }
                    }
                ]
            });
            // 使用刚指定的配置项和数据显示图表。
        });
    });

    $('#job_city').change(function () {
        var city = $('#job_city').val();
        if(city.length == 0)
            return;

        var province = $('#job_province').val();
        city = city.substring(0,2);

        if(province.length == 0)
            return;
        var changeArr = ['北京市','天津市','重庆市','上海市','香港特别行政区','澳门特别行政区'];
        for(var i =0; i < changeArr.length; i++){
            if(province == changeArr[i]){
                province = province.substring(0,2);
                break;
            }
        }
        myChart_3.showLoading();
        myChart_4.showLoading();
        $.get("/hot/city",{"province":province,"city":city}).done(function(data){
            // 使用刚指定的配置项和数据显示图表。
            myChart_3.hideLoading();
            myChart_4.hideLoading();

            myChart_3.setOption({
                title: {
                    text: '最热门职业',
                    subtext: '统计出' + province + '-' + city + ' 热门的职业Top(10)\n\n下图显示的最热门职业及其招聘量',
                    x: "20%"
                },
                legend: {
                    type: 'scroll',
                    orient: 'vertical',
                    x:"65%"
                },
                tooltip: {},
                dataset: {
                    // 提供一份数据。
                    source: data.data
                },
                series: [
                    {
                        name: '招聘数量',
                        type: 'pie',
                        roseType : 'radius',
                        center: ['30%', '60%'],
                        encode:{
                            value: "hot",
                            itemName: "title"
                        }
                    }
                ]
            });

            myChart_4.setOption({
                title: {
                    text: province + '-' + city + ' 最热门职业',
                    subtext: '统计出' + province + '-' + city + ' 热门的职业Top(10)\n\n下图显示的最热门职位薪资和平均要求工作经验',
                    x: "20%"
                },
                tooltip : {
                    trigger: 'axis',
                    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                        type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                legend: {
                    // data: ['最低工资', '平均工资','最高工资']
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    top: '10%',
                    containLabel: true
                },
                xAxis:  [
                    {
                        type: 'value',
                        axisLabel: {
                            formatter: '{value} k'
                        },
                        max:150
                    },
                    {
                        type: 'value',
                        axisLabel: {
                            formatter: '{value} 年'
                        },
                        inverse:true,
                        max: 20
                    }
                ],
                yAxis: [
                    {
                        type: 'category'
                    },
                    {
                        type: 'category',
                        show: false,
                    }
                ],

                dataset: {
                    // 提供一份数据。
                    source: data.data
                },
                series: [
                    {
                        name: '最低工资',
                        type: 'bar',
                        stack: '总量',
                        label: {
                            normal: {
                                show: false,
                                position: 'insideRight'
                            }
                        },
                        encode:{
                            x: "minSalary",
                            y: "title"
                        }
                    },
                    {
                        name: '平均工资',
                        type: 'bar',
                        stack: '总量',
                        label: {
                            normal: {
                                show: false,
                                position: 'insideRight'
                            }
                        },
                        encode:{
                            x: "avgSalary",
                            y: "title"
                        }
                    },
                    {
                        name: '最高工资',
                        type: 'bar',
                        stack: '总量',
                        label: {
                            normal: {
                                show: true,
                                position: 'insideRight'
                            }
                        },
                        encode:{
                            x: "maxSalary",
                            y: "title"
                        }
                    },
                    {
                        name: '平均要求经验',
                        type: 'bar',
                        xAxisIndex : 1,
                        yAxisIndex : 1,
                        label: {
                            normal: {
                                show: false,
                                position: 'insideRight'
                            }
                        },
                        encode:{
                            x: "avgExperience",
                            y: "title"
                        }
                    }
                ]
            });

        });
    })

</script>




<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart_1 = echarts.init(document.getElementById('chart1'),'dark');
    var myChart_2 = echarts.init(document.getElementById('chart2'),'dark');
    var myChart_3 = echarts.init(document.getElementById('chart3'),'dark');
    var myChart_4 = echarts.init(document.getElementById('chart4'),'dark');

    $.ajax("/json/hot_job.json").done(function(data){
        // 使用刚指定的配置项和数据显示图表。
        myChart_1.setOption({
            title: {
                text: '最热门职业',
                subtext: '统计出热门的职业Top(20)\n\n下图显示的最热门职业及其招聘量',
                x: "20%"
            },
            legend: {
                type: 'scroll',
                orient: 'vertical',
                x:"65%"
            },
            tooltip: {},
            dataset: {
                // 提供一份数据。
                source: data.data
            },
            series: [
                {
                    name: '招聘数量',
                    type: 'pie',
                    roseType : 'radius',
                    center: ['30%', '60%'],
                    encode:{
                        value: "hot",
                        itemName: "title"
                    }
                }
            ]
        });

        myChart_2.setOption({
            title: {
                text: '最热门职业',
                subtext: '统计出热门的职业Top(20)\n\n下图显示的最热门职位薪资和平均要求工作经验',
                x: "20%"
            },
            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            legend: {
                // data: ['最低工资', '平均工资','最高工资']
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                top: '10%',
                containLabel: true
            },
            xAxis:  [
                {
                    type: 'value',
                    axisLabel: {
                        formatter: '{value} k'
                    },
                    max:150
                },
                {
                    type: 'value',
                    axisLabel: {
                        formatter: '{value} 年'
                    },
                    inverse:true,
                    max: 20
                }
            ],
            yAxis: [
                {
                    type: 'category'
                },
                {
                    type: 'category',
                    show: false,
                }
            ],

            dataset: {
                // 提供一份数据。
                source: data.data
            },
            series: [
                {
                    name: '最低工资',
                    type: 'bar',
                    stack: '总量',
                    label: {
                        normal: {
                            show: false,
                            position: 'insideRight'
                        }
                    },
                    encode:{
                        x: "min_salary",
                        y: "title"
                    }
                },
                {
                    name: '平均工资',
                    type: 'bar',
                    stack: '总量',
                    label: {
                        normal: {
                            show: false,
                            position: 'insideRight'
                        }
                    },
                    encode:{
                        x: "avg_salary",
                        y: "title"
                    }
                },
                {
                    name: '最高工资',
                    type: 'bar',
                    stack: '总量',
                    label: {
                        normal: {
                            show: true,
                            position: 'insideRight'
                        }
                    },
                    encode:{
                        x: "max_salary",
                        y: "title"
                    }
                },
                {
                    name: '平均要求经验',
                    type: 'bar',
                    xAxisIndex : 1,
                    yAxisIndex : 1,
                    label: {
                        normal: {
                            show: false,
                            position: 'insideRight'
                        }
                    },
                    encode:{
                        x: "avg_experience",
                        y: "title"
                    }
                }
            ]
        });
    });


</script>
</body>
</html>