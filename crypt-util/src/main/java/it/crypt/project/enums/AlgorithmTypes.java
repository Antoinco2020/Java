package it.crypt.project.enums;

public enum AlgorithmTypes {
    AES_CBC_NO_PADDING("AES/CBC/NoPadding"),
    AES_CBC_PKCS5PADDING("AES/CBC/PKCS5Padding"),
    AES_EBC_NO_PADDING("AES/ECB/NoPadding"),
    AES_EBC_PKCS5PADDING("AES/ECB/PKCS5Padding"),
    DES_CBC_NO_PADDING("DES/CBC/NoPadding"),
    DES_CBC_PKCS5PADDING("DES/CBC/PKCS5Padding"),
    DES_EBC_NO_PADDING("DES/ECB/NoPadding"),
    DES_EBC_PKCS5PADDING("DES/ECB/PKCS5Padding"),
    DESEDE_CBC_NO_PADDING("DESede/CBC/NoPadding"),
    DESEDE_CBC_PKCS5PADDING("DESede/CBC/PKCS5Padding"),
    DESEDE_EBC_NO_PADDING("DESede/ECB/NoPadding"),
    DESEDE_EBC_PKCS5PADDING("DESede/ECB/PKCS5Padding"),
    RSA_EBC_PKCS1PADDING("RSA/ECB/PKCS1Padding"),
    RSA_EBC_OAEP_SHA1_MGF1_PADDING("RSA/ECB/OAEPWithSHA-1AndMGF1Padding"),
    RSA_EBC_OAEP_SHA256_MGF1_PADDING("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");

    public final String label;

    private AlgorithmTypes(String label){
        this.label = label;
    }
}
