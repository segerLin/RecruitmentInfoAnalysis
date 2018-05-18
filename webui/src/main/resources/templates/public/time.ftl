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
        html,body { height: 100%; margin: 0px; padding: 0px;background:#333333}
    </style>

</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div style="height: 5%"></div>
<div id="chart1" style="height: 70%; overflow: auto"></div>


<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart_1 = echarts.init(document.getElementById('chart1'),'dark');

    $.ajax("/json/job_public_time.json").done(function(data){
        // 使用刚指定的配置项和数据显示图表。
        myChart_1.setOption({
            title: {
                text: '根据发布时间进行划分',
                subtext: '抽取2018/3/1 - 2018/4/10发布的招聘量进行显示',
                x: '20%'
            },
            legend: {},
            tooltip: {
                trigger: 'axis',
                position: function (pt) {
                    return [pt[0], '10%'];
                }
            },

            xAxis: [
                {
                    type: 'category',
                    boundaryGap: false,
                    inverse: true
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    name: '发布量',
                    boundaryGap: [0, '100%']
                }
            ],
            dataset: {
                // 提供一份数据。
                source: data.data
            },
            dataZoom: [{
                type: 'inside',
                start: 70,
                end: 100
            }, {
                start: 0,
                end: 10,
                handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
                handleSize: '80%',
                handleStyle: {
                    color: '#fff',
                    shadowBlur: 3,
                    shadowColor: 'rgba(0, 0, 0, 0.6)',
                    shadowOffsetX: 2,
                    shadowOffsetY: 2
                }
            }],
            series: [
                {
                    name: '发布数量',
                    type: 'line',
                    smooth: true,
                    symbol: 'none',
                    sampling: 'average',
                    itemStyle: {
                        normal: {
                            color: 'rgb(255, 70, 131)'
                        }
                    },
                    areaStyle: {
                        normal: {
                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                                offset: 0,
                                color: 'rgb(255, 158, 68)'
                            }, {
                                offset: 1,
                                color: 'rgb(255, 70, 131)'
                            }])
                        }
                    },
                    encode:{
                        x: "public_time",
                        y: "job_num"

                    }
                }
            ]
        });
    });

</script>
</body>
</html>