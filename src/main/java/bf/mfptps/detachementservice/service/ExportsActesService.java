/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service;

import java.io.OutputStream;

/**
 * interface dediée aux operations d'export/generation d'actes
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface ExportsActesService {

    /**
     * MAJ 03072024: generer la demande timbree avec les different avis
     *
     * @param demandeId: id de la demande recue/persistee
     * @param outputStream
     */
    void printDemandeTraitee(Long demandeId, OutputStream outputStream);

    /**
     * MAJ 02072024: disponibiliser un recepisse automatiquement apres
     * soumission de demande
     *
     * @param demandeId: id de la demande recue/persistee
     * @param outputStream
     */
    void printRecepisseDemande(Long demandeId, OutputStream outputStream);

    /**
     *
     * Le metier a decidé d’elaborer l’acte sur le SIGASPE. Notre fonction de
     * generation se resumera à disponibiliser pour telechargement/impression le
     * formulaire introduit initialement par le demandeur. Ce pdf sera imprimé
     * et joint au projet élaboré sur le SIGASPE; et le tout transmis au SG pour
     * signature.
     *
     * @param demandeId : id de la demande dont au veut exporter le formulaire
     * @param outputStream
     */
    void printFormulaireDemande(Long demandeId, OutputStream outputStream);

    /**
     * Fonctionnalité integrée sur demande du DRH : generer un projet d'arrêté
     * de detachement sur la base d'une demande ayant fait l'objet de
     * traitement.
     *
     * Elle prend en compte NOUVEAU DETACHEMENT et RENOUVELLEMENT DE
     * DETACHEMENT.
     *
     * @param demandeId : id de la demande dont on veut generer le projet
     * d'arrêté pour signature
     * @param isRegularisation : s'il s'agit d'un acte à titre de
     * regularisation, isRegularisation=true
     * @param outputStream
     */
    void printArreteDetachement(Long demandeId, boolean isRegularisation, OutputStream outputStream);
}
