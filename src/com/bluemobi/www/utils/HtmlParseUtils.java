package com.bluemobi.www.utils;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HtmlParseUtils {
	
	public static Document getHtmlDocByFile(String filePath){
		File input = new File(filePath);
		Document doc = null;
		try {
			doc = Jsoup.parse(input, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}

}
