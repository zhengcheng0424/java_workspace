package com.mq.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("`temperature`")
public class Temperature {
    @TableId(type = IdType.AUTO)
//    @TableField(exist = false)
    private Long id;
    private String serialId;
    private String imei;
    @TableField(value = "`signal`")
    private Integer signal;
    @TableField(value = "`data`")
    private String data;
    private String address;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
