/*
 *  pexak
 *  Cyber Security Base - Course Project I
 *
 *  Course given by University of Helsinki in collaboration with F-Secure 
 *
 */

package sec.project.domain;

import javax.persistence.Entity;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.annotation.Id;

@Entity
public class Person extends AbstractPersistable<Long> {
    
    private String firstName;
    
    private String lastName;
    
    private String declaration;
    
    @Id
    private String sotu;
    
    public Person() {
        super();
    }
    
    public Person(String firstName, String lastName, String declaration, String sotu) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.declaration = declaration;
        this.sotu = sotu;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLasttName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getDeclaration() {
        return declaration;
    }
    
    public void setDeclaration(String declaration) {
        this.declaration = declaration;
    }
    
    public String getSotu() {
        return sotu;
    }
    
    public void setSotu(String sotu) {
        this.sotu = sotu;
    }
    
}
