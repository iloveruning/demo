package com.cll.demo.example.dto;

import com.cll.demo.example.entity.Record;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @author chenliangliang
 * @date 2018/11/3
 */
@Mapper(componentModel = "spring")
public interface RecordMapStruct {


    @Mappings(value = {
            @Mapping(source = "query.honourId",target = "honourId"),
            @Mapping(source = "query.userId",target = "userId"),
            @Mapping(source = "query.startDate",target = "startDate"),
            @Mapping(source = "query.endDate",target = "endDate")
    })
    Record toRecord(RecordQuery query);

}
