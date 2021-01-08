/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hspf.authservice.secure;

import de.hspf.authservice.Account;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
        if(!profileRepository.userExists(account)){
            profileRepository.safeUser(account);
            logger.info("Save new user " + account.getUsername());
        }else{
            account = profileRepository.getAccountByEmail(account);
        }
        return account;
    }
}
