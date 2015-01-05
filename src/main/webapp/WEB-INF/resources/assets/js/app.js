var ws = null;
var usercount = 0;
(function($) {
  'use strict';

  $(function() {
    var $fullText = $('.admin-fullText');
    $('#admin-fullscreen').on('click', function() {
      $.AMUI.fullscreen.toggle();
      $.AMUI.fullscreen.isFullscreen ? $fullText.text('关闭全屏') : $fullText.text('开启全屏');
    });
  });
  webSocketStart();
})(jQuery);

function webSocketStart(){
    ws =new WebSocket(websocketUrl);
    
    ws.onopen = function(){
        log('#p_status','服务器已连接。');
    };
    
    ws.onclose = function(event){
        log('#p_status','服务器已关闭。');
    }
    
    ws.onmessage = function(event){
        var msg = eval("("+event.data+")");
        if(msg.c==1){
            usercount++;
            $("#s_count").html(usercount);
            var data = msg.d;
            addDrive(data.sessionId,data.addr,'就绪',data.name,data.port,data.lastLoginTime);
            log('#p_msg', '用户：'+data.name+'已连接。');
        }else if(msg.c==2){
            usercount--;
            $("#s_count").html(usercount);
            var name = $('#row_'+msg.d + ' td:eq(4)').text();
            removeDrive(msg.d);
            log('#p_msg', name+'已断开连接。');
        }
    }
}

function log(tag,msg){
    var d = new Date();
    var formatdate= d.Format("yyyy-M-d h:m:s");
    $(tag).html(formatdate + '&nbsp;' +msg);
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
{ 
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
function addDrive(sessionId,ip,state,name,port,lastLoginTime){
        var d = new Date(lastLoginTime);
        var formatdate= d.Format("yyyy年M月d日 h:m:s");
        var trhtml = '<tr id="row_'+sessionId+'" ><td><input type="checkbox" /></td>';
        trhtml += "<td class='drivelist_xh' ></td>";
        trhtml += "<td>"+ip+"</td>";
        trhtml +="<td>"+state+"</td>";
        trhtml +="<td>"+name+"</td>";
        trhtml +="<td>"+formatdate+"</td>";
        trhtml+='<td><div class="am-btn-toolbar"><div class="am-btn-group am-btn-group-xs">';
        trhtml += '<button class="am-btn am-btn-default  am-text-secondary" onclick=openWin("'+ip+'",'+port+') ><span class="am-icon-pencil-square-o"></span> 查看</button>';
        trhtml +='<button onclick="removeDrive(\'row_'+sessionId+'\')" class="am-btn am-btn-default  am-text-danger"><span class="am-icon-trash-o"></span> 断开</button>';
        trhtml += '</div></div></td></tr>';
        
        $("#drivelist").append(trhtml);
        orderByXh();
}

function removeDrive(rowid){
    $("#row_"+rowid).remove();
    orderByXh();
}

function orderByXh(){
    $(".drivelist_xh").each(function(i){
        $(this).html(i+1);
    });
}

function openWin(ip,port){
    var uri = "rtsp://"+ ip +":" + prot;
}