<!DOCTYPE html>
<html lang="en" >

<head>
    <meta charset="UTF-8">
    <link rel="icon" href="/img/logo.png">

    <title>招聘信息分析</title>
    <!-- <link href="https://fonts.googleapis.com/css?family=Oswald:700" rel="stylesheet"> -->

    <link rel="stylesheet" href="/css/style.css">

</head>

<body>

        <div class="panel-list">
            <div class="word">
                <#list data as item>
                    <a onclick="jump(this)">${item}</a>
                    <#if item_index % 7 == 0 && item_index != 0>
                        <br>
                    </#if>
                </#list>
            </div>
        </div>

</body>

<script>
    function jump(a) {
        window.open('/hot/detail?title=' + a.text);
    }
    
</script>

</html>
