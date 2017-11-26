package ru.sfedu.organizer.model;


import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import java.util.*;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import static ru.sfedu.organizer.model.Types.*;


public class Opera extends Generic{

  @Attribute  
  @CsvBindByPosition (position = 0)
  private long id;
  
  private Types type;
  
  @Element (required = false)  
  @CsvBindByPosition (position = 1)  
  private String title;
  
  @Element (required = false)
  @CsvBindByPosition (position = 2)
  private String history;
  
  @Element (required = false)
  @CsvBindByPosition (position = 3)
  private long librettoId;
  
  private List<Long> aries;
  
  public Opera () {
      this.type = OPERA;
  };
  
  public Opera (long id) {
      this.id = id;
      this.type = OPERA;
  };
  
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public long getLibrettoId() {
        return librettoId;
    }

    public void setLibrettoId(long librettoId) {
        this.librettoId = librettoId;
    }

    public List<Long> getAries() {
        return aries;
    }

    public void setAries(List<Long> aries) {
        this.aries = aries;
    }

    

    

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public Types getType() {
        return type;
    }
       
}
