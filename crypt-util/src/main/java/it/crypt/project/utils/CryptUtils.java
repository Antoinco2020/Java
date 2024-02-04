package it.crypt.project.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.crypt.project.enums.AlgorithmName;
import it.crypt.project.enums.AlgorithmTypes;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.*;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

@Getter
@Setter
public class CryptUtils {

    // Change this Inputkey GUID with a new GUID when you use this code in your own program !!!
    // Keep this inputkey and passkey very safe and prevent someone from decoding it some way !!!
    final static String inputKey = "560A18CD-6346-4CF0-A2E8-671F9B6B9EA9";
    final static String passKey = "Wjh!Sqr$XybXyHjUtFd$?rT!";
    private String keyPassword;
    private AlgorithmName algorithmName;
    private AlgorithmTypes algorithmTypes;
    private IvParameterSpec ivParameterSpec;
    private SecretKey secretKey;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    @JsonCreator
    private CryptUtils(@JsonProperty("algorithmName") AlgorithmName algorithmName, @JsonProperty("algorithmTypes") AlgorithmTypes algorithmTypes,
                       @JsonProperty("keyPassword") String keyPassword) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, UnsupportedEncodingException {
        this.keyPassword = keyPassword;
        this.algorithmName = algorithmName;
        this.algorithmTypes = algorithmTypes;
        this.ivParameterSpec = getIvParameterSpec(algorithmTypes);
        if(algorithmName.label.equals("RSA")){
            KeyPair keyPair = rsaKeyPairGenerator(algorithmName);
            this.privateKey = keyPair.getPrivate();
            this.publicKey = keyPair.getPublic();
        }
        else{
            if(keyPassword == null || keyPassword.isEmpty()){
                this.secretKey = getKeyFromPassword(passKey, algorithmName);
            }
            else{
                this.secretKey = getKeyFromPassword(keyPassword, algorithmName);
            }

        }
    }
    public static CryptUtils getInstance(AlgorithmName algorithmName, AlgorithmTypes algorithmTypes) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, UnsupportedEncodingException {
        return new CryptUtils(algorithmName, algorithmTypes, null);
    }
    public static CryptUtils getInstance(AlgorithmName algorithmName, AlgorithmTypes algorithmTypes, String keyPassword) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, UnsupportedEncodingException {
        return new CryptUtils(algorithmName, algorithmTypes, keyPassword);
    }
    private static IvParameterSpec getIvParameterSpec(AlgorithmTypes algorithmTypes){

        return switch (algorithmTypes) {
            case AES_CBC_NO_PADDING, AES_CBC_PKCS5PADDING -> new IvParameterSpec(new SecureRandom().generateSeed(16));
            case DES_CBC_NO_PADDING, DES_CBC_PKCS5PADDING, DESEDE_CBC_NO_PADDING, DESEDE_CBC_PKCS5PADDING ->
                    new IvParameterSpec(new SecureRandom().generateSeed(8));
            default -> null;
        };
    }
    private static int getKeySize(AlgorithmTypes algorithmTypes){

        return switch (algorithmTypes) {
            case AES_EBC_NO_PADDING, AES_EBC_PKCS5PADDING, AES_CBC_NO_PADDING, AES_CBC_PKCS5PADDING -> 128;
            case DESEDE_EBC_NO_PADDING, DESEDE_EBC_PKCS5PADDING, DESEDE_CBC_NO_PADDING, DESEDE_CBC_PKCS5PADDING -> 112;
            case RSA_EBC_OAEP_SHA256_MGF1_PADDING -> 256;
            default -> 56;
        };
    }
    public static SecretKey generateKey(AlgorithmName algorithmName, AlgorithmTypes algorithmTypes) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithmName.label);

        int n = getKeySize(algorithmTypes);

        keyGenerator.init(n);
        return keyGenerator.generateKey();
    }
    public static SecretKey getKeyFromPassword(String password, AlgorithmName algorithmName)
            throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, UnsupportedEncodingException {

        SecretKeyFactory factory = null;
        KeySpec spec = null;
        switch (algorithmName) {
            case DES -> {
                factory = SecretKeyFactory.getInstance(algorithmName.label);
                spec = new DESKeySpec(password.getBytes(StandardCharsets.UTF_8));
            }
            case DESEDE -> {
                factory = SecretKeyFactory.getInstance(algorithmName.label);
                spec = new DESedeKeySpec(password.getBytes(StandardCharsets.UTF_8)); //24
            }
            case RSA, AES -> {
                factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
                spec = new PBEKeySpec(password.toCharArray(), inputKey.getBytes(StandardCharsets.UTF_8), 65536, 128);
            }
        }

        return new SecretKeySpec(factory.generateSecret(spec)
                .getEncoded(), algorithmName.label);
    }
    public static KeyPair rsaKeyPairGenerator(AlgorithmName algorithmName) throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(algorithmName.label);
        keyGen.initialize(1024);
        return keyGen.generateKeyPair();
    }
    public String encrypt(String text) throws Exception {

        if(!algorithmTypes.label.toUpperCase().contains(algorithmName.label.toUpperCase()))
            throw new Exception("The algorithm name has to match at the algorithm type");

        Cipher cipher = Cipher.getInstance(algorithmTypes.label);
        if(algorithmName.label.equals("RSA")){
            cipher.init(Cipher.ENCRYPT_MODE, publicKey, ivParameterSpec);
        }
        else{
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
        }

        byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
        return java.util.Base64.getEncoder()
                .encodeToString(encrypted);
    }
    private static Boolean isBase64String(String base64String) {
        base64String = base64String.trim();

        //Pattern pattern = Pattern.compile("^[a-zA-Z0-9\\+/]*={0,3}$", Pattern.);
        return (base64String.length() % 4 == 0) &&
                 Base64.isBase64(base64String);
    }
    public String decrypt(String cipherText) throws Exception {
        if(cipherText == null)
            throw new NullPointerException("The parameter cipherText is mandatory");

        if (cipherText.isEmpty())
            throw new NullPointerException("The parameter cipherText is mandatory");

        if (!isBase64String(cipherText))
            throw new Exception("The parameter cipherText is not a base64");

        if(!algorithmTypes.label.toUpperCase().contains(algorithmName.label.toUpperCase()))
            throw new Exception("The algorithm name has to match at the algorithm type");

        Cipher cipher = Cipher.getInstance(algorithmTypes.label);
        if(algorithmName.label.equals("RSA")){
            cipher.init(Cipher.DECRYPT_MODE, privateKey, ivParameterSpec);
        }
        else{
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
        }

        byte[] decrypted = cipher.doFinal(java.util.Base64.getDecoder()
                .decode(cipherText));
        return new String(decrypted);
    }

}
