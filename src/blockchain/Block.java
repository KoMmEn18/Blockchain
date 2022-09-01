package blockchain;

import blockchain.util.StringUtil;

import java.io.Serializable;
import java.util.Date;

public class Block implements Serializable {

    private int id;
    private long timeStamp;
    private String previousBlockHash;
    private int magicNumber;
    private long secondsToGenerate;
    private static int nextId = 1;
    private static final long serialVersionUID = 1L;

    public Block(String previousBlockHash, int leadingZeros) {
        Date startDate = new Date();
        this.id = nextId++;
        this.timeStamp = new Date().getTime();
        this.previousBlockHash = previousBlockHash;
        setMagicNumber(leadingZeros);
        Date endDate = new Date();
        this.secondsToGenerate = (endDate.getTime() - startDate.getTime()) / 1000;
    }

    public int getId() {
        return id;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getPreviousBlockHash() {
        return previousBlockHash;
    }

    public int getMagicNumber() {
        return magicNumber;
    }

    public long getSecondsToGenerate() {
        return secondsToGenerate;
    }

    public String getBlockHash() {
        return StringUtil.sha256(id + timeStamp + previousBlockHash + magicNumber);
    }

    @Override
    public String toString() {
        return String.join(System.getProperty("line.separator"),
                "Block:",
                "Id: " + getId(),
                "Timestamp: " + getTimeStamp(),
                "Magic number: " + getMagicNumber(),
                "Hash of the previous block:",
                getPreviousBlockHash(),
                "Hash of the block:",
                getBlockHash(),
                "Block was generating for " + getSecondsToGenerate() + " seconds");
    }

    public static void setNextId(int nextId) {
        Block.nextId = nextId;
    }

    private void setMagicNumber(int leadingZeros) {
        int magic = 1;
        do {
            this.magicNumber = magic++;
        } while (!(getBlockHash().startsWith("0".repeat(leadingZeros)) && getBlockHash().charAt(leadingZeros) != '0'));
    }
}
