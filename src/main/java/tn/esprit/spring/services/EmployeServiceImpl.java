package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.ContratDto;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.EmployeDto;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.TimesheetRepository;


import java.util.Optional;



@Service
public class EmployeServiceImpl implements IEmployeService {

	private static final Logger l = LoggerFactory.getLogger(EmployeServiceImpl.class);

	@Autowired
	EmployeRepository employeRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	ContratRepository contratRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;

	@Override
	public int ajouterEmploye(Employe employe) {
		try {
			employeRepository.save(employe);
			l.info("Employee added  : {} .", employe);
			return employe.getId();

		} catch (Exception e) {
			l.error("Add failure");
			return 0;
		}

	}

	public int mettreAjourEmailByEmployeId(String email, int employeId) {

		try {
			Optional<Employe> employeManagedEntity = employeRepository.findById(employeId);

			if (employeManagedEntity.isPresent()) {
				Employe employe = employeManagedEntity.get();
				employe.setEmail(email);
				employeRepository.save(employe);
				l.info("Employee Updated  : {} .", employe);

				return employeId;

			} else {

				l.warn("Employe don't exist");
				return 0;

			}

		} catch (Exception e) {

			l.error(e.toString());
			return 0;
		}

	}

	@Transactional
	public int affecterEmployeADepartement(int employeId, int depId) {
		try {

			Optional<Departement> depManagedEntity = deptRepoistory.findById(depId);
			Optional<Employe> employeManagedEntity = employeRepository.findById(employeId);

			if (depManagedEntity.isPresent() && employeManagedEntity.isPresent()) {

				Departement department = depManagedEntity.get();
				Employe employe = employeManagedEntity.get();

				if (department.getEmployes() == null) {

					List<Employe> employes = new ArrayList<>();
					employes.add(employe);
					department.setEmployes(employes);
				} else {

					department.getEmployes().add(employe);
				}

				deptRepoistory.save(department);

				l.info("departement affected  : {} .", department);
				l.info("employee affected  : {} .", employe);

				return employeId ;

			}

			else {
				l.warn("Cet employe ou ce departement n'existe pas");
				return 0;
			}

		} catch (Exception e) {
			l.error(e.toString());
			return 0;
		}
	}

	@Transactional
	public void desaffecterEmployeDuDepartement(int employeId, int depId) {
		Departement dep = deptRepoistory.findById(depId).orElse(null);
		if (dep != null) {
			int employeNb = dep.getEmployes().size();
			for (int index = 0; index < employeNb; index++) {
				if (dep.getEmployes().get(index).getId() == employeId) {
					dep.getEmployes().remove(index);
					break;
				}
			}
		} else {
			l.warn("Dep don't exist");
		}
	}

	public int ajouterContrat(Contrat contrat) {
		contratRepoistory.save(contrat);
		return contrat.getReference();
	}

	public void affecterContratAEmploye(int contratId, int employeId) {
		Contrat contratManagedEntity = contratRepoistory.findById(contratId).orElse(null);
		Employe employeManagedEntity = employeRepository.findById(employeId).orElse(null);

		if ((contratManagedEntity != null) && (employeManagedEntity != null)) {
			contratManagedEntity.setEmploye(employeManagedEntity);
			contratRepoistory.save(contratManagedEntity);
			l.info("Contract affected");

		} else {
			l.warn("Employe or Contract not found");
		}

	}

	public String getEmployePrenomById(int employeId) {
		Employe employeManagedEntity = employeRepository.findById(employeId).orElse(null);
		if (employeManagedEntity != null) {
			return employeManagedEntity.getPrenom();

		} else {
			l.warn("Employe don't exist");
		}
		return null;
	}

	public int deleteEmployeById(int employeId) {
		Employe employe = employeRepository.findById(employeId).orElse(null);

		if (employe != null) {
			for (Departement dep : employe.getDepartements()) {
				dep.getEmployes().remove(employe);
			}

			employeRepository.delete(employe);
			l.info("employe deleted");
			return 1;
		} else {
			l.warn("Employe dosen't exist");
			return 0;
		}
	}

	public void deleteContratById(int contratId) {
		Contrat contratManagedEntity = contratRepoistory.findById(contratId).orElse(null);
		if (contratManagedEntity != null) {
			contratRepoistory.delete(contratManagedEntity);
			l.info("Contract Deleted");
		} else {
			l.warn("Contract not found");
		}

	}

	public int getNombreEmployeJPQL() {
		return employeRepository.countemp();
	}

	public List<String> getAllEmployeNamesJPQL() {
		return employeRepository.employeNames();

	}

	public List<Employe> getAllEmployeByEntreprise(Entreprise entreprise) {
		return employeRepository.getAllEmployeByEntreprisec(entreprise);
	}

	public void mettreAjourEmailByEmployeIdJPQL(String email, int employeId) {
		employeRepository.mettreAjourEmailByEmployeIdJPQL(email, employeId);

	}

	public void deleteAllContratJPQL() {
		employeRepository.deleteAllContratJPQL();
	}

	public float getSalaireByEmployeIdJPQL(int employeId) {
		return employeRepository.getSalaireByEmployeIdJPQL(employeId);
	}

	public Double getSalaireMoyenByDepartementId(int departementId) {
		return employeRepository.getSalaireMoyenByDepartementId(departementId);
	}

	public List<Timesheet> getTimesheetsByMissionAndDate(Employe employe, Mission mission, Date dateDebut,
														 Date dateFin) {
		return timesheetRepository.getTimesheetsByMissionAndDate(employe, mission, dateDebut, dateFin);
	}

	public List<Employe> getAllEmployes() {
		return (List<Employe>) employeRepository.findAll();
	}

	@Override
	public Employe mapToEntity(EmployeDto employeDto) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(employeDto, Employe.class);

	}

	@Override
	public EmployeDto mapToDto(Employe employe) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(employe, EmployeDto.class);
	}
	
	@Override
	public Contrat mapToEntityC(ContratDto contratDto) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(contratDto, Contrat.class);

	}

	@Override
	public ContratDto mapToDtoC(Contrat contrat) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(contrat, ContratDto.class);

	}

	
}