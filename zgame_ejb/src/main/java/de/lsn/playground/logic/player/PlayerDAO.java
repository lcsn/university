package de.lsn.playground.logic.player;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.Query;

import de.lsn.playground.entity.player.Player;
import de.lsn.playground.logic.AbstractDAO;

@Stateless
@Remote(PlayerDAORemote.class)
public class PlayerDAO extends AbstractDAO implements PlayerDAOLocal {

	@Override
	public void createPlayer(Player p) {
		em.persist(p);
	}

	@Override
	public Player findPlayerByUsernameAndPassword(String username, String password) {
		Query query = em.createNamedQuery(Player.FIND_BY_USERNAME_AND_PASSWORD);
		query.setParameter("username", username);
		query.setParameter("password", password);
		return (Player) query.getSingleResult();
	}

}
