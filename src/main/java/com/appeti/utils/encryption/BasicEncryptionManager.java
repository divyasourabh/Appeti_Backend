/**
 * @author ashafie
 * @since Jul 3, 2006 
 * @version $Revision$
 */
package com.appeti.utils.encryption;


import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class BasicEncryptionManager implements EncryptionManager {

	private static final String __CVS_VERSION = "@{#}CVS versionInfo: $Id$";
	
	private static final BasicEncryptionManager sInstance = new BasicEncryptionManager();
	private static final String sChecksumSep = "-";


	public static BasicEncryptionManager getInstance() {
		return sInstance;
	}

	private BasicEncryptionManager() {}

	public String encrypt(String raw) {
		long cs = getStringChecksum(raw);
		String rawcs = cs + "-" + raw;
		byte[] bytes;
		
		try {
			bytes = rawcs.getBytes("UTF-8");
		} catch (java.io.UnsupportedEncodingException e) {
			bytes = rawcs.getBytes();
		}

		for (int i=0; i<bytes.length; i++){
			bytes[i] ^= 0x69;
		}

		return Base64.encode(bytes);
	}

	public String decrypt(String inputStr) {
		try {
			byte[] bytes = Base64.decode(inputStr);
			for (int i=0; i<bytes.length; i++){
				bytes[i] ^= 0x69;
			}
			String rawcs = new String(bytes,"UTF-8");
			if (rawcs.indexOf(sChecksumSep)>0){
				String[] parts = rawcs.split(sChecksumSep);
				String raw = rawcs.replaceFirst(parts[0]+sChecksumSep, "");
				if (!parts[0].equals(getStringChecksum(raw)+""))
					return inputStr;
				return raw;
			} else {
				return inputStr;
			}
		} catch (Exception e) {
			return inputStr;
		}
	}

	protected static long getStringChecksum(String str) {
		return getStringChecksum(new CRC32(), str);
	}

	protected static long getStringChecksum(Checksum checksum, String str) {
		byte[] bytes = str.getBytes();
		checksum.update(bytes, 0, bytes.length);
		return checksum.getValue();
	}

}
