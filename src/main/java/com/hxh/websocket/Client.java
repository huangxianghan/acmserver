/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hxh.websocket;

import org.springframework.web.socket.WebSocketSession;

/**
 * 
 * @author xianghan
 */
public class Client {
    private final User user;
    private final WebSocketSession session;

    public Client(User user, WebSocketSession session) {
        this.user = user;
        this.session = session;
    }
    
    public void updateUser(String addr, 
            String port, 
            String driveName, 
            String driveversion){
        this.user.update(addr, port, driveName, driveversion);
    }
    
    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @return the session
     */
    public WebSocketSession getSession() {
        return session;
    }
    
    /**
     * 获取sessionid
     * @return 
     */
    public String getSessionId(){
        return session.getId();
    }
    
}
