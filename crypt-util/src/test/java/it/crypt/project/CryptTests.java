package it.crypt.project;

import it.crypt.project.enums.AlgorithmName;
import it.crypt.project.enums.AlgorithmTypes;
import it.crypt.project.utils.CryptUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class CryptTests {
    private final String text = "ExampleVariable1";

    @Test
    public void AES_EBC_NOPADDING_TEST() {
        try {
            CryptUtils cryptUtils = CryptUtils.getInstance(AlgorithmName.AES, AlgorithmTypes.AES_EBC_NO_PADDING);
            String result = cryptUtils.encrypt(text);
            Assertions.assertNotEquals(text, result);
            result = cryptUtils.decrypt(result);
            Assertions.assertEquals(text, result);
        }catch (Exception e){
            fail(e);
        }

    }

    @Test
    public void AES_EBC_PKCS5PADDING_TEST() {
        try {
            CryptUtils cryptUtils = CryptUtils.getInstance(AlgorithmName.AES, AlgorithmTypes.AES_EBC_PKCS5PADDING, null);
            String result = cryptUtils.encrypt(text);
            Assertions.assertNotEquals(text, result);
            result = cryptUtils.decrypt(result);
            Assertions.assertEquals(text, result);
        }catch (Exception e){
            fail(e);
        }

    }

    @Test
    public void AES_CBC_NOPADDING_TEST() {
        try {
            CryptUtils cryptUtils = CryptUtils.getInstance(AlgorithmName.AES, AlgorithmTypes.AES_CBC_NO_PADDING);
            String result = cryptUtils.encrypt(text);
            Assertions.assertNotEquals(text, result);
            result = cryptUtils.decrypt(result);
            Assertions.assertEquals(text, result);
        }catch (Exception e){
            fail(e);
        }

    }
    @Test
    public void AES_CBC_PKCS5PADDING_TEST() {
        try {
            CryptUtils cryptUtils = CryptUtils.getInstance(AlgorithmName.AES, AlgorithmTypes.AES_CBC_NO_PADDING);
            String result = cryptUtils.encrypt(text);
            Assertions.assertNotEquals(text, result);
            result = cryptUtils.decrypt(result);
            Assertions.assertEquals(text, result);
        }catch (Exception e){
            fail(e);
        }

    }
    @Test
    public void DES_EBC_NOPADDING_TEST() {
        try {
            CryptUtils cryptUtils = CryptUtils.getInstance(AlgorithmName.DES, AlgorithmTypes.DES_EBC_NO_PADDING);
            String result = cryptUtils.encrypt(text);
            Assertions.assertNotEquals(text, result);
            result = cryptUtils.decrypt(result);
            Assertions.assertEquals(text, result);
        }catch (Exception e){
            fail(e);
        }

    }
    @Test
    public void DES_EBC_PKCS5PADDING_TEST() {

        try {
            CryptUtils cryptUtils = CryptUtils.getInstance(AlgorithmName.DES, AlgorithmTypes.DES_EBC_PKCS5PADDING);
            String result = cryptUtils.encrypt(text);
            Assertions.assertNotEquals(text, result);
            result = cryptUtils.decrypt(result);
            Assertions.assertEquals(text, result);
        } catch (Exception e){
            fail(e);
        }
    }
    @Test
    public void DES_CBC_NOPADDING_TEST() {
        try {
            CryptUtils cryptUtils = CryptUtils.getInstance(AlgorithmName.DES, AlgorithmTypes.DES_CBC_NO_PADDING);
            String result = cryptUtils.encrypt(text);
            Assertions.assertNotEquals(text, result);
            result = cryptUtils.decrypt(result);
            Assertions.assertEquals(text, result);
        }catch (Exception e){
            fail(e);
        }

    }
    @Test
    public void DES_CBC_PKCS5PADDING_TEST() {
        try {
            CryptUtils cryptUtils = CryptUtils.getInstance(AlgorithmName.DES, AlgorithmTypes.DES_CBC_PKCS5PADDING);
            String result = cryptUtils.encrypt(text);
            Assertions.assertNotEquals(text, result);
            result = cryptUtils.decrypt(result);
            Assertions.assertEquals(text, result);
        }catch (Exception e){
            fail(e);
        }

    }
    @Test
    public void DESEDE_EBC_NOPADDING_TEST() {
        try {
            CryptUtils cryptUtils = CryptUtils.getInstance(AlgorithmName.DESEDE, AlgorithmTypes.DESEDE_EBC_NO_PADDING);
            String result = cryptUtils.encrypt(text);
            Assertions.assertNotEquals(text, result);
            result = cryptUtils.decrypt(result);
            Assertions.assertEquals(text, result);
        }catch (Exception e){
            fail(e);
        }

    }
    @Test
    public void DESEDE_EBC_PKCS5PADDING_TEST() {
        try {
            CryptUtils cryptUtils = CryptUtils.getInstance(AlgorithmName.DESEDE, AlgorithmTypes.DESEDE_EBC_PKCS5PADDING);
            String result = cryptUtils.encrypt(text);
            Assertions.assertNotEquals(text, result);
            result = cryptUtils.decrypt(result);
            Assertions.assertEquals(text, result);
        }catch (Exception e){
            fail(e);
        }

    }
    @Test
    public void DESEDE_CBC_NOPADDING_TEST() {
        try {
            CryptUtils cryptUtils = CryptUtils.getInstance(AlgorithmName.DESEDE, AlgorithmTypes.DESEDE_CBC_NO_PADDING);
            String result = cryptUtils.encrypt(text);
            Assertions.assertNotEquals(text, result);
            result = cryptUtils.decrypt(result);
            Assertions.assertEquals(text, result);
        }catch (Exception e){
            fail(e);
        }

    }
    @Test
    public void DESEDE_CBC_PKCS5PADDING_TEST() {
        try {
            CryptUtils cryptUtils = CryptUtils.getInstance(AlgorithmName.DESEDE, AlgorithmTypes.DESEDE_CBC_PKCS5PADDING);
            String result = cryptUtils.encrypt(text);
            Assertions.assertNotEquals(text, result);
            result = cryptUtils.decrypt(result);
            Assertions.assertEquals(text, result);
        }catch (Exception e){
            fail(e);
        }

    }
    @Test
    public void RSA_EBC_PKCS1PADDING_TEST() {
        try {
            CryptUtils cryptUtils = CryptUtils.getInstance(AlgorithmName.RSA, AlgorithmTypes.RSA_EBC_PKCS1PADDING);
            String result = cryptUtils.encrypt(text);
            Assertions.assertNotEquals(text, result);
            result = cryptUtils.decrypt(result);
            Assertions.assertEquals(text, result);
        }catch (Exception e){
            fail(e);
        }

    }
    @Test
    public void RSA_EBC_OAEP_SHA1_MGF1_PADDING_TEST() {
        try {
            CryptUtils cryptUtils = CryptUtils.getInstance(AlgorithmName.RSA, AlgorithmTypes.RSA_EBC_OAEP_SHA1_MGF1_PADDING);
            String result = cryptUtils.encrypt(text);
            Assertions.assertNotEquals(text, result);
            result = cryptUtils.decrypt(result);
            Assertions.assertEquals(text, result);
        }catch (Exception e){
            fail(e);
        }

    }
    @Test
    public void RSA_EBC_OAEP_SHA256_MGF1_PADDING_TEST() {
        try {
            CryptUtils cryptUtils = CryptUtils.getInstance(AlgorithmName.RSA, AlgorithmTypes.RSA_EBC_OAEP_SHA256_MGF1_PADDING);
            String result = cryptUtils.encrypt(text);
            Assertions.assertNotEquals(text, result);
            result = cryptUtils.decrypt(result);
            Assertions.assertEquals(text, result);
        }catch (Exception e){
            fail(e);
        }

    }

    @Test
    public void GENERATE_KEY_FROM_PASSWORD_TEST() {
        try {
            CryptUtils cryptUtils = CryptUtils.getInstance(AlgorithmName.RSA, AlgorithmTypes.RSA_EBC_PKCS1PADDING, "PRovaTestdiventiquattrocaratterilunghezzamassima");
            String result = cryptUtils.encrypt(text);
            Assertions.assertNotEquals(text, result);
            result = cryptUtils.decrypt(result);
            Assertions.assertEquals(text, result);
        }catch (Exception e){
            fail(e);
        }

    }

    @Test
    public void MULTIPLE_ENCRYPTION_DECRYPTION_TEST() {
        try {
            CryptUtils cryptUtils = CryptUtils.getInstance(AlgorithmName.DESEDE, AlgorithmTypes.DESEDE_CBC_PKCS5PADDING);
            String encryption = cryptUtils.encrypt(text);
            Assertions.assertNotEquals(text, encryption);
            String decryption = cryptUtils.decrypt(encryption);
            Assertions.assertEquals(text, decryption);

            String encryption2 = cryptUtils.encrypt(text);
            Assertions.assertEquals(encryption, encryption2);
            String decryption2 = cryptUtils.decrypt(encryption);
            Assertions.assertEquals(decryption, decryption2);
        }catch (Exception e){
            fail(e);
        }

    }

    @Test
    public void NO_CONGRUENT_ALGORITHM_TEST() {

        try {
            CryptUtils cryptUtils = CryptUtils.getInstance(AlgorithmName.AES, AlgorithmTypes.RSA_EBC_OAEP_SHA256_MGF1_PADDING);
            String result = cryptUtils.encrypt(text);
        }catch (Exception e){
            Assertions.assertEquals(e.getMessage(), "The algorithm name has to match at the algorithm type");
        }
    }

    @Test
    public void NULL_TEXT_DECRYPT_TEST() {

        try {
            CryptUtils cryptUtils = CryptUtils.getInstance(AlgorithmName.AES, AlgorithmTypes.RSA_EBC_OAEP_SHA256_MGF1_PADDING);
            cryptUtils.decrypt(null);
        }catch (Exception e){
            Assertions.assertEquals("The parameter cipherText is mandatory", e.getMessage());
        }
    }
    @Test
    public void EMPTY_TEXT_DECRYPT_TEST() {

        try {
            CryptUtils cryptUtils = CryptUtils.getInstance(AlgorithmName.AES, AlgorithmTypes.RSA_EBC_OAEP_SHA256_MGF1_PADDING);
            cryptUtils.decrypt("");
        }catch (Exception e){
            Assertions.assertEquals(e.getMessage(), "The parameter cipherText is mandatory");
        }
    }
}
