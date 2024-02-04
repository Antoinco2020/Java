package it.crypt.project.enums;

public enum AlgorithmName {
    AES("AES"),
    DES("DES"),
    DESEDE("DESEDE"),
    RSA("RSA");
    public final String label;
    private AlgorithmName(String label){
        this.label = label;
    }
}
