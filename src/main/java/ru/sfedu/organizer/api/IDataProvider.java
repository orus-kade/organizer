
package ru.sfedu.organizer.api;

import ru.sfedu.organizer.model.Generic;
import ru.sfedu.organizer.model.Result;


public interface IDataProvider {
    public Result addRecord(Generic obj);
    
    public Result editRecord(Generic obj);
    
    public Result deleteRecord(Generic obj);
    
    public Result getRecordById(Generic obj);
    
    public Result getAllRecords(Generic obj);
   
}
