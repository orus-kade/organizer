/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.organizer.api;

import ru.sfedu.organizer.model.Relation;
import java.util.List;
import org.simpleframework.xml.ElementList;


/**
 *
 * @author orus-kade
 */
public class XmlListRelations {
    
    @ElementList
    private List<Relation> list;

    /**
     *
     */
    public XmlListRelations() {
    }

    /**
     *
     * @param list
     */
    public XmlListRelations(List<Relation> list) {
        this.list = list;
    }
        
    /**
     *
     * @return
     */
    public List<Relation> getList() {
        return list;
    }

    /**
     *
     * @param list
     */
    public void setList(List<Relation> list) {
        this.list = list;
    }
    
    
}
