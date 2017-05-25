
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Alumn;
import domain.DanceTest;
import repositories.DanceTestRepository;

@Service
@Transactional
public class DanceTestService {

	@Autowired
	private DanceTestRepository danceTestRepository;
	
	@Autowired
	private DanceClassService danceClassService;

	@Autowired
	private AlumnService alumnService;
	@Autowired
	private Validator validator;


	// Constructor
	public DanceTestService() {
		super();
	}

	// Simple CRUD methods
	public DanceTest create(){
		DanceTest res=new DanceTest();
		
		res.setAlumns(new ArrayList<Alumn>());

		return res;
	}

	public Collection<DanceTest> findAll() {
		return this.danceTestRepository.findAll();
	}

	public DanceTest findOne(final int id) {
		DanceTest result;
		result = this.danceTestRepository.findOne(id);
		return result;
	}
	
	public DanceTest save(DanceTest danceTest){
		Assert.notNull(danceTest);
		return danceTestRepository.save(danceTest);
	}
	
	
	public DanceTest reconstruct(DanceTest danceTest,int danceClassId,BindingResult bindingResult){
		DanceTest res;
		
		if(danceTest.getId()==0){
			res=danceTest;
			res.setAlumns(new ArrayList<Alumn>());
			res.setDanceClass(danceClassService.findOne(danceClassId));
		}else{
			res=danceTestRepository.findOne(danceTest.getId());
			
			res.setDanceLevel(danceTest.getDanceLevel());
			res.setLimitInscription(danceTest.getLimitInscription());
			res.setTestDate(danceTest.getTestDate());
		}
		validator.validate(res, bindingResult);
		
		return res;
	}

	public Collection<DanceTest> findDanceTestsAvailableByDanceClass(int danceClassId){
		return danceTestRepository.findDanceTestsAvailableByDanceClass(danceClassId);
	}

	public void joinInDanceTest(int danceTestId){
		DanceTest danceTest = findOne(danceTestId);
		danceTest.getAlumns().add(alumnService.findByPrincipal());
		save(danceTest);
	}

	public Collection<DanceTest> findDanceTestsByAlumn(int alumnId){
		return danceTestRepository.findDanceTestsByAlumn(alumnId);
	}

	public Collection<DanceTest> danceTestsCanJoinIn(int alumnId){
		return danceTestRepository.danceTestsCanJoinIn(alumnId);
	}

}
