/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hxh.websocket.handler;

import com.hxh.websocket.JsonMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hxh.websocket.service.UserOnlineService;
import com.hxh.utils.JacksonMapper;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    private final static boolean DEBUG = false;
    
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
            doUserLogin(session,jm.getD(ArrayList.class));
        }else if(command == JsonMessage.USER_LOGOUT ){
            doUserLogout(session,jm.getD(String.class));
        }
        
        super.handleTextMessage(session, message);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String clientAddress = getClientAddress(session);
        System.out.println("已建立"+clientAddress+"的websocket.");
        super.afterConnectionEstablished(session);
        
    }
    
    private String getClientAddress(WebSocketSession session){
        InetSocketAddress address = session.getRemoteAddress();
        /**
         * 如果采用 address.getHostName(); 会很久才响应。getHostName的源注释如下：
         * This method may trigger a name service reverse lookup if the address
         * was created with a literal IP address.
         */
        String s = address.getHostString();
        int cp = address.getPort();
        return s+":"+cp;
    }
    
    private void doUserLogin(WebSocketSession session,ArrayList<String> upp){
        //String clientAddress = getClientAddress(session);
        boolean flag = false;
        if(upp!=null && upp.size()==5){
            String clientIp = session.getRemoteAddress().getHostString();
            flag = service.registerClient(upp.get(0), upp.get(1),clientIp, upp.get(2),upp.get(3),upp.get(4),session);
        }
        
        if(!flag) return;
        
        try {
            JsonMessage jm = new JsonMessage(JsonMessage.LOGIN_SUCCESS,null);
            session.sendMessage(new TextMessage(jm.toJson()));
        } catch (IOException ex) {
            Logger.getLogger(AndroidEndPoint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void doUserLogout(WebSocketSession session,String d){
        if(d!=null && !d.equals("")){
            service.removeClient(d);
        }
    }
    
}
