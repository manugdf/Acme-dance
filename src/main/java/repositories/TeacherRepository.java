
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Teacher;

import java.util.Collection;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
	
	@Query("select t from Teacher t where t.userAccount.id=?1")
	public Teacher findByPrincipal(int teacherId);


	@Query("select r.teacher from Review r where r.alumn.id = ?1")
	public Collection<Teacher> findTeachersByAlumnReview(int alumnId);

	@Query("select t from Alumn a join a.payments p join p.danceClass.teachers t where a.id = ?1")
	public Collection<Teacher> teachersCanReview(int alumnId);
}
