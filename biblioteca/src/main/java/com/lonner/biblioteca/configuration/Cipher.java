package com.lonner.biblioteca.configuration;

import com.lonner.biblioteca.exceptions.BusinessException;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
@Getter
public class Cipher {

        private PrivateKey privateKey;
        private PublicKey publicKey;
        private SignatureAlgorithm algorithm;

        public Cipher(ResourceLoader resourceLoader) {
            try {
                String privada = new String(IOUtils.toByteArray(resourceLoader.getResource("classpath:security/keys/control-biblioteca-key").getInputStream())).replace("\n", "").replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "");
                String publica = new String(IOUtils.toByteArray(resourceLoader.getResource("classpath:security/keys/control-biblioteca-key.pub").getInputStream())).replaceAll("\n", "").replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");
                PKCS8EncodedKeySpec spec
                        = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privada));
                KeyFactory kf = KeyFactory.getInstance("RSA");
                this.privateKey = kf.generatePrivate(spec);
                X509EncodedKeySpec specPublic
                        = new X509EncodedKeySpec(Base64.getDecoder().decode(publica));
                this.publicKey = kf.generatePublic(specPublic);
                this.algorithm = SignatureAlgorithm.RS512;
            } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException ex) {
                throw new InternalError("No se pueden leer las llaves",ex);
            }
        }
}
