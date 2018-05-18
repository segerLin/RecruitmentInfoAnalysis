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


<script>
    var provinceMap = new Map();
    provinceMap.set("安徽","anhui");
    provinceMap.set("福建","fujian");
    provinceMap.set("甘肃","gansu");
    provinceMap.set("广东","guangdong");
    provinceMap.set("广西","guangxi");
    provinceMap.set("贵州","guizhou");
    provinceMap.set("海南","hainan");
    provinceMap.set("河北","hebei");
    provinceMap.set("黑龙","heilongjiang");
    provinceMap.set("河南","henan");
    provinceMap.set("湖北","hubei");
    provinceMap.set("湖南","hunan");
    provinceMap.set("江苏","jiangsu");
    provinceMap.set("江西","jiangxi");
    provinceMap.set("吉林","jilin");
    provinceMap.set("辽宁","liaoning");
    provinceMap.set("内蒙","neimenggu");
    provinceMap.set("宁夏","ningxia");
    provinceMap.set("青海","qinghai");
    provinceMap.set("山东","shandong");
    provinceMap.set("上海","shanghai");
    provinceMap.set("山西","shanxi");
    provinceMap.set("陕西","shanxi1");
    provinceMap.set("四川","sichuan");
    provinceMap.set("天津","tianjin");
    provinceMap.set("新疆","xinjiang");
    provinceMap.set("西藏","xizang");
    provinceMap.set("云南","yunnan");
    provinceMap.set("浙江","zhejiang");
    provinceMap.set("北京","beijing");
    provinceMap.set("上海","shanghai");
    provinceMap.set("重庆","chongqing");
    provinceMap.set("天津","tianjin");

</script>

<script type="text/javascript">
    function cleanData(param, key) {
        var schema = 'jobCity';
        if(province == '北京' || province == '天津' || province == '重庆' || province == '上海')
            schema = 'jobArea';
        var arrays = [];
        for(var i = 0; i < param.length; i++){
            var t = new Map();
            Object.keys(param[i]).forEach(key =>{
                t.set(key, param[i][key])
        });
            var m = {};
            m.name = t.get(schema);
            m.value = t.get(key);
            arrays.push(m)
        }
        return arrays;
    };

    var province = '${province}';
    var mapName = "";
    if(province.indexOf("黑龙江") == 0 || province.indexOf("内蒙古") == 0)
        mapName = province.substring(0,3);
    else
        mapName = province.substring(0,2);

    var path = "/js/province/" +provinceMap.get(province.substring(0,2)) + '.js';
    var sNew = document.createElement("script");
    sNew.async = true;
    sNew.src = path;
    var s0 = document.getElementsByTagName('script')[0];
    s0.parentNode.insertBefore(sNew, s0);
    document.write("<script scr=" +  path +"><\/script>")

    // 基于准备好的dom，初始化echarts实例
    var myChart_1 = echarts.init(document.getElementById('chart1'),'dark');
    var myChart_2 = echarts.init(document.getElementById('chart2'),'dark');

    var getPath = "/area/getCity";
    var getParam = {"province":province};
    var whiteList = ['北京','天津','重庆','上海'];
    for(var i = 0; i < whiteList.length; i++){
        if(whiteList[i] == province){
            getPath = "/area/getArea";
            getParam = {"province":province, 'city':province};
            break;
        }
    }
    $.get(getPath,getParam).done(function(data){
        var companyNum = cleanData(data.data, "companyNum");
        var jobNUm = cleanData(data.data, "jobNum");
        var companyAreaNum= cleanData(data.data, "companyAreaNum");
        var avgSalary = cleanData(data.data, "avgSalary");
        var minSalary = cleanData(data.data, "minSalary");
        var maxSalary = cleanData(data.data, "maxSalary");
        var jobTitleNum = cleanData(data.data, "jobTitleNum");
        // 使用刚指定的配置项和数据显示图表。
        myChart_1.setOption({
            title : {
                text: '按省份-市区分布',
                subtext: province + '的工作数、公司数等',
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
                    mapType: mapName,
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
                    mapType: mapName,
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
                    mapType: mapName,
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
                    mapType: mapName,
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
                text: province + '工资分布',
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
                    mapType: mapName,
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
                    mapType: mapName,
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
                    mapType: mapName,
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

<script>
    <#--myChart_1.on('click', function (params) {-->
        <#--var path = "province=${province}"+ "&city=" + params.name;-->
        <#--console.log(path)-->
        <#--// window.open('/area/city?province=' + province + "省");-->
    <#--});-->
    <#--myChart_1.on('click', function (params) {-->
        <#--var path = "province=${province}"+ "&city=" + params.name;-->
        <#--console.log(path)-->
        <#--// window.open('/area/city?province=' + province + "省");-->
    <#--});-->
</script>
</body>
</html>