
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Award;

import java.util.Collection;

@Repository
public interface AwardRepository extends JpaRepository<Award, Integer> {
    @Query("select a from Award a where a.competition.id = ?1")
    public Collection<Award> awardsByCompetition(int competitionId);
}
