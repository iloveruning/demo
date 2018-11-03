package com.cll.demo.example.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author chenliangliang
 * @date 2018/11/3
 */
@Getter
@AllArgsConstructor
public enum StateEnum {

    /**
     * 最新记录
     */
    NEWEST(1, "最新记录"),
    /**
     * 历史记录
     */
    HISTORY(0, "历史记录");

    @EnumValue
    private final Integer value;
    private final String desc;

}
