/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service;

import bf.mfptps.detachementservice.service.dto.DemandeDTO;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface MailService {

    public void sendEmail(String to, String subject, String body);

    public String constructReceptionBody(DemandeDTO demandeDTO);

    public String constructSHBody(DemandeDTO demandeDTO);

}
