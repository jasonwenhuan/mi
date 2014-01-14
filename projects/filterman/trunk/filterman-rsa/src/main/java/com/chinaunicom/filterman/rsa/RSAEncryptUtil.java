package com.chinaunicom.filterman.rsa;

import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.chinaunicom.filterman.utilities.Logging;

/**
 *  Copyright 2013 by eclipse 
 *  All right reserved.
 *  Created on Sep 3, 2013 12:14:24 PM 
 *  by wenhuan
 */
public class RSAEncryptUtil{

	//for thread safe
	private static class RSAEncryptUtilHolder{
		private static final RSAEncryptUtil INSTANCE = new RSAEncryptUtil();
	}
	
	public static final RSAEncryptUtil getInstance(){
		return RSAEncryptUtilHolder.INSTANCE;
	}
	
    protected static final String ALGORITHM = "RSA";

    public RSAEncryptUtil()
    {
    	 Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * Init java security to add BouncyCastle as an RSA provider
     */
    public static void init()
    {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * Generate key which contains a pair of privae and public key using 1024 bytes
     * @return key pair
     * @throws NoSuchAlgorithmException
     */
    public static KeyPair generateKey() throws NoSuchAlgorithmException
    {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
        keyGen.initialize(512);
        KeyPair key = keyGen.generateKeyPair();
        return key;
    }


    /**
     * Encrypt a text using public key.
     * @param text The original unencrypted text
     * @param key The public key
     * @return Encrypted text
     * @throws java.lang.Exception
     */
    public static byte[] encrypt(byte[] text, Key key) throws Exception
    {
        byte[] cipherText = null;
        try
        {
            //
            // get an RSA cipher object and print the provider
            Cipher cipher = Cipher.getInstance("RSA");

            Logging.logDebug("\nProvider is: " + cipher.getProvider().getInfo());
            Logging.logDebug("\nStart encryption with public key");

            // encrypt the plaintext using the public key
            cipher.init(Cipher.ENCRYPT_MODE, key);
            cipherText = cipher.doFinal(text);
        }
        catch (Exception e)
        {
            Logging.logError("Doing RSAEncryptUtil.encrypt1 error occurred : ", e);
            throw e;
        }

        return cipherText;
    }

    /**
     * Encrypt a text using public key. The result is enctypted BASE64 encoded text
     * @param text The original unencrypted text
     * @param key The public key
     * @return Encrypted text encoded as BASE64
     * @throws java.lang.Exception
     */
    public static String encrypt(String text, Key key) throws Exception
    {
        String encryptedText;
        try
        {
            byte[] cipherText = encrypt(text.getBytes("UTF8"),key);
            //encryptedText = encodeBASE64(cipherText);
            encryptedText = bytesToString(cipherText);
            Logging.logDebug("Enctypted text is: " + encryptedText);
        }
        catch (Exception e)
        {
            Logging.logError("Doing RSAEncryptUtil.encrypt 2 error occrred: ", e);
            throw e;
        }
        return encryptedText;
    }
    
    private static String bytesToString(byte[] encrytpByte) {
    	String result = "";
    	for (Byte bytes : encrytpByte) {
    	    result += bytes.toString() + " ";
    	}
    	return result;
    }

    /**
     * Decrypt text using private key
     * @param text The encrypted text
     * @param key The private key
     * @return The unencrypted text
     * @throws java.lang.Exception
     */
    public static byte[] decrypt(byte[] text, Key key) throws Exception
    {
        byte[] dectyptedText = null;
        try
        {
            // decrypt the text using the private key
            Cipher cipher = Cipher.getInstance("RSA");
            Logging.logDebug("Start decryption");
            cipher.init(Cipher.DECRYPT_MODE, key);
            dectyptedText = cipher.doFinal(text);
        }
        catch (Exception e)
        {
            Logging.logError("Doing RSAEncryptUtil.decrypt1 error occurred :" , e);
            throw e;
        }
        return dectyptedText;

    }

    /**
     * Decrypt BASE64 encoded text using private key
     * @param text The encrypted text, encoded as BASE64
     * @param key The private key
     * @return The unencrypted text encoded as UTF8
     * @throws java.lang.Exception
     */
    public static String decrypt(String text, Key key) throws Exception
    {
        String result;
        try
        {
            // decrypt the text using the private key
            //byte[] dectyptedText = decrypt(decodeBASE64(text),key);
        	byte[] waitDecrypt = stringToBytes(text);
        	byte[] dectyptedText = decrypt(waitDecrypt, key);
            result = new String(dectyptedText, "UTF8");
            Logging.logDebug("Decrypted text is: " + result);
        }
        catch (Exception e)
        {
            Logging.logError("Doing RSAEncryptUtil.decrypt2 error occurred :" , e);
            throw e;
        }
        return result;

    }
    
    private static byte[] stringToBytes(String encrytpStr) {
    	String[] strArr = encrytpStr.split(" ");
    	int len = strArr.length;
    	byte[] clone = new byte[len];
    	for (int i = 0; i < len; i++) {
    	    clone[i] = Byte.parseByte(strArr[i]);
    	}
    	return clone;
    }

    /**
     * Convert a Key to string encoded as BASE64
     * @param key The key (private or public)
     * @return A string representation of the key
     */
    public static String getKeyAsString(Key key)
    {
        // Get the bytes of the key
        byte[] keyBytes = key.getEncoded();
        // Convert key to BASE64 encoded string
        BASE64Encoder b64 = new BASE64Encoder();
        return b64.encode(keyBytes);
    }

    /**
     * Generates Private Key from BASE64 encoded string
     * @param key BASE64 encoded string which represents the key
     * @return The PrivateKey
     * @throws java.lang.Exception
     */
    public static PrivateKey getPrivateKeyFromString(String key) throws Exception
    {
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        BASE64Decoder b64 = new BASE64Decoder();
        EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(b64.decodeBuffer(key));
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
        return privateKey;
    }

    /**
     * Generates Public Key from BASE64 encoded string
     * @param key BASE64 encoded string which represents the key
     * @return The PublicKey
     * @throws java.lang.Exception
     */
    public static PublicKey getPublicKeyFromString(String key) throws Exception
    {
        BASE64Decoder b64 = new BASE64Decoder();
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(b64.decodeBuffer(key));
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
        return publicKey;
    }

    /**
     * Encode bytes array to BASE64 string
     * @param bytes
     * @return Encoded string
     */
    private static String encodeBASE64(byte[] bytes)
    {
        BASE64Encoder b64 = new BASE64Encoder();
        return b64.encode(bytes);
    }

    /**
     * Decode BASE64 encoded string to bytes array
     * @param text The string
     * @return Bytes array
     * @throws IOException
     */
    private static byte[] decodeBASE64(String text) throws IOException
    {
        BASE64Decoder b64 = new BASE64Decoder();
        return b64.decodeBuffer(text);
    }

    /**
     * Encrypt file using 1024 RSA encryption
     *
     * @param srcFileName Source file name
     * @param destFileName Destination file name
     * @param key The key. For encryption this is the Private Key and for decryption this is the public key
     * @throws Exception
     */
    public static void encryptFile(String srcFileName, String destFileName, PublicKey key) throws Exception
    {
        encryptDecryptFile(srcFileName,destFileName, key, Cipher.ENCRYPT_MODE);
    }

    /**
     * Decrypt file using 1024 RSA encryption
     *
     * @param srcFileName Source file name
     * @param destFileName Destination file name
     * @param key The key. For encryption this is the Private Key and for decryption this is the public key
     * @throws Exception
     */
    public static void decryptFile(String srcFileName, String destFileName, PrivateKey key) throws Exception
    {
        encryptDecryptFile(srcFileName,destFileName, key, Cipher.DECRYPT_MODE);
    }

    /**
     * Encrypt and Decrypt files using 1024 RSA encryption
     *
     * @param srcFileName Source file name
     * @param destFileName Destination file name
     * @param key The key. For encryption this is the Private Key and for decryption this is the public key
     * @param cipherMode Cipher Mode
     * @throws Exception
     */
    public static void encryptDecryptFile(String srcFileName, String destFileName, Key key, int cipherMode) throws Exception
    {
        OutputStream outputWriter = null;
        InputStream inputReader = null;
        try
        {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            String textLine = null;
            //RSA encryption data size limitations are slightly less than the key modulus size,
            //depending on the actual padding scheme used (e.g. with 1024 bit (128 byte) RSA key,
            //the size limit is 117 bytes for PKCS#1 v 1.5 padding. (http://www.jensign.com/JavaScience/dotnet/RSAEncrypt/)
            byte[] buf = cipherMode == Cipher.ENCRYPT_MODE? new byte[100] : new byte[128];
            int bufl;
            // init the Cipher object for Encryption...
            cipher.init(cipherMode, key);

            // start FileIO
            outputWriter = new FileOutputStream(destFileName);
            inputReader = new FileInputStream(srcFileName);
            while ( (bufl = inputReader.read(buf)) != -1)
            {
                byte[] encText = null;
                if (cipherMode == Cipher.ENCRYPT_MODE)
                {
                      encText = encrypt(copyBytes(buf,bufl),(PublicKey)key);
                }
                else
                {
                    Logging.logDebug("buf = " + new String(buf));

                    encText = decrypt(copyBytes(buf,bufl),(PrivateKey)key);
                }
                outputWriter.write(encText);

                Logging.logDebug("encText = " + new String(encText));
            }
            outputWriter.flush();

        }
        catch (Exception e)
        {
            Logging.logError("Doing RSAEncryptUtil.encryptDecryptFile error occurred : " , e);
            throw e;
        }
        finally
        {
            try
            {
                if (outputWriter != null)
                {
                    outputWriter.close();
                }
                if (inputReader != null)
                {
                    inputReader.close();
                }
            }
            catch (Exception e)
            {
                // do nothing...
            } // end of inner try, catch (Exception)...
        }
    }

    public static byte[] copyBytes(byte[] arr, int length)
    {
        byte[] newArr = null;
        if (arr.length == length)
        {
            newArr = arr;
        }
        else
        {
            newArr = new byte[length];
            for (int i = 0; i < length; i++)
            {
                newArr[i] = (byte) arr[i];
            }
        }
        return newArr;
    }

}
