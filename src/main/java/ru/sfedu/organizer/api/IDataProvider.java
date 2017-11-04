
package ru.sfedu.organizer.api;

import ru.sfedu.organizer.model.Generic;


public interface IDataProvider {
    public int addRecord(Generic obj);
    
    public int editRecord(Generic obj);
    
    public int deleteRecord(Generic obj);
    
    public Generic getRecordById(Generic obj);
   
}
