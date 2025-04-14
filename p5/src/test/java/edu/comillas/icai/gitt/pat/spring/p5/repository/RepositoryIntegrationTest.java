package edu.comillas.icai.gitt.pat.spring.p5.repository;

import edu.comillas.icai.gitt.pat.spring.p5.entity.AppUser;
import edu.comillas.icai.gitt.pat.spring.p5.entity.Token;
import edu.comillas.icai.gitt.pat.spring.p5.model.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static edu.comillas.icai.gitt.pat.spring.p5.model.Role.USER;
import static edu.comillas.icai.gitt.pat.spring.p5.model.Role.ADMIN;
import static org.junit.jupiter.api.Assertions.*;

import edu.comillas.icai.gitt.pat.spring.p5.repository.AppUserRepository;
import edu.comillas.icai.gitt.pat.spring.p5.repository.TokenRepository;
import java.util.Optional;

@DataJpaTest
class RepositoryIntegrationTest {
    @Autowired TokenRepository tokenRepository;
    @Autowired AppUserRepository appUserRepository;

    /**
     * TODO#9 Hecho
     * Completa este test de integración para que verifique
     * que los repositorios TokenRepository y AppUserRepository guardan
     * los datos correctamente, y las consultas por AppToken y por email
     * definidas respectivamente en ellos retornan el token y usuario guardados.
     */
    @Test void saveTest() {
        // Given ...
        AppUser user = new AppUser();
        Token token = new Token();

        Role role = USER;
        String name = "inigo";
        String password = "aaaaaaA1";
        String email = "inigo@email.com";

        user.setName(name);
        user.setRole(role);
        user.setPassword(password);
        user.setEmail(email);

        token.setAppUser(user);

        appUserRepository.save(user);
        tokenRepository.save(token);


        //Una vez hemos guardado en los repositorios los respectivos token y usuario, vamos a comprobar si se han guardado bien.

        Optional<AppUser> userComprobacion = appUserRepository.findByEmail("inigo@email.com");
        Optional<Token> tokenComprobacion = tokenRepository.findByAppUser(user);

        //Primero comprobamos que no son null
        assertFalse(userComprobacion.isEmpty()); //Se comprueba que no es empty
        assertFalse(tokenComprobacion.isEmpty());//Se comprueba que no es empty

        //En segundo lugar vamos a comprobar que los campos corresponden a los creados
        AppUser appUser = userComprobacion.get();
        Token token1 = tokenComprobacion.get();
        //Comprobamos campos de user.
        assertEquals(appUser.getName(),name); //Se comprueba el nombre;
        assertEquals(appUser.getEmail(),email); //Se comprueba el email;
        assertEquals(appUser.getPassword(),password); //Se comprueba la contraseña;
        assertEquals(appUser.getRole(),role); //Se comprueba el rol;
        //Comprobamos campos de token
        assertEquals(token1.getAppUser(),user);//Se comprueba que corresponde al mismo usuario


    }

    /**
     * TODO#10 Hecho
     * Completa este test de integración para que verifique que
     * cuando se borra un usuario, automáticamente se borran sus tokens asociados.
     */
    @Test void deleteCascadeTest() {
        // Given ... Creamos un usuario y un token y asociamos el token al usuario. Los guardamos el los repositorios.
        AppUser user = new AppUser();
        Role role = USER;
        String name = "inigo";
        String password = "aaaaaaA1";
        String email = "inigo@email.com";

        user.setName(name);
        user.setRole(role);
        user.setPassword(password);
        user.setEmail(email);

        Token token = new Token();
        token.setAppUser(user);

        appUserRepository.save(user);
        tokenRepository.save(token);


        //When Cuando borremos al usuario vamos a comprobar que también se borra el token.
        appUserRepository.delete(user);

        Optional<Token> tokenBorrado = tokenRepository.findByAppUser(user);

        assertFalse(tokenBorrado.isPresent()); //Comprobamos que no exista en el repositorio nunca mas el token del usuario borrado

        Assertions.assertEquals(0, appUserRepository.count(),"Se ha borrado el token del usuario borrado tal y como se esperaba");

    }
}