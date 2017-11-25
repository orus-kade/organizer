
package ru.sfedu.organizer.api;

import ru.sfedu.organizer.model.Generic;


public interface IDataProvider<T extends Generic> {
    public Result addRecord(T obj);
    
    public Result editRecord(T obj);
    
    public Result deleteRecord(T obj);
    
    public Result getRecordById(T obj);
    
    public Result getAllRecords(T obj);
   
}
