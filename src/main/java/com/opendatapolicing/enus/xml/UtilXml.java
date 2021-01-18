package com.opendatapolicing.enus.xml;

/**	
 *	Static methods for escaping XML. 
 **/
public class UtilXml {
	
	escape(String str) {
		String o = str;
		
		if(str != null) { 
			o = o.replace("&", "&amp;").replace("<", "&#60;").replace(">", "&#62;");
		}
		
		return o;
	}

	escapeInApostrophes(String str) {
		String o = str;
		
		if(str != null) { 
			o = o.replace("\n", " ").replace("\t", " ").replace("\r", "").replace("&", "&amp;").replace("<", "&#60;").replace(">", "&#62;").replace("'", "&apos;");
		}
		
		return o;
	}

	escapeInQuotes(String str) {
		String o = str;
		
		if(str != null) { 
			o = o.replace("\n", " ").replace("\t", " ").replace("\r", "").replace("&", "&amp;").replace("<", "&#60;").replace(">", "&#62;").replace("\"", "&quot;");
		}
		
		return o;
	}

	escapeAddQuotes(String str) {
		String o = str;
		
		if(str != null) { 
			o = "\"" + o.replace("\n", " ").replace("\t", " ").replace("\r", "").replace("&", "&amp;").replace("<", "&#60;").replace(">", "&#62;").replace("\"", "&quot;") + "\"";
		}
		
		return o;
	}
}