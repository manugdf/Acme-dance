
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.CensoredWords;

@Repository
public interface CensoredWordsRepository extends JpaRepository<CensoredWords, Integer> {

}
