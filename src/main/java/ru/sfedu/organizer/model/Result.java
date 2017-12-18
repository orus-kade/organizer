package ru.sfedu.organizer.model;

import java.util.*;
import ru.sfedu.organizer.model.Generic;

/**
 *
 * @author orus-kade
 */

public class Result {
    
    private ResultStatuses status;
    private String message;
    private List<Generic> list = new ArrayList<Generic>(); 
    
    public Result(List<Generic> list){
        this.list.addAll(list);
    }

    public Result() {
    }

    public Result(ResultStatuses status) {
        this.status = status;
    }

    
    
    public Result(ResultStatuses status, String message, List<Generic> list) {
        this.list.addAll(list);
        this.status = status;
        this.message = message;
    }   
    
    public Result(ResultStatuses status, String message) {
        this.status = status;
        this.message = message;
    }    

    public Result(ResultStatuses status, List<Generic> list) {
        this.list.addAll(list);
        this.status = status;
    }
    
    public ResultStatuses getStatus() {
        return status;
    }

    public void setStatus(ResultStatuses status) {
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
        this.list.addAll(list);
    }
    
}
