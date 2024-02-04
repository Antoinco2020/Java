package it.restapp.project.config;

import it.crypt.project.enums.AlgorithmName;
import it.crypt.project.enums.AlgorithmTypes;
import it.crypt.project.utils.CryptUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CryptConfig {
    @Bean
    public CryptUtils getCryptUtils() throws Exception {
        return CryptUtils.getInstance(AlgorithmName.AES, AlgorithmTypes.AES_EBC_PKCS5PADDING, null);
    }
}
