package com.javainsight.utils;


import org.apache.log4j.Logger;


/**
 * Demonstrate how to handle hex strings.
 *
 * @author Sunny Jain
 * @version 1.0 2009-06-05 - initial version.
 * @since 2010-03-20
 */
public final class HexToBytes
    {
    /**
     * precomputed translate table for chars 0..'f'
     */
	private static Logger logger = Logger.getLogger(HexToBytes.class);
    private static byte[] correspondingNibble = new byte['f' + 1];
    // -------------------------- PUBLIC STATIC METHODS --------------------------

    /**
     * Convert a hex string to an unsigned byte array.
     * Permits upper or lower case hex.
     *
     * @param s String must have even number of characters.
     *          and be formed only of digits 0-9 A-F or
     *          a-f. No spaces, minus or plus signs.
     *
     * @return corresponding unsigned byte array. 
     */
    public static byte[] fromHexString( String s )
        {
        int stringLength = s.length();
        if ( ( stringLength & 0x1 ) != 0 )
            {
            throw new IllegalArgumentException( "fromHexString requires an even number of hex characters" );
            }
        byte[] bytes = new byte[stringLength / 2];

        for ( int i = 0, j = 0; i < stringLength; i += 2, j++ )
            {
            int high = charToNibble( s.charAt( i ) );
            int low = charToNibble( s.charAt( i + 1 ) );
            // You can store either unsigned 0..255 or signed -128..127 bytes in a byte type.
            bytes[ j ] = ( byte ) ( ( high << 4 ) | low );
            }
        return bytes;
        }

    // -------------------------- STATIC METHODS --------------------------

    static
        {
        // only 0..9 A..F a..f have meaning. rest are errors.
        for ( int i = 0; i <= 'f'; i++ )
            {
            correspondingNibble[ i ] = -1;
            }
        for ( int i = '0'; i <= '9'; i++ )
            {
            correspondingNibble[ i ] = ( byte ) ( i - '0' );
            }
        for ( int i = 'A'; i <= 'F'; i++ )
            {
            correspondingNibble[ i ] = ( byte ) ( i - 'A' + 10 );
            }
        for ( int i = 'a'; i <= 'f'; i++ )
            {
            correspondingNibble[ i ] = ( byte ) ( i - 'a' + 10 );
            }
        }

    /**
     * convert  a single char to corresponding nibble using a precalculated array.
     * Based on code by:
      *
     * @param c char to convert. must be 0-9 a-f A-F, no
     *          spaces, plus or minus signs.
     *
     * @return corresponding integer  0..15
     * @throws IllegalArgumentException on invalid c.
     */
    private static int charToNibble( char c )
        {
        if ( c > 'f' )
            {
            throw new IllegalArgumentException( "Invalid hex character: " + c );
            }
        int nibble = correspondingNibble[ c ];
        if ( nibble < 0 )
            {
            throw new IllegalArgumentException( "Invalid hex character: " + c );
            }
        return nibble;
        }

 
    // --------------------------- main() method ---------------------------

    /**
     * Test harness
     *
     * @param args not used
     */
    public static byte[] toUnsignedByte( String message )
        {
        String hexString = message;        
        // convert hex string to an array of bytes
        byte[] bytes = fromHexString( hexString );
        String tmp = "";
        for(byte b : bytes){
        	tmp += b;
        }
       // logger.info(tmp);
        return bytes;
        }
    
    public static String stringToHex(String base)
    {
     StringBuffer buffer = new StringBuffer();
     int intValue;
     for(int x = 0; x < base.length(); x++)
         {
         int cursor = 0;
         intValue = base.charAt(x);
         String binaryChar = new String(Integer.toBinaryString(base.charAt(x)));
         for(int i = 0; i < binaryChar.length(); i++)
             {
             if(binaryChar.charAt(i) == '1')
                 {
                 cursor += 1;
             }
         }
         if((cursor % 2) > 0)
             {
             intValue += 128;
         }
         buffer.append(Integer.toHexString(intValue));
     }
     return buffer.toString();
}
    	
    }