/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hxh.websocket.beans;

/**
 * 定义json消息格式
 * @author 黄香翰
 */
public class JsonMessage {
    
    public static final int USER_LOGIN = 1;//用户登入

    public static final int USER_LOGOUT = 2;//用户登出

    public static final int USER_STREAM_READY = 3;//用户视频准备好

    public static final int USER_CLOSE_STREAM = 4;//用户关闭视频流
    
    public static final int UNRECOGNIZED_COMMAND = 5;//无法识别的命令
    
    private int v = 1;//消息版本号
    
    private int c;//命令
    
    private Object d;//数据
    
    public JsonMessage(){
        
    }
    
    public JsonMessage(int v,int c,Object d){
        this.v = v;
        this.c = c;
        this.d = d;
    }

    /**
     * @return 消息版本号
     */
    public int getV() {
        return v;
    }

    /**
     * @param v 消息版本号
     */
    public void setV(int v) {
        this.v = v;
    }
    
    /**
     * @return 命令
     */
    public int getC() {
        return c;
    }

    /**
     * @param c 命令
     */
    public void setC(int c) {
        this.c = c;
    }

    /**
     * @return 数据
     */
    public Object getD() {
        return d;
    }

    /**
     * @param d 数据
     */
    public void setD(Object d) {
        this.d = d;
    }
    
    public <T> T getD(Class<T> classType){
	return classType.cast(d);
    }
}
