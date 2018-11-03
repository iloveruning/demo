package com.cll.demo.example.controller;

import com.cll.demo.example.dto.RecordQuery;
import com.cll.demo.example.dto.Result;
import com.cll.demo.example.dto.group.RecordGroup;
import com.cll.demo.example.service.RecordService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author chenliangliang
 * @date 2018/11/3
 */
@RestController
@RequestMapping("/record")
public class RecordController {


    private RecordService recordService;

    @PostMapping
    public Object saveRecord(@Validated({RecordGroup.SaveRecord.class}) @RequestBody RecordQuery query){
        recordService.saveRecord(query);
        return Result.ok();
    }



    @GetMapping("/honour/{honourId}")
    public Object listRecord(@Validated({RecordGroup.ListRecord.class}) RecordQuery query){
        return Result.ok();
    }





    protected RecordController(RecordService recordService){
        this.recordService=recordService;
    }
}
