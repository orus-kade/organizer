package ru.sfedu.organizer.model;


import com.opencsv.bean.CsvBindByPosition;
import java.util.*;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import static ru.sfedu.organizer.model.Types.*;


/**
 * Class Singer
 */
public class Singer extends Human {

  //
  // Fields
  //
  
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

  @Element (required = false)
  @CsvBindByPosition (position = 7) 
  private String voice;
  
  private List<Long> aries = new ArrayList<Long>();
  private List<Long> notes = new ArrayList<Long>();

    /**
     *
     */
    public Singer () {
      this.type = SINGER;
  };
  
    /**
     *
     * @param id
     */
    public Singer (long id) {
      this.id = id;
      this.type = SINGER;
  };
  
    /**
     *
     * @return
     */
    public String getVoice() {
        return voice;
    }

    /**
     *
     * @param voice
     */
    public void setVoice(String voice) {
        this.voice = voice;
    }

    /**
     *
     * @return
     */
    public List<Long> getAries() {
        return aries;
    }

    /**
     *
     * @param aries
     */
    public void setAries(List<Long> aries) {
        this.aries.clear();
        this.aries.addAll(aries);
    }   

    /**
     *
     * @return
     */
    public List<Long> getNotes() {
        return notes;
    }

    /**
     *
     * @param notes
     */
    public void setNotes(List<Long> notes) {
        this.notes.clear();
        this.notes.addAll(notes);
    }   
    
    /**
     *
     * @return
     */
    @Override
    public long getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    @Override
    public void setId(long id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    @Override
    public Types getType() {
        return type;
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    @Override
    public String getBiography() {
        return biography;
    }

    /**
     *
     * @param biography
     */
    @Override
    public void setBiography(String biography) {
        this.biography = biography;
    }

    /**
     *
     * @return
     */
    @Override
    public long getBirthDate() {
        return birthDate;
    }

    /**
     *
     * @param birthDate
     */
    @Override
    public void setBirthDate(long birthDate) {
        this.birthDate = birthDate;
    }

    /**
     *
     * @return
     */
    @Override
    public long getDeathDate() {
        return deathDate;
    }

    /**
     *
     * @param deathDate
     */
    @Override
    public void setDeathDate(long deathDate) {
        this.deathDate = deathDate;
    }

    /**
     *
     * @return
     */
    @Override
    public String getSurname() {
        return this.surname;
    }

    /**
     *
     * @param surname
     */
    @Override
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     *
     * @return
     */
    @Override
    public String getPatronymic() {
        return this.patronymic;
    }

    /**
     *
     * @param patronymic
     */
    @Override
    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @Override
    public String toString() {
        return "Singer{" + "id=" + id + ", type=" + type + ", name=" + name + ", surname=" + surname + ", patronymic=" + patronymic + ", biography=" + biography + ", birthDate=" + new Date(birthDate) + ", deathDate=" + new Date(deathDate) + ", voice=" + voice + ", aries=" + aries + ", notes=" + notes +'}';
    }    
}
