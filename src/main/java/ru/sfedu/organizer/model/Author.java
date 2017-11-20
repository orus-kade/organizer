package ru.sfedu.organizer.model;


import com.opencsv.bean.CsvBindByPosition;
import java.util.*;
import static ru.sfedu.organizer.model.Types.*;
    


public class Author extends Human {
    
    
  @CsvBindByPosition (position = 0)    
  private long id;
  
  @CsvBindByPosition (position = 1)  
  private String name;
  
  @CsvBindByPosition (position = 2)  
  private String biography;
  
  @CsvBindByPosition (position = 3)   
  private String birthDate;
  
  @CsvBindByPosition (position = 4)   
  private String deathDate;    

  private List<Generic> aries;
  private List<Generic> librettos;
  

  public Author () {
      super(AUTHOR);
  };
  
    public List<Generic> getAries() {
        return aries;
    }

    public void setAries(List<Generic> aries) {
        this.aries = aries;
    }

    public List<Generic> getLibrettos() {
        return librettos;
    }

    public void setLibrettos(List<Generic> librettos) {
        this.librettos = librettos;
    }

    @Override
    public String toString() {
        return "Author{" + "id=" + getId() + ", name=" + getName() + ", biography=" + getBiography() + ", birthDate=" + getBirthDate() + ", deathDate=" + getDeathDate() + ", aries=" + aries + ", librettos=" + librettos + '}';
    }

 
    
}
