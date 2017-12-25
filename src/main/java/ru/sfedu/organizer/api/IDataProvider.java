
package ru.sfedu.organizer.api;

import ru.sfedu.organizer.model.Result;
import ru.sfedu.organizer.model.*;

/**
 *
 * @author orus-kade
 * @param <T>
 */
public interface IDataProvider<T extends Generic> {

    /**
     *
     * @param obj to add
     * @return Result
     */
    public Result addRecord(Note obj);
    
    /**
     *
     * @param obj to edit
     * @return Result
     */
    public Result editRecord(Note obj);
    
    /**
     *
     * @param obj to delete
     * @return Result
     */
    public Result deleteRecord(Note obj);
    
    /**
     *
     * @param obj to find by id
     * @return Result
     */
    public Result getRecordById(T obj);
    
    /**
     *
     * @param obj defines type of objects
     * @return Result
     */
    public Result getAllRecords(T obj);
    
    /**
     *
     * @param obj with parameters of search. Search by fields that are not null
     * @return Result
     */
    public Result findRecord(T obj);
   
}
