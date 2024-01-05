import java.util.*;

public class TokenBucket{
    public static void main(String args[]){

        Scanner in = new Scanner(System.in);

        int tokensInBucket=0;//total tokens in the bucket
        int tokenSent;

        System.out.println("Enter the bucket capacity");
        int bucketCapacity=in.nextInt();
        System.out.println("Enter the Token generation rate (Rate at which tokens are sent to the bucket)");
        int tokenRate=in.nextInt();
        System.out.println("Enter the number of requests");
        int n=in.nextInt();

        int[] requests= new int[n];

        System.out.println("Enter the number of packets sent per request: ");
        for(int i=0; i<n; i++){
            System.out.printf("Request %d: ", i+1);
            requests[i]=in.nextInt();
        }

        System.out.printf("%s\t\t%s\t%s\t%s", "Time", "Tokens Sent", "Packets/Req", "Tokens Remaining in bucket");

        for(int i=0;i<n;i++){

          int prev=tokensInBucket;
          tokensInBucket=Math.min(tokensInBucket+ tokenRate, bucketCapacity);

          tokenSent=tokensInBucket-prev;

          try{
            Thread.sleep(1000);
        }catch(Exception E){
            E.printStackTrace();
        }

            if(tokensInBucket>=requests[i]){
                tokensInBucket-=requests[i];
                System.out.printf("%d\t\t%d\t\t%d\t\t%d\t", i + 1,tokenSent, requests[i], tokensInBucket);
            }else{
                 System.out.printf("%d\t\t%d\t\t%d\t\t%d\t", i + 1,tokenSent, requests[i], tokensInBucket);
                System.out.println("More tokens requested than present!Waiting for the bucket to replenish...");
            }
            
        }
    }
}
