package bf.mfptps.detachementservice.service;

import bf.mfptps.detachementservice.domain.Demande;
import bf.mfptps.detachementservice.domain.PieceJointe;
import bf.mfptps.detachementservice.service.dto.DemandeDTO;
import bf.mfptps.detachementservice.service.dto.PieceJointeDTO;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;

import static com.google.common.io.Files.getFileExtension;
@Slf4j
@Service
public class FileService {
    private String uploadPath;

    @Value("${storage.folder}")
    private String storageFolder;

    public PieceJointe save(MultipartFile file) throws FileUploadException {
        try {

            String chemin = "C:\\pieces";
            File dossier = new File(chemin);
            if (!dossier.exists()) {
                dossier.mkdir();
            }

            Path root = Paths.get(chemin);
            String path = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() + ("_").concat(file.getOriginalFilename());
            Path resolve = root.resolve(path);

       /*     if (resolve.toFile()
                    .exists()) {
                throw new FileUploadException("Le fichier a déjà été envoyé: " + file.getOriginalFilename());
            }*/
            Files.copy(file.getInputStream(), resolve);

            String url =  root.toFile().getAbsolutePath().concat("/").concat(path);

            PieceJointe pieceJointe = new PieceJointe(
                    file.getOriginalFilename(),
                    url,
                    file.getOriginalFilename(),
                    FilenameUtils.getExtension(file.getOriginalFilename()),
                    file.getSize(),
                    new Demande()
            );

            return pieceJointe;
        } catch (Exception e) {
            throw new FileUploadException("Impossible d'enregistrer le fichier. Error: " + e.getMessage());
        }
    }

    public Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    public byte[] recuperPiece(String fileUrl) throws IOException {
        Path file = Paths.get(fileUrl);
        return Files.readAllBytes(file);
    }

    @PostConstruct
    public void initFolderCreate() {
        if(!existFolder(storageFolder)) {
            createFolder(storageFolder);
        }
    }

    @Async
    public String storeFile(MultipartFile multipartFile, String folder, String filename) {
        log.debug("created file  : {}", filename);
        try {
            try (InputStream inputStream = multipartFile.getInputStream()) {
                Files.copy(inputStream, Paths.get(folder + filename), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            e.printStackTrace();;
        }
        return filename;
    }


    public byte[] readFile(String filename, String folder) throws IOException {
        File file = new File(folder + filename);
        Path path = Paths.get(file.toURI());
        return Files.readAllBytes(path);
    }

    public String generateFileName(MultipartFile multipartFile, DemandeDTO demande) {
        String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        return demande.getAgent().getNom()+demande.getAgent().getPrenom()+"_"+ filename.replace(" ", "_");
    }

    private boolean existFolder(String folder) {
        log.debug("check if folder  : {} exist", folder);
        File file = new File(folder);
        return file.exists();
    }

    private boolean createFolder(String folder) {
        log.debug("created file store root folder : {}", folder);
        return new File(folder).mkdirs();
    }
}
