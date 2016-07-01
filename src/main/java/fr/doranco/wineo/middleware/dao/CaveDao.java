package fr.doranco.wineo.middleware.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import fr.doranco.wineo.middleware.objetmetier.cave.Cave;

/**
 * Un DAO JPA de gestion des caves. 
 * 
 * @author Snekkja JFDC
 */
@Stateless
@Transactional
public class CaveDao {

	@PersistenceContext
	private EntityManager em;

	public void persister(final Cave cave) {
		
		em.persist(cave);
	}
	
	public void modifier(final Cave cave) {
		
		em.merge(cave);
	}
	
	public void retirer(final String reference) {
		
		em.remove(em.getReference(Cave.class, reference));
	}
	
	public Cave obtenir(final String reference) {
		
		return em.find(Cave.class, reference);
	}
	
	public boolean exister(final String reference) {

		boolean resultat = false;
		
		try {
			em.getReference(Cave.class, reference);
			resultat = true;
		} catch (EntityNotFoundException e) {
			resultat = false;
		}
		
		return resultat;
	}
	
}
