package tn.esprit.spring;

import static org.junit.Assert.*;
import java.util.Optional;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;
import tn.esprit.spring.services.IEntrepriseService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EntrepriseTest {

	
	private static final Logger l = LogManager.getLogger(EntrepriseTest.class);
	
	@Autowired
	IEntrepriseService eS;
	
	@Autowired
	EntrepriseRepository eR;
	
	@Autowired
	DepartementRepository dR;
	
	@Before
	public void setUp() {
		Entreprise e = eR.save(new Entreprise("BluePrint", "Unity"));
		 Departement d = dR.save(new Departement("GRH"));
		d.setEntreprise(e);
		dR.save(d);
}
	
	@After
	public void tearDown() {
		eR.deleteAll();
		dR.deleteAll();
		
	}
	
	@Test
	public void testAjouterDepartement() {
		Departement dep = new Departement("GRH");
		long startTime = System.nanoTime();
		int id = eS.ajouterDepartement(dep);
		long stopTime = System.nanoTime();
        double elapsedTimeInSecond = (double) (stopTime - startTime) / 1_000_000_000;
        l.log(Level.INFO, () -> "Temps necessaire pour l'ajout d'un départelent : " + elapsedTimeInSecond +" seconds");
		Optional<Departement> departement = dR.findById(id);
		if(departement.isPresent()) {
			String name= departement.get().getName();
			assertEquals("GRH", name);
		}
		
	}
	
	@Test
	public void  testajouterEntreprise(){
		Entreprise ent= new Entreprise ("entreprise1","entreprise1");
		long startTime = System.nanoTime();
		int id= eS.ajouterEntreprise(ent); 
		long stopTime = System.nanoTime();
		double elapsedTimeInSecond = (double) (stopTime - startTime) / 1_000_000_000;
        l.log(Level.INFO, () -> "maked time : " + elapsedTimeInSecond +"  total l en seconde seconds");
        Optional<Entreprise> entr = eR.findById(id);
        if(entr.isPresent()) {
			String name= entr.get().getName();
			assertEquals("entreprise1", name);
        }
	   
	}
	/*
	@Test
	public void testDeleteEntrepriseById()
	{
		Entreprise e= new Entreprise("entreprise2","entreprise2");
		int i= eS.ajouterEntreprise(e);
		int id = 46;
		l.info("suppression efféctuée avec succés");
		Assert.assertEquals(i, id);
		eS.deleteEntrepriseById(id);
	}*/
}