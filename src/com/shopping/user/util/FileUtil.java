package com.shopping.user.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
	/**
	 * Copy 文件
	 */
	public static void copyFile(String oldPath, String newPath){
		InputStream inStream =null;
		FileOutputStream fs =null;
		 try
         {
             int bytesum = 0;
             int byteread = 0;
             
             File oldFile = new File(oldPath);
             if (oldFile.exists() )
             { //文件存在时
            	 inStream = new FileInputStream(oldFile); //读入原文件
            	 fs= new FileOutputStream(newPath );
                 byte[] buffer = new byte[ 1444 ];
                 int length;
                 while ( ( byteread = inStream.read( buffer ) ) != -1 )
                 {
                     bytesum += byteread; //字节数 文件大小
                     System.out.println( bytesum );
                     fs.write( buffer, 0, byteread );
                 }
                 inStream.close();
             }
         }
         catch ( Exception e )
         {
             System.out.println( "复制单个文件操作出错" );
             e.printStackTrace();

         }finally{
        	 if(inStream != null){
        		 try {
					inStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	 }
        	 if(fs != null){
        		 try {
					fs.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	 }
         }
	}
	
	
	
	public static void main(String[] args){
		List list ;// new ArrayList();
		System.out.println();
	}
	
}
