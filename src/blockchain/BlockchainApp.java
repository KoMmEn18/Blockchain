package blockchain;

import java.util.Scanner;

public class BlockchainApp {

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter how many zeros the hash must start with: ");
        int leadingZeros = Integer.parseInt(scanner.nextLine());

        BlockChain blockChain = new BlockChain(leadingZeros);
        System.out.println();
        for (int i = 0; i < 5; i++) {
            blockChain.generateNewBlock();
        }
        blockChain.printBlockChain();
    }
}
