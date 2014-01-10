package de.lsn.playground.logic.player;

import javax.ejb.Remote;

import de.lsn.playground.entity.player.Player;
import de.lsn.playground.framwork.exception.ZgameException;

@Remote
public interface PlayerServiceDAORemote {

//	######## MISC METHODS ########
	public boolean authenticate(String username, String password) throws ZgameException;
	
//	######## CREATIONAL METHODS ########
	public void createPlayer(Player p) throws ZgameException;
	
//	######## FINDER METHODS ########
	
	public Long validateUsername(String username);
	
	public Player findPlayerByUsernameAndPassword(String username, String password) throws ZgameException;
	
}
