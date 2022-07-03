package com.wgx.threekingdomskills.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * author:wgx
 * version:1.0
 */
@Controller
public class ViewController {
    @GetMapping("/tkk")
    public String index() {
        return "index";
    }
    @GetMapping("/tkk/room")
    public String room() {
        return "room";
    }
    @GetMapping("/tkk/game")
    public String game(@RequestParam("gameId") Integer gameId, HttpSession session) {
        session.setAttribute("gameId", gameId);
        return "game";
    }
}
