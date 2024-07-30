/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service.impl;

import bf.mfptps.detachementservice.domain.ArticleDemande;
import bf.mfptps.detachementservice.domain.Demande;
import bf.mfptps.detachementservice.exception.CustomException;
import bf.mfptps.detachementservice.repository.ArticleDemandeRepository;
import bf.mfptps.detachementservice.repository.DemandeRepository;
import bf.mfptps.detachementservice.repository.TypeDemandeRepository;
import bf.mfptps.detachementservice.service.ArticleDemandeService;
import bf.mfptps.detachementservice.service.dto.ArticleDemandeDTO;
import bf.mfptps.detachementservice.service.dto.DemandeDTO;
import bf.mfptps.detachementservice.service.mapper.ArticleDemandeMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleDemandeServiceImpl implements ArticleDemandeService {

    private final ArticleDemandeRepository articleDemandeRepository;
    private final DemandeRepository demandeRepository;
    private final ArticleDemandeMapper articleDemandeMapper;

    public ArticleDemandeServiceImpl(ArticleDemandeRepository articleDemandeRepository, TypeDemandeRepository typeDemandeRepository,
                                     DemandeRepository demandeRepository, ArticleDemandeMapper articleDemandeMapper) {
        this.articleDemandeRepository = articleDemandeRepository;
        this.demandeRepository = demandeRepository;
        this.articleDemandeMapper = articleDemandeMapper;
    }

    @Transactional
    @Override
    public ArticleDemandeDTO create(ArticleDemandeDTO articleDemandeDTO) {
        log.info("creation d'un visaDemande : {}", articleDemandeDTO);
        ArticleDemande articleDemande = articleDemandeMapper.toEntity(articleDemandeDTO);
        return articleDemandeMapper.toDto(articleDemandeRepository.save(articleDemande));
    }





    @Override
    public ArticleDemandeDTO update(ArticleDemandeDTO articleDemandeDTO) {
        log.info("update d'un articleDemande : {}", articleDemandeDTO);
        ArticleDemande articleDemande = articleDemandeMapper.toEntity(articleDemandeDTO);
        return articleDemandeMapper.toDto(articleDemandeRepository.save(articleDemande));
    }

    @Override
    public Optional<ArticleDemandeDTO> get(Long id) {
        log.info("consultation d'un articleDemande : {}", id);
        return Optional.ofNullable(articleDemandeMapper.toDto(articleDemandeRepository.findById(id).orElseThrow()));
    }

    @Override
    public Page<ArticleDemandeDTO> findAll(Pageable pageable) {
        log.info("liste paginée des articleDemandes");
        return articleDemandeRepository.findAll(pageable).map(articleDemandeMapper::toDto);
    }

    @Override
    public List<ArticleDemandeDTO> findAllList() {
        log.info("liste des articleDemandes");
        return articleDemandeRepository.findAll().stream().map(articleDemandeMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ArticleDemandeDTO> findAllByDemandeId(Long demandeId) {
        log.info("liste des articleDemandes de la demande : {}", demandeId);
        return articleDemandeRepository.findAllByDemandeId(demandeId).stream().map(articleDemandeMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        log.info("suppression d'un articleDemande  de code : {}", id);
        articleDemandeRepository.deleteById(id);
    }

}
