
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Alumn;

@Repository
public interface AlumnRepository extends JpaRepository<Alumn, Integer> {

}
