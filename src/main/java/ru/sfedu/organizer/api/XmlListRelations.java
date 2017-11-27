/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.organizer.api;

import java.util.List;
import org.simpleframework.xml.ElementList;


/**
 *
 * @author sterie
 */
public class XmlListRelations {
    
    @ElementList
    private List<Relation> list;

    public XmlListRelations() {
    }

    public XmlListRelations(List<Relation> list) {
        this.list = list;
    }
        
    public List<Relation> getList() {
        return list;
    }

    public void setList(List<Relation> list) {
        this.list = list;
    }
    
    
}
