/* The Team Class
   Quattrocchi, Warren
*/

public class Team
{
  private String nickname;
  private int wins;
  private int losses;

  public Team(String newNickname, int newWins, int newLosses)
  {
   this.nickname = newNickname;
   this.wins = newWins;
   this.losses = newLosses;
  }
 
  /** getNickname
  * @return nickname
  */
  public String getNickname( )
  {
    return nickname; 
  }

  /** getWins
  * @return wins
  */
  public int getWins( )
  {
     return wins;
  }

  /** getLosses
  * @return losses
  */
  public int getLosses( )
  {
    return losses; 
  }

  /** setNickname
  * @param newNickname  new value for nickname
  */
  public void setNickname( String newNickname )
  {
    this.nickname = newNickname;  
  }

  /** setWins
  * @param newWins  new value for wins
  *                 newWins must be >= 0. otherwise value is unchanged
  */
  public void setWins( int newWins )
  {
    this.wins = newWins;   
  }

  /** setLosses
  * @param newLosses  new value for losses
  *                   newLosses must be >= 0. otherwise value is unchanged
  */
  public void setLosses( int newLosses )
  {
     this.losses = newLosses;
  }

  /** equals
  * @param o  another Team object
  * @return  true if the instance variables in this object are equal to the
  *          instance variables in t; false otherwise
  */
  public boolean equals( Object o )
  {
	 return o == this;
    
  }

  /** toString
  * @return String representation of nickname, wins, and losses
  */
  public String toString( )
  {
     String s = String.format("Nickname: %-20s Wins: %-20d Losses: %d \n",nickname,wins,losses);
     return s;  
  }

  /** winningPercentage
  * @return  wins / total games; 0 if no games have been played
  */
  public double winningPercentage( )
  {
     if(this.losses == 0)
      return 100;
     return (wins/losses) * 100;
  }
}
