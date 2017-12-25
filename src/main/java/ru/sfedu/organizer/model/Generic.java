
package ru.sfedu.organizer.model;

import com.opencsv.bean.CsvBindByName;
import org.simpleframework.xml.Attribute;

/**
 *
 * @author sterie
 */
public class Generic {
    
    @Attribute  
    @CsvBindByName
    private long id; 
    
    private Types type;

    /**
     *
     * @param id
     * @param type
     */
    public Generic(long id, Types type) {
        this.id = id;
        this.type = type;
    }

    /**
     *
     * @param type
     */
    public Generic(Types type) {
        this.type = type;
    }
    
    /**
     *
     * @param id
     */
    public void setId(long id){
        this.id = id;
    }
    
    /**
     *
     * @return
     */
    public long getId (){
        return this.id;
    }

    /**
     *
     * @return
     */
    public Types getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(Types type) {
        this.type = type;
    }
    
    
}
