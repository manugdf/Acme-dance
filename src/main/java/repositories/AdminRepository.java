
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Admin;
import domain.Alumn;
import domain.Manager;
import domain.Teacher;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

	@Query("select a from Admin a where a.userAccount.id=?1")
	public Admin findByPrincipal(int adminId);
	
	//Dashboard
	// a.El empresario o empresarios con más escuelas aceptadas. 
	@Query("select m from Manager m where (select count(d) from DanceSchool d where (d.state='ACCEPTED' and d.manager=m)) >=ALL(select (select count(d) from DanceSchool d where (d.state='ACCEPTED' and d.manager=mm)) from Manager mm)")
	public Collection<Manager> managerMoreDanceSchoolAccepted();

	// b.El ratio de escuelas aceptadas respecto a las que han sido rechazadas.
	@Query("select ((count(d)*1.0)/(select count(dd) from DanceSchool dd where dd.state = 'REJECTED'))from DanceSchool d where d.state = 'ACCEPTED'")
	public Integer acceptedDeniedRatio();

	// c.El profesor o profesores con el mejor rating de opiniones.
	@Query("select t from Teacher t where t.averageScore >=ALL(select t.averageScore from Teacher t)")
	public Collection<Teacher> bestRating();

	// d.El ratio de mensajes enviados por usuario.
	@Query("select avg(a.messagesSended.size) from Actor a")
	public Double messagesUsersRatio();

	// e.El usuario o usuarios que más mensajes ha enviado.
	@Query("select a from Actor a where a.messagesSended.size >=ALL(select a.messagesSended.size from Actor a)")
	public Collection<Actor> actorMoreMessageSend();

	// f.El alumno o alumnos que se han apuntado a más clases.
	@Query("select a from Alumn a where a.payments.size >=ALL(select a.payments.size from Alumn a)")
	public Collection<Alumn> alumnsMoreClasses();

	// g.El empresario que ha propuesto más banners aceptados.
	@Query("select m from Manager m where (select count(b) from Banner b where (b.state='ACCEPTED' and b.manager=m)) >=ALL(select (select count(b) from Banner b where (b.state='ACCEPTED' and b.manager=mm)) from Manager mm)")
	public Collection<Manager> managerMoreBannersAccepted();

	// h.Una lista de profesores, ordenada de mejor a peor ratio de opiniones.
	@Query("select t from Teacher t order by t.averageScore DESC")
	public Collection<Teacher> teachersOrderedByRatio();

	// i.El mínimo, el máximo y la media de clases impartidas por profesores.
	@Query("select min(t.danceClasses.size),max(t.danceClasses.size),avg(t.danceClasses.size) from Teacher t")
	public Object[] minAvgMaxClassesPerTeacher();

	// j.La media de duración de todos los eventos.
	@Query("select avg(e.duration) from Event e")
	public Double eventAverageDuration();
}
