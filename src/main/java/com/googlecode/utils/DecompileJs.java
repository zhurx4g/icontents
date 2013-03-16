package com.googlecode.utils;

import java.io.IOException;

public class DecompileJs {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static String decompileJs(String content) throws IOException {
		
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
		return buffer.toString();
	}

}
