package de.lsn.playground.logic.game;

import javax.ejb.Remote;

import de.lsn.playground.entity.ZgameEntity;
import de.lsn.playground.framwork.exception.ZgameException;

@Remote
public interface ZgameServiceDAORemote {

//	######## MISC METHODS ########	
	
	public void updateZgameEntity(ZgameEntity zgameEntity) throws ZgameException;
	
//	######## CREATIONAL METHODS ########

//	######## FINDER METHODS ########

}
