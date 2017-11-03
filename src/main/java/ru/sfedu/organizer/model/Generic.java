
package ru.sfedu.organizer.model;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Generic extends AbstractBeanField<Object>{

   // @CsvBindByPosition(position = 0) 
    private long id;
    private Types type;

    public Generic(){}
    
    public Generic(Types type) {
        this.type = type;
    }    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {

        this.id = id;
    }

    public Types getType() {
        return type;
    }

    public void setType(Types type) {
        this.type = type;
    }

    @Override
    public String toString() {
        JSONObject jsonObject  = new JSONObject();
        jsonObject.put("id", getId());
        jsonObject.put("type", getType().toString());
        return jsonObject.toJSONString();
    }

    @Override
    protected Object convert(String string) throws CsvDataTypeMismatchException, CsvConstraintViolationException {        
        try {
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(string);
            setId((long) jsonObject.get("id"));
            setType(Types.valueOf((String) jsonObject.get("type")));
        } catch (ParseException ex) {
            Logger.getLogger(Generic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
