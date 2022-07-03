package com.wgx.threekingdomskills.controller;

import com.wgx.threekingdomskills.bean.Message;
import com.wgx.threekingdomskills.service.GameInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * author:wgx
 * version:1.0
 */
@Controller
@RequestMapping("/tkk")
public class GameInfoController {
    @Resource
    private GameInfoService gameInfoService;

    @GetMapping("/startGame/{roomId}")
    @ResponseBody
    public Message startGame(@PathVariable("roomId") String roomId) {
        return gameInfoService.startGame(roomId);
    }



    @GetMapping("/queryGameInfoById/{gameId}")
    @ResponseBody
    public Message  queryGameInfoById(@PathVariable("gameId") Integer gameId) {
        return gameInfoService.queryGameInfoById(gameId);
    }

    @GetMapping("/endGame/{gameId}")
    public String endGame(@PathVariable("gameId") Integer gameId, HttpSession session) {
        String roomId = (String) session.getAttribute("roomId");
        gameInfoService.endGame(gameId, roomId);
        return "redirect:/tkk/room";
    }
}
