package 线程.java;
package 线程.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

public class MovieStone
{

public static void main(String[] args)
{
long begin=new Date().getTime();
InputStream in=null;
OutputStream out=null;
File dir=new File("D:\\export");
File[] listFiles = dir.listFiles();
for(int i=0;i<listFiles.length;i++)
{
try
{
in=new FileInputStream(listFiles[i]);
out=new FileOutputStream("D:\\新地方\\"+listFiles[i].getName());
byte[] b=new byte[1024];
int len=0;
while((len=in.read(b))!=-1)
{

out.write(b,0,len);
}
}
catch (FileNotFoundException e)
{
// TODO Auto-generated catch block
e.printStackTrace();
}
catch (IOException e)
{
// TODO Auto-generated catch block
e.printStackTrace();
}
finally
{
try
{
in.close();
out.close();
}
catch (IOException e)
{
// TODO Auto-generated catch block
e.printStackTrace();
}


public class 线程 {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根

	}

}
