
package ru.sfedu.organizer.api;

import ru.sfedu.organizer.model.Result;
import ru.sfedu.organizer.model.*;

/**
 *
 * @author sterie
 * @param <T>
 */
public interface IDataProvider<T extends Generic> {

    /**
     *
     * @param obj
     * @return
     */
    public Result addRecord(Note obj);
    
    /**
     *
     * @param obj
     * @return
     */
    public Result editRecord(Note obj);
    
    /**
     *
     * @param obj
     * @return
     */
    public Result deleteRecord(Note obj);
    
    /**
     *
     * @param obj
     * @return
     */
    public Result getRecordById(T obj);
    
    /**
     *
     * @param obj
     * @return
     */
    public Result getAllRecords(T obj);
    
    /**
     *
     * @param obj
     * @return
     */
    public Result findRecord(T obj);
   
}
