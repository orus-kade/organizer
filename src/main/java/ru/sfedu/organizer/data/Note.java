
import java.util.*;


/**
 * Class Note
 */
public class Note {

  //
  // Fields
  //

  private int id;
  private String description;
  private Object object;
  
  //
  // Constructors
  //
  public Note () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of id
   * @param newVar the new value of id
   */
  private void setId (int newVar) {
    id = newVar;
  }

  /**
   * Get the value of id
   * @return the value of id
   */
  private int getId () {
    return id;
  }

  /**
   * Set the value of description
   * @param newVar the new value of description
   */
  private void setDescription (String newVar) {
    description = newVar;
  }

  /**
   * Get the value of description
   * @return the value of description
   */
  private String getDescription () {
    return description;
  }
  
  /**
   * Set the value of object
   * @param newVar the new value of object
   */
  private void setObject (int newVar) {
    object = newVar;
  }

  /**
   * Get the value of id
   * @return the value of id
   */
  private Object getObject () {
    return object;
  }

  //
  // Other methods
  //

}
