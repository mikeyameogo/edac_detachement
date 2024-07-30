/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Data
public class PiecesFourniesDTO {

    private String libelle; //le libellé de la piece en question

    private MultipartFile file; //le fichier scanné joint/uploaded
}
