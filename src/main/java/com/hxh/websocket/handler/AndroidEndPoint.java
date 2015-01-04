/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hxh.websocket.handler;

import com.hxh.websocket.beans.JsonMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hxh.websocket.service.UserOnlineService;
import com.hxh.utils.JacksonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * 处理由安卓客户端通过WebSocket发送过来的消息
 * @author 黄香翰
 */
public class AndroidEndPoint extends TextWebSocketHandler{
    
    @Autowired
    UserOnlineService service;

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("已关闭"+getClientAddress(session)+"的websocket。代码:"+
                status.getCode()+"原因："+status.getReason());
        super.afterConnectionClosed(session, status);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("来自"+getClientAddress(session)+"的websocket传输出现错误。");
        super.handleTransportError(session, exception);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        
        
        ObjectMapper om = JacksonMapper.getInstance();
        JsonMessage jm = null;
        try{
            jm =om.readValue(message.getPayload(), JsonMessage.class);
        }catch(Exception e){
            TextMessage tsg = new TextMessage(e.toString());
            session.sendMessage(tsg);
            e.printStackTrace();
            return;
        }
        
        int command = jm.getC();
        
        if(command == JsonMessage.USER_LOGIN){
            //JsonMessage msg = jm;
            //doUserLogin(session,msg.getD(String[].class));
        }else if(command == JsonMessage.USER_LOGOUT ){
            //JsonMessage msg = jm;
            //doUserLogout(session,msg.getD(String.class));
        }
        
        System.out.println("the client say:"+ message.getPayload());
        TextMessage tsg = new TextMessage("I'm good 3Q");
        
        session.sendMessage(tsg);
        
        super.handleTextMessage(session, message);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String clientAddress = getClientAddress(session);
        System.out.println("已建立"+clientAddress+"的websocket。");
        super.afterConnectionEstablished(session);
    }
    
    private String getClientAddress(WebSocketSession session){
        String s = session.getRemoteAddress().getHostName();
        int cp = session.getRemoteAddress().getPort();
        return s+":"+cp;
    }
    
    private void doUserLogin(WebSocketSession session,String[] upp){
        //String clientAddress = getClientAddress(session);
        if(upp!=null && upp.length==5){
            String clientIp = session.getRemoteAddress().getHostName();
            service.registerUser(upp[0], upp[1],clientIp, upp[2],upp[3],upp[4],session);
        }
    }
    
    private void doUserLogout(WebSocketSession session,String d){
        if(d!=null && !d.equals("")){
            service.removeUser(d);
        }
    }
    
}
