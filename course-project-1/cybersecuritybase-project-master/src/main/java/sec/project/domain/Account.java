/*
 *  pexak
 *  Cyber Security Base - Course Project I
 *
 *  Course given by University of Helsinki in collaboration with F-Secure 
 *
 */

package sec.project.domain;

import java.util.List;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Account extends AbstractPersistable<Long> {

    @Column(unique = true)
    private String username;
    private String password;
    
    
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private Collection<Role> roles;

    @OneToMany(mappedBy = "account")
    private List<Feedback> feedbacks;
    
    public Collection<Role> getRoles() {
        return roles;
    }
    
    public Account() {
        super();
    }
    
    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
