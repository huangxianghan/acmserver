/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hxh.websocket.handler;


import com.hxh.websocket.service.UserOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 *
 * @author 黄香翰
 */
public class WebsocketEndPoint extends TextWebSocketHandler  {

    @Autowired
    UserOnlineService service;
    
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        TextMessage returnMessage = null;
        String msg = message.getPayload();
        System.out.println("接到客户端 sessionid "+session.getId()+"消息："+msg);
        if(msg.equals("getusers")){
            
        }else{
            returnMessage = new TextMessage("err");
            System.out.println("无法识别客户端 sessionid "+session.getId()+"消息："+msg);
        }
        session.sendMessage(returnMessage);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        service.removeSession(session);
        String clientAddress = getClientAddress(session);
        System.out.println("已断开"+clientAddress+"的websocket。");
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session); 
        String clientAddress = getClientAddress(session);
        System.out.println("已建立"+clientAddress+"的websocket。");
        service.registerSession(session);
        //System.out.println("已注册ws_session");
    }
    
    private String getClientAddress(WebSocketSession session){
        String s = session.getRemoteAddress().getHostName();
        int cp = session.getRemoteAddress().getPort();
        return s+":"+cp;
    }
}
