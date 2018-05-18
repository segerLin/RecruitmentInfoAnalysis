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
        html,body { height: 100%; margin: 0px; padding: 0px; background:#333333}
    </style>
</head>
<body>

<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div style="height: 5%"></div>
<div id="type" style="height:60%"></div>
<div style="height: 2%"></div>
<div data-toggle="distpicker">
    <select data-province="---- 选择省 ----", id="job_province"></select>
    <select data-city="---- 选择市 ----" id="job_city"></select>
</div>
<div style="height: 2%"></div>
<div id="province" style="height: 60%"></div>


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
        provinceChart.showLoading();

        $.get("/type/province",{"province":province}).done(function(data){
            provinceChart.hideLoading();

            // 使用刚指定的配置项和数据显示图表。
            provinceChart.setOption({
                title : {
                    text: '按照工作类型进行展示',
                    subtext: '按省份显示'
                },
                legend: {},
                tooltip: {},
                dataset: {
                    // 提供一份数据。
                    source: jsonToDataSet(data.data)
                },
                // 声明一个 X 轴，类目轴（category）。默认情况下，类目轴对应到 dataset 第一列。
                // 声明多个 bar 系列，默认情况下，每个系列会自动对应到 dataset 的每一列。
                series: [
                    {
                        type: 'pie'
                    }
                ]
            });
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
        provinceChart.showLoading();
        $.get("/type/city",{"province":province,"city":city}).done(function(data){
            // 使用刚指定的配置项和数据显示图表。
            provinceChart.hideLoading();

            provinceChart.setOption({
                title : {
                    text: '按照工作类型进行展示',
                    subtext: '按省份和城市显示'
                },
                legend: {},
                tooltip: {},
                dataset: {
                    // 提供一份数据。
                    source: jsonToDataSet(data.data)
                },
                // 声明一个 X 轴，类目轴（category）。默认情况下，类目轴对应到 dataset 第一列。

                // 声明多个 bar 系列，默认情况下，每个系列会自动对应到 dataset 的每一列。
                series: [
                    {
                        type: 'pie'
                    }
                ]
            });
        });
    })

</script>

<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var typeChart = echarts.init(document.getElementById('type'),'dark');

    $.ajax("/json/job_type.json").done(function(data){
        // 使用刚指定的配置项和数据显示图表。
        typeChart.setOption({
            title : {
                text: '按照工作类型进行展示',
                subtext: '按全部职位显示'
            },
            legend: {},
            tooltip: {},
            dataset: {
                // 提供一份数据。
                source: jsonToDataSet(data.data)
            },
            // 声明一个 X 轴，类目轴（category）。默认情况下，类目轴对应到 dataset 第一列。

            // 声明多个 bar 系列，默认情况下，每个系列会自动对应到 dataset 的每一列。
            series: [
                {
                    type: 'pie'
                }
            ]
        });
    });
    var provinceChart = echarts.init(document.getElementById('province'),'dark');


</script>
</body>
</html>

