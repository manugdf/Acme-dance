
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.DanceClass;
import domain.DanceSchool;
import domain.DanceTest;
import domain.Manager;
import domain.Material;
import domain.Payment;
import domain.Schedule;
import domain.Teacher;
import forms.DanceClassAuxForm;
import forms.DanceClassForm;
import repositories.DanceClassRepository;

@Service
@Transactional
public class DanceClassService {

	@Autowired
	private DanceClassRepository	danceClassRepository;
	@Autowired
	private DanceSchoolService		danceSchoolService;
	@Autowired
	private ManagerService			managerService;
	@Autowired
	private Validator				validator;


	// Constructor
	public DanceClassService() {
		super();
	}

	// Simple CRUD methods

	public DanceClass create() {
		final DanceClass res = new DanceClass();

		//res.setAlumns(new ArrayList<Alumn>());
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

	public DanceClass save(final DanceClass danceClass) {
		Assert.notNull(danceClass);

		return this.danceClassRepository.save(danceClass);
	}

	public void delete(final DanceClass danceClass) {
		Assert.notNull(danceClass);
		this.danceClassRepository.delete(danceClass);
	}

	public Collection<DanceClass> findDanceClassesBySchool(final int danceSchoolId) {
		final DanceSchool school = this.danceSchoolService.findOne(danceSchoolId);
		final Collection<DanceClass> classes = school.getDanceClasses();
		return classes;
	}

	public Collection<DanceClass> findDanceClassesByManager(final int managerId) {
		final Manager manager = this.managerService.findOne(managerId);
		final Collection<DanceClass> danceClasses = new ArrayList<DanceClass>();
		final Collection<DanceSchool> danceSchools = manager.getDanceSchools();
		for (final DanceSchool d : danceSchools)
			for (final DanceClass da : d.getDanceClasses())
				if (!danceClasses.contains(da))
					danceClasses.add(da);
		return danceClasses;
	}

	public Collection<DanceClass> findAllByManager(final int managerId) {

		final Collection<DanceClass> res = this.danceClassRepository.findAllByManagerId(managerId);

		return res;

	}

	public DanceClass reconstruct(final DanceClassForm danceClassForm, final BindingResult binding) {
		final DanceClass res = this.create();

		res.setDanceSchool(danceClassForm.getDanceSchool());
		res.setStyle(danceClassForm.getStyle());
		res.setMaxAlumns(danceClassForm.getMaxAlumns());
		res.setMonthlyPrice(danceClassForm.getMonthlyPrice());
		res.setYearlyPrice(danceClassForm.getYearlyPrice());
		res.setDescription(danceClassForm.getDescription());

		this.validator.validate(res, binding);
		return res;
	}

	public DanceClass reconstructEdit(final DanceClass danceClassForm, final DanceClass res) {

		res.setStyle(danceClassForm.getStyle());
		res.setMaxAlumns(danceClassForm.getMaxAlumns());
		res.setMonthlyPrice(danceClassForm.getMonthlyPrice());
		res.setYearlyPrice(danceClassForm.getYearlyPrice());
		res.setDescription(danceClassForm.getDescription());

		return res;
	}

	public DanceClass reconstructAux(final DanceClassAuxForm danceClassAuxForm) {
		final DanceClass aux = danceClassAuxForm.getDanceClass();
		aux.getTeachers().add(danceClassAuxForm.getTeacher());

		return aux;

	}

	public Collection<DanceClass> findDanceClassActiveByAlumn(int alumnId){
		return danceClassRepository.findDanceClassActiveByAlumn(alumnId);
	}
}
