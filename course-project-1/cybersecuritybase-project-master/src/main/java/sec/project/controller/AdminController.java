/*
 *  pexak
 *  Cyber Security Base - Course Project I
 *
 *  Course given by University of Helsinki in collaboration with F-Secure 
 *
 */

package sec.project.controller;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.h2.tools.RunScript;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;

@Controller
public class AdminController {
    
    @Autowired
    private SignupRepository signupRepository;
    
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Model model) {
        model.addAttribute("signups", signupRepository.findAll());
        return "admin";
    }
    
    @RequestMapping(value = "/admin", method = RequestMethod.POST)
    public String addSignup(Model model, @RequestParam String name, @RequestParam String address) throws Exception {
        
        if(signupRepository.findByNameAndAddress(name, address) != null) {
            
            model.addAttribute("signedup", name + " from address " + address + " already signed up!");
            model.addAttribute("signups", signupRepository.findAll());
            return "admin";
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
        
        return "redirect:/admin";
    }
    
    @RequestMapping(value = "/admin", method = RequestMethod.DELETE)
    public String deleteSignup(Model model, @RequestParam String name, @RequestParam String address) throws Exception {
        
        if(signupRepository.findByNameAndAddress(name, address) != null) {
            signupRepository.delete(signupRepository.findByNameAndAddress(name, address));
        } else {
            model.addAttribute("noDeleted", "No signup with name " + name + " and address " + address + ".");
            model.addAttribute("signups", signupRepository.findAll());
            return "admin";
        }
        
        Connection connection = DriverManager.getConnection("jdbc:h2:file:./database", "sa", "");
        
        ResultSet resultSet = connection.createStatement()
                                        .executeQuery("SELECT seat FROM Signup WHERE name = '" + name + "' AND address = '" + address + "'");
        int seat = 100000;
        
        if(resultSet.next())
            seat = resultSet.getInt("seat");
        
        String insert = "DELETE FROM Signup WHERE name = ? AND address = ?";
        PreparedStatement stm = connection.prepareStatement(insert);
        stm.setString(1, name);
        stm.setString(2, address);
        stm.execute();
        stm.close();
        
        connection.createStatement().execute("UPDATE signup SET seat = seat - 1 WHERE seat > " + seat);
        
        connection.close();
                
        return "redirect:/admin";
    }
    
}
