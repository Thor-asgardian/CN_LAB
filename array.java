import java.util.*;

public class sort1{
 public static void main(String[] args){
 List<int[]> frame=new ArrayList<>();
 System.out.println("Enter no. of frames:");
 Scanner sc=new Scanner(System.in);
 int n=sc.nextInt(); 
 for(int i=0;i<n;i++){
  Random random=new Random();
  int seqNum=random.nextInt(1000)+1;
  System.out.printf("Enter data for %dth frame>>",i+1);
  int data=sc.nextInt();
  frame.add(new int []{seqNum,data});
 }
 System.out.println("\n\nBefore Sorting>>a");
 for(int[] i : frame){
  System.out.printf("seqNum->%d,Data->%d\n",i[0] , i[1]);
 }
 frame=sortFrame(frame);
 System.out.println("\n\n After sorting>>");
 for(int[] i : frame){
 System.out.printf("seqNum->%d,Data->%d\n",i[0] , i[1]);
 }
 }
 
 public static List<int []>sortFrame(List<int[] >frame){
  Collections.sort(frame,(a , b) -> Integer.compare(a[0] , b[0]));
  return frame;
  }
 }
/*
Enter no. of frames:
5
Enter data for 1th frame>>1
Enter data for 2th frame>>2
Enter data for 3th frame>>3
Enter data for 4th frame>>4
Enter data for 5th frame>>5
Before Sorting>>
seqNum->624,Data->1
seqNum->751,Data->2
seqNum->418,Data->3
seqNum->48,Data->4
seqNum->964,Data->5After sorting>>
seqNum->48,Data->4
seqNum->418,Data->3seqNum->624,Data->1seqNum->751,Data->2
seqNum->964,Data->5
*/
