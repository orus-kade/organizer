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
