
package ru.sfedu.organizer.model;

import com.opencsv.bean.CsvBindByName;
import org.simpleframework.xml.Attribute;

/**
 *
 * @author sterie
 */
public class Generic {
    
    @Attribute  
    @CsvBindByName//(position = 0) 
    private long id; 
    
    private Types type;

    public Generic(long id, Types type) {
        this.id = id;
        this.type = type;
    }

    public Generic(Types type) {
        this.type = type;
    }
    
    public void setId(long id){
        this.id = id;
    }
    
    public long getId (){
        return this.id;
    }

    public Types getType() {
        return type;
    }

    public void setType(Types type) {
        this.type = type;
    }
    
    
}
