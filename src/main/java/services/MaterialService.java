
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Material;
import domain.Teacher;
import repositories.MaterialRepository;

@Service
@Transactional
public class MaterialService {

	@Autowired
	private MaterialRepository	materialRepository;

	@Autowired
	private TeacherService		teacherService;


	// Constructor
	public MaterialService() {
		super();
	}

	// Simple CRUD methods

	public Material create() {
		final Material material = new Material();
		final Teacher teacher = this.teacherService.findByPrincipal();
		Assert.isTrue(this.teacherService.isTeacher());
		material.setTeacher(teacher);
		return material;
	}

	public Collection<Material> findAll() {
		return this.materialRepository.findAll();
	}

	public Material findOne(final int id) {
		Material result;
		result = this.materialRepository.findOne(id);
		return result;
	}

	public Material save(final Material material) {
		Assert.notNull(material);
		final Material saved = this.materialRepository.save(material);
		return saved;
	}

	public void delete(final Material material) {
		this.materialRepository.delete(material);
	}

}
