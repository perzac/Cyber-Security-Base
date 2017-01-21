/*
 *  pexak
 *  Cyber Security Base - Course Project I
 *
 *  Course given by University of Helsinki in collaboration with F-Secure 
 *
 */

package sec.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sec.project.domain.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
    
    Person findBySotu(String sotu);

}
