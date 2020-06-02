package com.appeti.utils.encryption;

import java.io.IOException;
import java.io.InputStream;

/**
 * Base64 encoding and decoding (MIME spec RFC 1521).
 */
public class Base64 {
	/**
	 * Stand alone test
	 */
	public static void main(String[] args) 
		throws Exception {
		if (args.length < 2) {
			System.out.println("Usage: Base64 <encode/decode> <inString>");
			System.exit(0);
		}
		
		String inString = args[1]; 
		System.out.println("InString = " + inString);
		
		if (args[0].equalsIgnoreCase("encode")) {
		       
		    String encodedString = Base64.encode(inString.getBytes());
		    System.out.println("EncodedString = " + encodedString);    
		    
		} else {
            String outString = new String(Base64.decode(inString));		
		    System.out.println("DecodedString = " + outString);
		}	
	}
	
	/**
	 * Base64-encode binary data.
	 *
	 * @param bytes A buffer holding the data to encode.
	 * @return String String containing the encoded data
	 */
	public static String encode(byte[] bytes) {
		return encode(bytes, false);
	}

	/**
	 * base64-encode binary data.
	 *
	 * @param bytes A buffer holding the data to encode.
	 * @param lineBreaks true if the encoded data will be broken
	 *				  into 64-character lines with CRLF pairs.
	 * @return	String String containing the encoded data
	 */
	public static String encode(byte[] bytes, 
								boolean lineBreaks) {

		/* slightly bigger buffer than we usually need */
		StringBuffer buf= new StringBuffer((int)(bytes.length * 1.4));

    	int temp			= 0;	/* holder for the current group of 24 bits */
    	int charsWritten	= 0;	/* count of char written (to current line if lineBreaks == true) */
    	int bytesRead		= 0;	/* count of bytes read in the current 24-bit group */
    	for(int index = 0; index < bytes.length; ++index) {
    		
			temp <<= 8;
			temp |= (bytes[index] & EIGHT_BITS);
			++bytesRead;
			if(bytesRead == 3) {
				/* The lower 3 bytes of temp now hold the 24 bits of a 
				 * translation group. Write them out as 4 characters 
				 * that represent 6 bits each.
				 */
				buf.append(valueToChar[(temp >> 18) & SIX_BITS]);
				buf.append(valueToChar[(temp >> 12) & SIX_BITS]);
				buf.append(valueToChar[(temp >>  6) & SIX_BITS]);
				buf.append(valueToChar[(temp >>  0) & SIX_BITS]);
	    		temp = 0;
	    		bytesRead = 0;
	    		charsWritten += 4;
   	    		if(lineBreaks && charsWritten >= 64) {
					buf.append(CRLF);
					charsWritten = 0;
				}
	    	}
		}

		// The input byte[] is exhaused.  
		// If there were fewer than 3 bytes in the last translation group,
		// we must complete the output and pad it to a multiple of 4 chars.
		
    	switch(bytesRead){
			case 1:
				// The low 8 bits of temp take 2 characters to represent. 
	    		buf.append(valueToChar[(temp >> 2) & SIX_BITS]);
	    		buf.append(valueToChar[(temp << 4) & SIX_BITS]);
				buf.append('='); // padding 
				buf.append('='); // padding 
				if(lineBreaks) {
					buf.append(CRLF);
				}
	    		break;

			case 2:
				// The low 16 bits of temp take 3 characters to represent 
	    		buf.append(valueToChar[(temp >> 10) & SIX_BITS]);
	    		buf.append(valueToChar[(temp >>  4) & SIX_BITS]);
	    		buf.append(valueToChar[(temp <<  2) & SIX_BITS]);
				buf.append('='); // padding 
				if(lineBreaks){
					buf.append(CRLF);
				}
	    		break;

			default:
				// There were exactly 3 bytes in the last group. No padding needed. 
	    		break;
		}
	    if(charsWritten > 0 && lineBreaks) {
			buf.append(CRLF);
		}
		return buf.toString();
	}

	/**
	 * Decode a base64-encoded string into binary data.<P>
	 * 
	 * Ignores (skips over) carriage-return and line-feed characters.
	 * Stops decoding when it encounters the base64 pad character ('=')
	 * or when characters are encountered that are not MIME/base64 numerals.
	 * 
	 * @param		mimeStr a String containing the base64-encoded data
	 * @return		a byte[] containing the decoded binary data.
	 * @exception	NextagException if the mimeStr parameter violates 
	 *					the base64 encoding rules.
	 */
	public static byte[] decode(String mimeStr) throws Exception {
      int inputLength = mimeStr.length();
      byte decoded[] = new byte[inputLength];
      int r = decode(mimeStr, decoded);
      if (r == decoded.length) {
        return decoded;
      }
      byte[] result = new byte[r];
      System.arraycopy(decoded, 0, result, 0, r);
      return result;
    }
    /**
     * If the target array is not big enough, this will result in a
     * IndexOutOfBoundsException!
     */
    protected static int decode(String mimeStr, byte[] target) throws Exception {
		int		inputLength		= mimeStr.length();
		int		temp			= 0;	// accumulator for 24-bit translation group 
		int 	charsRead		= 0;	// characters read in current translation group 
		int		bytesWritten	= 0;	// count of bytes written into decoded[] 
		char	currChar;				// current character from mimeStr 
		byte	currByte;				// base64 value represented by currChar 
		int		equalCount		= 0;	// The number of '=' characters we've encountered. 

		for (int i = 0; i < inputLength; ++i) {
			currChar = mimeStr.charAt(i);
			if (currChar == CR || currChar == LF) {
				//Skip over carriage return or line-feed character. 
				continue;
			}
			try {
				currByte = charToValue[(byte)currChar];
			} catch(ArrayIndexOutOfBoundsException obe) {
				currByte = BAD_VAL;
			}
			if (currByte == BAD_VAL) {
				if (charsRead == 0) {
					 /*
					  * First character of a translation group is a 
					  * non-base64 character.  We have presumably 
					  * reached the end of base64 encoded data. 
					  */
					break;
				} else if (currChar == '=' & charsRead >= 2) {
					// Only the 3d and 4th characters may be '=' 
					currByte = 0;
					++equalCount;
				} else
					throw new Exception("E_INVALID_CHAR_ENCODED_STRING calling decode with mimeStr: '" + mimeStr + "'.");
			}
			temp <<= 6;
			temp |= currByte;
			++charsRead;
			if (charsRead == 4) {
				/* 
				 * temp has all 24 bits of the translation group, 
				 * write the bytes to decode[]
				 */
				target[bytesWritten++]= (byte)(temp >> 16);
				if(equalCount < 2) {
					target[bytesWritten++]= (byte)(temp >> 8);
					if(equalCount < 1) {
						target[bytesWritten++]= (byte)(temp >> 0);
					}
				}
				charsRead = 0;
				temp = 0;
				if (equalCount > 0) {
					// Encoded data is not permitted after an '=', we must be done. 
					break;
				}
			}
		}	// end for == end of string
        return bytesWritten;
	}

	private static final byte BAD_VAL = (byte)0xFF;

	private static final int SIX_BITS = 0x3F;
	private static final int EIGHT_BITS = 0xFF;

	private static final char CR = '\r';
	private static final char LF = '\n';

	private static final String CRLF = "\r\n";

	/**
	 * Value to be encoded is an index into this array, so the 
	 * character representation of byte value = valueToChar[value]
	 */
	private static final char valueToChar[] = {
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 
		'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 
		'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 
		'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 
		'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 
		'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 
		'w', 'x', 'y', 'z', '0', '1', '2', '3', 
		'4', '5', '6', '7', '8', '9', '+', '/'
	};

	/**
	 * The ASCII value of a character is an index into this array, so that 
	 * decoded byte value = charToValue[(byte)(character)]. <P>
	 * 
	 * Characters that are not valid base64 numerals are detected
	 * one of two ways: either the expression above yeilds BAD_VAL
	 * or the array access gives an ArrayIndexOutOfBoundsException. <P>
	 *
	 */
	private static final byte charToValue[] = {
		BAD_VAL,	BAD_VAL,	BAD_VAL,	BAD_VAL,	BAD_VAL,	BAD_VAL,	BAD_VAL,	BAD_VAL,	
		BAD_VAL,	BAD_VAL,	BAD_VAL,	BAD_VAL,	BAD_VAL,	BAD_VAL,	BAD_VAL,	BAD_VAL,	
		BAD_VAL,	BAD_VAL,	BAD_VAL,	BAD_VAL,	BAD_VAL,	BAD_VAL,	BAD_VAL,	BAD_VAL,	
		BAD_VAL,	BAD_VAL,	BAD_VAL,	BAD_VAL,	BAD_VAL,	BAD_VAL,	BAD_VAL,	BAD_VAL,	
		BAD_VAL,	BAD_VAL,	BAD_VAL,	BAD_VAL,	BAD_VAL,	BAD_VAL,	BAD_VAL,	BAD_VAL,	
		BAD_VAL,	BAD_VAL,	BAD_VAL,	62,			BAD_VAL,	BAD_VAL,	BAD_VAL,	63,	
		52,			53,			54,			55,			56,			57,			58,			59,	
		60,			61,			BAD_VAL,	BAD_VAL,	BAD_VAL,	BAD_VAL,	BAD_VAL,	BAD_VAL,	
		BAD_VAL,	0,			1,			2,			3,			4,			5,			6,	
		7,			8,			9,			10,			11,			12,			13,			14,	
		15,			16,			17,			18,			19,			20,			21,			22,	
		23,			24,			25,			BAD_VAL,	BAD_VAL,	BAD_VAL,	BAD_VAL,	BAD_VAL,	
		BAD_VAL,	26,			27,			28,			29,			30,			31,			32,	
		33,			34,			35,			36,			37,			38,			39,			40,	
		41,			42,			43,			44,			45,			46,			47,			48,	
		49,			50,			51,			BAD_VAL,	BAD_VAL,	BAD_VAL,	BAD_VAL,	BAD_VAL
	};
    
    public static InputStream base64Decoder(final InputStream stream) {
      return new InputStream() {
        private char[] tmp = new char[4]; // help gc out a bit
        private byte[] dec = new byte[3];
        private int r, l;
        
        private int next() throws IOException {
          int rr;
          do {
            rr = stream.read();
          } while (rr > 0 && Character.isWhitespace((char)rr));
          return rr;
        }
        
        public int read() throws IOException {
          if (r < l) {
            return dec[r++] & 0xFF;
          }
          int a = next(); // four bytes per decode block
          int b = next();
          int c = next();
          int d = next();
          if (d < 0) {
            return -1;
          }
          tmp[0] = (char)a; tmp[1] = (char)b; tmp[2] = (char)c; tmp[3] = (char)d;
          try {
            l = decode(new String(tmp), dec);
            if (l == 0) {
              return -1;
            }
            r = 0;
            return dec[r++] & 0xFF;
          } catch (Exception e) {
            throw (IOException)new IOException("Error decoding stream!").initCause(e);
          }
        }
        public void close() throws IOException {
          stream.close();
        }
      };
    }
}
