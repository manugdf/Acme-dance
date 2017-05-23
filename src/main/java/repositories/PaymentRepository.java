
package repositories;

import domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    @Query("select p from Payment p where p.danceClass.id = ?1 and(p.endDate > CURRENT_DATE)")
    public Collection<Payment> paymentsActivesFromDanceClass(int danceClassId);
}
