package com.hxh.websocket;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.hxh.utils.JacksonMapper;
import java.io.IOException;
import java.io.StringWriter;

/**
 * 定义json消息格式
 * @author 黄香翰
 */
public class JsonMessage {
    
    /* 客户端命令 */
    public final static int USER_LOGIN = 1;//用户登入
    public final static int USER_LOGOUT = 2;//用户登出
    public final static int USER_STREAM_READY = 3;//用户视频准备好
    public final static int USER_CLOSE_STREAM = 4;//用户关闭视频流


    /* 服务端命令 */
    public final static int CONNECT_OPEN =1;//连接打开
    public final static int CONNECT_CLOSE =2;//连接关闭
    public final static int ERROR_COMMAND_VERSION_CODE = 3;//错误的版本号
    public final static int COMMAND_VERSION_NOT_SUPPORTED = 4;//不支持的命令版本
    public final static int UNRECOGNIZED_COMMAND = 5;//无法识别的命令
    public final static int LOGIN_SUCCESS = 6;//登入成功
    public final static int LOGIN_FAIL = 7;//登入失败
    
    private int v = 1;//消息版本号
    
    private int c;//命令
    
    private Object d;//数据
    
    public JsonMessage(){
        
    }
    
    public JsonMessage(int c,Object d){
        this.c = c;
        this.d = d;
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
    
    public static JsonMessage fromJson(String json)
	throws JsonParseException, JsonMappingException, IOException{
        return JacksonMapper.getInstance().readValue(json, JsonMessage.class);
    }
	
    public String toJson() throws JsonGenerationException, JsonMappingException, IOException{
            StringWriter sw = new StringWriter();
            JacksonMapper.getInstance().writeValue(sw, this);
            return sw.toString();
    }
}
