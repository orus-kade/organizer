
import java.time.LocalDate;
import java.util.*;


/**
 * Class Human
 */
public class Human  extends Object{

  //
  // Fields
  //

  private String name;
  private String biography;
  private LocalDate birthDate;
  private LocalDate deathDate;
  
  //
  // Constructors
  //
  public Human () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of name
   * @param newVar the new value of name
   */
  private void setName (String newVar) {
    name = newVar;
  }

  /**
   * Get the value of name
   * @return the value of name
   */
  private String getName () {
    return name;
  }

  /**
   * Set the value of biography
   * @param newVar the new value of biography
   */
  private void setBiography (String newVar) {
    biography = newVar;
  }

  /**
   * Get the value of biography
   * @return the value of biography
   */
  private String getBiography () {
    return biography;
  }

  /**
   * Set the value of birthDate
   * @param newVar the new value of birthDate
   */
  private void setBirthDate (LocalDate newVar) {
    birthDate = newVar;
  }

  /**
   * Get the value of birthDate
   * @return the value of birthDate
   */
  private LocalDate getBirthDate () {
    return birthDate;
  }

  /**
   * Set the value of deathDate
   * @param newVar the new value of deathDate
   */
  private void setDeathDate (LocalDate newVar) {
    deathDate = newVar;
  }

  /**
   * Get the value of deathDate
   * @return the value of deathDate
   */
  private LocalDate getDeathDate () {
    return deathDate;
  }

  //
  // Other methods
  //

}
