package com.cll.demo.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cll.demo.example.dto.RecordQuery;
import com.cll.demo.example.entity.Record;

/**
 * @author chenliangliang
 * @date 2018/11/3
 */
public interface RecordService extends IService<Record> {

    /**
     * 保存颁奖记录
     * @param query
     */
    void saveRecord(RecordQuery query);
}
