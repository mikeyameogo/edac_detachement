package bf.mfptps.detachementservice.service.impl;

import bf.mfptps.detachementservice.domain.Ministere;
import bf.mfptps.detachementservice.domain.MinistereStructure;
import bf.mfptps.detachementservice.domain.Structure;
import bf.mfptps.detachementservice.domain.TypeStructure;
import bf.mfptps.detachementservice.exception.CustomException;
import bf.mfptps.detachementservice.repository.MinistereRepository;
import bf.mfptps.detachementservice.repository.MinistereStructureRepository;
import bf.mfptps.detachementservice.repository.StructureRepository;
import bf.mfptps.detachementservice.repository.TypeStructureRepository;
import bf.mfptps.detachementservice.service.StructureService;
import bf.mfptps.detachementservice.service.dto.ChangeMinistereDTO;
import bf.mfptps.detachementservice.service.dto.StructureDTO;
import bf.mfptps.detachementservice.service.mapper.StructureMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@Service
@Transactional
public class StructureServiceImpl implements StructureService {

    private final StructureRepository structureRepository;
    private final MinistereStructureRepository ministereStructureRepository;
    private final MinistereRepository ministereRepository;
    private final TypeStructureRepository typeStructureRepository;
    private final StructureMapper structureMapper;

    public StructureServiceImpl(StructureRepository structureRepository,
            MinistereStructureRepository ministereStructureRepository,
            MinistereRepository ministereRepository,
            TypeStructureRepository typeStructureRepository,
            StructureMapper structureMapper) {
        this.structureRepository = structureRepository;
        this.ministereStructureRepository = ministereStructureRepository;
        this.ministereRepository = ministereRepository;
        this.typeStructureRepository = typeStructureRepository;
        this.structureMapper = structureMapper;
    }

    @Override
    public Structure create(StructureDTO structureDTO) {
        log.info("creation d'une structure : {}", structureDTO);
        Structure structure = structureMapper.toEntity(structureDTO);
        Ministere ministere = ministereRepository
                .findById(structureDTO.getMinistere().getId())
                .orElseThrow(() -> new CustomException("Le Ministère d'id " + structureDTO.getMinistere().getId() + " est inexistant."));
        TypeStructure typeStructure = typeStructureRepository
                .findById(structureDTO.getType().getId())
                .orElseThrow(() -> new CustomException("Le type de structure" + structureDTO.getType().getId() + " est inexistant."));
        if (structureDTO.getParent() != null) {
            MinistereStructure ministereStructureChecked = ministereStructureRepository
                    .findByStructureIdAndStatutIsTrue(structureDTO.getParent().getId())
                    .orElseThrow(() -> new CustomException("La structure parente n'est pas rattachée à un ministère."));
            if (ministere.getId() != ministereStructureChecked.getMinistere().getId()) {
                throw new CustomException("Veuillez selectionner le ministère approprié (" + structureDTO.getParent().getSigle() + " => " + ministereStructureChecked.getMinistere().getSigle() + ").");
            }
        }
        structure.setType(typeStructure);
        Structure structureSaved = structureRepository.save(structure);
        MinistereStructure ministereStructure = new MinistereStructure();
        ministereStructure.setMinistere(ministere);
        ministereStructure.setStructure(structureSaved);
        ministereStructure.setDateDebut(new Date());
        ministereStructureRepository.save(ministereStructure);
        return structureSaved;
    }

    @Override
    public Structure update(Structure structure) {
        log.info("update d'une structure : {}", structure);
        if (structure.getParent() != null) {
            ministereStructureRepository
                    .findByStructureIdAndStatutIsTrue(structure.getParent().getId())
                    .orElseThrow(() -> new CustomException("La structure parente n'est pas rattachée à un ministère."));
        }
        return structureRepository.save(structure);
    }

    @Override
    public Optional<Structure> get(long id) {
        log.info("consultation d'une structure : {}", id);
        return structureRepository.findById(id);
    }

    @Override
    public Ministere getMinistereByStructureId(long idStructure) {
        log.info("consultation du ministere d'une structure : {}", idStructure);
        return ministereStructureRepository.findByStructureIdAndStatutIsTrue(idStructure).get().getMinistere();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Structure> findAll(Pageable pageable) {
        log.info("liste paginée des structures");
        return structureRepository.findAll(pageable);

    }

    @Override
    public void delete(Long id) {
        log.info("suppression d'une structure : {}", id);
        structureRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Structure changementMinistere(ChangeMinistereDTO changeMinistereDTO) {
        log.info("change le ministere rataché d'une structure : {}", changeMinistereDTO);
        //Check if exists Objects Ministere and Structure
        Optional<Structure> existStructure = structureRepository.findById(changeMinistereDTO.getStructureId())
                .map(s -> {
                    if (changeMinistereDTO.getStructureParentId() != null) {//tenir compte des sous structures
                        s.setParent(structureRepository.findById(changeMinistereDTO.getStructureParentId()).get());
                    }
                    return s;
                });
        if (!existStructure.isPresent()) {
            throw new CustomException("La Structure d'id " + changeMinistereDTO.getStructureId() + " est inexistante.");
        }
        Ministere existMinistere = ministereRepository.findById(changeMinistereDTO.getMinistereId())
                .orElseThrow(() -> new CustomException("Ministere inexistant."));

        //find MinistereStructure element from bd
        MinistereStructure existMinistereStructure = ministereStructureRepository
                .findByStructureIdAndStatutIsTrue(existStructure.get().getId()).get();

        //New line to be add as MinistereStructure object
        MinistereStructure ministereStructure = new MinistereStructure();
        Date date = new Date();
        ministereStructure.setMinistere(existMinistere);
        ministereStructure.setStructure(existStructure.get());
        ministereStructure.setDateDebut(date);

        //update (set to disable) old MinistereStructure object
        existMinistereStructure.setStatut(false);
        existMinistereStructure.setDateFin(date);

        ministereStructureRepository.save(existMinistereStructure);
        MinistereStructure response = ministereStructureRepository.save(ministereStructure);

        //this.updateSubStructures(existStructure.get(), existMinistere);//change also all subStructures
        return structureRepository.findById(response.getStructure().getId()).orElseThrow();
    }

    @Override
    public List<Structure> findAllList() {
        log.info("liste des structures");
        return new ArrayList<>(ministereStructureRepository.findAllStructuresActives());
    }

}
