///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package bf.mfptps.detachementservice.service.dto;
//
//import bf.mfptps.detachementservice.config.Constants;
//import javax.validation.constraints.Email;
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.Pattern;
//import javax.validation.constraints.Size;
//
///**
// *
// * @author Canisius <canisiushien@gmail.com>
// */
//public class AgentStructureDTO {
//
//    private Long id;
//
//    @NotBlank
//    @Pattern(regexp = Constants.LOGIN_REGEX)
//    @Size(min = 1, max = 50)
//    private String matricule;
//
//    @Size(max = 50)
//    private String nom;
//
//    @Size(max = 50)
//    private String prenom;
//
//    private String telephone;
//
//    @Email
//    @Size(min = 5, max = 254)
//    private String email;
//
//    private String structure;
//
//    public AgentStructureDTO() {
//    }
//
//    public AgentStructureDTO(Long id, String matricule, String nom, String prenom, String telephone, String email, String structure) {
//        this.id = id;
//        this.matricule = matricule;
//        this.nom = nom;
//        this.prenom = prenom;
//        this.telephone = telephone;
//        this.email = email;
//        this.structure = structure;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getMatricule() {
//        return matricule;
//    }
//
//    public void setMatricule(String matricule) {
//        this.matricule = matricule;
//    }
//
//    public String getNom() {
//        return nom;
//    }
//
//    public void setNom(String nom) {
//        this.nom = nom;
//    }
//
//    public String getPrenom() {
//        return prenom;
//    }
//
//    public void setPrenom(String prenom) {
//        this.prenom = prenom;
//    }
//
//    public String getTelephone() {
//        return telephone;
//    }
//
//    public void setTelephone(String telephone) {
//        this.telephone = telephone;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getStructure() {
//        return structure;
//    }
//
//    public void setStructure(String structure) {
//        this.structure = structure;
//    }
//
//}
