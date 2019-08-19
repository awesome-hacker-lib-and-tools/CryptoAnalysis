package example;

import example._utils.U;
import javax.crypto.*;
import java.security.*;
import org.bouncycastle.jce.provider.*;

// Encriptação e decriptação com chave assimétrica
public final class SecureConfig112bitsRSA_2048x256_2 {

  public static void main(String args[]) throws NoSuchAlgorithmException,
          NoSuchPaddingException, InvalidKeyException, BadPaddingException,
          IllegalBlockSizeException, NoSuchProviderException,
          InvalidAlgorithmParameterException {

    Security.addProvider(new BouncyCastleProvider()); // provedor BC

    // configurações do sistema criptográfico para Ana e Beto
    int ksize = 2048; // tamanho da chave RSA
    int hsize = 256; // tamanho do hash 
    int maxLenBytes = (ksize - 2 * hsize) / 8 - 2; // tamanho máximo do texto claro 
    
    // Beto cria um par de chaves
    KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", "BC");
    kpg.initialize(ksize);
    KeyPair kp = kpg.generateKeyPair(); 
    
    // configurações comuns para Ana e Bato
    Cipher c = Cipher.getInstance("RSA/None/OAEPwithSHA256andMGF1Padding","BC");
 
    // Encriptação pela Ana com a chabe pública de Beto
    Key pubk = kp.getPublic();
    c.init(Cipher.ENCRYPT_MODE, pubk);
    byte[] ptAna = U.cancaoDoExilio.substring(0, maxLenBytes).getBytes();
    byte[] ct = c.doFinal(ptAna);

    // decriptação pelo Beto com sua chave privada
    Key privk = kp.getPrivate();
    c.init(Cipher.DECRYPT_MODE, privk); //inicializa o AES para decriptacao
    byte[] ptBeto = c.doFinal(ct); // Decriptando

    //U.println("Chave pública: " + pubk);
    //U.println("Chave privada: " + privk);

    U.println("Encriptado com: " + c.getAlgorithm());
    U.println("Texto claro  da Ana: " + U.b2s(ptAna));
    U.println("Criptograma (A-->B): " + U.b2x(ct) + ", bits " + ct.length * 8);
    U.println("Texto claro do Beto: " + new String(ptBeto));
  }
}
