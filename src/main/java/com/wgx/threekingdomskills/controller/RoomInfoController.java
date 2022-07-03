package com.wgx.threekingdomskills.controller;

import com.wgx.threekingdomskills.bean.Message;
import com.wgx.threekingdomskills.service.RoomInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * author:wgx
 * version:1.0
 */
@Slf4j
@RestController
@RequestMapping("/tkk")
public class RoomInfoController {
    @Resource
    private RoomInfoService roomInfoService;

    @GetMapping("/queryAllRooms")
    public Message queryAllRooms() {
        return roomInfoService.queryAllRooms();
    }

    @GetMapping("/createRoom")
    public void createRoom(HttpSession session) {
        roomInfoService.createRoom(session);
    }

    @GetMapping("/joinRoom/{roomId}")
    public void joinRoom(@PathVariable("roomId") String roomId, HttpSession session) {
        roomInfoService.joinRoom(roomId, session);
    }

    @GetMapping("/queryRoomInfoById/{roomId}")
    public Message queryRoomInfoById(@PathVariable("roomId") String roomId) {
        return roomInfoService.queryRoomInfoById(roomId);
    }

    @GetMapping("/quitRoom/{roomId}")
    public void quitRoom(@PathVariable("roomId") String roomId, HttpSession session) throws InterruptedException {
        roomInfoService.quitRoom(roomId, session);
    }

    @GetMapping("/flushCurrentMembers/{roomId}")
    public Message flushCurrentMembers(@PathVariable("roomId") String roomId) {
        return roomInfoService.flushCurrentMembers(roomId);
    }

    @GetMapping("/changeMaxMembers/{roomId}/{changeValue}")
    public Message changeMaxMembers(@PathVariable("roomId") String roomId, @PathVariable("changeValue") Integer changValue) {
        return roomInfoService.changeMaxMembers(roomId, changValue);
    }
}
