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

    public Human(long id, Types type) {
        super(id, type);
    }

    public Human(Types type) {
        super(type);
    }   
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public long getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(long birthDate) {
        this.birthDate = birthDate;
    }

    public long getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(long deathDate) {
        this.deathDate = deathDate;
    }
  
    
}
