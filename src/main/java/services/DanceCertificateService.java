package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Alumn;
import domain.DanceCertificate;
import domain.DanceClass;
import domain.DanceTest;
import domain.Teacher;
import repositories.DanceCertificateRepository;

import java.util.Collection;
import java.util.Date;

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
	private DanceTestService danceTestService;
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
			res.setCertificateDate(new Date());
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
	
	public void createDanceCertificate(DanceCertificate danceCertificate, int alumnId, int danceTestId){
		Assert.isTrue(this.teacherService.isTeacher());
		
		DanceCertificate aux = save(danceCertificate);

		Alumn alumn=alumnService.findOne(alumnId);
		alumn.getDanceCertificates().add(aux);
		alumnService.save(alumn);

		DanceTest danceTest=danceTestService.findOne(danceTestId);
		danceTest.getDanceCertificates().add(aux);
		danceTestService.save(danceTest);
		
	}
}
