/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service.impl;

import bf.mfptps.detachementservice.service.MailService;
import bf.mfptps.detachementservice.service.dto.DemandeDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@Service
public class MailServiceImpl implements MailService {
    
    private final JavaMailSender javaMailSender;
    
    public MailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    
    @Async
    @Override
    public void sendEmail(String to, String subject, String body) {
        log.info("Envoi de mail à {} ...", to);
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(to);
            helper.setSubject(subject);
            InternetAddress fromAddress = new InternetAddress("no_reply@spmabg.org", "MFPTPS");
            helper.setReplyTo(fromAddress);
            helper.setFrom(fromAddress);
            helper.setText(body, true);
        } catch (MessagingException | UnsupportedEncodingException ex) {
            Logger.getLogger(MailServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        javaMailSender.send(message);
    }

    /**
     * on cronstruit le corps du message pour la notification de reception de
     * dossier
     *
     * @param d
     * @return
     */
    @Override
    public String constructReceptionBody(DemandeDTO d) {
        String expediteur = "La Direction des Ressources Humaines.";
        return "Cher-e " + d.getAgent().getMatricule() + ",<br />"
                + "Nous accusons reception de votre requête portant <b>" + d.getTypeDemande().getLibelle() + "</b>.<br />"
                + "Nous l'avons enregistré sous le numéro : <b>" + d.getNumero() + "</b>.<br />"
                + "Vous êtes prié de suivre le dossier au cas où nous ne parvenons pas à vous informer de son évolution.<br /><br />"
                + expediteur;
    }

    @Override
    public String constructSHBody(DemandeDTO d) {
        String expediteur = "La Direction des Ressources Humaines.";
        LocalDate delais = d.getDateDemande().plusDays(2);
        return "Cher-e " + d.getAgent().getSuperieurHierarchique().getMatricule() + ",<br />"
                + "Nous accusons reception d'une requête de votre agent <b>" +d.getAgent().getMatricule()+ "</b>, portant <b>" + d.getTypeDemande().getLibelle() + "</b>.<br />"
                + "Vous êtes prié de donner votre avis au plus tard le <b>" +delais+ "</b>, afin que le service technique puisse commencer le traitement.<br /><br />"
                + expediteur;
    }
}
