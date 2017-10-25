
import java.util.*;


/**
 * Class Aria
 */
public class Aria extends Object{

  //
  // Fields
  //

  private String title;
  private String text;
  private List<Composer> composers;
  private List<Writer> writers;
  private List<Singer> famousSingers;
  
  //
  // Constructors
  //
  public Aria () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of title
   * @param newVar the new value of title
   */
  private void setTitle (String newVar) {
    title = newVar;
  }

  /**
   * Get the value of title
   * @return the value of title
   */
  private String getTitle () {
    return title;
  }

  /**
   * Set the value of text
   * @param newVar the new value of text
   */
  private void setText (String newVar) {
    text = newVar;
  }

  /**
   * Get the value of text
   * @return the value of text
   */
  private String getText () {
    return text;
  }

  /**
   * Set the value of composers
   * @param newVar the new value of composers
   */
  private void setComposers (List<Composer> newVar) {
    composers = newVar;
  }

  /**
   * Get the value of composers
   * @return the value of composers
   */
  private List<Composer> getComposers () {
    return composers;
  }

  /**
   * Set the value of writers
   * @param newVar the new value of writers
   */
  private void setWriters (List<Writer> newVar) {
    writers = newVar;
  }

  /**
   * Get the value of writers
   * @return the value of writers
   */
  private List<Writer> getWriters () {
    return writers;
  }

  /**
   * Set the value of famousSingers
   * @param newVar the new value of famousSingers
   */
  private void setFamousSingers (List<Singer> newVar) {
    famousSingers = newVar;
  }

  /**
   * Get the value of famousSingers
   * @return the value of famousSingers
   */
  private List<Singer> getFamousSingers () {
    return famousSingers;
  }

  //
  // Other methods
  //

}
