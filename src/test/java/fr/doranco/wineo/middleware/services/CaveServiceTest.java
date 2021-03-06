package fr.doranco.wineo.middleware.services;

import static org.fest.assertions.Assertions.assertThat;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import fr.doranco.wineo.middleware.objetmetier.bouteille.Bouteille;
import fr.doranco.wineo.middleware.objetmetier.bouteille.BouteilleDejaExistanteException;
import fr.doranco.wineo.middleware.objetmetier.bouteille.BouteilleInexistanteException;
import fr.doranco.wineo.middleware.objetmetier.bouteille.BouteilleInvalideException;
import fr.doranco.wineo.middleware.objetmetier.cave.Cave;
import fr.doranco.wineo.middleware.objetmetier.cave.CaveInexistanteException;
import fr.doranco.wineo.middleware.objetmetier.cave.CaveInvalideException;
import fr.doranco.wineo.middleware.objetmetier.cave.Entrepot;
import fr.doranco.wineo.middleware.objetmetier.cave.PlaceInsuffisanteException;

public class CaveServiceTest {

	private CaveService caveService;

	private Entrepot entrepot;

	private Cave caveTestee;

	@Before
	public void initialisation() {

		// Initialisation des bouteilles
		final Bouteille bouteille1 = new Bouteille();
		bouteille1.setAnnee(2012);
		bouteille1.setContenance(1d);
		bouteille1.setDesignation("Ma bouteille de champomy");
		bouteille1.setReference("REF_90");

		final Bouteille bouteille2 = new Bouteille();
		bouteille2.setAnnee(2011);
		bouteille2.setContenance(1d);
		bouteille2.setDesignation("Saint-Julien");
		bouteille2.setReference("REF_91");

		final Bouteille bouteille3 = new Bouteille();
		bouteille3.setAnnee(2011);
		bouteille3.setContenance(1d);
		bouteille3.setDesignation("Lambrusco");
		bouteille3.setReference("REF_92");

		final List<Bouteille> bouteilles = new ArrayList<>();
		bouteilles.add(bouteille1);
		bouteilles.add(bouteille2);
		bouteilles.add(bouteille3);

		// Initialisation des caves
		caveTestee = new Cave();
		caveTestee.setBouteilles(bouteilles);
		caveTestee.setCapaciteMaximale(10);
		caveTestee.setNomProprietaire("Moi");
		caveTestee.setReference("B");

		final Cave cave2 = new Cave();
		cave2.setBouteilles(new ArrayList<>());
		cave2.setCapaciteMaximale(10);
		cave2.setNomProprietaire("Moi");
		cave2.setReference("C");

		final Set<Cave> caves = new HashSet<>();
		caves.add(caveTestee);
		caves.add(cave2);

		// Initialisation de l'entrepot
		entrepot = new Entrepot();

		entrepot.setCapaciteMaximale(100);
		entrepot.setCaves(caves);

		// Initialisation du service
		caveService = new CaveService();

	}

	@Test
	@Ignore
	public void testAjouterBouteilleNominal() throws PlaceInsuffisanteException, BouteilleInexistanteException,
			CaveInexistanteException, BouteilleInvalideException, CaveInvalideException, BouteilleDejaExistanteException {

		// Initialisation du test
		final Bouteille bouteilleAjoutee = new Bouteille();
		bouteilleAjoutee.setAnnee(2015);
		bouteilleAjoutee.setContenance(0.75d);
		bouteilleAjoutee.setDesignation("Beaujolais nouveau");
		bouteilleAjoutee.setReference("REF_01");

		// Nous récupérons le nombre de bouteille initial,
		// avant l'ajout d'une nouvelle bouteille.
		final Integer nombreBouteilleInitial = caveTestee.getBouteilles().size();

		// Lancement du test
		caveService.ajouterBouteille(bouteilleAjoutee, caveTestee);

		// Vérification du test
		/*
		 * La condition de réussite du test est que la cave contienne la
		 * bouteille
		 */
		// FIXME : Il faut corriger ce test avec de l'injection. 
		// Assertions.assertThat(caveTestee.getBouteilles().get(bouteilleAjoutee.getReference())).isEqualTo(bouteilleAjoutee);
		Assertions.assertThat(caveTestee.getBouteilles().size()).isEqualTo(nombreBouteilleInitial + 1);

	}

	@Test(expected = PlaceInsuffisanteException.class)
	@Ignore
	public void testAjouterBouteillePlaceInsuffisante() throws PlaceInsuffisanteException,
			BouteilleInexistanteException, CaveInexistanteException, BouteilleInvalideException, CaveInvalideException, BouteilleDejaExistanteException {

		// Initialisation du test
		final Bouteille bouteilleAjoutee = new Bouteille();
		bouteilleAjoutee.setAnnee(2015);
		bouteilleAjoutee.setContenance(0.75d);
		bouteilleAjoutee.setDesignation("Beaujolais nouveau");
		bouteilleAjoutee.setReference("REF_01");
		
		// Nous initialisons la capacité maximale de la cave afin qu'elle soit considérée comme pleine.
		caveTestee.setCapaciteMaximale(caveTestee.getBouteilles().size());

		// Lancement du test
		caveService.ajouterBouteille(bouteilleAjoutee, caveTestee);
	}

	@Ignore
	public void test_retirerBouteille_nominal() {

		// Initialisation
		final CaveService caveService = new CaveService();

		final List<Bouteille> bouteilles = new ArrayList<>();

		final Bouteille bouteille1 = Mockito.mock(Bouteille.class);
		final Bouteille bouteille2 = Mockito.mock(Bouteille.class);
		final Bouteille bouteille3 = Mockito.mock(Bouteille.class);

		bouteilles.add(bouteille1);
		bouteilles.add(bouteille2);
		bouteilles.add(bouteille3);
		
		final Cave cave = new Cave();
		cave.setBouteilles(bouteilles);
		cave.setCapaciteMaximale(10);
		cave.setNomProprietaire("Moi");
		cave.setReference("Coucou!");

		final Integer nombreInitialBouteilles = cave.getBouteilles().size();

		// Lancement du test
		caveService.retirerBouteille(bouteille1);

		// Vérification du test

		/*
		 * Nous vérifions que le nombre de bouteilles total est bien n-1 après
		 * le retrait d'une bouteille.
		 */
		Assertions.assertThat(cave.getBouteilles().size()).isEqualTo(nombreInitialBouteilles - 1);

		/*
		 * Nous vérifions que la bouteille retirée n'existe plus dans la liste
		 * des bouteilles de la cave.
		 */
		// FIXME : Corriger ce test en utilisant de l'injection. 
		// Assertions.assertThat(cave.getBouteilles().containsKey("B1")).isFalse();

	}
	
	@Ignore
	public void testAjouterBouteillesNominal() throws PlaceInsuffisanteException, BouteilleInexistanteException, CaveInexistanteException, BouteilleInvalideException, CaveInvalideException {
		
		// Initialisation du test
		final Bouteille bouteille1 = mock(Bouteille.class);
		final Bouteille bouteille2 = mock(Bouteille.class);
		final Bouteille bouteille3 = mock(Bouteille.class);
		
		when(bouteille1.getReference()).thenReturn("REF_A01");
		when(bouteille2.getReference()).thenReturn("REF_A02");
		when(bouteille3.getReference()).thenReturn("REF_A03");
		
		final Collection<Bouteille> bouteilles = new ArrayList<>();
		bouteilles.add(bouteille1);
		bouteilles.add(bouteille2);
		bouteilles.add(bouteille3);
		
		final Integer nombreBouteilleInitial = caveTestee.getBouteilles().size();
		
		// Lancement du test
		caveService.ajouterBouteilles(bouteilles, caveTestee);
		
		// Vérification du test
		assertThat(bouteille1).isIn(caveTestee.getBouteilles());
		assertThat(bouteille2).isIn(caveTestee.getBouteilles());
		assertThat(bouteille3).isIn(caveTestee.getBouteilles());
		assertThat(nombreBouteilleInitial + 3).isEqualTo(caveTestee.getBouteilles().size());
		
	}

}
