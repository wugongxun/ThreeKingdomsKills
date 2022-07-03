package com.wgx.threekingdomskills.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wgx.threekingdomskills.bean.Message;
import com.wgx.threekingdomskills.bean.RoomInfo;
import com.wgx.threekingdomskills.mapper.RoomInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;

/**
 * author:wgx
 * version:1.0
 */
@Service
public class RoomInfoService {
    @Resource
    private RoomInfoMapper roomInfoMapper;

    public Message queryAllRooms() {
        QueryWrapper<RoomInfo> wrapper = new QueryWrapper<>();
        wrapper.ne("status", 4);
        List<RoomInfo> roomInfos = roomInfoMapper.selectList(wrapper);
        if (!(roomInfos.isEmpty())) {
            return new Message(200, "ok", roomInfos);
        } else {
            return new Message(500, "没有房间", null);
        }
    }

    public void createRoom(HttpSession session) {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(now);
        String maxIdString = roomInfoMapper.queryMaxRoomIdInToday(date);
        RoomInfo roomInfo = null;
        if (maxIdString == null) {
            date = date.replaceAll("-", "");
            session.setAttribute("roomId", date + "001");
            session.setAttribute("playerNumber", "p1");
            roomInfo = new RoomInfo(date + "001", 1, 5, date, '1');
        } else {
            Long maxId = Long.valueOf(maxIdString);
            String id = String.valueOf(maxId + 1);
            session.setAttribute("roomId", id);
            session.setAttribute("playerNumber", "p1");
            roomInfo = new RoomInfo(id, 1, 5, date, '1');
        }
        roomInfoMapper.insert(roomInfo);
    }

    public void joinRoom(String roomId, HttpSession session) {
        RoomInfo roomInfo = roomInfoMapper.selectById(roomId);
        Integer currentMembers = roomInfo.getCurrentMembers();
        Integer maxMembers = roomInfo.getMaxMembers();
        if (currentMembers < maxMembers) {
            //当前人数加一
            roomInfo.setCurrentMembers(currentMembers + 1);
            if (currentMembers + 1 == maxMembers) {
                roomInfo.setStatus('3');
            }
            roomInfoMapper.updateById(roomInfo);
            session.setAttribute("roomId", roomInfo.getRoomId());
            session.setAttribute("playerNumber", "p" + (roomInfo.getCurrentMembers() + 1));
        }
    }

    public Message queryRoomInfoById(String roomId) {
        RoomInfo roomInfo = roomInfoMapper.selectById(roomId);
        if (roomInfo != null) {
            return new Message(200, "ok", roomInfo);
        } else {
            return new Message(500, "未查询到房间", null);
        }
    }

    public void quitRoom(String roomId, HttpSession session) throws InterruptedException {
        RoomInfo roomInfo = roomInfoMapper.selectById(roomId);
        Integer currentMembers = roomInfo.getCurrentMembers();
        roomInfo.setCurrentMembers(currentMembers - 1);
        if (currentMembers - 1 == 0) {
            roomInfo.setStatus('4');
        } else if (currentMembers == roomInfo.getMaxMembers()){
            roomInfo.setStatus('1');
        }
        roomInfoMapper.updateById(roomInfo);
    }

    public Message flushCurrentMembers(String roomId) {
        RoomInfo roomInfo = roomInfoMapper.selectById(roomId);
        if (roomInfo != null) {
            return new Message(200, "ok", roomInfo.getCurrentMembers());
        } else {
            return new Message(500, "为查询到房间", null);
        }
    }

    public Message changeMaxMembers(String roomId, Integer changeValue) {
        int i = roomInfoMapper.updateById(new RoomInfo(roomId, null, changeValue, null, null));
        if (i > 0) {
            return new Message(200, "ok", null);
        } else {
            return new Message(500, "错误", null);
        }
    }
}
