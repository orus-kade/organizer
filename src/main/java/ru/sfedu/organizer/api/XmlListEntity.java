/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.organizer.api;

import java.util.List;
import org.simpleframework.xml.ElementList;
import ru.sfedu.organizer.model.Generic;

/**
 *
 * @author sterie
 */
public class XmlListEntity {
    
    @ElementList
    private List<Generic> list;

    public XmlListEntity() {
    }

    public XmlListEntity(List<Generic> list) {
        this.list = list;
    }
        
    public List<Generic> getList() {
        return list;
    }

    public void setList(List<Generic> list) {
        this.list = list;
    }
    
    
}
