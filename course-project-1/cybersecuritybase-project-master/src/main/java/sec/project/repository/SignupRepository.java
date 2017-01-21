/*
 *  pexak
 *  Cyber Security Base - Course Project I
 *
 *  Course given by University of Helsinki in collaboration with F-Secure 
 *
 */

package sec.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sec.project.domain.Signup;

public interface SignupRepository extends JpaRepository<Signup, Long> {
    
    Signup findByNameAndAddress(String name, String address);

}
