package ru.sfedu.organizer.model;


import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import java.util.*;
import static ru.sfedu.organizer.model.Types.*;


/**
 * Class Opera
 */
public class Opera extends Generic{

  //
  // Fields
  //
  @CsvBindByPosition (position = 0)
  private long id;
    
  @CsvBindByPosition (position = 1)  
  private String title;
  
  @CsvBindByPosition (position = 2)
  private String history;
  
  @CsvCustomBindByPosition (converter = Generic.class, position = 3)
  private Generic libretto;
  
  private List<Generic> composers;
  private List<Generic> authors;
  private List<Generic> aries;
  
  
  //
  // Constructors
  //
  public Opera () {
      super(OPERA);
  };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Generic> getComposers() {
        return composers;
    }

    public void setComposers(List<Generic> composers) {
        this.composers = composers;
    }

    public List<Generic> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Generic> authors) {
        this.authors = authors;
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

    
}
