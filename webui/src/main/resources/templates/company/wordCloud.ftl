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


<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart_1 = echarts.init(document.getElementById('chart1'),'dark');
    var schema = "";
    var path = '';
    if('${type}' == 'name'){
        path = "/json/company/name/" + '${key}' + '.json';
        schema = "公司名称"
    }
    if('${type}' == 'area'){
        path = "/json/company/area/"+ '${key}' + '.json';
        schema = "公司领域";
    }
    if('${type}' == 'nop'){
        path = "/json/company/nop/"+ '${key}' + '.json';
        schema = "公司人数";
    }
    if('${type}' == 'size'){
        path = "/json/company/size/"+ '${key}' + '.json';
        schema = "公司阶段"
    }

    var shape = ["circle","cardioid","diamond","apple","triangle-forward","triangle","pentagon", "star"];
    $.ajax(path).done(function(data) {
        myChart_1.setOption({
            title: {
                text: "按照 " + schema,
                subtext: '显示 ' + '${key}' + ' 招聘诱惑词云',
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

                sizeRange: [30, 90],

                // Text rotation range and step in degree. Text will be rotated randomly in range [-90, 90] by rotationStep 45

                rotationRange: [-90, 90],
                rotationStep: 45,

                // size of the grid in pixels for marking the availability of the canvas
                // the larger the grid size, the bigger the gap between words.

                gridSize: 20,

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
                data: data.data
            }]
        });

    });
    // 使用刚指定的配置项和数据显示图表。

</script>
</body>
</html>