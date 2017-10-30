
package ru.sfedu.organizer.api;

import ru.sfedu.organizer.model.Generic;


public interface IDataProvider {
    public int saveRecord(Generic obj);
    
    public int deleteRecord(Generic obj);
    
    public Generic getRecordById(Generic obj);
   
}
