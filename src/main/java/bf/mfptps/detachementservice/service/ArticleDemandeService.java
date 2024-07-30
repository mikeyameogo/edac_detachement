/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service;

import bf.mfptps.detachementservice.service.dto.ArticleDemandeDTO;
import bf.mfptps.detachementservice.service.dto.VisaDemandeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ArticleDemandeService {

    ArticleDemandeDTO create(ArticleDemandeDTO articleDemandeDTO);

    ArticleDemandeDTO update(ArticleDemandeDTO articleDemandeDTO);

    Optional<ArticleDemandeDTO> get(Long id);

    Page<ArticleDemandeDTO> findAll(Pageable pageable);

    List<ArticleDemandeDTO> findAllList();

    List<ArticleDemandeDTO> findAllByDemandeId(Long demandeId);

    void delete(Long id);
}
