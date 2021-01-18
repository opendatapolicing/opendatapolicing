package org.southerncoalition.enus.search;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.southerncoalition.enus.request.SiteRequestEnUS;
import org.southerncoalition.enus.user.SiteUser;
import org.southerncoalition.enus.wrap.Wrap;

/** 
 * Keyword: classSimpleNameSearchList
 */
public class SearchList<DEV> extends SearchListGen<DEV> {

	protected void _c(Wrap<Class<DEV>> c) {
		
	}

	/**
	 * {@inheritDoc}
	 * Ignore: true
	 */
	protected void _siteRequest_(Wrap<SiteRequestEnUS> c) {
	}

	protected void _store(Wrap<Boolean> c) {
		c.o(false);
	}

	protected void _populate(Wrap<Boolean> c) {
		c.o(false);
	}

	protected void _fields(List<String> c) {
	}

	protected void _solrQuery(SolrQuery o) {
	}

	public DEV get(Integer index) {
		return list.get(index);
	}

	public boolean next(String dt) {
		boolean next = false;
		Long numFound = Optional.ofNullable(getSolrDocumentList()).map(l -> l.getNumFound()).orElse(0L);
		if(numFound > 0) {
			try {
				setQueryResponse(siteRequest_.getSiteContext_().getSolrClient().query(solrQuery));
			} catch (SolrServerException | IOException e) {
				ExceptionUtils.rethrow(e);
			}
			_solrDocumentList(solrDocumentListWrap);
			setSolrDocumentList(solrDocumentListWrap.o);
			list.clear();
			_list(list);
			next = true;
		}
		return next;
	}

	public boolean next() {
		boolean next = false;
		Long start = Optional.ofNullable(getSolrDocumentList()).map(l -> l.getStart()).orElse(0L);
		Integer rows = Optional.ofNullable(getRows()).orElse(0);
		Long numFound = Optional.ofNullable(getSolrDocumentList()).map(l -> l.getNumFound()).orElse(0L);
		if(rows > 0 && (start + rows) < numFound) {
			try {
				setStart(start.intValue() + rows);
				setQueryResponse(siteRequest_.getSiteContext_().getSolrClient().query(solrQuery));
			} catch (SolrServerException | IOException e) {
				ExceptionUtils.rethrow(e);
			}
			_solrDocumentList(solrDocumentListWrap);
			setSolrDocumentList(solrDocumentListWrap.o);
			list.clear();
			_list(list);
			next = true;
		}
		return next;
	}

	protected void _queryResponse(Wrap<QueryResponse> c) {
		if(this.c != null)
			solrQuery.addFilterQuery("classCanonicalNames_indexed_strings:" + ClientUtils.escapeQueryChars(this.c.getCanonicalName()));
		SiteUser siteUser = siteRequest_.getSiteUser();
		if(siteUser == null || !siteUser.getSeeDeleted())
			solrQuery.addFilterQuery("deleted_indexed_boolean:false");
		if(siteUser == null || !siteUser.getSeeArchived())
			solrQuery.addFilterQuery("archived_indexed_boolean:false");
		if(solrQuery.getQuery() != null) {
			try {
				QueryResponse o = siteRequest_.getSiteContext_().getSolrClient().query(solrQuery);
				c.o(o);
			} catch (SolrServerException | IOException e) {
				ExceptionUtils.rethrow(e);
			}
		}
	}

	protected void _solrDocumentList(Wrap<SolrDocumentList> c) {
		if(solrQuery.getQuery() != null) {
			SolrDocumentList o = queryResponse.getResults();
			c.o(o);
		}
	}

	protected void _list(List<DEV> l) {
		if(solrQuery.getQuery() != null) {
			for(SolrDocument solrDocument : solrDocumentList) {
				try {
					if(solrDocument != null) {
						DEV o = c.newInstance();
						MethodUtils.invokeMethod(o, "setSiteRequest_", siteRequest_);
						if(populate)
							MethodUtils.invokeMethod(o, "populateForClass", solrDocument);
						if(store)
							MethodUtils.invokeMethod(o, "storeForClass", solrDocument);
		//				MethodUtils.invokeMethod(o, "initDeepForClass", siteRequest_);
						l.add(o);
					}
				} catch (InstantiationException | IllegalAccessException | NoSuchMethodException
						| InvocationTargetException e) {
					ExceptionUtils.rethrow(e);
				}
			}
		}
	}

	public DEV first() {
		if(list.size() > 0)
			return list.get(0);
		else
			return null;
	}

	public int size() {
		return list.size();
	}

	public SolrQuery setTerms(boolean b) {
		return solrQuery.setTerms(b);
	}

	public boolean getTerms() {
		return solrQuery.getTerms();
	}

	public SolrQuery addField(String field) {
		return solrQuery.addField(field);
	}

	public SolrQuery addTermsField(String field) {
		return solrQuery.addTermsField(field);
	}

	public String[] getTermsFields() {
		return solrQuery.getTermsFields();
	}

	public SolrQuery setTermsLower(String lower) {
		return solrQuery.setTermsLower(lower);
	}

	public String getTermsLower() {
		return solrQuery.getTermsLower();
	}

	public SolrQuery setTermsUpper(String upper) {
		return solrQuery.setTermsUpper(upper);
	}

	public String getTermsUpper() {
		return solrQuery.getTermsUpper();
	}

	public SolrQuery setTermsUpperInclusive(boolean b) {
		return solrQuery.setTermsUpperInclusive(b);
	}

	public boolean getTermsUpperInclusive() {
		return solrQuery.getTermsUpperInclusive();
	}

	public SolrQuery setTermsLowerInclusive(boolean b) {
		return solrQuery.setTermsLowerInclusive(b);
	}

	public boolean getTermsLowerInclusive() {
		return solrQuery.getTermsLowerInclusive();
	}

	public SolrQuery setTermsLimit(int limit) {
		return solrQuery.setTermsLimit(limit);
	}

	public int getTermsLimit() {
		return solrQuery.getTermsLimit();
	}

	public SolrQuery setTermsMinCount(int cnt) {
		return solrQuery.setTermsMinCount(cnt);
	}

	public int getTermsMinCount() {
		return solrQuery.getTermsMinCount();
	}

	public SolrQuery setTermsMaxCount(int cnt) {
		return solrQuery.setTermsMaxCount(cnt);
	}

	public int getTermsMaxCount() {
		return solrQuery.getTermsMaxCount();
	}

	public SolrQuery setTermsPrefix(String prefix) {
		return solrQuery.setTermsPrefix(prefix);
	}

	public String getTermsPrefix() {
		return solrQuery.getTermsPrefix();
	}

	public SolrQuery setTermsRaw(boolean b) {
		return solrQuery.setTermsRaw(b);
	}

	public boolean getTermsRaw() {
		return solrQuery.getTermsRaw();
	}

	public SolrQuery setTermsSortString(String type) {
		return solrQuery.setTermsSortString(type);
	}

	public String getTermsSortString() {
		return solrQuery.getTermsSortString();
	}

	public SolrQuery setTermsRegex(String regex) {
		return solrQuery.setTermsRegex(regex);
	}

	public String getTermsRegex() {
		return solrQuery.getTermsRegex();
	}

	public SolrQuery setTermsRegexFlag(String flag) {
		return solrQuery.setTermsRegexFlag(flag);
	}

	public String[] getTermsRegexFlags() {
		return solrQuery.getTermsRegexFlags();
	}

	public SolrQuery addFacetField(String...fields) {
		return solrQuery.addFacetField(fields);
	}

	public SolrQuery addFacetPivotField(String...fields) {
		return solrQuery.addFacetPivotField(fields);
	}

	public SolrQuery addNumericRangeFacet(String field, Number start, Number end, Number gap) {
		return solrQuery.addNumericRangeFacet(field, start, end, gap);
	}

	public SolrQuery addDateRangeFacet(String field, Date start, Date end, String gap) {
		return solrQuery.addDateRangeFacet(field, start, end, gap);
	}

	public SolrQuery addIntervalFacets(String field, String[] intervals) {
		return solrQuery.addIntervalFacets(field, intervals);
	}

	public String[] removeIntervalFacets(String field) {
		return solrQuery.removeIntervalFacets(field);
	}

	public String[] getFacetFields() {
		return solrQuery.getFacetFields();
	}

	public boolean removeFacetField(String name) {
		return solrQuery.removeFacetField(name);
	}

	public SolrQuery setFacet(boolean b) {
		return solrQuery.setFacet(b);
	}

	public SolrQuery setFacetPrefix(String prefix) {
		return solrQuery.setFacetPrefix(prefix);
	}

	public SolrQuery setFacetPrefix(String field, String prefix) {
		return solrQuery.setFacetPrefix(field, prefix);
	}

	public SolrQuery addFacetQuery(String f) {
		return solrQuery.addFacetQuery(f);
	}

	public String[] getFacetQuery() {
		return solrQuery.getFacetQuery();
	}

	public boolean removeFacetQuery(String q) {
		return solrQuery.removeFacetQuery(q);
	}

	public SolrQuery setFacetLimit(int lim) {
		return solrQuery.setFacetLimit(lim);
	}

	public int getFacetLimit() {
		return solrQuery.getFacetLimit();
	}

	public SolrQuery setFacetMinCount(int cnt) {
		return solrQuery.setFacetMinCount(cnt);
	}

	public int getFacetMinCount() {
		return solrQuery.getFacetMinCount();
	}

	public SolrQuery setFacetMissing(Boolean v) {
		return solrQuery.setFacetMissing(v);
	}

	public String getFacetSortString() {
		return solrQuery.getFacetSortString();
	}

	public SolrQuery setFacetSort(String sort) {
		return solrQuery.setFacetSort(sort);
	}

	public SolrQuery addHighlightField(String f) {
		return solrQuery.addHighlightField(f);
	}

	public boolean removeHighlightField(String f) {
		return solrQuery.removeHighlightField(f);
	}

	public String[] getHighlightFields() {
		return solrQuery.getHighlightFields();
	}

	public SolrQuery setHighlightSnippets(int num) {
		return solrQuery.setHighlightSnippets(num);
	}

	public int getHighlightSnippets() {
		return solrQuery.getHighlightSnippets();
	}

	public SolrQuery setHighlightFragsize(int num) {
		return solrQuery.setHighlightFragsize(num);
	}

	public int getHighlightFragsize() {
		return solrQuery.getHighlightFragsize();
	}

	public SolrQuery setHighlightRequireFieldMatch(boolean flag) {
		return solrQuery.setHighlightRequireFieldMatch(flag);
	}

	public boolean getHighlightRequireFieldMatch() {
		return solrQuery.getHighlightRequireFieldMatch();
	}

	public SolrQuery setHighlightSimplePre(String f) {
		return solrQuery.setHighlightSimplePre(f);
	}

	public String getHighlightSimplePre() {
		return solrQuery.getHighlightSimplePre();
	}

	public SolrQuery setHighlightSimplePost(String f) {
		return solrQuery.setHighlightSimplePost(f);
	}

	public String getHighlightSimplePost() {
		return solrQuery.getHighlightSimplePost();
	}

	public String getSortField() {
		return solrQuery.getSortField();
	}

	public SolrQuery clearSorts() {
		return solrQuery.clearSorts();
	}

	public SolrQuery setSorts(List<SortClause> value) {
		return solrQuery.setSorts(value);
	}

	public List<SortClause> getSorts() {
		return solrQuery.getSorts();
	}

	public SolrQuery setSort(String field, ORDER order) {
		return solrQuery.setSort(field, order);
	}

	public SolrQuery setSort(SortClause sortClause) {
		return solrQuery.setSort(sortClause);
	}

	public SolrQuery addSort(String field, ORDER order) {
		return solrQuery.addSort(field, order);
	}

	public SolrQuery addSort(SortClause sortClause) {
		return solrQuery.addSort(sortClause);
	}

	public SolrQuery addOrUpdateSort(String field, ORDER order) {
		return solrQuery.addOrUpdateSort(field, order);
	}

	public SolrQuery addOrUpdateSort(SortClause sortClause) {
		return solrQuery.addOrUpdateSort(sortClause);
	}

	public SolrQuery removeSort(SortClause sortClause) {
		return solrQuery.removeSort(sortClause);
	}

	public SolrQuery removeSort(String itemName) {
		return solrQuery.removeSort(itemName);
	}

	public void  setGetFieldStatistics(boolean v) {
		solrQuery.setGetFieldStatistics(v);
	}

	public void  setGetFieldStatistics(String field) {
		solrQuery.setGetFieldStatistics(field);
	}

	public void  addGetFieldStatistics(String...field) {
		solrQuery.addGetFieldStatistics(field);
	}

	public void  addStatsFieldFacets(String field, String...facets) {
		solrQuery.addStatsFieldFacets(field, facets);
	}

	public void  addStatsFieldCalcDistinct(String field, boolean calcDistinct) {
		solrQuery.addStatsFieldCalcDistinct(field, calcDistinct);
	}

	public SolrQuery setFilterQueries(String...fq) {
		return solrQuery.setFilterQueries(fq);
	}

	public SolrQuery addFilterQuery(String...fq) {
		return solrQuery.addFilterQuery(fq);
	}

	public boolean removeFilterQuery(String fq) {
		return solrQuery.removeFilterQuery(fq);
	}

	public String[] getFilterQueries() {
		return solrQuery.getFilterQueries();
	}

	public boolean getHighlight() {
		return solrQuery.getHighlight();
	}

	public SolrQuery setHighlight(boolean b) {
		return solrQuery.setHighlight(b);
	}

	public SolrQuery addMoreLikeThisField(String field) {
		return solrQuery.addMoreLikeThisField(field);
	}

	public SolrQuery setMoreLikeThisFields(String...fields) {
		return solrQuery.setMoreLikeThisFields(fields);
	}

	public String[] getMoreLikeThisFields() {
		return solrQuery.getMoreLikeThisFields();
	}

	public SolrQuery setMoreLikeThisMinTermFreq(int mintf) {
		return solrQuery.setMoreLikeThisMinTermFreq(mintf);
	}

	public int getMoreLikeThisMinTermFreq() {
		return solrQuery.getMoreLikeThisMinTermFreq();
	}

	public SolrQuery setMoreLikeThisMinDocFreq(int mindf) {
		return solrQuery.setMoreLikeThisMinDocFreq(mindf);
	}

	public int getMoreLikeThisMinDocFreq() {
		return solrQuery.getMoreLikeThisMinDocFreq();
	}

	public SolrQuery setMoreLikeThisMinWordLen(int minwl) {
		return solrQuery.setMoreLikeThisMinWordLen(minwl);
	}

	public int getMoreLikeThisMinWordLen() {
		return solrQuery.getMoreLikeThisMinWordLen();
	}

	public SolrQuery setMoreLikeThisMaxWordLen(int maxwl) {
		return solrQuery.setMoreLikeThisMaxWordLen(maxwl);
	}

	public int getMoreLikeThisMaxWordLen() {
		return solrQuery.getMoreLikeThisMaxWordLen();
	}

	public SolrQuery setMoreLikeThisMaxQueryTerms(int maxqt) {
		return solrQuery.setMoreLikeThisMaxQueryTerms(maxqt);
	}

	public int getMoreLikeThisMaxQueryTerms() {
		return solrQuery.getMoreLikeThisMaxQueryTerms();
	}

	public SolrQuery setMoreLikeThisMaxTokensParsed(int maxntp) {
		return solrQuery.setMoreLikeThisMaxTokensParsed(maxntp);
	}

	public int getMoreLikeThisMaxTokensParsed() {
		return solrQuery.getMoreLikeThisMaxTokensParsed();	
	}

	public SolrQuery setMoreLikeThisBoost(boolean b) {
		return solrQuery.setMoreLikeThisBoost(b);
	}

	public boolean getMoreLikeThisBoost() {
		return solrQuery.getMoreLikeThisBoost();
	}

	public SolrQuery setMoreLikeThisQF(String qf) {
		return solrQuery.setMoreLikeThisQF(qf);
	}

	public String getMoreLikeThisQF() {
		return solrQuery.getMoreLikeThisQF();
	}

	public SolrQuery setMoreLikeThisCount(int count) {
		return solrQuery.setMoreLikeThisCount(count);
	}

	public int getMoreLikeThisCount() {
		return solrQuery.getMoreLikeThisCount();
	}

	public SolrQuery setMoreLikeThis(boolean b) {
		return solrQuery.setMoreLikeThis(b);
	}

	public boolean getMoreLikeThis() {
		return solrQuery.getMoreLikeThis();
	}

	public SolrQuery setIncludeScore(boolean includeScore) {
		return solrQuery.setIncludeScore(includeScore);
	}

	public SolrQuery setQuery(String query) {
		return solrQuery.setQuery(query);
	}

	public String getQuery() {
		return solrQuery.getQuery();
	}

	public SolrQuery setRows(Integer rows) {
		return solrQuery.setRows(rows);
	}

	public Integer getRows() {
		return solrQuery.getRows();
	}

	public SolrQuery setShowDebugInfo(boolean showDebugInfo) {
		return solrQuery.setShowDebugInfo(showDebugInfo);
	}

	public void  set(String name, boolean val) {
		solrQuery.set(name, val);
	}

	public void  set(String name, int val) {
		solrQuery.set(name, val);
	}

	public void  set(String name, String vals) {
		solrQuery.set(name, vals);
	}

	public void  add(String name, String...vals) {
		solrQuery.add(name, vals);
	}

	public void  setDistrib(boolean val) {
		solrQuery.setDistrib(val);
	}

	public SolrQuery setStart(Integer start) {
		return solrQuery.setStart(start);
	}

	public Integer getStart() {
		return solrQuery.getStart();
	}

	public SolrQuery setRequestHandler(String qt) {
		return solrQuery.setRequestHandler(qt);
	}

	public String getRequestHandler() {
		return solrQuery.getRequestHandler();
	}

	public SolrQuery setParam(String name, String...values) {
		return solrQuery.setParam(name, values);
	}

	public SolrQuery setParam(String name, boolean value) {
		return solrQuery.setParam(name, value);
	}

	public SolrQuery getCopy() {
		return solrQuery.getCopy();
	}

	public SolrQuery setTimeAllowed(Integer milliseconds) {
		return solrQuery.setTimeAllowed(milliseconds);
	}

	public Integer getTimeAllowed() {
		return solrQuery.getTimeAllowed();
	}

	@Override()
	public String toString() {
		StringBuilder sb = new StringBuilder();
//		sb.append("ListeRecherche { ");
//		list.stream().forEach(o -> {
//			sb.append(o);
//		});
//		sb.append(" }");
//		return sb.toString();
		
		try {
			sb.append(URLDecoder.decode(solrQuery.toString(), "UTF-8")).append("\n");
		} catch (UnsupportedEncodingException e) {
			ExceptionUtils.rethrow(e);
		}
		Long numFound = Optional.ofNullable(getQueryResponse()).map(QueryResponse::getResults).map(SolrDocumentList::getNumFound).orElse(null);
		if(numFound != null)
			sb.append("numFound: ").append(numFound).append("\n");
		return sb.toString();
	}