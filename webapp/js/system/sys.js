$.fn.tree.defaults.loadFilter = function (data, parent) {
    var opt = $(this).data().tree.options;
    var idFiled,
    textFiled,
    parentField;
    if (opt.parentField) {
        idFiled = opt.idFiled || 'id';
        textFiled = opt.textFiled || 'text';
        parentField = opt.parentField;
        
        var i,
        l,
        treeData = [],
        tmpMap = [];
        
        for (i = 0, l = data.length; i < l; i++) {
            tmpMap[data[i][idFiled]] = data[i];
        }
        
        for (i = 0, l = data.length; i < l; i++) {
            if (tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
                if (!tmpMap[data[i][parentField]]['children']){
                	tmpMap[data[i][parentField]]['children'] = [];
                }                    
                data[i]['text'] = data[i][textFiled];
                tmpMap[data[i][parentField]]['children'].push(data[i]);
            } else {
                data[i]['text'] = data[i][textFiled];
                treeData.push(data[i]);
            }
        }
        return treeData;
    }
    return data;
};

function triggerTree(treeNode) {
	if (typeof (treeNode.menuUrl)) {
		if (treeNode.menuUrl != "") {
			var tabs = $("#tt").tabs("tabs");
			if (tabs) {
				for ( var i = 0, length = tabs.length; i < length; i++) {
					$("#tt").tabs('close', 0);
				}
			}
			$("#tt").tabs('add',{
				title : treeNode.text,
				width : "100%",
				height : "600px",
				content : '<iframe title="content" frameborder="0" height="100%"'
						+ 'width="100%" allowTransparency="true" align="center"'
						+ 'src='
						+ treeNode.menuUrl
						+ '></iframe>',
				closable : false
			});
		}
	}
}

$(document).ready(function() {
	$('#tree').tree({
	    url:'./system/initSystemMenuTree.do',
 		method:'post',
 		parentField:'parent',
 		animate:true,
		cascadeCheck:true,
		onClick:triggerTree
	});
});
