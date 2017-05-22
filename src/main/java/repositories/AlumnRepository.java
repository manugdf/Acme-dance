
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Alumn;

@Repository
public interface AlumnRepository extends JpaRepository<Alumn, Integer> {

	@Query("select a from Alumn a where a.userAccount.id=?1")
	public Alumn findByPrincipal(int alumnId);

}
