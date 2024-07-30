package bf.mfptps.detachementservice.service.dto;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public class AffectationDTO {

    private String username;
    private Long structureId;

    public AffectationDTO() {
    }

    public Long getStructureId() {
        return structureId;
    }

    public void setStructureId(Long structureId) {
        this.structureId = structureId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
