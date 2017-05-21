
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.DanceClass;
import domain.DanceSchool;
import domain.Teacher;
import repositories.TeacherRepository;

@Service
@Transactional
public class TeacherService {

	@Autowired
	private TeacherRepository	teacherRepository;

	@Autowired
	private DanceSchoolService	danceSchoolService;


	// Constructor
	public TeacherService() {
		super();
	}

	// Simple CRUD methods

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

}
