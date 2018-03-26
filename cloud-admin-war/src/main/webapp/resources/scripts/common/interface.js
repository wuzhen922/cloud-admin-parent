/**
 * Created by wtw on 2018/1/19.
 */
var Interface = function(name,methods){
    if(arguments.length != 2){
        throw new Error('接口需传两个参数 ')
    }
    this.name = name;
    this.methods = [];
    for(var i = 0,len = methods.length;i<len;i++){
        if(typeof methods[i] !== 'string'){
            throw new Error('接口构造函数methods希望是["param1","param1"]形式')
        }
        this.methods.push(methods[i]);
    }
};

//静态检验接口是否全部实现方法
Interface.ensureImplements = function(object){
    if(arguments.length<2){
        throw new Error('该方法至少传两个参数')
    }
    for(var i = 1,len=arguments.length;i<len;i++){
        var interface = arguments[i];
        if(interface.constructor !== Interface){
            throw new Error('该方法需Interface实例调用')
        }
    }
    for(var j = 0,methodsLen = interface.methods.length;j<methodsLen;j++){
        var method = interface.methods[j];
        if(!object[method] || typeof object[method] !== 'function'){
            throw new Error ('方法没有全部实现')
        }
    }
}