
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Material;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {

}
