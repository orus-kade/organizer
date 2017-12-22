/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.organizer.api;

import com.opencsv.bean.CsvBindByPosition;

/**
 *
 * @author orus-kade
 */
public class Relation {
    
        @CsvBindByPosition (position = 0) 
        private long id1 = 0; 
        
        @CsvBindByPosition (position = 1)
        private long id2 = 0;

    /**
     *
     * @param id1
     * @param id2
     */
    public Relation(long id1, long id2) {
            this.id1 = id1;
            this.id2 = id2;            
        }
        
    /**
     *
     */
    public Relation(){}

    /**
     *
     * @return
     */
    public long getId1() {
            return id1;
        }

    /**
     *
     * @param id1
     */
    public void setId1(long id1) {
            this.id1 = id1;
        }

    /**
     *
     * @return
     */
    public long getId2() {
            return id2;
        }

    /**
     *
     * @param id2
     */
    public void setId2(long id2) {
            this.id2 = id2;
        }
}
