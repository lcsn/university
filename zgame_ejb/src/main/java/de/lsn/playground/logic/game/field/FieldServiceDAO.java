package de.lsn.playground.logic.game.field;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import de.lsn.playground.entity.attribute.Name;
import de.lsn.playground.entity.map.Field;
import de.lsn.playground.entity.map.FieldDefinition;
import de.lsn.playground.entity.map.Terrain;
import de.lsn.playground.framwork.exception.ZgameException;
import de.lsn.playground.logic.AbstractDAO;

@Stateless
@Remote(FieldServiceDAORemote.class)
public class FieldServiceDAO extends AbstractDAO implements FieldServiceDAOLocal {

//	######## CREATIONAL METHODS ########
	public Terrain createTerrain(Terrain terrain) throws ZgameException {
		if(null != terrain.getId()) {
			throw new ZgameException("Can not persist detached entity");
		}
		em.persist(terrain);
		return em.find(Terrain.class, terrain.getId());
	}
	
	public FieldDefinition createFieldDefinition(FieldDefinition fieldDefinition) throws ZgameException {
		if(null != fieldDefinition.getId()) {
			throw new ZgameException("Can not persist detached entity");
		}
		em.persist(fieldDefinition);
		return em.find(FieldDefinition.class, fieldDefinition.getId());
	}

	public Field createFieldByFieldDefinition(FieldDefinition fieldDefinition) throws ZgameException {
		Field newFieldInstance = fieldDefinition.newFieldInstance();
		em.persist(newFieldInstance);
		return em.find(Field.class, newFieldInstance.getId());
	}

	public Field createFieldByFieldDefinitionId(Long fieldDefinitionId) throws ZgameException {
		return createFieldByFieldDefinition(findFieldDefinitionById(fieldDefinitionId));
	}
	
//	######## FINDER METHODS ########
	public Terrain findTerrainByName(String terrainName) throws ZgameException {
		TypedQuery<Terrain> query = em.createNamedQuery(Terrain.FIND_BY_ID, Terrain.class);
		query.setParameter("terrainName", new Name(terrainName));
		Terrain t = null;
		try {
			t = query.getSingleResult();
		} catch (NoResultException | EntityNotFoundException e) {
			throw new ZgameException("Could not find " + Terrain.class.getSimpleName() + " with name: " + terrainName, e);
		}
		return t;
	}
	
	public FieldDefinition findFieldDefinitionById(Long fieldDefinitionId) throws ZgameException {
		TypedQuery<FieldDefinition> query = em.createNamedQuery(FieldDefinition.FIND_BY_ID, FieldDefinition.class);
		query.setParameter("id", fieldDefinitionId);
		FieldDefinition fieldDefinition = null;
		try {
			fieldDefinition = query.getSingleResult();
		} catch (NoResultException | EntityNotFoundException e) {
			throw new ZgameException("Could not find " + FieldDefinition.class.getSimpleName() + " with id: " + fieldDefinitionId, e);
		}
		
		return fieldDefinition;
	}
	
}
