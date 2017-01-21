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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.sql.Connection;
import java.sql.DriverManager;
import org.h2.tools.RunScript;
import java.io.FileReader;
import java.sql.ResultSet;

@Controller
public class InfoController {

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String Info() {
        return "info";
    }
    
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public String seatQuery(Model model, @RequestParam String name) throws Exception {
        
        Connection connection = DriverManager.getConnection("jdbc:h2:file:./database", "sa", "");
        
        try {
            RunScript.execute(connection, new FileReader("sql/database-schema.sql"));
            RunScript.execute(connection, new FileReader("sql/database-import.sql"));
        } catch (Throwable t) {
            System.err.println(t.getMessage());
        }
        
        // To put:    Jack1'; DELETE FROM Signup WHERE name = 'Ann5      to the form cause harm.
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT seat FROM Signup WHERE name = '" + name + "'");
        
        if(resultSet.next()) {
            int seat = resultSet.getInt("seat");
            model.addAttribute("seat", Integer.toString(seat));
        } else {
            model.addAttribute("seat", "No seat number for you");
        }
        
        resultSet.close();
        connection.close();
        
        return "info";
        
    }
}