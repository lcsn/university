package de.lsn.playground.logic.game;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import de.lsn.playground.entity.ZgameEntity;
import de.lsn.playground.framwork.exception.ZgameException;
import de.lsn.playground.logic.AbstractDAO;

@Stateless
@Remote(ZgameServiceDAORemote.class)
public class ZgameServiceDAO extends AbstractDAO implements ZgameServiceDAOLocal {

//	######## MISC METHODS ########
	public void updateZgameEntity(ZgameEntity zgameEntity) throws ZgameException {
		if(zgameEntity.getId() == null) {
			throw new ZgameException("Non persistent entity passed to merge. Call kind of create method instead!");
		}
		zgameEntity.merged();
		em.merge(zgameEntity);
	}
	
//	######## CREATIONAL METHODS ########

//	######## FINDER METHODS ########
	
}
