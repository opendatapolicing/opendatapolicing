package com.opendatapolicing.enus.escape;


/**
 **/

public class EscapeTool {
	
	escapeXml(String str) {
		String result = str;
		
		if(str != null) { 
			result = result.replace("&", "&amp;").replace("<", "&#60;").replace(">", "&#62;");
		}
		
		return result;
	}

	escapeXmlInApostrophes(String str) {
		String result = str;
		
		if(str != null) { 
			result = result.replace("\n", " ").replace("\t", " ").replace("\r", "").replace("&", "&amp;").replace("<", "&#60;").replace(">", "&#62;").replace("'", "&apos;");
		}
		
		return result;
	}

	escapeXmlInQuotes(String str) {
		String result = str;
		
		if(str != null) { 
			result = result.replace("\n", " ").replace("\t", " ").replace("\r", "").replace("&", "&amp;").replace("<", "&#60;").replace(">", "&#62;").replace("\"", "&quot;");
		}
		
		return result;
	}

	escapeXmlAddQuotes(String str) {
		String result = str;
		
		if(str != null) { 
			result = "\"" + result.replace("\n", " ").replace("\t", " ").replace("\r", "").replace("&", "&amp;").replace("<", "&#60;").replace(">", "&#62;").replace("\"", "&quot;") + "\"";
		}
		
		return result;
	}
}