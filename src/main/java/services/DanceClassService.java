
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import domain.*;
import forms.DanceClassForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
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
	@Autowired
	private Validator validator;


	// Constructor
	public DanceClassService() {
		super();
	}

	// Simple CRUD methods

	public DanceClass create(){
		DanceClass res = new DanceClass();

		res.setAlumns(new ArrayList<Alumn>());
		res.setTeachers(new ArrayList<Teacher>());
		res.setMaterials(new ArrayList<Material>());
		res.setSchedules(new ArrayList<Schedule>());
		res.setDanceTests(new ArrayList<DanceTest>());
		res.setPayments(new ArrayList<Payment>());

		return res;
	}

	public Collection<DanceClass> findAll() {
		return this.danceClassRepository.findAll();
	}

	public DanceClass findOne(final int id) {
		DanceClass result;
		result = this.danceClassRepository.findOne(id);
		return result;
	}

	public DanceClass save(DanceClass danceClass){
		Assert.notNull(danceClass);

		return this.danceClassRepository.save(danceClass);
	}

	public void delete(DanceClass danceClass){
		Assert.notNull(danceClass);
		this.danceClassRepository.delete(danceClass);
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

	
	

	public DanceClass reconstruct(DanceClassForm danceClassForm, BindingResult binding){
		DanceClass res = create();

		res.setDanceSchool(danceClassForm.getDanceSchool());
		res.setStyle(danceClassForm.getStyle());
		res.setMaxAlumns(danceClassForm.getMaxAlumns());
		res.setMonthlyPrice(danceClassForm.getMonthlyPrice());
		res.setYearlyPrice(danceClassForm.getYearlyPrice());
		res.setDescription(danceClassForm.getDescription());

		this.validator.validate(res, binding);
		return res;
	}
}
