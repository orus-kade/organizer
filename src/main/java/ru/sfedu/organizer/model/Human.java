package ru.sfedu.organizer.model;

/**
 * Class Human
 */
public abstract class Human  extends Generic{
  
    public abstract String getName(); 

    public abstract void setName(String name);

    public abstract String getBiography();

    public abstract void setBiography(String biography);
    
    public abstract String getBirthDate();

    public abstract void setBirthDate(String birthDate);

    public abstract String getDeathDate();

    public abstract void setDeathDate(String deathDate);
    
    @Override
    public abstract long getId();

    @Override
    public abstract void setId(long id);
    
    @Override
    public abstract Types getType();

}
