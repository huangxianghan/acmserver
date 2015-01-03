/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hxh.websocket;

import java.util.Map;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 *
 * @author Administrator
 */
public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor{

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
            WebSocketHandler wsHandler, Exception ex) {
        System.out.println("After Handshake,ex:"+ (ex==null?"No Exception": ex.getMessage()));
        super.afterHandshake(request, response, wsHandler, ex); 
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
            WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("Before Handshake");
        System.out.println(request.getHeaders().toString());
        System.out.println("请求地址:"+request.getURI());
        System.out.println("请求内容:"+request.getBody());
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }
    
}
