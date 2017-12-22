/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.organizer.api;

import java.util.ArrayList;
import java.util.List;
import org.simpleframework.xml.ElementList;
import ru.sfedu.organizer.model.Generic;

/**
 *
 * @author orus-kade
 */
public class XmlListEntity {
    
    @ElementList
    private List<Generic> list = new ArrayList<Generic>();

    /**
     *
     */
    public XmlListEntity() {
    }

    /**
     *
     * @param list
     */
    public XmlListEntity(List<Generic> list) {
        this.list.addAll(list);
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
