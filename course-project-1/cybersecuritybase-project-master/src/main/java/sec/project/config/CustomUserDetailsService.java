/*
 *  pexak
 *  Cyber Security Base - Course Project I
 *
 *  Course given by University of Helsinki in collaboration with F-Secure 
 *
 */

package sec.project.config;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sec.project.repository.AccountRepository;
import sec.project.domain.Account;
import sec.project.domain.Role;
import sec.project.repository.RoleRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    

    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        
        Role role1 = new Role();
        role1.setName("USER");
        roleRepository.save(role1);
        Role role2 = new Role();
        role2.setName("USER");
        roleRepository.save(role2);
        Role role3 = new Role();
        role3.setName("ADMIN");
        roleRepository.save(role3);
        Role role4 = new Role();
        role4.setName("USER");
        roleRepository.save(role4);
        Role role5 = new Role();
        role5.setName("ADMIN");
        roleRepository.save(role5);
        
        Account account1 = new Account();
        account1.setUsername("worker1");
        account1.setPassword(passwordEncoder.encode("passwd1"));
        accountRepository.save(account1);
        role1.setAccount(account1);
        
        Account account2 = new Account();
        account2.setUsername("worker2");
        account2.setPassword(passwordEncoder.encode("passwd2"));
        accountRepository.save(account2);
        role2.setAccount(account2);
        role3.setAccount(account2);
        
        Account account3 = new Account();
        account3.setUsername("admin");
        account3.setPassword(passwordEncoder.encode("admin"));
        accountRepository.save(account3);
        role4.setAccount(account3);
        role5.setAccount(account3);
        
        roleRepository.save(role1);
        roleRepository.save(role2);
        roleRepository.save(role3);
        roleRepository.save(role4);
        roleRepository.save(role5);
        
        
        account1.setRoles(roleRepository.findByAccount(account1));
        accountRepository.save(account1);
        
        account2.setRoles(roleRepository.findByAccount(account2));
        accountRepository.save(account2);
        
        account3.setRoles(roleRepository.findByAccount(account3));
        accountRepository.save(account3);
        
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("No such user: " + username);
        }

        return new org.springframework.security.core.userdetails.User(
                account.getUsername(),
                account.getPassword(),
                true,
                true,
                true,
                true,
                account.getRoles()
        );
    }
}
