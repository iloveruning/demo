package com.cll.demo.example.dto;

import com.cll.demo.example.dto.group.RecordGroup;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author chenliangliang
 * @date 2018/11/3
 */
@Data
public class RecordQuery implements Serializable {

    @NotNull(groups = {RecordGroup.SaveRecord.class,RecordGroup.ListRecord.class}, message = "荣誉类型id不能为空")
    @Length(min = 1, max = 20, message = "荣誉类型id长度不能小于1或超过20", groups = {RecordGroup.SaveRecord.class})
    private String honourId;
    @NotNull(groups = {RecordGroup.SaveRecord.class}, message = "班级uid列表不能为空")
    @Size(min = 1, max = 20, groups = {RecordGroup.SaveRecord.class}, message = "班级id列表数量须大于1小于20")
    private List<String> classIds;
    @NotNull(groups = {RecordGroup.SaveRecord.class}, message = "荣誉开始时间不能为空")
    private Date startDate;
    @NotNull(groups = {RecordGroup.SaveRecord.class}, message = "荣誉截止时间不能为空")
    private Date endDate;
    @NotNull(groups = {RecordGroup.SaveRecord.class}, message = "颁发者不能为空")
    private String userId;
}
