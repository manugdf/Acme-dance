
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.DanceTest;

import java.util.Collection;

@Repository
public interface DanceTestRepository extends JpaRepository<DanceTest, Integer> {
    @Query("select d from DanceTest d where d.danceClass.id = ?1 and(d.limitInscription>CURRENT_DATE)")
    public Collection<DanceTest> findDanceTestsAvailableByDanceClass(int danceClassId);

    @Query("select a.danceTests from Alumn a where a.id = ?1")
    public Collection<DanceTest> findDanceTestsByAlumn(int alumnId);

    @Query(" select d from Alumn a join a.payments p join p.danceClass.danceTests d where a.id = ?1 and(d.limitInscription > CURRENT_DATE)")
    public Collection<DanceTest> danceTestsCanJoinIn(int alumnId);
}
