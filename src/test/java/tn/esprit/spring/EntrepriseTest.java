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
import tn.esprit.spring.services.EntrepriseServiceImpl;
import tn.esprit.spring.services.IEntrepriseService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EntrepriseTest {

	
	private static final Logger l = LogManager.getLogger(EntrepriseServiceImpl.class);
	
	@Autowired
	IEntrepriseService eS;
	
	@Autowired
	EntrepriseRepository eR;
	
	@Autowired
	DepartementRepository dR;
	
	private Entreprise e;
	private Departement d;
	
	@Before
	public void setUp() {
		e = eR.save(new Entreprise("BluePrint", "Unity"));
		d = dR.save(new Departement("TIC"));
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
		int Id = eS.ajouterDepartement(dep);
		long stopTime = System.nanoTime();
        double elapsedTimeInSecond = (double) (stopTime - startTime) / 1_000_000_000;
        l.log(Level.INFO, () -> "Execution time of AjouterDepartement : " + elapsedTimeInSecond +" seconds");
		Optional<Departement> departement = dR.findById(Id);
		if(departement.isPresent()) {
			String name= departement.get().getName();
			assertEquals("GRH", name);
		}
		
	}
}