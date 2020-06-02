package com.appeti.utils.encryption;

/**
 * @author ashafie
 * @version $Revision$
 */
public interface EncryptionManager {

	public String encrypt(String inputStr);

	public String decrypt(String inputStr);

}
