
package ru.sfedu.organizer.model;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public abstract class Generic extends AbstractBeanField<Object>{

    public abstract long getId();

    public abstract void setId(long id);

    public abstract Types getType();

    public abstract void setType(Types type);

    @Override
    public String toString() {
        JSONObject jsonObject  = new JSONObject();
        jsonObject.put("id", getId());
        jsonObject.put("type", getType().toString());
        return jsonObject.toJSONString();
    }

    @Override
    protected Object convert(String string) throws CsvDataTypeMismatchException, CsvConstraintViolationException {        
        Generic obj = null;
        try {
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(string);
              Types type = Types.valueOf((String) jsonObject.get("type")); 
                switch (type){
                  case ARIA : obj = new Aria();
                    break;
                  case COMPOSER : obj = new Composer();
                      break;
                  //case LIBRETTO : obj = new Libretto();
                  //    break;
                  case OPERA : obj = new Opera(); 
                       break;
                 //  case SINGER : obj = new Singer();
                 //     break;
                 //case AUTHOR : obj = new Author();
                 //     break;
                 //case NOTE: obj = new Note();  
                }
                obj.setId((long) jsonObject.get("id"));
        } catch (ParseException ex) {
            Logger.getLogger(Generic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obj;
    }
   
}
