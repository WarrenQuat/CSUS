/* The TeamNode class
   Quattrocchi, Warren
*/

public class TeamNode
{
  private Team team;
  private TeamNode next;

  /** default constructor
  * sets team and next to null
  */
  public TeamNode( )
  {
     this.team = null;
     this.next = null;
  }

  /** constructor
  * @param t a Team object reference
  *   sets team to t, and next to null
  */
  public TeamNode( Team t )
  {
     this.team = t;
     this.next = null;   
  }

  /** getTeam
  *  @return team
  */
  public Team getTeam( )
  {
    return team;
  }

  /** getNext
  * @return next
  */
  public TeamNode getNext( )
  {
    return next;
  }

  /** setTeam
  * @param t new value foe team
  */
  public void setTeam( Team t )
  {
      this.team = t;   
  }

  /** setNext
  * @param t TeamNode reference for next
  */
  public void setNext( TeamNode t )
  {
      this.next = t;    
  }
}
