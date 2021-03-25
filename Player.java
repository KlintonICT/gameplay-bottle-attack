//Klinton Chhun 6088111 Section1
public class Player {

	public enum PlayerType {Healer, Tank, Samurai, BlackMage, Phoenix, Cherry};
	
	private PlayerType type; 	//Type of this player. Can be one of either Healer, Tank, Samurai, BlackMage, or Phoenix
	private double maxHP;		//Max HP of this player
	private double currentHP;	//Current HP of this player 
	private double atk;			//Attack power of this player
	private int internalCount;
	private int numSpecialTurn;
	private boolean taunting, cursed, sleeping;
	private String attackTeam;
	private Player cursePlayer;
	public void setTaunting(boolean taunting) {
		this.taunting = taunting;
	}

	public void setCursed(boolean curse) {
		this.cursed = curse;
	}

	public void setSleeping(boolean sleep) {
		this.sleeping = sleep;
	}

	/**
	 * Constructor of class Player, which initializes this player's type, maxHP, atk, numSpecialTurns, 
	 * as specified in the given table. It also reset the internal turn count of this player. 
	 * @param _type
	 */
	public Player(PlayerType _type)
	{	
		//INSERT YOUR CODE HERE
		type = _type; 
		switch(type) {
			case Healer:
				maxHP = 4790; atk = 238; numSpecialTurn = 4; 
			break;
			case Tank:
				maxHP = 5340; atk = 255; numSpecialTurn = 4; 
			break;
			case Samurai:
				maxHP = 4005; atk = 368; numSpecialTurn = 3; 
			break;
			case BlackMage:
				maxHP = 4175; atk = 303; numSpecialTurn = 4;
			break;
			case Phoenix:
				maxHP = 4175; atk = 209; numSpecialTurn = 8;
			break;
			default:
				maxHP = 3560; atk = 198; numSpecialTurn = 4;
			break;
		}
		currentHP = maxHP; internalCount=1;
	}
	public int getNumSpecialTurn() {
		return numSpecialTurn;
	}
	/**
	 * Returns the current HP of this player
	 * @return
	 */
	public double getCurrentHP()
	{
		//INSERT YOUR CODE HERE
		return currentHP;
	}
	
	/**
	 * Returns type of this player
	 * @return
	 */
	public Player.PlayerType getType()
	{
		//INSERT YOUR CODE HERE
		return type;
	}
	
	/**
	 * Returns max HP of this player. 
	 * @return
	 */
	public double getMaxHP()
	{
		//INSERT YOUR CODE HERE
		return maxHP;
	}
	
	/**
	 * Returns whether this player is sleeping.
	 * @return
	 */
	public boolean isSleeping()
	{
		//INSERT YOUR CODE HERE
		return sleeping;
	}
	
	/**
	 * Returns whether this player is being cursed.
	 * @return
	 */
	public boolean isCursed()
	{
		//INSERT YOUR CODE HERE
		return cursed;
	}
	
	/**
	 * Returns whether this player is alive (i.e. current HP > 0).
	 * @return
	 */
	public boolean isAlive()
	{
		//INSERT YOUR CODE HERE
		if(currentHP>0) {
			return true;
		}
		this.setTaunting(false); this.internalCount=0; 
		this.setSleeping(false); this.setCursed(false);
		return false;
	}
	
	/**
	 * Returns whether this player is taunting the other team.
	 * @return
	 */
	
	public boolean isTaunting()
	{
		//INSERT YOUR CODE HERE
		return taunting;
	}
	
	public void attack(Player target)
	{	
		//INSERT YOUR CODE HERE
		if(target!=null) {
			target.currentHP -= atk;
			if(target.currentHP<=0) {
				target.currentHP=0;
			}
		}
	}
	
	public void useSpecialAbility(Player[][] myTeam, Player[][] theirTeam)
	{	
		//INSERT YOUR CODE HERE
		Player attackTarget = theirTeam[0][0]; int k=0;
		switch(this.type) { 
			case Samurai:
			{
				boolean check = false;
				for(int j=0; j<theirTeam[0].length; j++) {
					if(theirTeam[0][j].isAlive()) {
						check = true;
					}
				}
				if(check) {
					for(int i=0; i<theirTeam[0].length; i++) {
						if(theirTeam[0][i].isAlive()) {
							attackTarget = theirTeam[0][i];k=i; break;
						}
					}
					for(int j=0; j<theirTeam[0].length; j++) {
						if(theirTeam[0][j].currentHP<attackTarget.currentHP && theirTeam[0][j].isAlive()) {
							attackTarget = theirTeam[0][j];k=j;
						}
					}
					outterloop:
					for(int i=0; i<2; i++) {
						for(int j=0; j<theirTeam[i].length; j++) {
							if(theirTeam[i][j].isTaunting() && theirTeam[i][j].isAlive()) {
								attackTarget = theirTeam[i][j];k=j;
								check=(i==0)?true:false;
								System.out.println("Double-Slashes "+(attackTeam=="A"?"B":"A")+"["+(check?"Front":"Back")+"]["+(k+1)+"] {"+attackTarget.type+"} ");
								break outterloop;
							}
						}
					}
					System.out.println(Arena.printAttacker+"Double-Slashes "+(attackTeam=="A"?"B":"A")+"["+(check?"Front":"Back")+"]["+(k+1)+"] {"+attackTarget.type+"} ");
					attack(attackTarget);attack(attackTarget);
				}else {
					for(int i=0; i<theirTeam[1].length; i++) {
						if(theirTeam[1][i].isAlive()) {
							attackTarget = theirTeam[1][i]; k=i; break;
						}
					}
					for(int j=0; j<theirTeam[1].length; j++) {
						if(theirTeam[1][j].currentHP<attackTarget.currentHP && theirTeam[1][j].isAlive()) {
							attackTarget = theirTeam[1][j];k=j;
						}
					}
					outterloop:
					for(int i=0; i<2; i++) {
						for(int j=0; j<theirTeam[i].length; j++) {
							if(theirTeam[i][j].isTaunting() && theirTeam[i][j].isAlive()) {
								attackTarget = theirTeam[i][j];k=j; 
								check=(i==0)?true:false;break outterloop;
							}
						}
					}
					System.out.println(Arena.printAttacker+"Double-Slahes "+(attackTeam=="A"?"B":"A")+"["+(check?"Front":"Back")+"]["+(k+1)+"] {"+attackTarget.type+"} ");
					attack(attackTarget); attack(attackTarget);
				}
			}
			break;
			case Tank:
			{
				System.out.println(Arena.printAttacker+"is Taunting");
				this.setTaunting(true);
			}
			break;
			case BlackMage:
			{
				boolean check = false;
				for(int j=0; j<theirTeam[0].length; j++) {
					if(theirTeam[0][j].isAlive()) {
						check = true;
					}
				}
				if(check) {
					for(int i=0; i<theirTeam[0].length; i++) {
						if(theirTeam[0][i].isAlive()) {
							attackTarget = theirTeam[0][i];k=i; break;
						}
					}
					for(int j=0; j<theirTeam[0].length; j++) {
						if(theirTeam[0][j].currentHP<attackTarget.currentHP && theirTeam[0][j].isAlive()) {
							attackTarget = theirTeam[0][j];k=j;
						}
					}
					attackTarget.setCursed(true); cursePlayer = attackTarget;
					System.out.println(Arena.printAttacker+"Curses "+(attackTeam=="A"?"B":"A")+"[Front]["+(k+1)+"] {"+attackTarget.type+"} ");
				}else {
					for(int i=0; i<theirTeam[1].length; i++) {
						if(theirTeam[1][i].isAlive()) {
							attackTarget = theirTeam[1][i];k=i; break;
						}
					}
					for(int j=0; j<theirTeam[1].length; j++) {
						if(theirTeam[1][j].currentHP<attackTarget.currentHP && theirTeam[1][j].isAlive()) {
							attackTarget = theirTeam[1][j];k=j;
						}
					}attackTarget.setCursed(true);cursePlayer = attackTarget;
					System.out.println(Arena.printAttacker+"Curses "+(attackTeam=="A"?"B":"A")+"[Back]["+(k+1)+"] {"+attackTarget.type+"} ");					
				}
			}
			break;
			case Healer:
			{
				boolean check = false; 
				for(int j=0; j<myTeam[0].length; j++) {
					if(myTeam[0][j].isAlive()) {
						check = true; break;
					}
				}
				if(check) {
					for(int j=0; j<myTeam[0].length; j++) {
						if(myTeam[0][j].isAlive()) {
								attackTarget=myTeam[0][j];k=j;break;
						}
					}
					for(int j=0; j<myTeam[0].length; j++) {
						if(myTeam[0][j].currentHP/myTeam[0][j].maxHP<attackTarget.currentHP/attackTarget.maxHP && myTeam[0][j].isAlive()) {
							attackTarget = myTeam[0][j];k=j;
						}
					}
					if(!attackTarget.isCursed()) {
						attackTarget.currentHP += (25*attackTarget.maxHP)/100.0;
						if(attackTarget.currentHP>attackTarget.maxHP) attackTarget.currentHP=attackTarget.maxHP;
						System.out.println(Arena.printAttacker+"Heals "+(attackTeam=="A"?"A":"B")+"[Front]["+(k+1)+"] {"+attackTarget.type+"} ");
					}
				}else {
					for(int j=0; j<myTeam[1].length; j++) {
						if(myTeam[1][j].isAlive()) {
							attackTarget=myTeam[1][j];k=j;break;
						}
					}
					for(int j=0; j<myTeam[1].length; j++) {
						if(myTeam[1][j].currentHP/myTeam[1][j].maxHP<attackTarget.currentHP/attackTarget.maxHP && myTeam[1][j].isAlive()) {
							attackTarget = myTeam[1][j];k=j;
						}
					}
					if(!attackTarget.isCursed()) {
						attackTarget.currentHP += (25*attackTarget.maxHP)/100.0;
						if(attackTarget.currentHP>attackTarget.maxHP) attackTarget.currentHP=attackTarget.maxHP;
						System.out.println(Arena.printAttacker+"Heals "+(attackTeam=="A"?"A":"B")+"[Back]["+(k+1)+"] {"+attackTarget.type+"} ");
					}
				}
			}
			break;
			case Phoenix:
			{
				String checkRow="";
				outterloop:
				for(int i=0; i<myTeam.length; i++) {
					for(int j=0; j<myTeam[i].length; j++) {
						if(!myTeam[i][j].isAlive()) {
							attackTarget=myTeam[i][j]; attackTarget.currentHP=(attackTarget.maxHP * 30) / 100.0;
							k=j;
							checkRow = i==0? "Front" : "Back"; break outterloop;
						}
					}
				}
				System.out.println(Arena.printAttacker+"Revises "+(attackTeam=="A"?"A":"B")+"["+checkRow+"]["+(k+1)+"] {"+attackTarget.type+"} ");
			}
			break;
			default:
			for(int i=0; i<2; i++) {
				for(int j=0; j<theirTeam[i].length; j++) {
					if(theirTeam[i][j].isAlive()) {
						theirTeam[i][j].setSleeping(true);
						System.out.println(Arena.printAttacker+"Feeds a Fortune Cookie to "+(attackTeam=="A"?"B":"A")+"["+(i==0?"Front":"Back")+"]["+(j+1)+"] {"+theirTeam[i][j].type+"} ");
					}
				}
			}
			break;
		}
	}
	public Player getCursePlayer(){
		return cursePlayer;
	}
	/**
	 * This method is called by Arena when it is this player's turn to take an action. 
	 * By default, the player simply just "attack(target)". However, once this player has 
	 * fought for "numSpecialTurns" rounds, this player must perform "useSpecialAbility(myTeam, theirTeam)"
	 * where each player type performs his own special move. 
	 * @param arena
	 */
	public void takeAction(Arena arena)
	{	
		//INSERT YOUR CODE HERE
		if(this.taunting) {
			this.setTaunting(false);
		}
		int k=0;
		Player attackTarget = arena.getTeamA()[0][0];
		if(arena.isMemberOf(this, Arena.Team.A)) {
			attackTeam = "A";
			if(this.internalCount<this.numSpecialTurn) {
				++this.internalCount;
				boolean check = false;
				for(int j=0; j<arena.getTeamB()[0].length; j++) {
					if(arena.getTeamB()[0][j].isAlive()) {
						check = true;
					}
				}
				if(check) {
					for(int i=0; i<arena.getTeamB()[0].length; i++) {
						if(arena.getTeamB()[0][i].isAlive()) {
							attackTarget=arena.getTeamB()[0][i];k=i;break;
						}
					}
					for(int j=0; j<arena.getTeamB()[0].length; j++) {
						if(arena.getTeamB()[0][j].currentHP<attackTarget.currentHP && arena.getTeamB()[0][j].isAlive()) {
							attackTarget = arena.getTeamB()[0][j];k=j;
						}
					}
					outterloop:
					for(int i=0; i<2; i++) {
						for(int j=0; j<arena.getTeamB()[i].length; j++) {
							if(arena.getTeamB()[i][j].isTaunting() && arena.getTeamB()[i][j].isAlive()) {
								attackTarget = arena.getTeamB()[i][j];k=j; 
								check=(i==0)?true:false;
								break outterloop;
							}
						}
					}
					attack(attackTarget); 
					System.out.println(Arena.printAttacker+"Attacks"+" B["+(check?"Front":"Back")+"]["+(k+1)+"] {"+attackTarget.type+"} ");
				} else {
					for(int i=0; i<arena.getTeamB()[1].length; i++) {
						if(arena.getTeamB()[1][i].isAlive()) {
							attackTarget=arena.getTeamB()[1][i];k=i;break;
						}
					}
					for(int j=0; j<arena.getTeamB()[1].length; j++) {
						if(arena.getTeamB()[1][j].currentHP<attackTarget.currentHP && arena.getTeamB()[1][j].isAlive()) {
							attackTarget = arena.getTeamB()[1][j];k=j;
						}
					}
					outterloop:
					for(int i=0; i<2; i++) {
						for(int j=0; j<arena.getTeamB()[i].length; j++) {
							if(arena.getTeamB()[i][j].isTaunting() && arena.getTeamB()[i][j].isAlive()) {
								attackTarget = arena.getTeamB()[i][j];k=j;
								break outterloop;
							}
						}
					}
					attack(attackTarget); 
					System.out.println(Arena.printAttacker+"Attacks"+" B["+(check?"Front":"Back")+"]["+(k+1)+"] {"+attackTarget.type+"} ");
				}
			}else {
				this.internalCount=1; 
				this.useSpecialAbility(arena.getTeamA(),arena.getTeamB());
			}
		}else {
			attackTeam = "B";
			if(this.internalCount<this.numSpecialTurn) {
				++this.internalCount;
				boolean check = false;
				for(int j=0; j<arena.getNumRowPlayers(); j++) {
					if(arena.getTeamA()[0][j].isAlive()) {
						check = true;
					}
				}
				if(check) {
					for(int i=0; i<arena.getTeamA()[0].length; i++) {
						if(arena.getTeamA()[0][i].isAlive()) {
							attackTarget=arena.getTeamA()[0][i];k=i;break;
						}
					}
					for(int j=0; j<arena.getNumRowPlayers(); j++) {
						if(arena.getTeamA()[0][j].currentHP<attackTarget.currentHP && arena.getTeamA()[0][j].isAlive()) {
							attackTarget = arena.getTeamA()[0][j];k=j;
						}
					}
					outterloop:
					for(int i=0; i<2; i++) {
						for(int j=0; j<arena.getTeamA()[i].length; j++) {
							if(arena.getTeamA()[i][j].isTaunting() && arena.getTeamA()[i][j].isAlive()) {
								attackTarget = arena.getTeamA()[i][j]; k=j;
								check = true;
								break outterloop;
							}
						}
					}
					attack(attackTarget); 
					System.out.println(Arena.printAttacker+"Attacks"+" A["+(check?"Front":"Back")+"]["+(k+1)+"] {"+attackTarget.type+"} ");
				}else {
					for(int i=0; i<arena.getTeamA()[1].length; i++) {
						if(arena.getTeamA()[1][i].isAlive()) {
							attackTarget=arena.getTeamA()[1][i];k=i;break;
						}
					}
					for(int j=0; j<arena.getNumRowPlayers(); j++) {
						if(arena.getTeamA()[1][j].currentHP<attackTarget.currentHP && arena.getTeamA()[1][j].isAlive()) {
							attackTarget = arena.getTeamA()[1][j];k=j;
						}
					}
					outterloop:
					for(int i=0; i<2; i++) {
						for(int j=0; j<arena.getTeamA()[i].length; j++) {
							if(arena.getTeamA()[i][j].isTaunting() && arena.getTeamA()[i][j].isAlive()) {
								attackTarget = arena.getTeamA()[i][j]; k=j;
								check = true;
								break outterloop;
							}
						}
					}
					attack(attackTarget); 
					System.out.println(Arena.printAttacker+"Attacks"+" A["+(check?"Front":"Back")+"]["+(k+1)+"] {"+attackTarget.type+"} ");
				}
			}
			else {
				this.internalCount=1; 
				this.useSpecialAbility(arena.getTeamB(), arena.getTeamA());
			}
		}
	}
	public int getInternalCount() {
		return internalCount;
	}
	
	/**
	 * This method overrides the default Object's toString() and is already implemented for you. 
	 */
	@Override
	public String toString()
	{
		return "["+this.type.toString()+" HP:"+this.currentHP+"/"+this.maxHP+" ATK:"+this.atk+"]["
				+((this.isCursed())?"C":"")
				+((this.isTaunting())?"T":"")
				+((this.isSleeping())?"S":"")
				+"]";
	}
	
}
