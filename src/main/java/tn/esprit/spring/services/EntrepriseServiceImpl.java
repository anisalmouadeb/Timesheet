package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;

@Service
public class EntrepriseServiceImpl implements IEntrepriseService {
	private static final Logger logger = LoggerFactory.getLogger(EntrepriseServiceImpl.class);

	@Autowired
    EntrepriseRepository entrepriseRepoistory;
	@Autowired
	DepartementRepository deptRepoistory;
	
	public int ajouterEntreprise(Entreprise entreprise) {
		try{
			logger.info("Ajouter Entreprise");
			logger.debug("je suis entrain d'ajouter une entreprise");
		    entrepriseRepoistory.save(entreprise);
		    logger.info("entreprise ajoutee");
		}catch (Exception e) {
			logger.error("Erreur : " );
		}finally{
			logger.info("Methode ajouter entreprise fini");
		}
		return entreprise.getId();
	}
	
	
	public int ajouterDepartement(Departement dep) {
		deptRepoistory.save(dep);
		return dep.getId();
	}
	
	public void affecterDepartementAEntreprise(int depId, int entrepriseId) {
		Optional <Entreprise> entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId);
		Optional <Departement> depManagedEntity = deptRepoistory.findById(depId);
		if (entrepriseManagedEntity.isPresent()) {
        Entreprise	ent = entrepriseManagedEntity.get();
			if (depManagedEntity.isPresent()) {
		Departement		dep = depManagedEntity.get();
		dep.setEntreprise(ent);
		deptRepoistory.save(dep);
			}
		}
}
	
	public List<String> getAllDepartementsNamesByEntreprise(int entrepriseId) {
		Entreprise entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId).get();
		List<String> depNames = new ArrayList<>();
		for(Departement dep : entrepriseManagedEntity.getDepartements()){
			depNames.add(dep.getName());
		}
		
		return depNames;
	}

	@Transactional
	public void deleteEntrepriseById(int entrepriseId) {
		entrepriseRepoistory.delete(entrepriseRepoistory.findById(entrepriseId).get());	
	}

	@Transactional
	public void deleteDepartementById(int depId) {
		Optional <Departement> depEnt = deptRepoistory.findById(depId);
		if (depEnt.isPresent()) {
		Departement	dep = depEnt.get();
		
		deptRepoistory.delete(dep);	
	}
	}


	public Entreprise getEntrepriseById(int entrepriseId) {
		return entrepriseRepoistory.findById(entrepriseId).get();	
	}

}
