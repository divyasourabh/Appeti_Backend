package com.appeti.utils.encryption;

public class TransformUtil {
	/**
     * Encrypts an array of bytes by XORing each byte of the array with a byte from a random pad and with result of the
     * XOR operation for the previous byte. In other words, the output of the XOR operation for each byte is an input to
     * the XOR operation of the next byte. The random pad is usually shorter than the array to encrypt, so the pad is
     * reused several times to encrypt the array; this is not really secure, but this method is not meant to provide
     * strong cryptographic guarantees. We start in the middle of the array, instead of the beginning or the end, so
     * that inputs that start or end the same way don't transform to similar-looking outputs. It is less likely for the
     * middle of two arrays to be the same.
     * 
     * It is permissible for plainBytes and cipherBytes to be the same array as long as plainStart and cipherStart are
     * the same. In that case, the output will just overwrite the input. The inverse of this method is
     * chainedXorInverse.
     * 
     * @param plainBytes array of bytes to encrypt
     * @param plainStart index of the first byte to encrypt
     * @param cipherBytes array in which to store the encrypted result
     * @param cipherStart index at which to start writing the encrypted result
     * @param length number of bytes to encrypt
     * @param randomPad randomly generated pad to use as a key
     * @see TransformUtil#chainedXorInverse(byte[], int, byte[], int, int, long)
     */
	public static void chainedXorTransform(byte[] plainBytes, int plainStart, byte[] cipherBytes, int cipherStart,
	        int length, long randomPad) {
		if (length <= 0) return;
		int mid = (length + 1) / 2 - 1;
		byte prev = 0;
		for (int i = mid; i >= 0; --i) {
			byte mask = (byte)(randomPad >> ((i % 8) << 3));
			prev = cipherBytes[i + cipherStart] = (byte)(plainBytes[i + plainStart] ^ prev ^ mask);
		}
		for (int i = length - 1; i > mid; --i) {
			byte mask = (byte)(randomPad >> ((i % 8) << 3));
			prev = cipherBytes[i + cipherStart] = (byte)(plainBytes[i + plainStart] ^ prev ^ mask);
		}
	}
	
	/**
     * Decrypts an array of bytes by XORing each byte of the array with a byte from a random pad and with the previous
     * byte. In other words, each byte is an input to the XOR operation of the next byte. The random pad is usually
     * shorter than the array to decrypt, so the pad is reused several times to decrypt the array; this is not really
     * secure, but this method is not meant to provide strong cryptographic guarantees.
     * 
     * It is permissible for cipherBytes and plainBytes to be the same array as long as cipherStart and plainStart are
     * the same. In that case, the output will just overwrite the input. This method is the inverse of
     * chainedXorTransform.
     * 
     * @param cipherBytes array of bytes to decrypt
     * @param cipherStart index of the first byte to decrypt
     * @param plainBytes array in which to store the decrypted result
     * @param plainStart index at which to start writing the decrypted result
     * @param length number of bytes to decrypt
     * @param randomPad randomly generated pad to use as a key
     * @see TransformUtil#chainedXorTransform(byte[], int, byte[], int, int, long)
     */
	public static void chainedXorInverse(byte[] cipherBytes, int cipherStart, byte[] plainBytes, int plainStart,
	        int length, long randomPad) {
		if (length <= 0) return;
		int mid = (length + 1) / 2 - 1;
		byte prev = 0;
		for (int i = mid; i >= 0; --i) {
			byte mask = (byte)(randomPad >> ((i % 8) << 3));
			byte curr = cipherBytes[i + cipherStart];
			plainBytes[i + plainStart] = (byte)(curr ^ prev ^ mask);
			prev = curr;
		}
		for (int i = length - 1; i > mid; --i) {
			byte mask = (byte)(randomPad >> ((i % 8) << 3));
			byte curr = cipherBytes[i + cipherStart];
			plainBytes[i + plainStart] = (byte)(curr ^ prev ^ mask);
			prev = curr;
		}
	}
}