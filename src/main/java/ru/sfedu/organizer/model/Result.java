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
    
    /**
     *
     * @param list
     */
    public Result(List<Generic> list){
        this.list.addAll(list);
    }

    /**
     *
     */
    public Result() {
    }

    /**
     *
     * @param status
     */
    public Result(ResultStatuses status) {
        this.status = status;
    }

    /**
     *
     * @param status
     * @param message
     * @param list
     */
    public Result(ResultStatuses status, String message, List<Generic> list) {
        this.list.addAll(list);
        this.status = status;
        this.message = message;
    }   
    
    /**
     *
     * @param status
     * @param message
     */
    public Result(ResultStatuses status, String message) {
        this.status = status;
        this.message = message;
    }    

    /**
     *
     * @param status
     * @param list
     */
    public Result(ResultStatuses status, List<Generic> list) {
        this.list.addAll(list);
        this.status = status;
    }
    
    /**
     *
     * @return
     */
    public ResultStatuses getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(ResultStatuses status) {
        this.status = status;
    }

    /**
     *
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     */
    public List<Generic> getList() {
        return list;
    }

    /**
     *
     * @param list
     */
    public void setList(List<Generic> list) {
        this.list.clear();
        this.list.addAll(list);
    }
    
}
