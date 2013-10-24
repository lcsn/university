package de.lsn.playground.logic.player;

import javax.ejb.Remote;

import de.lsn.playground.entity.player.Player;
import de.lsn.playground.framwork.exception.ZgameException;

@Remote
public interface PlayerDAORemote {

//	######## CREATIONAL METHODS ########
	public void createPlayer(Player p);
	
//	######## FINDER METHODS ########
	public Player findPlayerByUsernameAndPassword(String username, String password) throws ZgameException;
	
}
