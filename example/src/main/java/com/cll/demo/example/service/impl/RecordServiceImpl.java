package com.cll.demo.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cll.demo.example.dto.RecordMapStruct;
import com.cll.demo.example.dto.RecordQuery;
import com.cll.demo.example.entity.Record;
import com.cll.demo.example.enums.StateEnum;
import com.cll.demo.example.mapper.RecordMapper;
import com.cll.demo.example.service.RecordService;
import com.cll.demo.redis.annotation.Lock;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * @author chenliangliang
 * @date 2018/11/3
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {


    private RecordMapStruct mapStruct;

    @Override
    @Lock(lockKey = "saveRecord", expireTime = 5000,timeout = 2000)
    public void saveRecord(RecordQuery query) {
        List<String> classIds = query.getClassIds().stream().distinct().collect(Collectors.toList());
        Long honourId = Long.valueOf(query.getHonourId());

        List<Record> records = baseMapper.selectList(new QueryWrapper<Record>().lambda()
                .eq(Record::getHonourId, honourId)
                .in(Record::getClassId, classIds)
                .eq(Record::getState, StateEnum.NEWEST));

        Map<String, Record> recordMap = records.stream().collect(Collectors.toMap(record -> record.getClassId().toString(), record -> record));

        List<Record> saveOrUpdateList = new ArrayList<>(classIds.size());

        for (String classId : classIds) {
            Record value = recordMap.get(classId);

            if (value != null) {
                Record record = new Record();
                record.setId(value.getId());
                record.setState(StateEnum.HISTORY);
                saveOrUpdateList.add(record);
            }

            Record record = mapStruct.toRecord(query);
            record.setClassId(Long.valueOf(classId));
            record.setState(StateEnum.NEWEST);
            saveOrUpdateList.add(record);
        }

        saveOrUpdateBatch(saveOrUpdateList);
    }


    protected RecordServiceImpl(RecordMapStruct mapStruct) {
        this.mapStruct = mapStruct;
    }
}
