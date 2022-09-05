package blockchain;

import blockchain.util.IntegerUtil;

import java.io.File;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

public class Message {

    private int id;
    private String content;
    private String author;
    private byte[] sign;

    public Message(int id, String content, String author) throws Exception {
        this.id = id;
        this.content = content;
        this.author = author;
        this.sign = sign(content);
    }

    public byte[] sign(String data) throws Exception {
        Signature rsa = Signature.getInstance("SHA1withRSA");
        rsa.initSign(getPrivate());
        rsa.update(data.getBytes());
        rsa.update(IntegerUtil.intToByteArray(id));
        return rsa.sign();
    }

    public PrivateKey getPrivate() throws Exception {
        byte[] keyBytes = Files.readAllBytes(new File("private.txt").toPath());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public byte[] getSign() {
        return sign;
    }
}
