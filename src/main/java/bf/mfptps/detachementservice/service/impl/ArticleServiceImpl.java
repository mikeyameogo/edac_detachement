/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service.impl;

import bf.mfptps.detachementservice.domain.Article;
import bf.mfptps.detachementservice.domain.TypeDemande;
import bf.mfptps.detachementservice.exception.CustomException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import bf.mfptps.detachementservice.domain.Article;
import bf.mfptps.detachementservice.repository.ArticleRepository;
import bf.mfptps.detachementservice.repository.TypeDemandeRepository;
import bf.mfptps.detachementservice.service.ArticleService;
import bf.mfptps.detachementservice.service.dto.ArticleDTO;
import bf.mfptps.detachementservice.service.mapper.ArticleMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    private final TypeDemandeRepository typeDemandeRepository;

    private final ArticleMapper articleMapper;

    public ArticleServiceImpl(ArticleRepository articleRepository, TypeDemandeRepository typeDemandeRepository,
            ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.typeDemandeRepository = typeDemandeRepository;
        this.articleMapper = articleMapper;
    }

    @Override
    public ArticleDTO create(ArticleDTO articleDTO) {
        log.info("creation d'un article : {}", articleDTO);
        // TypeDemande typeDemande = typeDemandeRepository
        // .findById(articleDTO.getTypeDemandeDTO().getId())
        // .orElseThrow(() -> new CustomException("Le type de demande " +
        // articleDTO.getTypeDemandeDTO().getId() + " est inexistant."));
        // ????????????? verifier que la table intermediaire est updated apres le save
        // ci-dessous
        Article article = articleMapper.toEntity(articleDTO);
        return articleMapper.toDto(articleRepository.save(article));
    }

    @Override
    public ArticleDTO update(ArticleDTO articleDTO) {
        log.info("update d'un article : {}", articleDTO);
        Article article = articleMapper.toEntity(articleDTO);
        return articleMapper.toDto(articleRepository.save(article));
    }

    @Override
    public Optional<ArticleDTO> get(Long id) {
        log.info("consultation d'un article : {}", id);
        return Optional.ofNullable(articleMapper.toDto(articleRepository.findById(id).orElseThrow()));
    }

    @Override
    public Page<ArticleDTO> findAll(Pageable pageable) {
        log.info("liste paginée des articles");
        return articleRepository.findAll(pageable).map(articleMapper::toDto);
    }

    @Override
    public List<ArticleDTO> findAllList() {
        log.info("liste des articles");
        return articleRepository.findAll().stream().map(articleMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        log.info("suppression d'un article  de code : {}", id);
        articleRepository.deleteById(id);
    }

}
