
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.DanceSchool;

@Repository
public interface DanceSchoolRepository extends JpaRepository<DanceSchool, Integer> {

}
