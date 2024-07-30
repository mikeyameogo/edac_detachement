/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import bf.mfptps.detachementservice.domain.Serveur;
import bf.mfptps.detachementservice.exception.CustomException;
import bf.mfptps.detachementservice.repository.ServeurRepository;
import bf.mfptps.detachementservice.service.ServeurService;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j 
@Service
public class ServeurServiceImpl implements ServeurService {

    private final ServeurRepository serveurRepository; 
    
    public ServeurServiceImpl(ServeurRepository serveurRepository) {
    	this.serveurRepository = serveurRepository;
    }

    @Override
    public Serveur create(Serveur serveur) {
        log.info("Creation ou mise à jour d'un serveur : {}", serveur);
        if (serveur.getActive() == true) {
            serveurRepository.findByActiveIsTrue().ifPresent(s -> {
                throw new CustomException("Vous ne pouvez paramétrer qu'un seul type de serveur actif.");
            });
            return serveurRepository.save(serveur);
        } else {
            return serveurRepository.save(serveur);
        }
    }

    @Override
    public Serveur update(Serveur serveur) {
        log.info("Creation ou mise à jour d'un serveur : {}", serveur);
        if (serveur.getActive() == true) {
            serveurRepository.findByActiveIsTrue().ifPresent(s -> {
                throw new CustomException("Vous ne pouvez paramétrer qu'un seul type de serveur actif.");
            });
            return serveurRepository.save(serveur);
        } else {
            return serveurRepository.save(serveur);
        }
    }

    @Override
    public Optional<Serveur> getActive() {
        return serveurRepository.findByActiveIsTrue();
    }

    @Override
    public List<Serveur> findAll() {
        return serveurRepository.findAll();
    }
 
}
