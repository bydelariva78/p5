package edu.comillas.icai.gitt.pat.spring.p5.repository;

import edu.comillas.icai.gitt.pat.spring.p5.entity.AppUser;
import edu.comillas.icai.gitt.pat.spring.p5.entity.Token;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;
/**
 * TODO#5 Hecho
 * Crea el repositorio para la entidad Token de modo que,
 * además de las operaciones CRUD, se pueda consultar el Token asociado
 * a un AppUser dado
 */

public interface TokenRepository extends CrudRepository<Token, String> {
    Optional<Token>  findByAppUser(AppUser appUser);//Lo he creado de tipo optional para evitar problemas con el nullpointer.
}