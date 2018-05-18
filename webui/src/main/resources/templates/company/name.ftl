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
<div id="chart1" style="height: 70%; overflow: auto"></div>
<div style="height: 5%"></div>
<div id="chart2" style="height: 70%"></div>
<div style="height: 5%"></div>
<div id="chart3" style="height: 40%"></div>
<div style="height: 5%"></div>
<div id="chart4" style="height: 70%"></div>
<div style="height: 5%"></div>



<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart_1 = echarts.init(document.getElementById('chart1'),'dark');
    var myChart_2 = echarts.init(document.getElementById('chart2'),'dark');
    var myChart_3 = echarts.init(document.getElementById('chart3'),'dark');
    var myChart_4 = echarts.init(document.getElementById('chart4'),'dark');


    myChart_1.on('click', function (params) {
        var name = params.name;
        window.open('/company/name/wordCloud?name=' + name);
    });

    $.ajax("/json/job_company_name.json").done(function(data){
        // 使用刚指定的配置项和数据显示图表。
        myChart_1.setOption({
            title: {
                text: '根据公司进行划分(可点击查看招聘词云)',
                subtext: '根据统计，共有77754家公司\n提取出最热门的种类TOP100(招聘数量最多)\n下图显示的是Top100公司和招聘数量，招聘职位类型数的二维关系',
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
                        rotate: 90,
                        inside:true
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
                    name: '职位类型数'
                }
            ],
            dataZoom: [
                {   // 这个dataZoom组件，默认控制x轴。
                    type: 'slider', // 这个 dataZoom 组件是 slider 型 dataZoom 组件
                    start: 0,      // 左边在 10% 的位置。
                    end: 10         // 右边在 60% 的位置。
                }
            ],
            dataset: {
                // 提供一份数据。
                source: data.data
            },
            series: [
                {
                    name: '招聘数量',
                    type: 'line',
                    smooth: true,
                    encode:{
                        x: "company_name",
                        y: "job_num"

                    }
                },
                {
                    name: '职位类型数',
                    type: 'line',
                    yAxisIndex: 1,
                    smooth: true,
                    encode:{
                        x: "company_name",
                        y: "job_type_num"
                    }
                }
            ]
        });

        myChart_2.setOption({
            title: {
                text: '根据公司进行划分',
                subtext: '下图显示的是Top100公司平均工资,最高工资和最低工资的对比图，以及最高工资的职位',
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
                        showMinLabel:true,
                        fontSize: 8
                    }
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    name: '工资',
                    axisLabel: {
                        formatter: '{value} k'
                    }
                }
            ],
            dataZoom: [
                {   // 这个dataZoom组件，默认控制x轴。
                    type: 'slider', // 这个 dataZoom 组件是 slider 型 dataZoom 组件
                    start: 0,      // 左边在 0% 的位置。
                    end: 5         // 右边在 10% 的位置。
                }
            ],
            dataset: {
                // 提供一份数据。
                source: data.data
            },
            series: [
                {
                    name: '平均工资',
                    type: 'bar',
                    encode:{
                        x: "company_name",
                        y: "avg_salary"

                    },
                    label: {
                        normal: {
                            show: true,
                            position: 'top'
                        }
                    }
                },
                {
                    name: '最高工资',
                    type: 'bar',
                    encode:{
                        x: "company_name",
                        y: "max_salary"
                    },
                    label: {
                        normal: {
                            show: true,
                            position: 'top'
                        }
                    }
                },
                {
                    name: '最低工资',
                    type: 'bar',
                    encode:{
                        x: "company_name",
                        y: "min_salary"
                    },
                    label: {
                        normal: {
                            show: true,
                            position: 'top'
                        }
                    }
                }
            ]
        });


        myChart_3.setOption({
            title: {
                subtext: 'Top100公司的最高薪资职位--薪资'
            },
            legend: {},
            tooltip: {},
            xAxis: [
                {
                    type: 'category',
                    axisLabel: {
                        interval:0,
                        fontSize: 10
                    },
                    gridIndex : 0,
                    position:"top"
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    name: '工资',
                    axisLabel: {
                        formatter: '{value} k'
                    }
                }
            ],
            dataZoom: [
                {   // 这个dataZoom组件，默认控制x轴。
                    type: 'slider', // 这个 dataZoom 组件是 slider 型 dataZoom 组件
                    start: 0,      // 左边在 0% 的位置。
                    end: 5         // 右边在 10% 的位置。
                }
            ],
            dataset: {
                // 提供一份数据。
                source: data.data
            },
            series: [
                {
                    name: '最高薪职位',
                    type: 'line',
                    smooth: true,
                    encode:{
                        x: "max_salary_job",
                        y: "max_salary"
                    }
                }
            ]
        });

        myChart_4.setOption({
            title: {
                subtext: 'Top100公司的最高薪资职位--薪资'
            },
            legend: {},
            tooltip: {},
            xAxis: [
                {
                    type: 'category',
                    axisLabel: {
                        interval:0,
                        showMinLabel:true,
                        fontSize: 8
                    }
                }
            ],
            yAxis: [
                {
                    type: 'category',
                    name: '最热职位',
                    axisLabel: {
                        interval:0,
                        showMinLabel:true,
                        fontSize: 8
                    }
                }
            ],
            dataZoom: [
                {   // 这个dataZoom组件，默认控制x轴。
                    type: 'slider', // 这个 dataZoom 组件是 slider 型 dataZoom 组件
                    xAxisIndex: 0,
                    start: 0,      // 左边在 0% 的位置。
                    end: 5         // 右边在 10% 的位置。
                },
                {
                    type: 'slider',
                    yAxisIndex: 0,
                    start: 0,
                    end: 5
                }
            ],
            dataset: {
                // 提供一份数据。
                source: data.data
            },
            series: [
                {
                    name: '最热职位',
                    type: 'scatter',
                    itemStyle: {
                        normal: {
                            opacity: 1.5
                        }
                    },
                    encode:{
                        x: "company_name",
                        y: "hot_job"
                    }
                }
            ]
        });

    });

</script>
</body>
</html>