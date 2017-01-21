/*
 *  pexak
 *  Cyber Security Base - Course Project I
 *
 *  Course given by University of Helsinki in collaboration with F-Secure 
 *
 */

package sec.project.controller;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import sec.project.repository.PersonRepository;
import sec.project.domain.Person;

@Controller
public class PeopleController {
    
    @Autowired
    private PersonRepository personRepository;
    
    @PostConstruct
    public void init() {
        Person person = new Person("Man", "One", "Hard man", "1111");
        this.personRepository.save(person);
        
        person = new Person("Woman", "One", "Hard woman", "2222");
        this.personRepository.save(person);
        
        person = new Person("Nainen", "Kaksi", "Soft woman", "3333");
        this.personRepository.save(person);
        
        person = new Person("Mies", "Kaksi", "Soft man", "4444");
        this.personRepository.save(person);
    }
    
    @RequestMapping(value = "/people", method = RequestMethod.GET)
    public String toLogout(Model model) {
        
        model.addAttribute("people", personRepository.findAll());
        
        return "people";
    }
    
    // Here browser shows the sotu!!
    @RequestMapping(value = "/people/{sotu}", method = RequestMethod.GET)
    public String showPerson(Model model, @PathVariable String sotu) {
        
        Person person = personRepository.findBySotu(sotu);
        
        model.addAttribute("shownName", person.getFirstName() + " " + person.getLasttName());
        model.addAttribute("shownDeclaration", person.getDeclaration());
        model.addAttribute("people", personRepository.findAll());
        
        return "people";
        
    }
    
}