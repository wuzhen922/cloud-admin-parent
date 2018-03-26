/**
 * Created by wtw on 2018/1/18.
 */
EchartOption.tree=(function(){
    var glyph_opts = {
        preset: "bootstrap3",
        map: {
        }
    };
    var treeInstance;
    var treeOption={
        extensions: ["dnd", "edit", "glyph", "wide",'select'],
        dnd: {
            focusOnClick: true,
            dragStart: function (node, data) {
                return true;
            },
            dragEnter: function (node, data) {
                return false;
            },
            dragDrop: function (node, data) {
                data.otherNode.copyTo(node, data.hitMode);
            }
        },
        glyph: glyph_opts,
        selectMode: 2,
        source:'',
        toggleEffect: {effect: "drop", options: {direction: "left"}, duration: 400},

        wide: {
            iconWidth: "1em",       // Adjust this if @fancy-icon-width != "16px"
            iconSpacing: "0.5em",   // Adjust this if @fancy-icon-spacing != "3px"
            labelSpacing: "0.1em",  // Adjust this if padding between icon and label != "3px"
            levelOfs: "1.5em"       // Adjust this if ul padding != "16px"
        }
    };


    //适配器--ajax返回数据-接口所需数据
    function convertDataLoop(ele) {
        ele = $.map(ele, function (c) {
            // Rename 'key' to 'id'
            c.key = c.id;
            delete c.id;
            c.tooltip = c.title;
            // Check if c is a child node
            if (!!c.children && c.children.length > 0) {
                c.folder = true;
                convertDataLoop(c.children);

            }
            return c;
        });
        return ele;
    }

    /**
     * 构造函数，返回两个公有方法
     * @returns {{initFanctree: initFanctree, getTreeSelected: getTreeSelected}}
     */
    function constructor(){
        return{
             //初始化树，特权函数
             initFanctree:function(resultList,treeId){

                    treeOption.source =  convertDataLoop(resultList);
                     $(treeId).fancytree(treeOption);

                },
                //数据获取节点，展示数据,桥接
             getTreeSelected:function(treeId,callback){
                    $(treeId).fancytree({
                        click: function(event, data) {
                            //点节点展开
                            if(event.toElement.className.indexOf('fancytree-expander')>-1){
                                data.node.toggleExpanded();
                                return false;
                            }
                            if(!!data.node.data && !!data.node.data.status &&  data.node.data.status =='20' ){
                                data.node.unselectable = true;
                                layer.msg('已禁用');//集成商禁用

                            }else {
                                var nodeList = data.tree.getSelectedNodes();
                                nodeList.forEach(function(ele,key){
                                    ele.selected = false;
                                    ele.extraClasses = '';
                                    $(ele.span).removeClass('selected');
                                });
                                data.node.setSelected(true);
                                data.node.extraClasses = 'selected';
                                callback(data.node);
                              /*  $('.totalFlowContent ').hasClass('none')?  callback1(data.node):callback2(data.node);

                                //callback1(data.node)选中节点查实时数据
                                //callback2(data.node)选中节点查统计数据*/

                            }
                            return false;

                        },

                    });
        }

    }
    }
    return {
        /**
         * 惰性加载，调用实例，返回treeInstance对象
         * @returns {*}
         */
        getInstance:function(){
            if(!treeInstance){
                treeInstance = constructor();
            }
            return treeInstance;

        }
    }


})();