package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.DanceCertificate;
import repositories.DanceCertificateRepository;

import java.util.Collection;

@Service
@Transactional
public class DanceCertificateService {
	
	@Autowired
	private DanceCertificateRepository danceCertificateRepository;
	
	@Autowired
	private AlumnService alumnService;
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private Validator validator;

	// Constructor
	public DanceCertificateService() {
		super();
	}

	// Simple CRUD methods
	public DanceCertificate findOne(final int danceCertificateId) {
		DanceCertificate result;
		result = this.danceCertificateRepository.findOne(danceCertificateId);
		return result;
	}
	
	public DanceCertificate save(DanceCertificate danceCertificate){
		Assert.notNull(danceCertificate);
		return danceCertificateRepository.save(danceCertificate);
	}

	// Other methods
	public DanceCertificate reconstruct(DanceCertificate danceCertificate, int alumnId,BindingResult bindingResult){
		DanceCertificate res;
		
		if(danceCertificate.getId()==0){
			res=danceCertificate;
			res.setTeacher(teacherService.findByPrincipal());
			res.setAlumn(alumnService.findOne(alumnId));
		}else{
			res=danceCertificateRepository.findOne(danceCertificate.getId());
			
			res.setCertificateDate(danceCertificate.getCertificateDate());
			res.setDanceLevel(danceCertificate.getDanceLevel());
		}
		validator.validate(res, bindingResult);
		
		return res;
	}

	public Collection<DanceCertificate> findDanceCertificatesByAlumn(int alumnId){
		return danceCertificateRepository.findDanceCertificatesByAlumn(alumnId);
	}
}
