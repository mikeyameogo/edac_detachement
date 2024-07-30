/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import bf.mfptps.detachementservice.domain.Acte;
import bf.mfptps.detachementservice.repository.*;
import bf.mfptps.detachementservice.service.dto.ActeDTO;
import bf.mfptps.detachementservice.service.mapper.ActeMapper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import bf.mfptps.detachementservice.domain.Serveur;
import bf.mfptps.detachementservice.exception.CustomException;
import bf.mfptps.detachementservice.service.ActeService;
import bf.mfptps.detachementservice.util.AppUtil;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@Service
public class ActeServiceImpl implements ActeService {

    private final ActeRepository acteRepository;
    private final ActeMapper acteMapper;

    private final ServeurRepository serveurRepository;

    public ActeServiceImpl(DemandeRepository demandeRepository, ActeRepository acteRepository,
                           ActeMapper acteMapper, ServeurRepository serveurRepository) {
        this.acteRepository = acteRepository;
        this.acteMapper = acteMapper;
        this.serveurRepository = serveurRepository;
    }
    @Override
    public ActeDTO create(ActeDTO acteDTO) {
        log.info("creation d'un acte : {}", acteDTO);
        Acte acte = acteMapper.toEntity(acteDTO);
        return acteMapper.toDto(acteRepository.save(acte));
    }

    @Override
    public ActeDTO update(ActeDTO acteDTO) {
        log.info("update d'un acte : {}", acteDTO);
        Acte acte = acteMapper.toEntity(acteDTO);
        return acteMapper.toDto(acteRepository.save(acte));
    }

    @Override
    public Optional<ActeDTO> get(Long id) {
        log.info("consultation d'un acte : {}", id);
        return Optional.ofNullable(acteMapper.toDto(acteRepository.findById(id).orElseThrow()));
    }

    @Override
    public Page<ActeDTO> findAll(Pageable pageable) {
        log.info("liste paginée des actes");
        return acteRepository.findAll(pageable).map(acteMapper::toDto);
    }

    @Override
    public List<ActeDTO> findAllList() {
        log.info("liste des actes");
        return acteRepository.findAll().stream().map(acteMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        log.info("suppression d'un acte  de code : {}", id);
        acteRepository.deleteById(id);
    }

    @Override
    public String uploadActe(MultipartFile fileData, Long dataId, boolean disponibilite) {
        // checker le type de serveur de stockage des fichiers
        Serveur serveur = serveurRepository.findByActiveIsTrue().orElseThrow(() -> new CustomException(
                "Veuillez contacter l'administrateur pour le paramétrage du type de serveur FTP."));
        choiceFilesServer(serveur, dataId, fileData, (disponibilite ? "disponibilites" : "detachements"));
        return "Document enregistré avec succès";
    }

    @Override
    public ByteArrayResource downloadActe(Long dataId, boolean disponibilite) {
        byte[] fileContent = null;// modifier ici pour charger le fichier
        ByteArrayResource resource = new ByteArrayResource(fileContent);
        return resource;
    }

    @Override
    public String signerProjetArrete(Long dataId, boolean disponibilite) {
        Acte acte = acteRepository.findById(dataId).orElse(null);// Adapter ici en fonction de la compréhension
        if (acte == null) {
            throw new CustomException("Aucun acte trouvé avec l'Id fourni");
        }
        acte.setStatut("Signé");
        acteRepository.save(acte);

        return "Acte marqué <signé> avec succès";
    }

    private void choiceFilesServer(Serveur serveur, Long dataId, MultipartFile fileData, String rootFolderName) {
        switch (serveur.getETypeServeur().name()) {
            case "FTP" -> {// Traitement spécifique pour un stockage serveur FTP
                System.out.println("-------------- Storage to ftp server --------------");
            }
            case "SFTP" -> {// Traitement spécifique pour un storage serveur SFTP
                System.out.println("-------------- Storage to sftp server --------------");
            }
            case "LOCAL" -> {// Traitement spécifique pour un storage serveur LOCAL
                System.out.println("-------------- Storage to local server --------------");
                this.localStorageProcess(dataId, fileData, rootFolderName);
            }
            default -> throw new IllegalArgumentException(
                    "Le type de serveur [" + serveur.getETypeServeur().name() + "] est invalide.");
        }
    }

    /**
     * Process d'enregistrement des infos pieces et stockage local des fichiers
     * joints
     *
     */
    private void localStorageProcess(Long dataId, MultipartFile fileData, String rootFolderName) {
        Path path = null;

        if (fileData != null) {
            path = this.initLocalStoragePath(
                    "actes" + File.separatorChar + rootFolderName + File.separatorChar + dataId.toString());
        }

        String fileName = StringUtils.cleanPath(fileData.getOriginalFilename());

        if (fileName.contains("..") && !fileName.toLowerCase().endsWith(AppUtil.EXTENSION_PDF)
                && !fileName.toLowerCase().endsWith(AppUtil.EXTENSION_PNG)
                && !fileName.toLowerCase().endsWith(AppUtil.EXTENSION_JPG)
                && !fileName.toLowerCase().endsWith(AppUtil.EXTENSION_JPEG)) {
            throw new CustomException(
                    "L'extension n'est pas acceptée ou le nom du fichier contient des caractères invalides.");
        }

        try {
            // String docUrl = path.toString().concat(File.separatorChar + dataId.toString()
            // + "_" + fileName);
            Files.copy(fileData.getInputStream(), path.resolve(dataId.toString() + "_" + fileName),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("Erreur survenue lors de l'enregistrement de la pièce.");
        }
    }

    /**
     * Verif et creation du repertoire local de stockage de fichiers uploaded
     *
     * @param directoryRef
     * @return
     */
    private Path initLocalStoragePath(String directoryRef) {
        Path rootPath = Paths.get(AppUtil.appStoreRootPath + directoryRef + File.separatorChar);
        try {
            Files.createDirectories(rootPath);
            return rootPath;
        } catch (IOException e) {
            throw new CustomException("Erreur survenue lors de l'initialisation de la source de stockage.");
        }
    }

}
