/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Service;

import bf.mfptps.detachementservice.service.FTPServeurService;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Service
public class FTPServeurServiceImpl implements FTPServeurService {

    private String host;

    private int port;

    private String username;

    private String password;

    FTPClient ftpClient = new FTPClient();

    @Override
    public void uploadFile(File file, String remoteDirectory) {

        try {
            boolean con = ftpConnect();
            if (con) {
//            ftpClient.connect(host, port);
//            ftpClient.login(username, password);
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

                // Créer le répertoire distant s'il n'existe pas déjà
                ftpClient.makeDirectory(remoteDirectory);

                // Naviguer vers le répertoire distant
                ftpClient.changeWorkingDirectory(remoteDirectory);

                // Charger le fichier vers le répertoire distant
                FileInputStream fis = new FileInputStream(file);
                try {
                    System.out.println("============\n" + file.getName() + "\n============");
                    ftpClient.storeFile(file.getName(), fis);
                } catch (Exception e) {
                    System.out.println("============ECHEC STORE============");
                }
                ftpDisconnect();
            } else {
                System.out.println("Echec de connection");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean ftpConnect() {
        try {
            serveurValues();
            ftpClient.connect(host, port);
            boolean success = ftpClient.login(username, password);
            return success;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }

    private void ftpDisconnect() {
        try {
            if (ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void serveurValues() {
        host = "127.0.0.1";
        port = 21;
        username = "cani";
        password = "12345678";
    }
}
