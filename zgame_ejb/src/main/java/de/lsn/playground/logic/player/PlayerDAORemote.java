package de.lsn.playground.logic.player;

import javax.ejb.Remote;

import de.lsn.playground.entity.player.Player;
import de.lsn.playground.framwork.exception.ZgameException;

@Remote
public interface PlayerDAORemote {

	public void createPlayer(Player p);

	public Player findPlayerByUsernameAndPassword(String username, String password) throws ZgameException;
	
}
