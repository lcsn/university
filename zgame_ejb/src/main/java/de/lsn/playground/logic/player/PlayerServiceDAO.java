package de.lsn.playground.logic.player;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import de.lsn.playground.entity.player.Player;
import de.lsn.playground.framwork.exception.ZgameException;
import de.lsn.playground.logic.AbstractDAO;

@Stateless
@Remote(PlayerDAORemote.class)
public class PlayerDAO extends AbstractDAO implements PlayerDAOLocal {

	@Override
	public void createPlayer(Player p) {
		em.persist(p);
	}

	@Override
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
