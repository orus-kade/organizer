package ru.sfedu.organizer.model;


import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import java.util.*;
import static ru.sfedu.organizer.model.Types.*;


public class Opera extends Generic{

  @CsvBindByPosition (position = 0)
  private long id;
    
  @CsvBindByPosition (position = 1)  
  private String title;
  
  @CsvBindByPosition (position = 2)
  private String history;
  
  @CsvCustomBindByPosition (converter = Generic.class, position = 3)
  private Generic libretto;
  
  private List<Generic> aries;
  
  public Opera () {
      super(OPERA);
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

    public List<Generic> getAries() {
        return aries;
    }

    public void setAries(List<Generic> aries) {
        this.aries = aries;
    }

    public Generic getLibretto() {
        return libretto;
    }

    public void setLibretto(Generic libretto) {
        this.libretto = libretto;
    }

    @Override
    public String toString() {
        return "Opera{" + "id=" + getId() + ", title=" + title + ", history=" + history + ", libretto=" + libretto + ", aries=" + aries + '}';
    }

    
}
