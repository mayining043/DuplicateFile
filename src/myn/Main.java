package myn;

import java.io.*;
import java.util.*;

public class Main {
	public static void main(String[] args) {
		while(true){
		System.out.println("������Ҫ����ϵͳ�ļ�Ŀ¼��");
		Scanner in=new Scanner(System.in);
		DuplicateFile temp = new DuplicateFile();
		File check=new File(in.nextLine());
		temp.run(check);	
		}
	}
}
