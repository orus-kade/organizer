package ru.sfedu.organizer.model;

/**
 * Class Human
 */
public abstract class Human  extends Generic{
  
    public abstract String getName(); 

    public abstract void setName(String name);
    
    public abstract String getSurname(); 

    public abstract void setSurname(String surname);
    
    public abstract String getPatronymic(); 

    public abstract void setPatronymic(String patronymic);

    public abstract String getBiography();

    public abstract void setBiography(String biography);
    
    public abstract long getBirthDate();

    public abstract void setBirthDate(long birthDate);

    public abstract long getDeathDate();

    public abstract void setDeathDate(long deathDate);
    
    @Override
    public abstract long getId();

    @Override
    public abstract void setId(long id);
    
    @Override
    public abstract Types getType();

}
