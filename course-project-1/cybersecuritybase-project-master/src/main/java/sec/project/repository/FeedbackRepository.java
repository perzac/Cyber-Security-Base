/*
 *  pexak
 *  Cyber Security Base - Course Project I
 *
 *  Course given by University of Helsinki in collaboration with F-Secure 
 *
 */

package sec.project.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import sec.project.domain.Feedback;
import sec.project.domain.Account;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    
    List<Feedback> findByAccount(Account account);
    
    Feedback findById(Long id);

}
