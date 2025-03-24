package fr.trxyy.alternative.alternative_api.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class AESUtil {

    private static final String AES = "AES";
    private static final String SECRET_KEY = "Pepsiforthewin"; // Change this to a secure key

    // Méthode pour chiffrer un mot de passe
    public static String encrypt(String password) {
        try {
            SecretKeySpec keySpec = generateKey(SECRET_KEY);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encrypted = cipher.doFinal(password.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du chiffrement", e);
        }
    }

    // Méthode pour déchiffrer un mot de passe
    public static String decrypt(String encryptedPassword) {
        try {
            SecretKeySpec keySpec = generateKey(SECRET_KEY);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decoded = Base64.getDecoder().decode(encryptedPassword);
            byte[] decrypted = cipher.doFinal(decoded);
            return new String(decrypted, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du déchiffrement", e);
        }
    }

    // Génération de la clé AES à partir d'une chaîne secrète
    private static SecretKeySpec generateKey(String secret) throws Exception {
        byte[] key = secret.getBytes("UTF-8");
        SecureRandom secureRandom = new SecureRandom();
        KeyGenerator keyGen = KeyGenerator.getInstance(AES);
        keyGen.init(128, secureRandom);
        SecretKey secretKey = keyGen.generateKey();
        return new SecretKeySpec(key, AES);
    }
}
