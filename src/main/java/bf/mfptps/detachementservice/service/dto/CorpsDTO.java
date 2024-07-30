package bf.mfptps.detachementservice.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class CorpsDTO {
private Long id;
private String code;
private String libelle;
private List<AgentDTO> agentsDTOs;
}
