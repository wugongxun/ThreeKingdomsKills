package com.wgx.threekingdomskills;

import com.wgx.threekingdomskills.bean.GameInfo;
import com.wgx.threekingdomskills.bean.Message;
import com.wgx.threekingdomskills.bean.RoomInfo;
import com.wgx.threekingdomskills.mapper.GameInfoMapper;
import com.wgx.threekingdomskills.mapper.RoomInfoMapper;
import com.wgx.threekingdomskills.service.GeneralService;
import com.wgx.threekingdomskills.service.RoomInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

@SpringBootTest
class ThreeKingdomsKillsApplicationTests {
    @Resource
    private RoomInfoMapper roomInfoMapper;
    @Resource
    private GameInfoMapper gameInfoMapper;
    @Resource
    private RoomInfoService roomInfoService;
    @Resource
    private GeneralService generalService;

    @Test
    void contextLoads() {
//        List<RoomInfo> roomInfos = roomInfoMapper.selectList(null);
//        System.out.println(roomInfos);
//        GameInfo gameInfo = gameInfoMapper.selectById("20220518001");
//        System.out.println(gameInfo);
//        Message message = roomInfoService.queryAllRooms();
//        System.out.println(message);
//        roomInfoService.createRoom();
        String roomId = "20220519001";
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
                    identities[i] = "主公";
                    break;
                case 2:
                    identities[i] = "忠臣";
                    break;
                case 3:
                    identities[i] = "内奸";
                    break;
                case 4:
                case 5:
                case 6:
                    identities[i] = "反贼";
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
        System.out.println(gameInfo.getGameId());
    }

    @Test
    public void test() {
        int a = 5;
        Random random = new Random();
        int[] arr = new int[a];
        int index = 0;
        out: while (index < arr.length) {
            int anInt = random.nextInt(21);
            for (int i = 0; i <= index; i++) {
                if (arr[i] == anInt) {
                    continue out;
                }
            }
            arr[index] = anInt;
            index++;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }

    @Test
    public void test2() {
        generalService.queryGeneral();
    }

}
