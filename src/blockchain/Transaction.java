package blockchain;

import blockchain.util.IntegerUtil;

import java.io.File;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

public class Transaction {

    private int id;
    private int value;
    private String sender;
    private String receiver;
    private byte[] sign;

    public Transaction(int id, int value, String sender, String receiver) throws Exception {
        this.id = id;
        this.value = value;
        this.sender = sender;
        this.receiver = receiver;
        this.sign = sign();
    }

    public int getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public int getValue() {
        return value;
    }

    public byte[] getSign() {
        return sign;
    }

    private byte[] sign() throws Exception {
        Signature rsa = Signature.getInstance("SHA1withRSA");
        rsa.initSign(getPrivate());
        rsa.update(IntegerUtil.intToByteArray(id));
        rsa.update(IntegerUtil.intToByteArray(value));
        rsa.update(sender.getBytes());
        rsa.update(receiver.getBytes());

        return rsa.sign();
    }

    private PrivateKey getPrivate() throws Exception {
        byte[] keyBytes = Files.readAllBytes(new File("private.txt").toPath());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }
}
