/*
 *  pexak
 *  Cyber Security Base - Course Project I
 *
 *  Course given by University of Helsinki in collaboration with F-Secure 
 *
 */

package sec.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FeelingsController {
	
    private List<String> feelings;
    
    public FeelingsController() {
    
        this.feelings = new ArrayList<>();
        this.feelings.add("<h3>Here are the feelings</h3>");
        
    }
    
    @RequestMapping(value = "/feelings")
    public String feelingsSend(Model model, @RequestParam(required = false) String feeling) {
    
        // One can put JavaScript or other code here via view
        if (feeling != null && !feeling.trim().isEmpty()) 
            this.feelings.add(feeling.trim());
           
        model.addAttribute("feelingsList", feelings);
     
        return "feelings";
        
    }
    
}