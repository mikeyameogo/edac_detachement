package bf.mfptps.detachementservice.service.impl;

import bf.mfptps.detachementservice.domain.Historique;
import bf.mfptps.detachementservice.domain.Historique;
import bf.mfptps.detachementservice.repository.HistoriqueRepository;
import bf.mfptps.detachementservice.repository.TypeDemandeRepository;
import bf.mfptps.detachementservice.service.HistoriqueService;
import bf.mfptps.detachementservice.service.dto.HistoriqueDTO;
import bf.mfptps.detachementservice.service.dto.HistoriqueDTO;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import bf.mfptps.detachementservice.service.dto.PieceJointeDTO;
import bf.mfptps.detachementservice.service.mapper.HistoriqueMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@Service
public class HistoriqueServiceImpl implements HistoriqueService {

    private final HistoriqueRepository historiqueRepository;

    private final HistoriqueMapper historiqueMapper;

    public HistoriqueServiceImpl(HistoriqueRepository historiqueRepository, HistoriqueMapper historiqueMapper) {
        this.historiqueRepository = historiqueRepository;
        this.historiqueMapper = historiqueMapper;
    }
    @Override
    public HistoriqueDTO create(HistoriqueDTO historiqueDTO) {
        log.info("creation d'un historique : {}", historiqueDTO);
        Historique historique = historiqueMapper.toEntity(historiqueDTO);
        return historiqueMapper.toDto(historiqueRepository.save(historique));
    }

    @Override
    public HistoriqueDTO update(HistoriqueDTO historiqueDTO) {
        log.info("update d'un historique : {}", historiqueDTO);
        Historique historique = historiqueMapper.toEntity(historiqueDTO);
        return historiqueMapper.toDto(historiqueRepository.save(historique));
    }

    @Override
    public Optional<HistoriqueDTO> get(Long id) {
        log.info("consultation d'un historique : {}", id);
        return Optional.ofNullable(historiqueMapper.toDto(historiqueRepository.findById(id).orElseThrow()));
    }

    @Override
    public Page<HistoriqueDTO> findAll(Pageable pageable) {
        log.info("liste paginée des historiques");
        return historiqueRepository.findAll(pageable).map(historiqueMapper::toDto);
    }

    @Override
    public List<HistoriqueDTO> findAllList() {
        log.info("liste des historiques");
        return historiqueRepository.findAll().stream().map(historiqueMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<HistoriqueDTO> findAllByDemande(Long idDemande) {
        log.info("Lise des historiques d'une demande : {}", idDemande);
        return historiqueRepository.findByDemandeId(idDemande).stream().map(historiqueMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        log.info("suppression d'un historique  de code : {}", id);
        historiqueRepository.deleteById(id);
    }
}
