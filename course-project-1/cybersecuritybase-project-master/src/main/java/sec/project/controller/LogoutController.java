/*
 *  pexak
 *  Cyber Security Base - Course Project I
 *
 *  Course given by University of Helsinki in collaboration with F-Secure 
 *
 */

package sec.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LogoutController {
    
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String toLogout() {
        return "redirect:/event";
    }
    
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logout() {
        return "logout";
    }
    
}
