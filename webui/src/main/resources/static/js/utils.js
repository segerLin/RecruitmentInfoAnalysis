function jsonToDataSet(param) {
    var arrays = [];
    var data = [];
    for(var key in param[0]){
        data.push(key)
    }
    arrays.push(data);
    for(var i = 0; i < param.length; i++){
        var data = [];
        for(var key in param[i]){
            data.push(param[i][key])
        }
        arrays.push(data)
    }
    return arrays;
}