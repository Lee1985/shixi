package com.bluemobi.www.utils;

import java.util.Map;

import org.jsoup.Jsoup;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

public class JNATest {  
    //上层接口  
    //需要继承stdCallLibrary，一种协议，当然也可以直接继承Library上层接口，看对方的DLL文件的编写遵从哪一种协议， 这个是jna标准    
    //必须定义一个接口,将Dll文件的方法提取出来，注意类型的对应  
    public interface EncrypStrTest extends StdCallLibrary {  
          
         //加载动态链接库，把库dll文件默认放到系统C盘window目录下的system32文件夹下或者到java的bin目录    
        EncrypStrTest instance = (EncrypStrTest)Native.loadLibrary("wininet", EncrypStrTest.class,W32APIOptions.UNICODE_OPTIONS);  
        //定义接口，  
        public boolean InternetGetCookieExW(String lpszURL, String lpszCookieName,
        		char[] lpszCookieData,int lpdwSize,int word,Pointer pointer);//定义接口  
  
    }  
      
    //测试  
    public static void main(String[] args) {  
        /*try{  
            EncrypStrTest jnaDemo = EncrypStrTest.instance;       
            String cookie = "";
            //PointerByReference p = new PointerByReference();
            //Pointer p = Pointer.createConstant(23512);
            String memory = new String();
            char[] b = new char[2048];
            boolean resultString = jnaDemo.InternetGetCookieExW("http://www.qq.com", null, b, 512,8192,new Pointer(1000));
            System.out.println("会有结果么：" + resultString);  
            
            resultString = jnaDemo.InternetGetCookieExW("http://www.qq.com", null, b, 512,8192,new Pointer(1000));            
            System.out.println("会有结果么：" + resultString); 
        }catch(Exception e){    
            e.printStackTrace();    
        }    */
    	try{
    		Map<String,String> map = Jsoup.connect("http://www.qq.com").execute().cookies();
    		System.out.println(map);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    }
}
