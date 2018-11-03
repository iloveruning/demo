package com.cll.demo.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chenliangliang
 * @date 2018/11/3
 */
@Data
@AllArgsConstructor
public class Result implements Serializable {

    private static final long serialVersionUID = 1597669254910754872L;

    private boolean flag;
    private String msg;
    private Object data;

    public static Result result(boolean flag,String msg,Object data){
        return new Result(flag,msg,data);
    }

    public static Result success(String msg,Object data){
        return result(true,msg,data);
    }

    public static Result ok(Object data){
        return success("OK",data);
    }

    public static Result ok(){
        return success("OK",null);
    }

    public static Result fail(String msg){
        return result(false,msg,null);
    }
}
