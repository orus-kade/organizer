/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.organizer.api;

import ru.sfedu.organizer.model.Generic;
import java.util.*;

/**
 *
 * @author orus-kade
 */
public class Response {
    
    private int status;
    private List<Generic> list = null;        

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Generic> getList() {
        return list;
    }

    public void setList(List<Generic> list) {
        this.list = list;
    }
    
}
