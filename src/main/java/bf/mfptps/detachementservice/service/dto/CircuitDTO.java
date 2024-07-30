package bf.mfptps.detachementservice.service.dto;

import java.util.List;

import bf.mfptps.detachementservice.domain.Circuit;
import lombok.Data;
@Data 
public class CircuitDTO {
    private Long id;
    private String position;
    private Boolean statut;
    private int delai;
    private Circuit parent;
}
