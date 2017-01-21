/*
 *  pexak
 *  Cyber Security Base - Course Project I
 *
 *  Course given by University of Helsinki in collaboration with F-Secure 
 *
 */

package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;
import java.sql.Connection;
import java.sql.DriverManager;
import org.h2.tools.RunScript;
import java.io.FileReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class SignupController {

    @Autowired
    private SignupRepository signupRepository;

    @RequestMapping("/")
    public String defaultMapping() {
        return "redirect:/form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm() {
        return "form";
    }
    
    @RequestMapping(value = "/form/{path}", method = RequestMethod.GET)
    public String signedup( Model model, @PathVariable String path) {
        
        String message = "You have already signed up!";
        
        model.addAttribute("signedup", message);
        
        return "form";
        
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(Model model, @RequestParam String name, @RequestParam String address) throws Exception {
        
        
        if(signupRepository.findByNameAndAddress(name, address) != null) {
            return signedup(model, "signedup");
        }
        
        Connection connection = DriverManager.getConnection("jdbc:h2:file:./database", "sa", "");
        
        try {
            RunScript.execute(connection, new FileReader("sql/database-schema.sql"));
            RunScript.execute(connection, new FileReader("sql/database-import.sql"));
        } catch (Throwable t) {
            System.err.println(t.getMessage());
        }
        
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT name, address FROM Signup");
        if(signupRepository.count() == 0)
            while(resultSet.next())
                signupRepository.save(new Signup(resultSet.getString("name"), resultSet.getString("address")));
        
        int seat = 1;
        resultSet = connection.createStatement().executeQuery("SELECT seat FROM Signup");
        while(resultSet.next())
            if (resultSet.getInt("seat") > seat)
                seat = resultSet.getInt("seat");
        resultSet.close();
        
        seat++;
        
        String insert = "INSERT INTO Signup (seat, name, address) VALUES (?, ?, ?)";
        PreparedStatement stm = connection.prepareStatement(insert);
        stm.setInt(1, seat);
        stm.setString(2, name);
        stm.setString(3, address);
        stm.execute();
        stm.close();
        
        signupRepository.save(new Signup(name, address));
        
        connection.close();
        
        return "done";
    }

}
