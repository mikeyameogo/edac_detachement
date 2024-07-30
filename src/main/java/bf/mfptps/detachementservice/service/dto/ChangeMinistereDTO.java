/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * POJO
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Data
public class ChangeMinistereDTO {

    @NotNull
    private Long structureId;

    private Long structureParentId;

    @NotNull
    private Long ministereId;

}
