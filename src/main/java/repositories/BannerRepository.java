
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Banner;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Integer> {

	@Query("select b from Banner b where b.manager.id = ?1")
	Collection<Banner> findAllByManager(int managerId);

	@Query("select b from Banner b where b.state = 'PENDING' ")
	Collection<Banner> findAllPending();

	@Query("select b from Banner b where b.state = 'ACCEPTED' ")
	Collection<Banner> findAllAccepted();

}
