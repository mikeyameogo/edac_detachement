/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service;

import bf.mfptps.detachementservice.service.dto.ArticleDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface ArticleService {

    ArticleDTO create(ArticleDTO articleDTO);

    ArticleDTO update(ArticleDTO articleDTO);

    Optional<ArticleDTO> get(Long id);

    Page<ArticleDTO> findAll(Pageable pageable);

    List<ArticleDTO> findAllList();

    void delete(Long id);
}
