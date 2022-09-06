package blockchain;

import blockchain.util.IntegerUtil;

import java.io.File;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

public class VerifyMessage {

    public boolean verifySignature(Transaction message) throws Exception {
        Signature sig = Signature.getInstance("SHA1withRSA");
        sig.initVerify(getPublic());
        sig.update(IntegerUtil.intToByteArray(message.getId()));
        sig.update(IntegerUtil.intToByteArray(message.getValue()));
        sig.update(message.getSender().getBytes());
        sig.update(message.getReceiver().getBytes());

        return sig.verify(message.getSign());
    }

    public PublicKey getPublic() throws Exception {
        byte[] keyBytes = Files.readAllBytes(new File("public.txt").toPath());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }
}