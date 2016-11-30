package com.bluemobi.www.utils;

public class VideoUtil {

	private static String width = "420";
	private static String height = "340";
	
	public static String embedToVideo(String str){
		System.out.println("str start----->    "+str);
		while(str!=null && str.indexOf("<embed") != -1){
			str = embedChangeVideo(str);
		}
		System.out.println("str end----->      "+str);
		return str;
	}
	
	private static String embedChangeVideo(String str){
		String url = "";
		String embedfront = str.substring(0, str.indexOf("<embed"));
		str = str.substring(str.indexOf("<embed"),str.length());
		System.out.println("str  ---------->"+str);
		String embed = str.substring(0, str.indexOf(">")+1);
		System.out.println("embed  ------->"+embed);
		String embedback = str.substring(str.indexOf(">")+1,str.length());
		url = embed.substring(embed.indexOf("src=\"")+5);
		url = url.substring(0,url.indexOf("\""));
		System.out.println("video   ----->"+embedfront+getVideo(url)+embedback);
		return embedfront+getVideo(url)+embedback;
	}
	
	private static String getVideo(String url){
		String video = "<video width=\""+width+"\" height=\""+height+"\" controls=\"controls\">"
				+"<source src=\"/i/movie.ogg\" type=\"video/ogg\">"
				+"<source src=\""+url+"\" type=\"video/mp4\" id=\"videoVideos\"></video>";
		return video;
	}
}
