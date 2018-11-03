package com.cll.demo.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cll.demo.example.enums.StateEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenliangliang
 * @date 2018/11/3
 */
@Data
@TableName("t_record")
public class Record implements Serializable {

    @TableId(value = "c_id",type = IdType.ID_WORKER)
    private Long id;
    @TableField("c_honour_id")
    private Long honourId;
    @TableField("c_class_id")
    private Long classId;
    @TableField("c_start_date")
    private Date startDate;
    @TableField("c_end_date")
    private Date endDate;
    @TableField("c_state")
    private StateEnum state;
    @TableField("c_user_id")
    private Long userId;
    @TableField("c_create_time")
    private Date createTime;
    @TableField("c_update_time")
    private Date updateTime;
}
