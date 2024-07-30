/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service;

import java.util.List;
import java.util.Optional;
 
import bf.mfptps.detachementservice.domain.Serveur;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface ServeurService {

    //=============== SERVEUR ==========================
    Serveur create(Serveur serveur);

    Serveur update(Serveur serveur);

    Optional<Serveur> getActive();

    List<Serveur> findAll(); 
}
