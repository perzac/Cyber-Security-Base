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
import sec.project.domain.Account;
import sec.project.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    
    List<Role> findByAccount(Account account);
    
}
