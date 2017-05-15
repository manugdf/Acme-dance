
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.CompetitionPlanner;

@Repository
public interface CompetitionPlannerRepository extends JpaRepository<CompetitionPlanner, Integer> {

}
