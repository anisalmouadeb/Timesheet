package tn.esprit.spring;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;



import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.services.EmployeServiceImpl;
import tn.esprit.spring.services.IEmployeService;
import tn.esprit.spring.services.IEntrepriseService;

@SpringBootTest
class EmployeServiceTest {
	
	
	
		
		@Autowired
		EmployeServiceImpl em;

		@Autowired
		IEmployeService employeService;
		
		@Autowired
		private IEmployeService iEmployeService;
		@Autowired
		IEntrepriseService ientrepriseservice;

		
		@Test
		@Order(1)
		void testAjouterEmploye() {
			Employe employe = new Employe("aze", "kh", "aalop", true, Role.ADMINISTRATEUR);
			Employe employe2 = new Employe("axxx", "ggg", "aalop", true, Role.ADMINISTRATEUR);
			Employe employe1 = new Employe("qqqq", "rrrr", "kwala", true, Role.CHEF_DEPARTEMENT);
			assertNotEquals(0, employeService.ajouterEmploye(employe));
			assertNotEquals(0, employeService.ajouterEmploye(employe1));
			assertNotEquals(0, employeService.ajouterEmploye(employe2));

		}

		@Test
		@Order(2)
		void testMettreAjourEmailByEmployeId() {
			assertNotEquals(0, employeService.mettreAjourEmailByEmployeId("test5@gmail.com", 2));
		}

		@Test
		@Order(3)
		void testGetAllEmployes() {
			assertNotEquals(0, employeService.getAllEmployes().size());

		}

		@Test
		@Order(5)
		void testDeleteEmployeById() {
			assertNotEquals(0, employeService.deleteEmployeById(1));

		}

		@Test
		@Order(4)
		void testgetEmployePrenomById() {
			assertNotNull(employeService.getEmployePrenomById(2));
		}
			
		
		
	
}