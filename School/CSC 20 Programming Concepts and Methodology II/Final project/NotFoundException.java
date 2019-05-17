/* The NotFoundException Class
   Warren Quattrocchi
   Justin Roldan
   
   this class creates a new exception type
*/

public class NotFoundException extends Exception
{
  /** constructor
  *   @param  s   error message
  */
  public NotFoundException( String s )
  {
    super( s );
  }
}