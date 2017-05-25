
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.CompetitionPlanner;

@Repository
public interface CompetitionPlannerRepository extends JpaRepository<CompetitionPlanner, Integer> {

	@Query("select a from CompetitionPlanner a where a.userAccount.id=?1")
	public CompetitionPlanner findByPrincipal(int id);

}
