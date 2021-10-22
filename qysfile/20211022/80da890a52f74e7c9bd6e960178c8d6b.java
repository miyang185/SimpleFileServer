package JinXianZhi;

import java.util.*;

public class JiSuanJiXian {
	public static void main(String[] args) {
	  System.out.println("请输入贴现率：");
	  @SuppressWarnings("resource")
	  Scanner input =new Scanner(System.in);
	  int x=input.nextInt();
	  double r = x * 0.01;
	  double s = 1.0;
	  double sum = 0.0;
	  double sum2=0.0;
	  double sum3=0.0;
	  double sum4=0.0;
	
	  int[] x1=new int[]{-100000,10000,10000,10000,20000,100000};
	  int[] x2=new int[]{-1000000,200000,200000,200000,200000,300000};
	  int[] x3=new int[]{-100000,30000,30000,30000,30000,30000};
	  int[] x4=new int[]{-120000,30000,30000,30000,30000,75000};
	 
	  for(int i=1;i<=5;i++) {
		  s=s/(1+r);
		  sum=sum+(x1[i]*s);
		  sum2=sum2+(x2[i]*s);
		  sum3=sum3+(x3[i]*s);
		  sum4=sum4+(x4[i]*s);
	  }
	  
	  sum = sum + x1[0];
	  sum2 = sum2 + x2[0];
	  sum3 = sum3 + x3[0];
	  sum4 = sum4 + x4[0];
	  System.out.print("项目1净现值为：");
	  System.out.print(String.format("%.2f",sum));
	  System.out.println("");
	  
	  System.out.print("项目2净现值为：");
	  System.out.print(String.format("%.2f",sum2));
	  System.out.println("");
	 
	  System.out.print("项目3净现值为：");
	  System.out.print(String.format("%.2f",sum3));
	  System.out.println("");
	  
	  System.out.print("项目4净现值为：");
	  System.out.print(String.format("%.2f",sum4));
	  System.out.println("");
	  
	}
}
