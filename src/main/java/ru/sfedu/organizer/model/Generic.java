
package ru.sfedu.organizer.model;

/**
 *
 * @author sterie
 */
public  abstract class Generic {
    
    /**
     *
     * @return
     */
    public abstract long getId();

    /**
     *
     * @param id
     */
    public abstract void setId(long id);
    
    /**
     *
     * @return
     */
    public abstract Types getType();  
     
}
