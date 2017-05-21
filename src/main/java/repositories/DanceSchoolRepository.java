
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.DanceSchool;

@Repository
public interface DanceSchoolRepository extends JpaRepository<DanceSchool, Integer> {

	@Query("select d from DanceSchool d where d.description like ?1 or d.name like ?1 or d.location.address like ?1 or d.location.city like ?1 or d.location.province like ?1")
	Collection<DanceSchool> findSchoolByKeyword(String word);

	@Query("select d from DanceSchool d where d.state = 'ACCEPTED' ")
	Collection<DanceSchool> findAllAccepted();

}
