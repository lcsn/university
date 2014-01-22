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

	public void createPlayer(Player p) throws ZgameException {
		if(null == p) {
			return;
		}
		if(p.getId() != null) {
			throw new ZgameException("Entity kann nicht persistiert werden");
		}
		em.persist(p);
	}

//	######## FINDER METHODS ########	
	
	public Long validateUsername(String username) {
		Long result = 0l;
		TypedQuery<Long> query = em.createNamedQuery(Player.VALIDATE_USERNAME, Long.class);
		query.setParameter("username", username);
		try {
			result = query.getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		return result;
	}
	
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
