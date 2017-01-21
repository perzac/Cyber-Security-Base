/*
 *  pexak
 *  Cyber Security Base - Course Project I
 *
 *  Course given by University of Helsinki in collaboration with F-Secure 
 *
 */

package sec.project.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.security.core.GrantedAuthority;

@Entity
public class Role extends AbstractPersistable<Long> implements GrantedAuthority {
    
    private String name;
    
    @ManyToOne
    Account account;
    
    public Role() {
        super();
    }
    
    public Role(String name) {
        this();
        this.name = name;
    }
    
    public Account getAccount() {
        return account;
    }
    
    public void setAccount(Account account) {
        this.account = account;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
            
    public String getAuthority() {
        return name;
    }
    
    public void setAuthority(String name) {
        this.name = name;
    }
    
}
