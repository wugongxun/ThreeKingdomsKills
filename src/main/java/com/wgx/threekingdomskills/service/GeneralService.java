package com.wgx.threekingdomskills.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wgx.threekingdomskills.bean.General;
import com.wgx.threekingdomskills.bean.Message;
import com.wgx.threekingdomskills.mapper.GeneralMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * author:wgx
 * version:1.0
 */
@Service
public class GeneralService {
    @Resource
    private GeneralMapper generalMapper;

    @Value("${generalsNumberCanChoose}")
    private int generalsNumberCanChoose;

    public Message queryGeneral() {
        int generalsCount = generalMapper.selectCount(null).intValue();
        Random random = new Random();
        Integer[] arr = new Integer[generalsNumberCanChoose];
        int index = 0;
        out: while (index < arr.length) {
            int anInt = random.nextInt(generalsCount + 1);
            Integer anInteger = Integer.valueOf(anInt);
            for (int i = 0; i <= index; i++) {
                if (anInteger.equals(arr[i])) {
                    continue out;
                }
            }
            arr[index] = anInteger;
            index++;
        }
        QueryWrapper<General> wrapper = new QueryWrapper<>();
        wrapper.in("id", Arrays.asList(arr));
        List<General> generals = generalMapper.selectList(wrapper);
        return new Message(200, "ok", generals);
    }
}
