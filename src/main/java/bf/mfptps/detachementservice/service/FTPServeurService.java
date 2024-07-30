/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service;

import java.io.File;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface FTPServeurService {

    public void uploadFile(File file, String remoteDirectory);
}
