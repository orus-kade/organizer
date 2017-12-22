package ru.sfedu.organizer.model;

/**
 * Class Human
 */
public abstract class Human  extends Generic{
  
    /**
     *
     * @return
     */
    public abstract String getName(); 

    /**
     *
     * @param name
     */
    public abstract void setName(String name);
    
    /**
     *
     * @return
     */
    public abstract String getSurname(); 

    /**
     *
     * @param surname
     */
    public abstract void setSurname(String surname);
    
    /**
     *
     * @return
     */
    public abstract String getPatronymic(); 

    /**
     *
     * @param patronymic
     */
    public abstract void setPatronymic(String patronymic);

    /**
     *
     * @return
     */
    public abstract String getBiography();

    /**
     *
     * @param biography
     */
    public abstract void setBiography(String biography);
    
    /**
     *
     * @return
     */
    public abstract long getBirthDate();

    /**
     *
     * @param birthDate
     */
    public abstract void setBirthDate(long birthDate);

    /**
     *
     * @return
     */
    public abstract long getDeathDate();

    /**
     *
     * @param deathDate
     */
    public abstract void setDeathDate(long deathDate);
    
    /**
     *
     * @return
     */
    @Override
    public abstract long getId();

    /**
     *
     * @param id
     */
    @Override
    public abstract void setId(long id);
    
    /**
     *
     * @return
     */
    @Override
    public abstract Types getType();

}
