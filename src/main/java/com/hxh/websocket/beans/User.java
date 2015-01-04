/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hxh.websocket.beans;

import java.util.Date;

/**
 *
 * @author 黄香翰
 */
public class User {
    private String name;
    private String pass;
    private String addr;
    private String port;
    private Date lastLoginTime;
    private String driveName;
    private String driveversion;
    
    public User(){
        
    }

    /**
     * 创建用户对象
     * @param name 用户名
     * @param pass 密码
     * @param addr IP地址
     * @param port 视频流端口
     * @param driveName 设备名称
     * @param driveversion 版本
     */
    public User(String name, String pass, String addr, String port, String driveName, String driveversion) {
        this.name = name;
        this.pass = pass;
        this.addr = addr;
        this.port = port;
        this.driveName = driveName;
        this.driveversion = driveversion;
        
        this.lastLoginTime = new Date();
    }

    
    
    /**
     * @return 用户名
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 用户名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return 密码
     */
    public String getPass() {
        return pass;
    }

    /**
     * @param pass 设置密码
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * @return ip地址
     */
    public String getAddr() {
        return addr;
    }

    /**
     * @param addr 设置ip地址
     */
    public void setAddr(String addr) {
        this.addr = addr;
    }

    /**
     * @return 视频流端口
     */
    public String getPort() {
        return port;
    }

    /**
     * @param port 设置视频流端口
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * @return 最后登入时间
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * @param lastLoginTime 设置最后登入时间
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * @return 设备名称
     */
    public String getDriveName() {
        return driveName;
    }

    /**
     * @param driveName 设置设备名称
     */
    public void setDriveName(String driveName) {
        this.driveName = driveName;
    }

    /**
     * @return 设备版本
     */
    public String getDriveversion() {
        return driveversion;
    }

    /**
     * @param driveversion 设置设备版本
     */
    public void setDriveversion(String driveversion) {
        this.driveversion = driveversion;
    }

   
    
    /**
     * 更新用户状态
     * @param addr ip地址
     * @param port 视频流端口
     * @param driveName 设备名称
     * @param driveversion 设备版本
     */
    public void update(String addr, 
            String port, 
            String driveName, 
            String driveversion){
        this.addr = addr;
        this.port = port;
        this.driveName = driveName;
        this.driveversion = driveversion;
        this.lastLoginTime = new Date();
    }
}
