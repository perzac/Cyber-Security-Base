/*
 *  pexak
 *  Cyber Security Base - Course Project I
 *
 *  Course given by University of Helsinki in collaboration with F-Secure 
 *
 */

package sec.project.controller;

import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import sec.project.repository.FeedbackRepository;
import sec.project.repository.AccountRepository;
import sec.project.domain.Feedback;
import sec.project.domain.Account;

@Controller
public class FeedbackController {
    
    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(value = "/feedback", method = RequestMethod.GET)
    public String list(Authentication authentication, Model model) {
        
        model.addAttribute("feedbacks", accountRepository.findByUsername(authentication.getName()).getFeedbacks());
        return "feedback";
        
    }
    
    @RequestMapping(value = "/feedback/{id}", method = RequestMethod.GET)
    public String delete(Authentication authentication, Model model, @PathVariable Long id) {
        
        model.addAttribute("feedbacks", accountRepository.findByUsername(authentication.getName()).getFeedbacks());
        
        Feedback feedback = feedbackRepository.findById(id);
        
        String content = feedback.getContent();
        
        model.addAttribute("number", id);
        model.addAttribute("content", content); // Show in the text box also others' feedbacks
                                                // when one writes number to url.
        
        return "feedback";
    }
    

    @RequestMapping(value = "/feedback", method = RequestMethod.POST)
    public String addFeedback(Authentication authentication, @RequestParam String title, @RequestParam String content) throws IOException {
        Account account = accountRepository.findByUsername(authentication.getName());

        Feedback feedback = new Feedback();
        feedback.setTitle(title);
        feedback.setContent(content);
        feedback.setAccount(account);

        feedbackRepository.save(feedback);

        return "redirect:/feedback";
    }

    @RequestMapping(value = "/feedback/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable Long id) {
        feedbackRepository.delete(feedbackRepository.findById(id));
        return "redirect:/feedback";
    }
    
}
