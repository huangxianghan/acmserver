/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hxh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Administrator
 */
@Controller
@RequestMapping(value="/users")
public class UsersController {
    
    /**
     * 转向到WEB-INF/jsp/users/list.jsp
     * @return 
     */
    @RequestMapping(value="/list")
    public String list(){
        return "users/list";
    }
    
}
