
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Material;
import repositories.MaterialRepository;

@Service
@Transactional
public class MaterialService {

	@Autowired
	private MaterialRepository materialRepository;


	// Constructor
	public MaterialService() {
		super();
	}

	// Simple CRUD methods

	public Collection<Material> findAll() {
		return this.materialRepository.findAll();
	}

	public Material findOne(final int id) {
		Material result;
		result = this.materialRepository.findOne(id);
		return result;
	}
}
