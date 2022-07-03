package com.wgx.threekingdomskills.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wgx.threekingdomskills.bean.RoomInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoomInfoMapper extends BaseMapper<RoomInfo> {
    String queryMaxRoomIdInToday(String date);
}
