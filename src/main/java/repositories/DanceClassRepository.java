
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.DanceClass;

@Repository
public interface DanceClassRepository extends JpaRepository<DanceClass, Integer> {

}
