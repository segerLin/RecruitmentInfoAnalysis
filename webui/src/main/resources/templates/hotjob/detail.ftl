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
    <script src="/js/wordcloud.js"></script>

    <style type="text/css">
        html,body { height: 100%; margin: 0px; padding: 0px;background:#333333 }
    </style>

</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div style="height: 5%"></div>
<div id="chart1" style="height: 70%; overflow: auto"></div>
<div style="height: 5%"></div>
<div id="chart2" style="height: 70%; overflow: auto"></div>
<div style="height: 5%"></div>
<div id="chart3" style="height: 70%; overflow: auto"></div>
<div style="height: 5%"></div>

<script type="text/javascript">

    function compareAbout(property1,property2) {
        return function (a,b) {
            var value1 = a[property1];
            var value2 = b[property1];
            var value3 = a[property2];
            var value4 = b[property2];

            if(value1 == value2)
                return value3 - value4;
            else
                return value1 - value2;
        }
    }

    function cleanSalary(param) {
        var arrays = [];
        param = param.sort(compareAbout('experience_min',"experience_max"));

        for(var i = 0; i < param.length; i++){
            var t = new Map();
            Object.keys(param[i]).forEach(key =>{
                t.set(key, param[i][key])
        });
            var m = {};
            if(t.get("experience_max") == 999 || t.get("experience_max") === 999)
                m.title = t.get("experience_min") + "年以上";

            else if(t.get("experience_max") == 0)
                m.title = "应届生(or无需经验)";
            else
                m.title = t.get("experience_min") + "-" + t.get("experience_max") + "年";
            if(t.get("avg_salary") <= 100)
                m.salary = t.get("avg_salary");
            else
                m.salary = "暂无";
            m.num = t.get("num");
            arrays.push(m)
        }
        return arrays;
    }

    function cleanEpri(param) {
        var arrays = [];
        param = param.sort(compareAbout('salary_min','salary_max'));
        for(var i = 0; i < param.length; i++){
            var t = new Map();
            Object.keys(param[i]).forEach(key =>{
                t.set(key, param[i][key])
        });
            var m = {};
            if(!(t.get("salary_max") == 999 || t.get("salary_max") === 999)){
                m.title = t.get("salary_min") + "-" + t.get("salary_max") + "K";
                m.count = t.get("count");
                arrays.push(m)
            }
        }
        return arrays;
    }
</script>


<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart_1 = echarts.init(document.getElementById('chart1'),'dark');
    var myChart_2 = echarts.init(document.getElementById('chart2'),'dark');
    var myChart_3 = echarts.init(document.getElementById('chart3'),'dark');


    var title = '${title}';
    var jobPath = '/json/detail/job/' + title +'.json';
    var salaryPath = '/json/detail/salary/' + title  + '/' + title + '.json';
    var epriPath = '/json/detail/experience/' + title  + '/' + title + '.json';
    var shape = ["circle","cardioid","diamond","apple","triangle-forward","triangle","pentagon", "star"];
    $.ajax(jobPath).done(function(jobData) {
        $.ajax(salaryPath).done(function (salaryData) {
            $.ajax(epriPath).done(function (epriData) {

                var chart2Data = cleanSalary(salaryData.data);
                var chart3Data = cleanEpri(epriData.data);

                myChart_1.setOption({
                    title: {
                        text: title,
                        subtext: '显示 ' + title + ' 招聘要求/描述的词云',
                        x: '20%'
                    },
                    legend: {},
                    series: [{
                        type: 'wordCloud',
                        // The shape of the "cloud" to draw. Can be any polar equation represented as a
                        // callback function, or a keyword present. Available presents are circle (default),
                        // cardioid (apple or heart shape curve, the most known polar equation), diamond (
                        // alias of square), triangle-forward, triangle, (alias of triangle-upright, pentagon, and star.

                        shape: shape[parseInt(shape.length * Math.random())],

                        // A silhouette image which the white area will be excluded from drawing texts.
                        // The shape option will continue to apply as the shape of the cloud to grow.

                        // Folllowing left/top/width/height/right/bottom are used for positioning the word cloud
                        // Default to be put in the center and has 75% x 80% size.

                        left: 'center',
                        top: 'center',
                        width: '70%',
                        height: '80%',
                        right: null,
                        bottom: null,

                        // Text size range which the value in data will be mapped to.
                        // Default to have minimum 12px and maximum 60px size.

                        sizeRange: [12, 50],

                        // Text rotation range and step in degree. Text will be rotated randomly in range [-90, 90] by rotationStep 45

                        rotationRange: [-90, 90],
                        rotationStep: 45,

                        // size of the grid in pixels for marking the availability of the canvas
                        // the larger the grid size, the bigger the gap between words.

                        gridSize: 15,

                        // set to true to allow word being draw partly outside of the canvas.
                        // Allow word bigger than the size of the canvas to be drawn
                        drawOutOfBound: false,

                        // Global text style
                        textStyle: {
                            normal: {
                                fontFamily: 'sans-serif',
                                fontWeight: 'bold',
                                // Color can be a callback function or a color string
                                color: function () {
                                    // Random color
                                    return 'rgb(' + [
                                        Math.round(Math.random() * (255-60+1)+60),
                                        Math.round(Math.random() * (255-60+1)+60),
                                        Math.round(Math.random() * (255-60+1)+60)
                                    ].join(',') + ')';
                                }
                            },
                            emphasis: {
                                shadowBlur: 10,
                                shadowColor: '#333'
                            }
                        },

                        // Data is an array. Each array item must have name and value property.
                        data: jobData.data
                    }]
                });
                myChart_2.setOption({
                    title: {
                        text: '分析 ' +  title + ' 工作经验和平均薪资|工作数量的关系'
                    },
                    legend: {
                        x : "70%"
                    },
                    tooltip: {},
                    xAxis: [
                        {
                            type: 'category',
                            axisLabel: {
                                interval:0,
                                rotate: 20
                            }
                        }

                    ],
                    yAxis: [
                        {
                            type: 'value',
                            name: '平均工资',
                            axisLabel: {
                                formatter: '{value} k'
                            }
                        },
                        {
                            type: 'value',
                            name: '工作数'
                        }
                    ],
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    dataset: {
                        // 提供一份数据。
                        source: chart2Data
                    },
                    series: [
                        {
                            name: '平均工资',
                            type: 'bar',
                            encode:{
                                x: "title",
                                y: "salary"
                            }
                        },
                        {
                            name: '职位数',
                            type: 'line',
                            yAxisIndex: 1,
                            smooth: true,
                            encode:{
                                x: "title",
                                y: "num"
                            }
                        }
                    ]
                });
                myChart_3.setOption({
                    title: {
                        text: '分析' + title + '的薪资分布'
                    },
                    legend: {
                        x : "70%"
                    },
                    tooltip: {},
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    xAxis: [
                        {
                            type: 'category',
                            axisLabel: {
                                interval:0,
                                rotate: 20
                            }
                        }

                    ],
                    yAxis: [
                        {
                            type: 'value',
                            name: '数量',
                            axisLabel: {
                                formatter: '{value} 位'
                            }
                        }
                    ],
                    dataZoom: [
                        {   // 这个dataZoom组件，默认控制x轴。
                            type: 'slider', // 这个 dataZoom 组件是 slider 型 dataZoom 组件
                            start: 0,      // 左边在 10% 的位置。
                            end: 20         // 右边在 60% 的位置。
                        }
                    ],
                    dataset: {
                        // 提供一份数据。
                        source: chart3Data
                    },
                    series: [
                        {
                            name: '平均工资',
                            type: 'line',
                            smooth: true,
                            encode:{
                                x: "title",
                                y: "count"
                            }
                        }
                    ]
                });


            });
        });

    });
    // 使用刚指定的配置项和数据显示图表。

</script>
</body>
</html>