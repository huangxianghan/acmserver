/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hxh.controller.console;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author 黄香翰
 */
@Controller
public class AdminLoginController {
    
    /**
     * 转到视图admin/login.jsp
     * 
     * @return String
    */
    @RequestMapping(value="/admin")
    public String loginPage(){
        return "admin/login";
    }
    
    /**
     * 转到视图admin/admin-index.jsp
     * @param email 
     * @param password
     * @return String
     */
    @RequestMapping(value="/adminlogin")
    public String login(String email,String password){
        
        if(email!=null&&email.equals("huangxianghan@icloud.com")){
            if(password!=null&&password.equals("hxh3506")){
                System.out.println("登入成功");
            }else{
                System.out.println("密码错误");
            }
        }else{
            System.out.println("用户不存在");
        }
        
        
        
        return "admin/console";
    }
    
    @RequestMapping(value="/adminlogin2")
    String loginToAndroidWebsocket(String email,String password){
        return "admin/console_1";
    }
    
    /**
     * 
     * @return 
     */
    @RequestMapping(value="/adminlogout")
    public String logout(){
        return "admin/login";
    }
    
    @RequestMapping(value="/test")
    public String test(){
        return "users/newjsp";
    }
    
}
