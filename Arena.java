// Klinton Chhun 6088111 Section1
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class Arena {

	public enum Row {Front, Back};	//enum for specifying the front or back row
	public enum Team {A, B};		//enum for specifying team A or B
	
	private Player[][] teamA = null;	//two dimensional array representing the players of Team A
	private Player[][] teamB = null;	//two dimensional array representing the players of Team B
	private int numRowPlayers = 0;		//number of players in each row
	
	public static final int MAXROUNDS = 100;	//Max number of turn
	public static final int MAXEACHTYPE = 3;	//Max number of players of each type, in each team.
	private final Path logFile = Paths.get("battle_log.txt");
	
	private int numRounds = 0;	//keep track of the number of rounds so far
	public static String printAttacker;
	/**
	 * Constructor. 
	 * @param _numRowPlayers is the number of players in each row.
	 */
	public Arena(int _numRowPlayers)
	{	
		//INSERT YOUR CODE HERE
		numRowPlayers = _numRowPlayers; 
		teamA = new Player[2][numRowPlayers];
		teamB = new Player[2][numRowPlayers];
		////Keep this block of code. You need it for initialize the log file. 
		////(You will learn how to deal with files later)
		try {
			Files.deleteIfExists(logFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		/////////////////////////////////////////
	}
	/**
	 * Returns true if "player" is a member of "team", false otherwise.
	 * Assumption: team can be either Team.A or Team.B
	 * @param player
	 * @param team
	 * @return
	 */
	public boolean isMemberOf(Player player, Team team)
	{
		//INSERT YOUR CODE HERE
		for(int i=0; i<2; i++) {
			for(int j=0; j<numRowPlayers; j++) {
				if(team == Team.A) {
					if(teamA[i][j].equals(player) && teamA[i][j].getCurrentHP()==player.getCurrentHP())return true;
				}else if(team == Team.B) {
					if(teamB[i][j].equals(player)&&teamB[i][j].getCurrentHP()==player.getCurrentHP())return true;
				}
			}
		}
		return false;
	}
	
	public int getNumRowPlayers() {
		return numRowPlayers;
	}

	/**
	 * This methods receives a player configuration (i.e., team, type, row, and position), 
	 * creates a new player instance, and places him at the specified position.
	 * @param team is either Team.A or Team.B
	 * @param pType is one of the Player.Type  {Healer, Tank, Samurai, BlackMage, Phoenix}
	 * @param row	either Row.Front or Row.Back
	 * @param position is the position of the player in the row. Note that position starts from 1, 2, 3....
	 */

	public void addPlayer(Team team, Player.PlayerType pType, Row row, int position)
	{	
		//INSERT YOUR CODE HERE
		if(team.equals(Team.A)) {
			if(row.equals(Row.Front)) {
				teamA[0][position-1]= new Player(pType);
			}else {
				teamA[1][position-1] = new Player(pType);
			}
		}else {
			if(row.equals(Row.Front)) {
				teamB[0][position-1]= new Player(pType);
			}else {
				teamB[1][position-1] = new Player(pType);
			}
		}
	}
	
	/**
	 * Validate the players in both Team A and B. Returns true if all of the following conditions hold:
	 * 
	 * 1. All the positions are filled. That is, there each team must have exactly numRow*numRowPlayers players.
	 * 2. There can be at most MAXEACHTYPE players of each type in each team. For example, if MAXEACHTYPE = 3
	 * then each team can have at most 3 Healers, 3 Tanks, 3 Samurais, 3 BlackMages, and 3 Phoenixes.
	 * 
	 * Returns true if all the conditions above are satisfied, false otherwise.
	 * @return
	 */
	public boolean validatePlayers()
	{
		//INSERT YOUR CODE HERE
		if(2*teamA[0].length==2*teamA[1].length && 2*teamB[0].length==2*teamB[1].length) {
			for(int i=0; i<2; i++) {
				for(int j=0; j<numRowPlayers; j++) {
					int check = 0, check1=0;
					for(int k=0; k<2; k++) {
						for(int m=0; m<numRowPlayers; m++) {
							if(teamA[i][j].getType().equals(teamA[k][m].getType())){
								check++; 
							}
							if(teamB[i][j].getType().equals(teamB[k][m].getType())) {
								check1++;
							}
						}
					}
					if(check>MAXEACHTYPE) {
						return false;
					}
					else if(check1>MAXEACHTYPE) {
						return false;
					}
				}
			}
			return true;
		}else {
			return false;
		}
	}
	/**
	 * Returns the sum of HP of all the players in the given "team"
	 * @param team
	 * @return
	 */
	public static double getSumHP(Player[][] team)
	{
		//INSERT YOUR CODE HERE
		double sum=0,sum1=0; 
		for(int i=0; i<team[0].length; i++) {
			sum+=team[0][i].getCurrentHP();
		}
		for(int i=0; i<team[0].length; i++) {
			sum1+=team[1][i].getCurrentHP();
		}
		return sum+sum1;
	}


	/**
	 * Return the team (either teamA or teamB) whose number of alive players is higher than the other. 
	 * 
	 * If the two teams have an equal number of alive players, then the team whose sum of HP of all the
	 * players is higher is returned.
	 * 
	 * If the sums of HP of all the players of both teams are equal, return teamA.
	 * @return
	 */
	public Player[][] getWinningTeam()
	{
		//INSERT YOUR CODE HERE	
		int countA=0 ,countB=0;;
		for(int i=0; i<2; i++) {
			for(int j=0; j<teamA[i].length; j++) {
				if(teamA[i][j].isAlive()) countA++;
			}
		}
		for(int i=0; i<2; i++) {
			for(int j=0; j<teamB[i].length; j++) {
				if(teamB[i][j].isAlive()) countB++;
			}
		}
		if(countA<countB) return teamB;
		else if(countA==countB) {
			if(getSumHP(teamB)>getSumHP(teamA)) return teamB;
		}
		return teamA;
	}
	
	/**
	 * This method simulates the battle between teamA and teamB. The method should have a loop that signifies
	 * a round of the battle. In each round, each player in teamA invokes the method takeAction(). The players'
	 * turns are ordered by its position in the team. Once all the players in teamA have invoked takeAction(),
	 * not it is teamB's turn to do the same. 
	 * 
	 * The battle terminates if one of the following two conditions is met:
	 * 
	 * 1. All the players in a team has been eliminated.
	 * 2. The number of rounds exceeds MAXROUNDS
	 * 
	 * After the battle terminates, report the winning team, which is determined by getWinningTeam().
	 */
	
	public void startBattle()
	{
		//INSERT YOUR CODE HERE
		int numTeamAlive=0, numTeamBlive=0;
		while(numRounds<MAXROUNDS) {
			++numRounds;
			System.out.println("@ Round "+numRounds);numTeamAlive=0;
			for(int i=0; i<2; i++) {
				for(int j=0; j<numRowPlayers; j++) {
					if(teamA[i][j].getCursePlayer() != null && teamA[i][j].getCursePlayer().isCursed()) {
						teamA[i][j].getCursePlayer().setCursed(false);
					}
					if(teamA[i][j].isSleeping() && teamA[i][j].isAlive()) {
						numTeamAlive++;
						teamA[i][j].setSleeping(false);
					}
					else if(teamA[i][j].isAlive()) {
						if(getSumHP(teamB)<=0) {
							if(teamA[i][j].getType().equals(Player.PlayerType.Healer) && teamA[i][j].getInternalCount()==teamA[i][j].getNumSpecialTurn() ||teamA[i][j].getType().equals(Player.PlayerType.Phoenix) && teamA[i][j].getInternalCount()==teamA[i][j].getNumSpecialTurn() ) {
								printAttacker = "# A["+  (i==0?"Front":"Back") +"]["+(j+1)+"] {"+teamA[i][j].getType()+"} ";
								teamA[i][j].takeAction(this);
								numTeamAlive++; 
							}else continue;
						}
						else {
							printAttacker = "# A["+  (i==0?"Front":"Back") +"]["+(j+1)+"] {"+teamA[i][j].getType()+"} ";
							teamA[i][j].takeAction(this);
							numTeamAlive++;
						}
					}
				}
			}numTeamBlive=0;
			for(int i=0; i<2; i++) {
				for(int j=0; j<numRowPlayers; j++) {
					if(teamB[i][j].getCursePlayer() != null && teamB[i][j].getCursePlayer().isCursed()) {
						teamB[i][j].getCursePlayer().setCursed(false);
					}
					if(teamB[i][j].isSleeping() && teamB[i][j].isAlive()) {
						numTeamBlive++;
						teamB[i][j].setSleeping(false);
					}
					else if(teamB[i][j].isAlive()) {
						if(getSumHP(teamA)<=0) {
							if(teamB[i][j].getType().equals(Player.PlayerType.Healer) && teamB[i][j].getInternalCount()==teamB[i][j].getNumSpecialTurn()||teamB[i][j].getType().equals(Player.PlayerType.Phoenix) && teamB[i][j].getInternalCount()==teamA[i][j].getNumSpecialTurn()) {
								printAttacker = "# B["+ (i==0?"Front":"Back") +"]["+(j+1)+"] {"+teamB[i][j].getType()+"} ";
								teamB[i][j].takeAction(this);
								numTeamBlive++;		
							}else continue;
						}
						else {
							printAttacker = "# B["+ (i==0?"Front":"Back") +"]["+(j+1)+"] {"+teamB[i][j].getType()+"} ";
							teamB[i][j].takeAction(this);
							numTeamBlive++;		
						}		
					}
				}
			}
			logAfterEachRound();
			displayArea(this,true);
			if(numTeamAlive==0 || numTeamBlive==0) break;
		}
		if(getWinningTeam()==teamA) System.out.println("@@@ Team A won.");
		else System.out.println("@@@ Team B won.");
	}
	public Player[][] getTeamA() {
		return teamA;
	}

	public Player[][] getTeamB() {
		return teamB;
	}

	/**
	 * This method displays the current area state, and is already implemented for you.
	 * In startBattle(), you should call this method once before the battle starts, and 
	 * after each round ends. 
	 * 
	 * @param arena
	 * @param verbose
	 */
	public static void displayArea(Arena arena, boolean verbose)
	{
		StringBuilder str = new StringBuilder();
		if(verbose)
		{
			str.append(String.format("%43s   %40s","Team A","")+"\t\t"+String.format("%-38s%-40s","","Team B")+"\n");
			str.append(String.format("%43s","BACK ROW")+String.format("%43s","FRONT ROW")+"  |  "+String.format("%-43s","FRONT ROW")+"\t"+String.format("%-43s","BACK ROW")+"\n");
			for(int i = 0; i < arena.numRowPlayers; i++)
			{
				str.append(String.format("%43s",arena.teamA[1][i])+String.format("%43s",arena.teamA[0][i])+"  |  "+String.format("%-43s",arena.teamB[0][i])+String.format("%-43s",arena.teamB[1][i])+"\n");
			}
		}
	
		str.append("@ Total HP of Team A = "+getSumHP(arena.teamA)+". @ Total HP of Team B = "+getSumHP(arena.teamB)+"\n\n");
		System.out.print(str.toString());	
	}
	
	/**
	 * This method writes a log (as round number, sum of HP of teamA, and sum of HP of teamB) into the log file.
	 * You are not to modify this method, however, this method must be call by startBattle() after each round.
	 * 
	 * The output file will be tested against the auto-grader, so make sure the output look something like:
	 * 
	 * 1	47415.0	49923.0
	 * 2	44977.0	46990.0
	 * 3	42092.0	43525.0
	 * 4	44408.0	43210.0
	 * 
	 * Where the numbers of the first, second, and third columns specify round numbers, sum of HP of teamA, and sum of HP of teamB respectively. 
	 */
	private void logAfterEachRound()
	{
		try {
			Files.write(logFile, Arrays.asList(new String[]{numRounds+"\t"+getSumHP(teamA)+"\t"+getSumHP(teamB)}), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
