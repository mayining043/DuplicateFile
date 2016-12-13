package myn;

import java.io.*;
import java.util.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

public class DuplicateFile {
	protected int dcount=0,fcount=0;
	protected Map<String,Vector<File>> map=new HashMap<String,Vector<File>>();
	void run(File file){
		sum=0;cur=0;
		if (!file.isDirectory()){
			System.out.println("�����ʽ����,������һ��Ŀ¼�ļ�");
			PALETTE.textArea.append("�����ʽ����,������һ��Ŀ¼�ļ�\n");
			return;
		}
		else{
			System.out.println("ɨ����............");
			PALETTE.textArea.append("ɨ����............\n");
			dfssum(file);
			dfs(file);
			PALETTE.num.setText("����չʾ�ظ��ļ���");
			PALETTE.textArea.setText("");
			print();
			PALETTE.num.setText("���");
		}
		return;
	}
	public int sum=0;
	public int cur=0;
	public void dfssum(File f){
		if(!f.isDirectory())
				sum++;
		else{
				sum++;
				File[] childs = f.listFiles();
				for (int i=0;i<childs.length;i++){
					dfssum(childs[i]);
				}
			}
	}
	public void dfs(File f){
		if(!f.isDirectory()){
			 FileInputStream fis;
			 cur++;
			 PALETTE.progressBar.setValue(cur*100/sum);
			 PALETTE.num.setText((int)cur*100/sum+" %");
			 fcount++;
			try {
				fis = new FileInputStream(f.getAbsolutePath());
				 String md5 = DigestUtils.md5Hex(IOUtils.toByteArray(fis));
				 IOUtils.closeQuietly(fis);
				 if (map.containsKey(md5)){
					 System.out.println("�Ѵ���md5 "+md5+" ,���ڽ�һ���Ƚ�:");
					 PALETTE.textArea.append("�Ѵ���md5 "+md5+" ,���ڽ�һ���Ƚ�:\n"  );
					 Vector<File> vct = map.get(md5);
					 File exsit=vct.get(0);
					 if (compareFile(f,exsit)){
							 System.out.println("���ļ���ͬ: "+f.getAbsolutePath()+" == "+exsit.getAbsolutePath());
							 PALETTE.textArea.append("���ļ���ͬ: "+f.getAbsolutePath()+" == "+exsit.getAbsolutePath()  +"\n");
							 vct.addElement(f);
						 }
					 else
						 return;
					 }
				 else{
					 Vector<File> temp=new Vector<File>();
					 temp.add(f);
					 map.put( md5,temp);			 
					 System.out.println("��¼md5-�ļ� : "+md5+" s "+f.getName());	
					 PALETTE.textArea.append("��¼md5-�ļ� : "+md5+" s "+f.getName()+"\n");
				 }
				return;
			}catch(Exception e){}
			} 
			else{
				 cur++;
				 PALETTE.progressBar.setValue(cur*100/sum);
				 PALETTE.num.setText((int)cur*100/sum+" %");
				dcount++;
				File[] childs = f.listFiles();
				for (int i=0;i<childs.length;i++){
					dfs(childs[i]);
				}
			}
			
	}
	public void print(){
		System.out.println("-------------------------------------------");
		System.out.println("ɨ����� : ��ɨ��"+dcount+"��Ŀ¼�ļ�, "+fcount+"����Ŀ¼�ļ�");
		System.out.println("��ѯ���:");
		PALETTE.textArea.append("-------------------------------------------\n");
		PALETTE.textArea.append("ɨ����� : ��ɨ��"+dcount+"��Ŀ¼�ļ�, "+fcount+"����Ŀ¼�ļ�\n");
		PALETTE.textArea.append("��ѯ���:\n");
		int sum=0;
		for(Map.Entry<String, Vector<File>> f : map.entrySet()){
			if(f.getValue().size()>1){
				sum++;
			System.out.println("md5Ϊ:"+f.getKey()+"���ļ�����"+f.getValue().size()+"����չʾ���£�");
			PALETTE.textArea.append("md5Ϊ:"+f.getKey()+"���ļ�����"+f.getValue().size()+"����չʾ���£�\n");
			for(File o:f.getValue()){
				System.out.println(o.toString());
				PALETTE.textArea.append(o.toString()+"\n");
			}
			System.out.println("");
			PALETTE.textArea.append("\n");
			}
		}
		System.out.println("��ɨ�赽"+sum+"���ظ��ļ�");
		PALETTE.textArea.append("��ɨ�赽"+sum+"���ظ��ļ�"+
		"\n");
	}

	protected boolean compareFile(File  file1,File file2) {
        try{
        	BufferedInputStream inFile1 = new BufferedInputStream(new FileInputStream(file1));
            BufferedInputStream inFile2 = new BufferedInputStream(new FileInputStream(file2));
            //�Ƚ��ļ��ĳ����Ƿ�һ��
            if(inFile1.available() != inFile2.available()){
            	inFile1.close();
            	inFile2.close();
            	return false;
            }
          //�Ƚ��ļ��ľ��������Ƿ�һ��
            while(inFile1.read() != -1 && inFile2.read() != -1){
                if(inFile1.read() != inFile2.read()){
                	inFile1.close();
                	inFile2.close();
                    return false;
                }
            }
        	inFile1.close();
        	inFile2.close();
            return true; 
        }catch (FileNotFoundException e){
            e.printStackTrace();
            return false;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }
}
