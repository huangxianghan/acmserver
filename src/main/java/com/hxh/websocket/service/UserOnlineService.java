/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hxh.websocket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hxh.websocket.beans.Client;
import com.hxh.websocket.beans.User;
import com.hxh.websocket.beans.JsonMessage;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * 为在线用户提供登入、退出、消息推送等服务
 * @author 黄香翰
 */
@Service
public class UserOnlineService {
    
    private final boolean DEBUG = true;
    
    //后台监控端消息推送列表
    final List<WebSocketSession> sessions =  Collections.synchronizedList(new LinkedList<WebSocketSession>()) ;
    
    //智能移动客户端在线列表
    final Map<String, Client> users = Collections.synchronizedMap(new HashMap<String, Client>());
    
    public UserOnlineService(){
        
    }
    
    public void addClient(Client client){
       users.put(client.getUser().getName(), client);
    }
    
    public void removeClient(String name){
        users.remove(name);
        JsonMessage msg = new JsonMessage();
        msg.setC(JsonMessage.USER_LOGOUT);
        msg.setD(name);
        pushMessageToWebClient(msg);
    }
    
    public boolean registerClient(String name,
            String pass,
            String addr,
            String port,
            String driveName,
            String driveversion,
            WebSocketSession session){
        
        if(name==null || name.equals("")){
            return false;
        }
        
        Client currClient = findClientbyName(name);
        
        if(currClient==null){
            User user = new User(name, 
                    pass, 
                    addr, 
                    port, 
                    driveName,
                    driveversion);
            currClient = new Client(user, session);
            addClient(currClient);
        }else{
            currClient.updateUser(addr, port, driveName, driveversion);
        }
        
        JsonMessage msg = new JsonMessage();
        msg.setC(JsonMessage.USER_LOGIN);
        msg.setD(currClient.getUser());
        
        pushMessageToWebClient(msg);
        return true;
    }
    
    private Client findClientbyName(String name){
        return users.get(name);
    }
    
    /**
     * 在消息推送列表中注册WebSocketSession,服务器以向WebSocketSession
     * 发送消息的方式来实现推送。
     * @param s 
     */
    public void registerSession(WebSocketSession s){
        sessions.add(s);
    }
    
    /**
     * 从消息推送列表中删除已注册的WebSocketSession
     * @param s 
     */
    public void removeSession(WebSocketSession s){
        sessions.remove(s);
    }
    
    /**
     * 服务器消息推送：
     * 向所有在消息推送列表中的WebSocketSession发送消息。
     * @param msg 
     */
    private void pushMessageToWebClient(JsonMessage msg){
        synchronized(sessions){
            for(WebSocketSession s : sessions){
                ObjectMapper mapper = new ObjectMapper();
                StringWriter sw = new StringWriter();
                try {
                    mapper.writeValue(sw, msg);
                    String jsonmsg = sw.toString();
                    s.sendMessage(new TextMessage(jsonmsg));
                } catch (IOException ex) {
                    Logger.getLogger(UserOnlineService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
