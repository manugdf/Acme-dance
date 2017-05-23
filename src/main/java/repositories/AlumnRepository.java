
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Alumn;

@Repository
public interface AlumnRepository extends JpaRepository<Alumn, Integer> {

	@Query("select a from Alumn a where a.userAccount.id=?1")
	public Alumn findByPrincipal(int alumnId);

	@Query("select a from Alumn a join a.payments p join p.danceClass d where d.danceSchool = ?1")
	public Collection<Alumn> findAlumnsBySchoolId(int schoolId);

}
