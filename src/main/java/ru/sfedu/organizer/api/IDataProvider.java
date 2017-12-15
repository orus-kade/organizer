
package ru.sfedu.organizer.api;

import ru.sfedu.organizer.model.Result;
import ru.sfedu.organizer.model.*;


public interface IDataProvider<T extends Generic> {
    public Result addRecord(Note obj);
    
    public Result editRecord(Note obj);
    
    public Result deleteRecord(Note obj);
    
    public Result getRecordById(T obj);
    
    public Result getAllRecords(T obj);
    
    public Result findRecord(T obj);
   
}
