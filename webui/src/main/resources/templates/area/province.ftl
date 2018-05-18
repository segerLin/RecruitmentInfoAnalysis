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
    <script src="/js/china.js"></script>
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

<script type="text/javascript">
    function cleanData(param, key) {
        var arrays = [];
        for(var i = 0; i < param.length; i++){
            var t = new Map();
            Object.keys(param[i]).forEach(key =>{
                t.set(key, param[i][key])
            });
            var m = {};
            if(t.get("job_province").indexOf("黑龙江") == 0 || t.get("job_province").indexOf("内蒙古") == 0)
                m.name = t.get("job_province").substring(0,3);
            else
                m.name = t.get("job_province").substring(0,2);
            m.value = t.get(key);
            arrays.push(m)
        }
        return arrays;
    }


    // 基于准备好的dom，初始化echarts实例
    var myChart_1 = echarts.init(document.getElementById('chart1'),'dark');
    var myChart_2 = echarts.init(document.getElementById('chart2'),'dark');

    var whiteList = ['香港','澳门'];
    var longList = ['内蒙古自治区','广西壮族自治区','新疆维吾尔自治区','宁夏回族自治区','西藏自治区'];

    myChart_1.on('click', function (params) {
        var province = params.name;
        for(var i = 0; i < longList.length; i++){
            if(longList[i].startsWith(province)){
                window.open('/area/city?province=' + longList[i]);
                return;
            }
        }
        for(var i = 0; i < whiteList.length; i++){
            if(whiteList[i] == province){
                return
            }
        }
        if(province == '上海' || province == '北京' || province == '重庆' || province == '天津')
            window.open('/area/city?province=' + province);
        else
            window.open('/area/city?province=' + province + "省");
    });

    myChart_2.on('click', function (params) {
        var province = params.name;
        for(var i = 0; i < longList.length; i++){
            if(longList[i].startsWith(province)){
                window.open('/area/city?province=' + longList[i]);
                return;
            }
        }
        for(var i = 0; i < whiteList.length; i++){
            if(whiteList[i] == province){
                console.log("fuck")
                return
            }
        }
        window.open('/area/city?province=' + province + "省");
    });
    $.ajax("/json/job_province.json").done(function(data){
        var companyNum = cleanData(data.data, "company_num");
        var jobNUm = cleanData(data.data, "job_num");
        var companyAreaNum= cleanData(data.data, "company_area_num");
        var avgSalary = cleanData(data.data, "avg_salary");
        var minSalary = cleanData(data.data, "min_salary");
        var maxSalary = cleanData(data.data, "max_salary");
        var jobTitleNum = cleanData(data.data, "job_title_num");
        // 使用刚指定的配置项和数据显示图表。
        myChart_1.setOption({
            title : {
                text: '全国分布',
                subtext: '按照各省份工作数、公司数等(可点击查看详细省份)',
                left: 'center'
            },
            tooltip : {
                trigger: 'item'
            },
            legend: {
                orient: 'vertical',
                left: 'left'
            },
            visualMap: [
                {
                    type: 'piecewise',
                    color: ['#d94e5d','#eac736','#50a3ba'],
                    pieces: [
                        {min: 100000, label:'10万以上'}, // 不指定 max，表示 max 为无限大（Infinity）。
                        {min: 50000, max:100000, label:'5万 到 10万'},
                        {min: 10000, max:50000, label:'1万 到 5万'},
                        {min: 5000, max: 10000, label:'5千 到 1万'},
                        {min: 1000, max: 5000, label:'1千 到 1万'},
                        {min: 100, max: 1000, label:'100 到 1千'},
                        {min: 0, max: 100, label:'0 到 100'}
                    ]
                }

            ],
            toolbox: {
                show: true,
                orient : 'vertical',
                left: 'right',
                top: 'center',
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            dataset: {
                // 提供一份数据。
                dimensions: ['job_province', 'company_num', 'job_num', 'company_area_num', 'avg_salary', 'max_salary', 'min_salary', 'job_title_num'],
                source: data.data
            },
            series : [
                {
                    name: '招聘数量',
                    type: 'map',
                    mapType: 'china',
                    roam: true,
                    showLegendSymbol: false,
                    label: {
                        normal: {
                            show: false
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    data:jobNUm
                },
                {
                    name: '公司数量',
                    type: 'map',
                    mapType: 'china',
                    roam: true,
                    showLegendSymbol: false,

                    label: {
                        normal: {
                            show: false
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    data:companyNum
                },
                {
                    name: '公司种类',
                    type: 'map',
                    mapType: 'china',
                    roam: true,
                    showLegendSymbol: false,

                    label: {
                        normal: {
                            show: false
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    data:companyAreaNum
                },
                {
                    name: '职位种类',
                    type: 'map',
                    mapType: 'china',
                    roam: true,
                    showLegendSymbol: false,
                    label: {
                        normal: {
                            show: false
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    data:jobTitleNum
                }
            ]
        });

        myChart_2.setOption({
            title : {
                text: '全国工资分布',
                subtext: '按各省份',
                left: 'center'
            },
            tooltip : {
                trigger: 'item'
            },
            legend: {
                orient: 'vertical',
                left: 'left'
            },
            visualMap: [
                {
                    type: 'piecewise',
                    color: ['#d94e5d','#eac736','#50a3ba'],
                    pieces: [
                        {min: 80, label:'> 80K'}, // 不指定 max，表示 max 为无限大（Infinity）。
                        {min: 60, max:80, label:'60 到 80K'},
                        {min: 40, max:60, label:'40 到 60K'},
                        {min: 20, max:40, label:'20 到 40K'},
                        {min: 11, max:20, label:'11 到 20K'},
                        {min: 8, max:11, label:'8 到 11K'},
                        {min: 5, max: 8, label:'5 到 8K'},
                        {min: 3, max: 5, label:'3 到 5K'},
                        {min: 1.5, max: 3, label:'1.5 到 3K'},
                        {min: 0, max: 1.5, label:'0 到 1.5K'}
                    ]
                }
            ],
            toolbox: {
                show: true,
                orient : 'vertical',
                left: 'right',
                top: 'center',
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            dataset: {
                // 提供一份数据。
                dimensions: ['job_province', 'company_num', 'job_num', 'company_area_num', 'avg_salary', 'max_salary', 'min_salary', 'job_title_num'],
                source: data.data
            },
            series : [


                {
                    name: '平均工资',
                    type: 'map',
                    mapType: 'china',
                    roam: true,
                    showLegendSymbol: false,

                    label: {
                        normal: {
                            show: false
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    data:avgSalary
                },
                {
                    name: '最大工资',
                    type: 'map',
                    mapType: 'china',
                    roam: true,
                    showLegendSymbol: false,
                    label: {
                        normal: {
                            show: false
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    data:maxSalary
                },
                {
                    name: '最低工资',
                    type: 'map',
                    mapType: 'china',
                    roam: true,
                    showLegendSymbol: false,
                    label: {
                        normal: {
                            show: false
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    data:minSalary
                }

            ]
        });

    });

</script>
</body>
</html>