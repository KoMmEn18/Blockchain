package blockchain;

import blockchain.util.StringUtil;

import java.io.Serializable;
import java.util.Date;

public class Block implements Serializable {

    private final int id;
    private final int minerId;
    private final long timeStamp;
    private final String previousBlockHash;
    private final long secondsToGenerate;
    private int magicNumber;
    private static final long serialVersionUID = 1L;

    public Block(int id, int minerId, String previousBlockHash, int leadingZeros) {
        Date startDate = new Date();
        this.id = id;
        this.minerId = minerId;
        this.timeStamp = new Date().getTime();
        this.previousBlockHash = previousBlockHash;
        setMagicNumber(leadingZeros);
        this.secondsToGenerate = (new Date().getTime() - startDate.getTime()) / 1000;
    }

    public int getId() {
        return id;
    }

    public int getMinerId() {
        return minerId;
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
                "Created by miner # " + getMinerId(),
                "Id: " + getId(),
                "Timestamp: " + getTimeStamp(),
                "Magic number: " + getMagicNumber(),
                "Hash of the previous block:",
                getPreviousBlockHash(),
                "Hash of the block:",
                getBlockHash(),
                "Block was generating for " + getSecondsToGenerate() + " seconds");
    }

    private void setMagicNumber(int leadingZeros) {
        int magic = 1;
        do {
            this.magicNumber = magic++;
        } while (!(getBlockHash().startsWith("0".repeat(leadingZeros)) && getBlockHash().charAt(leadingZeros) != '0'));
    }
}
