package ru.sfedu.organizer.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Element;

/**
 * Class Human
 */
public class Human  extends Generic{
    
    @Element
    @CsvBindByName 
    private String name;

    @Element 
    @CsvBindByName 
    private String surname;

    @Element (required = false)
    @CsvBindByName 
    private String patronymic;

    @Element (required = false)
    @CsvBindByName 
    private String biography;

    @Element (required = false)
    @CsvBindByName 
    private long birthDate;

    @Element (required = false)
    @CsvBindByName 
    private long deathDate;   

    /**
     *
     * @param id
     * @param type
     */
    public Human(long id, Types type) {
        super(id, type);
    }

    /**
     *
     * @param type
     */
    public Human(Types type) {
        super(type);
    }   
    
    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getSurname() {
        return surname;
    }

    /**
     *
     * @param surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     *
     * @return
     */
    public String getPatronymic() {
        return patronymic;
    }

    /**
     *
     * @param patronymic
     */
    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    /**
     *
     * @return
     */
    public String getBiography() {
        return biography;
    }

    /**
     *
     * @param biography
     */
    public void setBiography(String biography) {
        this.biography = biography;
    }

    /**
     *
     * @return
     */
    public long getBirthDate() {
        return birthDate;
    }

    /**
     *
     * @param birthDate
     */
    public void setBirthDate(long birthDate) {
        this.birthDate = birthDate;
    }

    /**
     *
     * @return
     */
    public long getDeathDate() {
        return deathDate;
    }

    /**
     *
     * @param deathDate
     */
    public void setDeathDate(long deathDate) {
        this.deathDate = deathDate;
    }
  
    
}
