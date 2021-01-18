package com.opendatapolicing.enus.escape;


/**
 **/

public class EscapeTool {

	public static String escapeXml(String str) {
		String result = str;
		
		if(str != null) { 
			result = result.replace("&", "&amp;").replace("<", "&#60;").replace(">", "&#62;");
		}
		
		return result;
	}

	public static String escapeXmlInApostrophes(String str) {
		String result = str;
		
		if(str != null) { 
			result = result.replace("\n", " ").replace("\t", " ").replace("\r", "").replace("&", "&amp;").replace("<", "&#60;").replace(">", "&#62;").replace("'", "&apos;");
		}
		
		return result;
	}

	public static String escapeXmlInQuotes(String str) {
		String result = str;
		
		if(str != null) { 
			result = result.replace("\n", " ").replace("\t", " ").replace("\r", "").replace("&", "&amp;").replace("<", "&#60;").replace(">", "&#62;").replace("\"", "&quot;");
		}
		
		return result;
	}

	public static String escapeXmlAddQuotes(String str) {
		String result = str;
		
		if(str != null) { 
			result = "\"" + result.replace("\n", " ").replace("\t", " ").replace("\r", "").replace("&", "&amp;").replace("<", "&#60;").replace(">", "&#62;").replace("\"", "&quot;") + "\"";
		}
		
		return result;
	}
}