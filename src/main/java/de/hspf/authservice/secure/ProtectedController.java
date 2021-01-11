package de.hspf.authservice.secure;

import de.hspf.authservice.Account;
import java.io.IOException;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.ClaimValue;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import org.eclipse.microprofile.jwt.JsonWebToken;

/**
 *
 */
@Path("/user")
@RequestScoped
public class ProtectedController {

    @Inject
    ProfileService profileService;

    @Inject
    @Claim("appl")
    private ClaimValue<String> custom;

    @Inject
    JsonWebToken jwt;

    @GET
    @RolesAllowed("protected")
    @Transactional
    public Account getProfile() throws IOException {
        return profileService.getAccount(jwt);
    }

    @PUT
    @RolesAllowed("protected")
    @Transactional
    public Account putProfile(Account account) {
        Account acc = profileService.updateAccount(account);
        return acc;
    }
}
