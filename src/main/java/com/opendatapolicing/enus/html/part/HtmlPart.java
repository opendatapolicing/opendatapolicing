package com.opendatapolicing.enus.html.part; 

import java.util.List;

import com.opendatapolicing.enus.cluster.Cluster;
import com.opendatapolicing.enus.wrap.Wrap;

/**      
 * Model: true
 * Api: true
 * Indexed: true
 * Saved: true
 * 
 * ApiTag.enUS: Html Part
 * ApiUri.enUS: /api/html-part
 * 
 * ApiMethod: POST
 * 
 * ApiMethod.enUS: PUTImport
 * ApiMethod.enUS: PUTMerge
 * ApiMethod.enUS: PUTCopy

 * ApiMethod: PATCH
 * ApiMethod: GET
 * ApiMethod.enUS: Search
 * 
 * ApiMethod.enUS: SearchPage
 * PageSearchPage.enUS: HtmlPartPage
 * PageSuperSearchPage.enUS: ClusterPage
 * ApiUriSearchPage.enUS: /html-part
 * 
 * AName.enUS:an HTML part
 * Color: khaki
 * IconGroup: regular
 * IconName: puzzle-piece
 * NameVar.enUS: html-part
 * Sort.asc: sort1
 * Sort.asc: sort2
 * Sort.asc: sort3
 * Sort.asc: sort4
 * Sort.asc: sort5
 * Sort.asc: sort6
 * Sort.asc: sort7
 * Sort.asc: sort8
 * Sort.asc: sort9
 * Sort.asc: sort10
 * Rows: 300
 * 
 * Role.enUS: SiteAdmin
 * 
*/    
public class HtmlPart extends HtmlPartGen<Cluster> {

	/**
	 * {@inheritDoc}
	 * Var.enUS: htmlPartKey
	 * Indexed: true
	 * Stored: true
	 * Description.enUS: The primary key of the season in the database. 
	 * DisplayName.enUS: key
	 */        
	protected void _htmlPartKey(Wrap<Long> c) {
		c.o(pk);
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: pageDesignKeys
	 * Indexed: true
	 * Stored: true
	 * Attribute: PageDesign.htmlPartKeys
	 * HtmlRow: 3
	 * HtmlCell: 1
	 * DisplayName.enUS: page designs
	*/                                      
	protected void _pageDesignKeys(List<Long> l) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: htmlLink
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: link
	 * Define: true
	 * HtmlRow: 3
	 * HtmlCell: 3
	 */                     
	protected void _htmlLink(Wrap<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: htmlElement
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: HTML element
	 * Define: true
	 * HtmlRow: 4
	 * HtmlCell: 1
	 */               
	protected void _htmlElement(Wrap<String> c) {
		if(htmlLink != null)
			c.o("a");
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: htmlId
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: HTML ID
	 * Define: true
	 * HtmlRow: 4
	 * HtmlCell: 2
	 */               
	protected void _htmlId(Wrap<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: htmlClasses
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: HTML classes
	 * Define: true
	 * HtmlRow: 4
	 * HtmlCell: 3
	 */               
	protected void _htmlClasses(Wrap<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: htmlStyle
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: HTML style
	 * Define: true
	 * HtmlRow: 4
	 * HtmlCell: 4
	 */              
	protected void _htmlStyle(Wrap<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: htmlBefore
	 * Stored: true
	 * DisplayName.enUS: HTML before
	 * Define: true
	 * HtmlRow: 5
	 * HtmlCell: 1
	 * Multiline: true
	 */               
	protected void _htmlBefore(Wrap<String> c) {
		if(htmlElement != null) {
			String id = htmlId == null ? "" : String.format(" id=\"%s\"", htmlId);
			String classes = htmlClasses == null ? "" : String.format(" class=\"%s\"", htmlClasses);
			String style = htmlStyle == null ? "" : String.format(" style=\"%s\"", htmlStyle);
			c.o(String.format("<%s%s%s%s>", htmlElement, id, classes, style));
		}
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: htmlAfter
	 * Stored: true
	 * DisplayName.enUS: HTML after
	 * Define: true
	 * HtmlRow: 6
	 * HtmlCell: 1
	 * Multiline: true
	 */               
	protected void _htmlAfter(Wrap<String> c) {
		if(htmlElement != null) {
			c.o(String.format("<%s>", htmlElement));
		}
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: htmlText
	 * Stored: true
	 * DisplayName.enUS: text
	 * Define: true
	 * Multiline: true
	 * HtmlRow: 7
	 * HtmlCell: 2
	 */             
	protected void _htmlText(Wrap<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: htmlVar
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: var
	 * Define: true
	 * HtmlRow: 8
	 * HtmlCell: 1
	 */               
	protected void _htmlVar(Wrap<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: htmlVarSpan
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: var span
	 * Define: true
	 * HtmlRow: 8
	 * HtmlCell: 2
	 */               
	protected void _htmlVarSpan(Wrap<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: htmlVarForm
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: var form
	 * Define: true
	 * HtmlRow: 9
	 * HtmlCell: 1
	 */            
	protected void _htmlVarForm(Wrap<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: htmlVarInput
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: var input
	 * Define: true
	 * HtmlRow: 9
	 * HtmlCell: 2
	 */            
	protected void _htmlVarInput(Wrap<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: htmlVarForEach
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: var for each
	 * Define: true
	 * HtmlRow: 10
	 * HtmlCell: 1
	 */             
	protected void _htmlVarForEach(Wrap<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: htmlVarHtml
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: var html
	 * Define: true
	 * HtmlRow: 10
	 * HtmlCell: 2
	 */            
	protected void _htmlVarHtml(Wrap<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: htmlVarBase64Decode
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: var base64 decode
	 * Define: true
	 * HtmlRow: 11
	 * HtmlCell: 1
	 */             
	protected void _htmlVarBase64Decode(Wrap<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: htmlExclude
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: HTML exclude
	 * Define: true
	 * HtmlRow: 12
	 * HtmlCell: 1
	 */         
	protected void _htmlExclude(Wrap<Boolean> c) {
		c.o(false);
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: pdfExclude
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: PDF exclude
	 * Define: true
	 * HtmlRow: 12
	 * HtmlCell: 2
	 */            
	protected void _pdfExclude(Wrap<Boolean> c) {
		c.o(false);
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: loginLogout
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: login/logout
	 * Define: true
	 * HtmlRow: 12
	 * HtmlCell: 3
	 */            
	protected void _loginLogout(Wrap<Boolean> c) {
		c.o(false);
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: sort1
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: sort1
	 * Define: true
	 * HtmlRow: 13
	 * HtmlCell: 1
	 */               
	protected void _sort1(Wrap<Double> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: sort2
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: sort2
	 * Define: true
	 * HtmlRow: 13
	 * HtmlCell: 2
	 */               
	protected void _sort2(Wrap<Double> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: sort3
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: sort3
	 * Define: true
	 * HtmlRow: 13
	 * HtmlCell: 3
	 */               
	protected void _sort3(Wrap<Double> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: sort4
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: sort4
	 * Define: true
	 * HtmlRow: 13
	 * HtmlCell: 4
	 */               
	protected void _sort4(Wrap<Double> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: sort5
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: sort5
	 * Define: true
	 * HtmlRow: 13
	 * HtmlCell: 5
	 */               
	protected void _sort5(Wrap<Double> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: sort6
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: sort6
	 * Define: true
	 * HtmlRow: 14
	 * HtmlCell: 6
	 */              
	protected void _sort6(Wrap<Double> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: sort7
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: sort7
	 * Define: true
	 * HtmlRow: 14
	 * HtmlCell: 7
	 */               
	protected void _sort7(Wrap<Double> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: sort8
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: sort8
	 * Define: true
	 * HtmlRow: 14
	 * HtmlCell: 8
	 */               
	protected void _sort8(Wrap<Double> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: sort9
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: sort9
	 * Define: true
	 * HtmlRow: 14
	 * HtmlCell: 9
	 */               
	protected void _sort9(Wrap<Double> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: sort10
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: sort10
	 * Define: true
	 * HtmlRow: 14
	 * HtmlCell: 10
	 */              
	protected void _sort10(Wrap<Double> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: _objectTitle
	 */ 
	@Override()
	protected void  _objectTitle(Wrap<String> c) {
		StringBuilder b = new StringBuilder();
		if(htmlBefore != null)
			b.append(htmlBefore);

		if(htmlVarForEach != null)
			b.append("for each {").append(htmlVarForEach).append("}").append(" into {").append(htmlVar).append("}");
		else {
			if(htmlVarSpan != null)
				b.append("<span>").append(htmlVarSpan).append("</span>");
			else if(htmlVar != null)
				b.append("{").append(htmlVar).append("}");
			else if(htmlVarHtml != null)
				b.append("{").append(htmlVarHtml).append("}");
		}

		if(htmlVarInput != null)
			b.append("[").append(htmlVarInput).append("]");
		if(htmlVarForm != null)
			b.append("[[").append(htmlVarForm).append("]]");
		if(loginLogout)
			b.append("[ login ] / [ logout ]");
		if(htmlText != null)
			b.append(htmlText);
		if(htmlAfter != null)
			b.append(htmlAfter);
		if(b.length() == 0)
			b.append(pk);
		c.o(b.toString());
	}

	@Override()
	protected void  _objectId(Wrap<String> c) {
		if(pk != null){
			c.o(pk.toString());
		}
	}
}
