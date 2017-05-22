
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.DanceClass;
import domain.DanceSchool;
import domain.Manager;
import repositories.DanceClassRepository;

@Service
@Transactional
public class DanceClassService {

	@Autowired
	private DanceClassRepository	danceClassRepository;
	@Autowired
	private DanceSchoolService		danceSchoolService;
	@Autowired
	private ManagerService 			managerService;


	// Constructor
	public DanceClassService() {
		super();
	}

	// Simple CRUD methods

	public Collection<DanceClass> findAll() {
		return this.danceClassRepository.findAll();
	}

	public DanceClass findOne(final int id) {
		DanceClass result;
		result = this.danceClassRepository.findOne(id);
		return result;
	}

	public Collection<DanceClass> findDanceClassesBySchool(final int danceSchoolId) {
		final DanceSchool school = this.danceSchoolService.findOne(danceSchoolId);
		final Collection<DanceClass> classes = school.getDanceClasses();
		return classes;
	}
	
	public Collection<DanceClass> findDanceClassesByManager(int managerId){
		final Manager manager=managerService.findOne(managerId);
		final Collection<DanceClass> danceClasses=new ArrayList<DanceClass>();
		final Collection<DanceSchool> danceSchools=manager.getDanceSchools();
		for(final DanceSchool d:danceSchools){
			for(final DanceClass da:d.getDanceClasses()){
				if(!danceClasses.contains(da)){
					danceClasses.add(da);
				}
			}
		}
		return danceClasses;
	}

	
	
}
