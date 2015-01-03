/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hxh.websocket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hxh.websocket.beans.User;
import com.hxh.websocket.beans.JsonMessage;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
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
    
    //后台监控端消息推送列表
    final List<WebSocketSession> sessions =  Collections.synchronizedList(new LinkedList<WebSocketSession>()) ;
    
    //智能移动客户端在线列表
    final List<User> users = Collections.synchronizedList(new LinkedList<User>());
    
    public UserOnlineService(){
        
    }
    
    public User addUser(User user){
       users.add(user);
       return user;
    }
    
    public void removeUser(String name){
        User ruser = null;
        //在遍历时需手工同步
        synchronized(users){
            for (User user : users) {
                if(user.getName().equals(name)){
                    ruser = user;
                    break;
                }
            }
        }
        users.remove(ruser);
        JsonMessage<String> msg = new JsonMessage();
        msg.setC(JsonMessage.USER_LOGOUT);
        msg.setD(ruser.getName());
        pushMessageToWebClient(msg);
        
    }
    
    public void registerUser(String name,
            String pass,
            String addr,
            String port,
            String driveName,
            String driveversion,
            WebSocketSession session){
        
        if(name==null || name.equals("")){
            return;
        }
        
        User ruser = findUserbyName(name);
        
        if(ruser==null){
            User user = new User(name, 
                    pass, 
                    addr, 
                    port, 
                    driveName,
                    driveversion,
                    session);
            ruser = addUser(user);
        }else{
            ruser.update(addr, port, driveName, driveversion, session);
        }
        
        JsonMessage<User> msg = new JsonMessage();
        msg.setC(JsonMessage.USER_LOGIN);
        msg.setD(ruser);
        
        pushMessageToWebClient(msg);
    }
    
    private User findUserbyName(String name){
        //在遍历时需手工同步
        synchronized(users){
            for (User user : users) {
                if(user.getName().equals(name)){
                    return user;
                }
            }
        }
        
        return null;
    }
    
    /**
     * 在消息推送列表中注册WebSocketSession,服务器以向WebSocketSession
     * 发送消息的方式来实现推送。
     * @param s 
     */
    public void registerSession(WebSocketSession s){
        System.out.println("注册ws_session");
        sessions.add(s);
        System.out.println("共有:"+sessions.size());
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
        System.out.println("开始推送");
        System.out.println("共有:"+sessions.size());
        synchronized(sessions){
            for(WebSocketSession s : sessions){
                ObjectMapper mapper = new ObjectMapper();
                StringWriter sw = new StringWriter();
                try {
                    mapper.writeValue(sw, msg);
                    String jsonmsg = sw.toString();
                    System.out.println("推送:"+jsonmsg);
                    s.sendMessage(new TextMessage(jsonmsg));
                } catch (IOException ex) {
                    Logger.getLogger(UserOnlineService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
