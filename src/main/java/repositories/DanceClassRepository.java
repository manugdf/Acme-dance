
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.DanceClass;

@Repository
public interface DanceClassRepository extends JpaRepository<DanceClass, Integer> {

	@Query("select d from DanceClass d where d.danceSchool.manager.id=?1 and d.danceSchool.state='ACCEPTED'")
	public Collection<DanceClass> findAllByManagerId(int id);

	@Query("select p.danceClass from Payment p where p.alumn.id = ?1 and(p.endDate > CURRENT_DATE)")
	public Collection<DanceClass> findDanceClassActiveByAlumn(int alumnId);
}
