package de.lsn.playground.logic.player;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import de.lsn.playground.entity.player.Player;
import de.lsn.playground.framwork.exception.ZgameException;
import de.lsn.playground.logic.AbstractDAO;

@Stateless
@Remote(PlayerServiceDAORemote.class)
public class PlayerServiceDAO extends AbstractDAO implements PlayerServiceDAOLocal {


//	######## MISC METHODS ########
	public boolean authenticate(String username, String password) throws ZgameException {
		boolean result = false;
		Player player = findPlayerByUsernameAndPassword(username, password);
		if(null!=player) {
			result = true;
		}
		return result;
	}
	
//	######## CREATIONAL METHODS ########

	public void createPlayer(Player p) {
		em.persist(p);
	}

//	######## FINDER METHODS ########	
	
	public Player findPlayerByUsernameAndPassword(String username, String password) throws ZgameException {
		Player player = null;
		TypedQuery<Player> query = em.createNamedQuery(Player.FIND_BY_USERNAME_AND_PASSWORD, Player.class);
		query.setParameter("username", username);
		query.setParameter("password", password);
		try {
			player = (Player) query.getSingleResult();
		} catch (NoResultException nre) {
			throw new ZgameException("Spieler wurde nicht gefunden", nre);
		}
		return player;
	}
	
}
