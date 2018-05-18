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
    <style type="text/css">
        html,body { height: 100%; margin: 0px; padding: 0px; background:#333333}
    </style>

</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div style="height: 5%"></div>
<div id="chart1" style="height: 70%; overflow: auto"></div>
<div style="height: 5%"></div>
<div id="chart2" style="height: 100%"></div>
<div style="height: 5%"></div>


<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart_1 = echarts.init(document.getElementById('chart1'),'dark');
    var myChart_2 = echarts.init(document.getElementById('chart2'),'dark');

    myChart_1.on('click', function (params) {
        var nop = params.name;
        window.open('/company/nop/wordCloud?nop=' + nop);
    });

    $.ajax("/json/job_company_nop.json").done(function(data){
        // 使用刚指定的配置项和数据显示图表。
        myChart_1.setOption({
            title: {
                text: '根据公司人数进行划分',
                subtext: '下图显示的是公司人数和招聘数量，公司数量的二维关系',
                x:'center',
                y:'30'
            },
            legend: {},
            tooltip: {},
            xAxis: [
                {
                    type: 'category',
                    axisLabel: {
                        interval:0,
                        rotate: 24
                    }
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    name: '招聘数量'
                },
                {
                    type: 'value',
                    name: '公司数量'
                }
            ],
            dataset: {
                // 提供一份数据。
                source: data.data
            },
            series: [
                {
                    name: '招聘数量',
                    type: 'bar',
                    smooth: true,
                    encode:{
                        x: "company_nop",
                        y: "job_num"

                    }
                },
                {
                    name: '公司数量',
                    type: 'bar',
                    yAxisIndex: 1,
                    smooth: true,
                    encode:{
                        x: "company_nop",
                        y: "company_num"
                    }
                }
            ]
        });
        myChart_2.setOption({
            title: {
                text: '根据公司人数(可点击查看招聘词云)',
                subtext: '图1:不同公司人数对应的热门职位\n图2:热门职位的热度，不同公司人数的平均工资',
                x:'center',
                y:'30'
            },
            legend: {},
            tooltip: {},
            xAxis: [
                {
                    type: 'category',
                    axisLabel: {
                        interval:0,
                        rotate: 90
                    },
                    gridIndex : 0
                },
                {
                    type: 'category',
                    axisLabel: {
                        interval:0,
                        rotate: 24
                    },
                    gridIndex : 1
                }
            ],
            yAxis: [
                {
                    type: 'category',
                    name: '热门职位',
                    gridIndex: 0
                },
                {
                    type: 'value',
                    name: '平均工资',
                    gridIndex: 1,
                    axisLabel: {
                        formatter: '{value} k'
                    }
                },
                {
                    type: 'value',
                    name: '热门职位热度',
                    gridIndex: 1
                }
            ],
            grid: [
                {bottom: '55%'},
                {top: '55%'}
            ],
            dataset: {
                // 提供一份数据。
                source: data.data
            },
            series: [
                {
                    name: '平均工资',
                    type: 'line',

                    yAxisIndex: 1,
                    xAxisIndex : 1,
                    smooth: true,
                    encode:{
                        x: "company_nop",
                        y: "avg_salary"

                    }
                },
                {
                    name: '热门职位热度',
                    type: 'line',
                    yAxisIndex: 2,
                    xAxisIndex : 1,
                    smooth: true,
                    encode:{
                        x: "company_nop",
                        y: "hot_job_num"
                    }
                },
                {
                    name:'热门职位',
                    type: 'scatter',
                    itemStyle: {
                        normal: {
                            opacity: 0.8
                        }
                    },
                    encode:{
                        x : "company_nop" ,
                        y : "hot_job"
                    },
                    label: {
                        normal: {
                            show: true,
                            position: 'inside'
                        }
                    }
                }

            ]
        });
    });

</script>
</body>
</html>