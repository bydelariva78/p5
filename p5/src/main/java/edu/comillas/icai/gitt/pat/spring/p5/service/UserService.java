package edu.comillas.icai.gitt.pat.spring.p5.service;

import edu.comillas.icai.gitt.pat.spring.p5.entity.AppUser;
import edu.comillas.icai.gitt.pat.spring.p5.entity.Token;
import edu.comillas.icai.gitt.pat.spring.p5.model.ProfileRequest;
import edu.comillas.icai.gitt.pat.spring.p5.model.ProfileResponse;
import edu.comillas.icai.gitt.pat.spring.p5.model.RegisterRequest;
import edu.comillas.icai.gitt.pat.spring.p5.model.Role;
import edu.comillas.icai.gitt.pat.spring.p5.repository.TokenRepository;
import edu.comillas.icai.gitt.pat.spring.p5.repository.AppUserRepository;
import edu.comillas.icai.gitt.pat.spring.p5.util.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * TODO#6 Hecho
 * Completa los métodos del servicio para que cumplan con el contrato
 * especificado en el interface UserServiceInterface, utilizando
 * los repositorios y entidades creados anteriormente
 */

@Service
public class UserService implements UserServiceInterface {

    private AppUserRepository appUserRepository;
    private TokenRepository tokenRepository;
    public Token login(String email, String password) {
        Optional<AppUser> appUserOptional = appUserRepository.findByEmail(email);
        if (appUserOptional.isEmpty()) return null;

        AppUser appUser = appUserOptional.get(); //Nos aseguramos que no sea null y recuperamos el objeto.

        if(appUser.getPassword().equals(password)){ //Verificamos que las contraseñas sean iguales
            Token token = new Token();
            token.setAppUser(appUser); //Asociamos un appuser al token
            tokenRepository.save(token); //Guardamos el token en la base de datos
            return(token);
        }else{
            return null;
        }
    }

    public AppUser authentication(String tokenId) {
        Optional<Token> tokenOpt = tokenRepository.findById(tokenId);
        if (tokenOpt.isEmpty()) return null;

        Token token = tokenOpt.get();
        return token.getAppUser();

    }

    public ProfileResponse profile(AppUser appUser) {
        ProfileResponse profileResponse = new ProfileResponse(appUser.getName(), appUser.getEmail(), appUser.getRole());
        return(profileResponse);
    }
    public ProfileResponse profile(AppUser appUser, ProfileRequest profile) {
        String nombre = profile.name();
        Role role = profile.role();
        String contrasena = profile.password();

        appUser.setName(nombre);
        appUser.setRole(role);
        appUser.setPassword(contrasena);

        appUserRepository.save(appUser);

        ProfileResponse profileResponse = new ProfileResponse(appUser.getName(), appUser.getEmail(), appUser.getRole());
        return profileResponse;
    }
    public ProfileResponse profile(RegisterRequest register) {

        AppUser appUser = new AppUser();

        appUser.setName(register.name());
        appUser.setEmail(register.email());
        appUser.setRole(register.role());
        appUser.setPassword(register.password());

        appUserRepository.save(appUser);

        ProfileResponse profileResponse = new ProfileResponse(appUser.getName(), appUser.getEmail(), appUser.getRole());

        return profileResponse;
    }

    public void logout(String tokenId) {
        Optional<Token> tokenOpt = tokenRepository.findById(tokenId);
        if (tokenOpt.isPresent()) {
            Token token = tokenOpt.get();
            tokenRepository.delete(token);
        }
    }

    public void delete(AppUser appUser) {
        appUserRepository.delete(appUser);
    }

}
