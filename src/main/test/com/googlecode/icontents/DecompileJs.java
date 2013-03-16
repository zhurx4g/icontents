package com.googlecode.icontents;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.io.FileUtils;

public class DecompileJs {
	private static ArrayList<Integer> list = new ArrayList<Integer>();

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String content = FileUtils.readFileToString(new File("miniui_raw.js"));
		
		int count=0;
		int INDEX = 1056200;
		
		StringBuffer buffer = new StringBuffer();
		boolean isInQuote = false;
		for(int i=0;i<content.length();i++){
			char ch = content.charAt(i);
			
			buffer.append(ch);
			if(ch=='"'){
				if(isInQuote){
					if(content.charAt(i-1)!='\\'){
						isInQuote=!isInQuote;
					}
				}else{
					isInQuote = true;
				}
			}
			if(ch==';'&&!isInQuote){
				if(count<INDEX){
					if((content.charAt(i+1)!='\\'&&content.charAt(i+1)!='"'&&content.charAt(i-1)!='"')){
						buffer.append('\n');
					}
				}
			}
			count++;			
		}
		FileUtils.writeStringToFile(new File("miniui.js"), buffer.toString(), "utf-8");
	}

}
