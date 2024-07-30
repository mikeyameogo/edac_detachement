package bf.mfptps.detachementservice.web;

import bf.mfptps.detachementservice.domain.PieceJointe;
import bf.mfptps.detachementservice.service.FileService;
import bf.mfptps.detachementservice.service.dto.PieceJointeDTO;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/detachements/files")
public class FileRessource {
    private final FileService fileService;
    public FileRessource(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload-piece")
    public ResponseEntity<PieceJointe> uploadFile(@RequestParam("file") MultipartFile file) throws FileUploadException {
        PieceJointe pieceJointe = fileService.save(file);
        return ResponseEntity.ok().body(pieceJointe);
    }

    @PostMapping("/recuperer-piece")
    byte[] retrieveFile(@RequestParam("fileName") String fileName) throws IOException {
        return fileService.recuperPiece(fileName);
    }

}
