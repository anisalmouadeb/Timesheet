package tn.esprit.spring;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.repository.TimesheetRepository;
import tn.esprit.spring.services.IEmployeService;
import tn.esprit.spring.services.ITimesheetService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TimesheetTest {

	@Autowired
	ITimesheetService tss;
	@Autowired
	MissionRepository mr;
	@Autowired
	TimesheetRepository tsr;
	@Autowired
	DepartementRepository dr;
	@Autowired
	IEmployeService employeService;
	private static final Logger l = LogManager.getLogger(TimesheetTest.class);

	

	 @Test public void addMissionTest(){ int id = tss.ajouterMission(new
	  Mission("mis","D1")); 
	  l.info("Mission added");
	  assertTrue("ajout Mission echouer",mr.findById(id).isPresent());
	  
	  }
	 

	
	 @Test
	   public void affecterMissionADepartementTest() {
	  
	  int idm = tss.ajouterMission(new Mission("missss", null)); if (idm > 0) {
	  
	  l.info("mission added");
	  
	  }
	  
	  Departement departement = new Departement("Dept"); dr.save(departement);
	  l.info("departement created and added");
	  
	  tss.affecterMissionADepartement(idm, departement.getId());
	  
	  Departement dept = dr.findById(departement.getId()).get();
	  
	  
	  
	  assertTrue("affectation mission a departement echouer",
	  mr.findById(idm).get().getDepartement().getId() == dept.getId());
	  
	  if( mr.findById(idm).get().getDepartement().getId() == dept.getId()){
	  l.info("Mission found"); }else{ l.warn("warning check your method"); }
	  
	 }
	 
	
	
	 @Test public void findAllMissionByEmployeJPQLTest() throws ParseException
	 {
	 
	 int employeId = employeService .ajouterEmploye(new Employe("anis", "mdb",
	 "anis@gmail.com", false, Role.TECHNICIEN));
	 l.info("employe added with id :" + employeId); int idm =
	 tss.ajouterMission(new Mission("M2SSION100", "Description"));
	 l.info("mission added with id :" + idm); int idm2 =
	 tss.ajouterMission(new Mission("M2SSION200", "Description2"));
	 l.info("mission added with id :" + idm2);
	 tss.affecterMissionADepartement(idm, 1);
	 l.info("mission affecter a departement");
	tss.affecterMissionADepartement(idm2, 1);
	l.info("mission affecter a departement"); SimpleDateFormat dateFormat =
	 new SimpleDateFormat("yyyy-MM-dd"); Date date1 =
	 dateFormat.parse("2022-03-23"); Date date2 =
	 dateFormat.parse("2022-03-28"); tss.ajouterTimesheet(idm, employeId,
	 date1, date2); l.info("timesheet added : employe " + employeId +
	 "mission " + idm); tss.validerTimesheet(idm, employeId, date1, date2, 1);
	 l.info("timesheet valider : employe " + employeId + "mission " + idm);
	 tss.ajouterTimesheet(idm2, employeId, date1, date2);
	 l.info("timesheet added : employe " + employeId + "mission " + idm2);
	 tss.validerTimesheet(idm2, employeId, date1, date2, 1);
	 l.info("timesheet valider : employe " + employeId + "mission " + idm2);
	 List<Mission> missions = tss.findAllMissionByEmployeJPQL(employeId);
	 assertThat(missions.size()).isEqualTo(2); if (missions.size() == 3) {
	 
	 l.info("Missions founded"); } else { l.warn("warning check your method");
	 
	 }
	 
	 }
	

	@Test
	public void getAllEmployeByMissionTest() throws ParseException {

		int employeId = employeService
				.ajouterEmploye(new Employe("anis11", "mdb", "anis@gmail.com", false, Role.TECHNICIEN));
		l.info("employe added with id :" + employeId);
		int employeId2 = employeService
				.ajouterEmploye(new Employe("anis22", "mdb", "anis@gmail.com", false, Role.TECHNICIEN));
		l.info("employe added with id :" + employeId2);
		int idm = tss.ajouterMission(new Mission("M2SSION", "Description"));
		l.info("mission added with id :" + idm);
		tss.affecterMissionADepartement(idm, 1);
		l.info("mission affecter a departement");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = dateFormat.parse("2022-03-23");
		Date date2 = dateFormat.parse("2022-03-28");
		tss.ajouterTimesheet(idm, employeId, date1, date2);
		l.info("timesheet added : employe " + employeId + "mission " + idm);
		tss.validerTimesheet(idm, employeId, date1, date2, 1);
		l.info("timesheet valider : employe " + employeId + "mission " + idm);
		tss.ajouterTimesheet(idm, employeId2, date1, date2);
		l.info("timesheet added : employe " + employeId2 + "mission " + idm);
		tss.validerTimesheet(idm, employeId2, date1, date2, 1);
		l.info("timesheet valider : employe " + employeId2 + "mission " + idm);
	
		List<Employe> emps = tss.getAllEmployeByMission(idm);
		assertThat(emps.size()).isEqualTo(2);
		if (emps.size() == 2) {
			 
			 l.info("employes founded"); } else { l.warn("warning check your method");

	}

}
}