/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import bf.mfptps.detachementservice.domain.Serveur;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface ServeurRepository extends JpaRepository<Serveur, Long> {

    Optional<Serveur> findByActiveIsTrue();
}
