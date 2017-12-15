package ru.sfedu.organizer.model;


import com.opencsv.bean.CsvBindByPosition;
import java.util.*;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import static ru.sfedu.organizer.model.Types.*;
    


public class Author extends Human {
    
  @Attribute  
  @CsvBindByPosition (position = 0)    
  private long id;
  
  private Types type;
  
  @Element
  @CsvBindByPosition (position = 1)  
  private String name;
  
  @Element 
  @CsvBindByPosition (position = 2)  
  private String surname;
  
  @Element (required = false)
  @CsvBindByPosition (position = 3)  
  private String patronymic;
  
  @Element (required = false)
  @CsvBindByPosition (position = 4)  
  private String biography;
  
  @Element (required = false)
  @CsvBindByPosition (position = 5)   
  private long birthDate;
  
  @Element (required = false)
  @CsvBindByPosition (position = 6)   
  private long deathDate;   
  

  private List<Long> aries;
  private List<Long> librettos;
  private List<Long> notes;
  

  public Author () {
      this.type = AUTHOR;
  };
  
  public Author (long id) {
      this.id = id;
      this.type = AUTHOR;
  };

    public List<Long> getAries() {
        return aries;
    }

    public void setAries(List<Long> aries) {
        this.aries = aries;
    }

    public List<Long> getLibrettos() {
        return librettos;
    }

    public void setLibrettos(List<Long> librettos) {
        this.librettos = librettos;
    }

    public List<Long> getNotes() {
        return notes;
    }

    public void setNotes(List<Long> notes) {
        this.notes = notes;
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

  @Override
    public String getName() {
        return name;
    }

  @Override
    public void setName(String name) {
        this.name = name;
    }

  @Override
    public String getBiography() {
        return biography;
    }

  @Override
    public void setBiography(String biography) {
        this.biography = biography;
    }

  @Override
    public long getBirthDate() {
        return birthDate;
    }

  @Override
    public void setBirthDate(long birthDate) {
        this.birthDate = birthDate;
    }

  @Override
    public long getDeathDate() {
        return deathDate;
    }

  @Override
    public void setDeathDate(long deathDate) {
        this.deathDate = deathDate;
    }

    @Override
    public String getSurname() {
        return this.surname;
    }

    @Override
    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String getPatronymic() {
        return this.patronymic;
    }

    @Override
    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @Override
    public String toString() {
        return "Author{" + "id=" + id + ", type=" + type + ", name=" + name + ", surname=" + surname + ", patronymic=" + patronymic + ", biography=" + biography + ", birthDate=" + birthDate + ", deathDate=" + deathDate + ", aries=" + aries + ", librettos=" + librettos + ", notes=" + notes + '}';
    }
 
}
