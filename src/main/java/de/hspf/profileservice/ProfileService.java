/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hspf.profileservice;

import de.hspf.profileservice.ProfileResporitory;
import de.hspf.profileservice.model.Account;
import de.hspf.profileservice.model.ExpertLevel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;

/**
 *
 * @author Marcel
 */
@ApplicationScoped
public class ProfileService {
    
    @Inject
    ProfileResporitory profileRepository;
    private Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public Account getAccount(JsonWebToken jwt) throws FileNotFoundException, IOException {
        Account account = new Account();
        account.setEmail(jwt.getClaim("sub").toString());
        account.setUsername(jwt.getClaim("username").toString());
        account.setRoles(jwt.getGroups());

        // Set Profile Pic for account
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("man.png").getFile());
        byte[] picInBytes = new byte[(int) file.length()];
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            fileInputStream.read(picInBytes);
        }
        account.setProfilePic(picInBytes);
        
        // If user already exists, load object from database. Otherwise save it in database
        if(!profileRepository.userExists(account)){
            account = this.setUpDefaultAccount(account);
            profileRepository.safeUser(account);
            logger.log(Level.INFO, "Save new user: {0}", account.getUsername());
        }else{
            account = profileRepository.getAccountByEmail(account);
            logger.log(Level.INFO, "Load user from database: {0}", account.getUsername());
        }
        
        return account;
    }
    
    private Account setUpDefaultAccount(Account account){
        account.setAge(0);
        account.setJob("");
        account.setLevel(ExpertLevel.BEGINNER);
        account.setDescription("Hello, I am " + account.getUsername());
        return account;
    }
    
    public Account updateAccount(Account account){
        Account acc = this.profileRepository.updateAccount(account);
        return acc;
    }
}
