package com.opendatapolicing.enus.xml;

/**	
 *	Static methods for escaping XML. 
 **/
public class UtilXml {

	public static String escape(String str) {
		String o = str;
		
		if(str != null) { 
			o = o.replace("&", "&amp;").replace("<", "&#60;").replace(">", "&#62;");
		}
		
		return o;
	}

	public static String escapeInApostrophes(String str) {
		String o = str;
		
		if(str != null) { 
			o = o.replace("\n", " ").replace("\t", " ").replace("\r", "").replace("&", "&amp;").replace("<", "&#60;").replace(">", "&#62;").replace("'", "&apos;");
		}
		
		return o;
	}

	public static String escapeInQuotes(String str) {
		String o = str;
		
		if(str != null) { 
			o = o.replace("\n", " ").replace("\t", " ").replace("\r", "").replace("&", "&amp;").replace("<", "&#60;").replace(">", "&#62;").replace("\"", "&quot;");
		}
		
		return o;
	}

	public static String escapeAddQuotes(String str) {
		String o = str;
		
		if(str != null) { 
			o = "\"" + o.replace("\n", " ").replace("\t", " ").replace("\r", "").replace("&", "&amp;").replace("<", "&#60;").replace(">", "&#62;").replace("\"", "&quot;") + "\"";
		}
		
		return o;
	}
}