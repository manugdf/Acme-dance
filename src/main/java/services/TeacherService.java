
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.DanceCertificate;
import domain.DanceClass;
import domain.DanceSchool;
import domain.Manager;
import domain.Material;
import domain.Message;
import domain.Review;
import domain.Teacher;
import forms.TeacherForm;
import repositories.TeacherRepository;
import security.Authority;
import security.UserAccount;

@Service
@Transactional
public class TeacherService {

	@Autowired
	private TeacherRepository	teacherRepository;

	@Autowired
	private DanceSchoolService	danceSchoolService;
	@Autowired
	private DanceClassService	danceClassService;
	@Autowired
	private ManagerService		managerService;


	// Constructor
	public TeacherService() {
		super();
	}

	// Simple CRUD methods
	public Teacher create() {

		final UserAccount userAccount = new UserAccount();
		final Authority aut = new Authority();
		aut.setAuthority(Authority.TEACHER);
		final Collection<Authority> authorities = userAccount.getAuthorities();
		authorities.add(aut);
		userAccount.setAuthorities(authorities);

		final Teacher res = new Teacher();
		res.setUserAccount(userAccount);
		res.setAverageScore(0.0);
		res.setReviews(new ArrayList<Review>());
		res.setDanceCertificates(new ArrayList<DanceCertificate>());
		res.setDanceClasses(new ArrayList<DanceClass>());
		res.setMaterials(new ArrayList<Material>());
		res.setMessagesReceived(new ArrayList<Message>());
		res.setMessagesSended(new ArrayList<Message>());

		return res;
	}

	public Collection<Teacher> findAll() {
		return this.teacherRepository.findAll();
	}

	public Teacher findOne(final int id) {
		Teacher result;
		result = this.teacherRepository.findOne(id);
		return result;
	}

	public Teacher save(final Teacher teacher) {

		return this.teacherRepository.save(teacher);
	}

	public Collection<Teacher> findTeachersBySchool(final int id) {
		final DanceSchool d = this.danceSchoolService.findOne(id);
		final Collection<Teacher> teachers = new ArrayList<Teacher>();
		final Collection<DanceClass> classes = d.getDanceClasses();
		for (final DanceClass dan : classes)
			for (final Teacher t : dan.getTeachers())
				if (!teachers.contains(t))
					teachers.add(t);
		return teachers;
	}

	public void register(Teacher teacher) {
		Assert.notNull(teacher);
		UserAccount userAccount;
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		userAccount = teacher.getUserAccount();
		userAccount.setPassword(encoder.encodePassword(userAccount.getPassword(), null));
		teacher.setUserAccount(userAccount);

		Teacher aux=this.save(teacher);
		
		Manager manager = this.managerService.findByPrincipal();
		manager.getTeachers().add(aux);
		this.managerService.save(manager);
	}

	public Teacher reconstruct(final TeacherForm teacherForm) {
		final Teacher teacher = this.create();
		teacher.getUserAccount().setUsername(teacherForm.getUsername());
		teacher.getUserAccount().setPassword(teacherForm.getPassword());
		teacher.setName(teacherForm.getName());
		teacher.setSurname(teacherForm.getSurname());
		teacher.setEmail(teacherForm.getEmail());
		teacher.setPhone(teacherForm.getPhone());
		teacher.setPicture(teacherForm.getPicture());
		teacher.setPresentationVideo(teacherForm.getPresentationVideo());
		//		teacher.getDanceClasses().add(teacherForm.getDanceClass());

		return teacher;
	}

	public Collection<Teacher> findTeachersByClass(final int classId) {
		final DanceClass d = this.danceClassService.findOne(classId);
		final Collection<Teacher> teachers = d.getTeachers();

		return teachers;
	}
	
	public Teacher modify(final Teacher teacher){
		Assert.isTrue(managerService.findByPrincipal().getTeachers().contains(teacher));
		return this.teacherRepository.saveAndFlush(teacher);
	}

	public TeacherForm reconstructForm(final Teacher teacher) {
		final TeacherForm teacherForm = new TeacherForm();
		teacherForm.setAcceptTerms(true);

		teacherForm.setName(teacher.getName());
		teacherForm.setSurname(teacher.getSurname());
		teacherForm.setEmail(teacher.getEmail());
		teacherForm.setPhone(teacher.getPhone());
		teacherForm.setPicture(teacher.getPicture());
		teacherForm.setPresentationVideo(teacher.getPresentationVideo());
		teacherForm.setUsername(teacher.getUserAccount().getUsername());

		return teacherForm;
	}

	public Teacher reconstructEdit(final TeacherForm teacherForm,final Teacher teacher){
		teacher.getUserAccount().setUsername(teacherForm.getUsername());

		if ((teacherForm.getNewpassword().length() > 0 && teacherForm.getRepeatnewpassword().length() > 0 && teacherForm.getNewpassword().equals(teacherForm.getRepeatnewpassword()))) {
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			teacher.getUserAccount().setPassword(encoder.encodePassword(teacherForm.getNewpassword(), null));
		}
		teacher.setName(teacherForm.getName());
		teacher.setSurname(teacherForm.getSurname());
		teacher.setEmail(teacherForm.getEmail());
		teacher.setPhone(teacherForm.getPhone());
		teacher.setPicture(teacherForm.getPicture());
		teacher.setPresentationVideo(teacherForm.getPresentationVideo());
		
		return teacher;
	}
}
