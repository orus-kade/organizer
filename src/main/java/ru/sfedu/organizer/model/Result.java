package ru.sfedu.organizer.model;

import java.util.*;

/**
 *
 * @author orus-kade
 */

public class Result {
    
    private String status;
    private String message;
    private List<Generic> list = null; 
    
    public Result(List<Generic> list){
        this.list = list;
    }

    public Result() {
    }

    public Result(String status, String message, List<Generic> list) {
        this.list = list;
        this.status = status;
        this.message = message;
    }   
    
    public Result(String status, String message) {
        this.status = status;
        this.message = message;
    }    

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Generic> getList() {
        return list;
    }

    public void setList(List<Generic> list) {
        this.list = list;
    }
    
}
