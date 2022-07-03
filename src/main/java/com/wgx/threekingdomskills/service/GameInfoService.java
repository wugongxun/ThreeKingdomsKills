package com.wgx.threekingdomskills.service;

import com.wgx.threekingdomskills.bean.GameInfo;
import com.wgx.threekingdomskills.bean.Message;
import com.wgx.threekingdomskills.bean.RoomInfo;
import com.wgx.threekingdomskills.mapper.GameInfoMapper;
import com.wgx.threekingdomskills.mapper.RoomInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * author:wgx
 * version:1.0
 */
@Service
public class GameInfoService {
    @Resource
    private GameInfoMapper gameInfoMapper;

    @Resource
    private RoomInfoMapper roomInfoMapper;

    public Message startGame(String roomId) {
        System.out.println(roomId);
        RoomInfo roomInfo = roomInfoMapper.selectById(roomId);
        Integer maxMembers = roomInfo.getMaxMembers();
        int[] arr = new int[maxMembers];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i + 1;
        }
        Random random = new Random();
        for(int i = 0; i < arr.length; i++){
            int index = random.nextInt(arr.length);
            //temp：临时变量，存储index位置处的值
            int temp = arr[index];
            arr[index] = arr[i];
            arr[i] = temp;
        }
        String[] identities = new String[maxMembers];
        for (int i = 0; i < identities.length; i++) {
            switch (arr[i]) {
                case 1:
                    identities[i] = "zhugong.jpg";
                    break;
                case 2:
                    identities[i] = "zhongchen.jpg";
                    break;
                case 3:
                    identities[i] = "neijian.jpg";
                    break;
                case 4:
                case 5:
                case 6:
                    identities[i] = "fanzei.jpg";
                    break;
            }
        }
        for (int i = 0; i < arr.length; i++) {
            switch (arr[i]) {
                case 1:

            }
        }
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = sdf.format(date);
        GameInfo gameInfo;
        if (maxMembers == 5) {
            gameInfo = new GameInfo(null, roomId, identities[0], identities[1], identities[2], identities[3], identities[4], null, null, now, null);
        } else {
            gameInfo = new GameInfo(null, roomId, identities[0], identities[1], identities[2], identities[3], identities[4], identities[5], null, now, null);
        }
        gameInfoMapper.insert(gameInfo);
        return new Message(200, "ok", gameInfo.getGameId());
    }

    public Message queryGameInfoById(Integer gameId) {
        GameInfo gameInfo = gameInfoMapper.selectById(gameId);
        return new Message(200, "ok", gameInfo);
    }

    public void endGame(Integer gameId, String roomId) {
        roomInfoMapper.updateById(new RoomInfo(roomId, null, null, null, '1'));
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = sdf.format(date);
        gameInfoMapper.updateById(new GameInfo(gameId, null, null, null, null, null, null, null, null, null, now));
    }
}
