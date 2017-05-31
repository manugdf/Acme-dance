
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

	@Query("select m from Actor c join c.messagesReceived m where m.copy=false and c.id=?1")
	Collection<Message> myRecivedMessages(int var1);

	@Query("select m from Actor c join c.messagesSended m where m.copy=true and c.id=?1")
	Collection<Message> mySendedMessages(int var1);

}
