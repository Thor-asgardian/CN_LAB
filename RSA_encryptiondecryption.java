import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class RSASimpleExample {

    private BigInteger d; 
    private BigInteger e; 
    private BigInteger n; 

    public RSASimpleExample(int bitLength) {
        generateKeyPairs(bitLength);
    }

    private void generateKeyPairs(int bitLength) {
        Random random = new Random();
        BigInteger p = BigInteger.probablePrime(bitLength, random);
        BigInteger q = BigInteger.probablePrime(bitLength, random);

        n = p.multiply(q);

        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)); 
        
        e = BigInteger.probablePrime(bitLength / 2, random);

        while(phi.gcd(e).intValue() > 1) {
            e = e.add(BigInteger.ONE);
        }

        d = e.modInverse(phi);
    }

    public BigInteger encrypt(BigInteger message) {
        return message.modPow(e, n);
    }

    public BigInteger decrypt(BigInteger encryptedMessage) {
        return encryptedMessage.modPow(d, n);
    }

    public static void main(String[] args) {
        int bitLength = 1024;
        String originalMessage;
        Scanner sc=new Scanner(System.in);

        RSASimpleExample rsa = new RSASimpleExample(bitLength);

        System.out.print("Enter a string: ");
        originalMessage=sc.nextLine();

        BigInteger message = new BigInteger(originalMessage.getBytes());
        
        BigInteger encryptedMessage = rsa.encrypt(message);
        System.out.println("Encrypted message: " + encryptedMessage);
        
        BigInteger decryptedMessage = rsa.decrypt(encryptedMessage);
        System.out.println("Decrypted message: " + new String(decryptedMessage.toByteArray()));
    }
}
/*
output:
Enter a string: I am Bhuvan
Encrypted message: 19608494737533747110981972534590106669173198617172058218984579044473679472971734713519549564305089396376894916718984446198154034206362049389709060679599406155319531589921402069579269192467037126239504438254398942073772849551211636097464221526722062749286895900229512657262118641643833902637943392760890971516389031439865601733057598618588027836908312851360499741110772811971485706412830119750893120218213952360204941396266968281653191254266850873543942681094098066564553490053980504009295301848217080381530134760467638226499861818886773259063311487368789841407236600301391167030449162239297950820933232468917455010072
Decrypted message: I am Bhuvan

Enter a string: I am inevitable
Encrypted message: 3740860002502272613371974825796303370561422230534287438458762469373270633180240027211331807235783697554871856301864107413341099621470777763901298432952644887644663765366254451038262947528804171915853911745492169608796781152467454279694295270677105615888626811644435017455379628251541117299744076061600761536667050368467714213790404068944608098792360548700411845634972021539742519216056618316928228072417821762963397831177235157975029013181871402994282343186728671570275568390138463707384983871357888297656645147872694932737592826817409521596198297288126272119383724305742631803428223554759109301065217873159102235613
Decrypted message: I am inevitable
*/
