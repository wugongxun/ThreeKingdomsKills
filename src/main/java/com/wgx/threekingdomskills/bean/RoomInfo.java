package com.wgx.threekingdomskills.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * author:wgx
 * version:1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("room_info")
public class RoomInfo {
    @TableId(type = IdType.INPUT)
    private String roomId;
    private Integer currentMembers;
    private Integer maxMembers;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String creationTime;
    //1表示正在准备，2表示正在游戏，3表示已满员，4表示已被销毁
    private Character status;
}
