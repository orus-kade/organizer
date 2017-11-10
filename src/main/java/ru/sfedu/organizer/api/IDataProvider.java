
package ru.sfedu.organizer.api;

import ru.sfedu.organizer.model.Generic;


public interface IDataProvider {
    public Response addRecord(Generic obj);
    
    public Response editRecord(Generic obj);
    
    public Response deleteRecord(Generic obj);
    
    public Response getRecordById(Generic obj);
   
}
