package de.hspf.authservice.secure;

import de.hspf.authservice.Account;
import java.util.Optional;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.ClaimValue;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.eclipse.microprofile.jwt.JsonWebToken;

/**
 *
 */
@Path("/user")
@RequestScoped
public class ProtectedController {

    @Inject
    @Claim("appl")
    private ClaimValue<String> custom;
    
    @Inject
    JsonWebToken jwt;

    @GET
    @RolesAllowed("protected")
    public Account getJWTBasedValue() {
        Account account = new Account();
        account.setEmail(jwt.getClaim("sub").toString());
        return account;
    }
}
