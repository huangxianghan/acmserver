var ws = null;
var id =0;
(function($) {
  'use strict';

  $(function() {
    var $fullText = $('.admin-fullText');
    $('#admin-fullscreen').on('click', function() {
      $.AMUI.fullscreen.toggle();
      $.AMUI.fullscreen.isFullscreen ? $fullText.text('关闭全屏') : $fullText.text('开启全屏');
    });
  });
  ws =new WebSocket(websocketUrl);
  ws.onopen = function () {
      log('websocket connection success');
  };
  ws.onclose = function (event) {  
       log('websocket connection closed.');
       log(event);
   };
    ws.onmessage = function (event) {  
        var msg = eval("("+event.data+")");
        if(msg.c==1){
            var data = msg.d;
            addDrive(data.addr,'就绪',data.name,data.lastLoginTime);
        }else if(msg.c==2){
            removeDrive(msg.d);
        }
    };
})(jQuery);

function log(msg){

}

function disconnect() {  
    if (ws != null) {  
        ws.close();  
        ws = null;  
    }  
 }

// 对Date的扩展，将 Date 转化为指定格式的String 
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
// 例子： 
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.Format = function(fmt) 
{ //author: meizz 
  var o = { 
    "M+" : this.getMonth()+1,                 //月份 
    "d+" : this.getDate(),                    //日 
    "h+" : this.getHours(),                   //小时 
    "m+" : this.getMinutes(),                 //分 
    "s+" : this.getSeconds(),                 //秒 
    "q+" : Math.floor((this.getMonth()+3)/3), //季度 
    "S"  : this.getMilliseconds()             //毫秒 
  }; 
  if(/(y+)/.test(fmt)) 
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
  for(var k in o) 
    if(new RegExp("("+ k +")").test(fmt)) 
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length))); 
  return fmt; 
}

/**
 * 添加设备
 * 在<table>元素中添加一行
 * @param {type} ip ip
 * @param {type} state 状态
 * @param {type} name 名字
 * @returns {undefined} 无返回值
 */
function addDrive(ip,state,name,lastLoginTime){
        id++;
        var d = new Date(lastLoginTime);
        var formatdate= d.Format("yyyy年M月d日 h:m:s");
        var trhtml = '<tr id="row_'+name+'" ><td><input type="checkbox" /></td>';
        trhtml += "<td class='drivelist_xh' ></td>";
        trhtml += "<td>"+ip+"</td>";
        trhtml +="<td>"+state+"</td>";
        trhtml +="<td>"+name+"</td>";
        trhtml +="<td>"+formatdate+"</td>";
        trhtml+='<td><div class="am-btn-toolbar"><div class="am-btn-group am-btn-group-xs">';
        trhtml += '<button class="am-btn am-btn-default  am-text-secondary"><span class="am-icon-pencil-square-o"></span> 查看</button>';
        trhtml +='<button onclick="removeDrive(\'row_'+name+'\')" class="am-btn am-btn-default  am-text-danger"><span class="am-icon-trash-o"></span> 断开</button>';
        trhtml += '</div></div></td>';
        
        $("#drivelist").append(trhtml);
        orderByXh();
}

function removeDrive(rowid){
    $("#"+rowid).remove();
    orderByXh();
}

function orderByXh(){
    $(".drivelist_xh").each(function(i){
        $(this).html(i+1);
    });
}