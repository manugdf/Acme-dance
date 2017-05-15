
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.DanceTest;

@Repository
public interface DanceTestRepository extends JpaRepository<DanceTest, Integer> {

}
