package com.opendatapolicing.enus.search;  

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.CursorMarkParams;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.common.util.SimpleOrderedMap;

import com.opendatapolicing.enus.config.ConfigKeys;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.opendatapolicing.enus.wrap.Wrap;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;

/** 
 * Keyword: classSimpleNameSearchList
 */
public class SearchList<DEV> extends SearchListGen<DEV> {

	protected void _c(Wrap<Class<?>> c) {
		
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

	public Future<Boolean> next() {
		Promise<Boolean> promise = Promise.promise();
		try {
			Long start = Optional.ofNullable(getSolrDocumentList()).map(l -> l.getStart()).orElse(0L);
			Integer rows = Optional.ofNullable(getRows()).orElse(0);
			Long numFound = Optional.ofNullable(getSolrDocumentList()).map(l -> l.getNumFound()).orElse(0L);
			if(rows > 0 && (start + rows) < numFound) {
				setStart(start.intValue() + rows);
				String solrHostName = siteRequest_.getConfig().getString(ConfigKeys.SOLR_HOST_NAME);
				Integer solrPort = siteRequest_.getConfig().getInteger(ConfigKeys.SOLR_PORT);
				String solrCollection = siteRequest_.getConfig().getString(ConfigKeys.SOLR_COLLECTION);
				String solrRequestUri = String.format("/solr/%s/select%s", solrCollection, solrQuery.toQueryString());
				siteRequest_.getWebClient().get(solrPort, solrHostName, solrRequestUri).send().onSuccess(a -> {
					JsonObject json = a.bodyAsJsonObject();
					Map<String, Object> map = json.getMap();
					QueryResponse r = generateSolrQueryResponse(map);
					setQueryResponse(r);
					_solrDocumentList(solrDocumentListWrap);
					setSolrDocumentList(solrDocumentListWrap.o);
					list.clear();
					_list(list);

					promise.complete(true);
				}).onFailure(ex -> {
					LOG.error(String.format("indexTrafficStop failed. "), ex);
					promise.fail(ex);
				});
			} else {
				promise.complete(false);
			}
		} catch (Exception ex) {
			promise.fail(ex);
			LOG.error(String.format("indexTrafficStop failed. "), ex);
		}
		return promise.future();
	}

	protected void _queryResponse(Promise<QueryResponse> promise) {        
		try {
			if(this.c != null)
				solrQuery.addFilterQuery("classCanonicalNames_indexed_strings:" + ClientUtils.escapeQueryChars(this.c.getCanonicalName()));
			if(solrQuery.getQuery() != null) {
				String solrHostName = siteRequest_.getConfig().getString(ConfigKeys.SOLR_HOST_NAME);
				Integer solrPort = siteRequest_.getConfig().getInteger(ConfigKeys.SOLR_PORT);
				String solrCollection = siteRequest_.getConfig().getString(ConfigKeys.SOLR_COLLECTION);
				String solrRequestUri = String.format("/solr/%s/select%s", solrCollection, solrQuery.toQueryString() + "&suggest=true&terms=true&terms.fl=stopPurposeTitle_indexed_string");
				siteRequest_.getWebClient().get(solrPort, solrHostName, solrRequestUri).send().onSuccess(a -> {
					try {
						JsonObject json = a.bodyAsJsonObject();
						Map<String, Object> map = json.getMap();
						QueryResponse r = generateSolrQueryResponse(map);
						setQueryResponse(r);
						promise.complete(r);
					} catch(Exception ex) {
						LOG.error(String.format("Could not read response from Solr: http://%s:%s%s", solrHostName, solrPort, solrRequestUri), ex);
						promise.fail(ex);
					}
				}).onFailure(ex -> {
					LOG.error(String.format("indexTrafficStop failed. "), new RuntimeException(ex));
					promise.fail(ex);
				});
			} else {
				promise.complete();
			}
		} catch (Exception ex) {
			promise.fail(ex);
			LOG.error(String.format("indexTrafficStop failed. "), ex);
		}
	}

	public QueryResponse generateSolrQueryResponse(Map<String, Object> map) {
		NamedList<Object> l = new NamedList<>();
		SolrDocumentList response = new SolrDocumentList();
		ArrayList<NamedList<Object>> clusters = new ArrayList<>();
		Map<String, NamedList<Object>> suggestInfo = new HashMap<>();
		NamedList<NamedList<Object>> terms = new NamedList<NamedList<Object>>();
		NamedList<SolrDocumentList> moreLikeThis = new NamedList<SolrDocumentList>();

		// clusters //
		clusters.addAll(Optional.ofNullable((List<Map<String, Object>>)map.get("clusters")).orElse(Arrays.asList()).stream().map(m -> new NamedList<Object>(m)).collect(Collectors.toList()));

		// suggest //
		Optional.ofNullable((Map<String, Map<String, Object>>)map.get("suggest")).orElse(new HashMap<String, Map<String, Object>>()).forEach((key, value) -> {
			suggestInfo.put(key, new NamedList<Object>(value));
		});

		// terms //
		Optional.ofNullable((Map<String, List<Object>>)map.get("terms")).orElse(new HashMap<String, List<Object>>()).forEach((key, list) -> {
			NamedList<Object> namedList1 = new NamedList<>();

			for(Integer i = 0; i < list.size(); i+=2) {
				NamedList<Object> namedList2 = new NamedList<>();
				namedList1.add((String)list.get(i), list.get(i + 1));
			}
			terms.add(key, namedList1);
		});

		// response //
		Optional.ofNullable((LinkedHashMap<String, Object>)map.get("response")).ifPresent(m -> {
			response.setStart(((Integer)m.get("start")).longValue());
			response.setNumFound(((Integer)m.get("numFound")).longValue());
			response.setMaxScore((Float)m.get("maxScore"));
			((ArrayList<LinkedHashMap<String, Object>>)m.get("docs")).stream().forEach(doc -> {
				response.add(new SolrDocument(doc));
			});
		});

		// facet_counts //
		NamedList<Object> facetCounts = new NamedList<Object>();
		Map<String, ? extends Object> facetCountsJson = (Map<String, Object>)map.get("facet_counts");
		if(facetCountsJson != null) {

			// facet_fields //
			Optional.ofNullable(facetCountsJson.get("facet_fields")).ifPresent(facetFieldsJson -> {
				NamedList<Object> facetFields = new NamedList<Object>();
				((Map<String, List<Object>>)facetFieldsJson).forEach((key1, value1) -> {
					NamedList<Object> namedList1 = new NamedList<>();
					for(Integer i = 0; i < value1.size(); i+=2) {
						namedList1.add((String)value1.get(i), value1.get(i + 1));
					}
					facetFields.add(key1, namedList1);
				});
				facetCounts.add("facet_fields", facetFields);
			});

			// terms //
			Optional.ofNullable((Map<String, List<Object>>)map.get("terms")).orElse(new HashMap<String, List<Object>>()).forEach((key, list) -> {
			});

			// facet_queries //
			Optional.ofNullable(facetCountsJson.get("facet_queries")).ifPresent(facet_queries -> {
				facetCounts.add("facet_queries", new NamedList<Object>((Map<String, ? extends Object>)facet_queries));
			});

			// facet_ranges //
			Optional.ofNullable(facetCountsJson.get("facet_ranges")).ifPresent(facetRangesJson -> {
				NamedList<Object> facetRanges = new NamedList<Object>();
				facetCounts.add("facet_ranges", facetRanges);
				((Map<String, Map<String, Object>>)facetRangesJson).forEach((key1, value1) -> {
					NamedList<Object> namedList1 = new NamedList<>();
					facetRanges.add(key1, namedList1);
					List<Object> countsJson = (List<Object>)value1.get("counts");
					NamedList<Integer> counts = new NamedList<>();
					namedList1.add("counts", counts);
					Optional.ofNullable((String)value1.get("gap")).ifPresent(gap -> {
						namedList1.add("gap", gap);
					});
					Optional.ofNullable((String)value1.get("start")).ifPresent(start -> {
						namedList1.add("start", start);
					});
					Optional.ofNullable((String)value1.get("end")).ifPresent(end -> {
						namedList1.add("end", end);
					});
					for(Integer i = 0; i < countsJson.size(); i+=2) {
						counts.add((String)countsJson.get(i), (Integer)countsJson.get(i + 1));
					}
				});
			});

			// facet_pivot //
			Optional.ofNullable((Map<String, List<Map<String, Object>>>)facetCountsJson.get("facet_pivot")).ifPresent(pivotsItem1 -> {
				NamedList<Object> facetPivots = new NamedList<Object>();
				facetCounts.add("facet_pivot", facetPivots);
				pivotsItem1.forEach((key1, pivotsJson1) -> {
					List<NamedList<Object>> pivots1 = new ArrayList<>();
					facetPivots.add(key1, pivots1);
					for(Map<String, Object> pivotJson1 : pivotsJson1) {
						NamedList<Object> namedList1 = new NamedList<>();
						pivots1.add(namedList1);
						namedList1.add("field", pivotJson1.get("field"));
						namedList1.add("value", pivotJson1.get("value"));
						namedList1.add("count", pivotJson1.get("count"));
						List<NamedList<Object>> pivots2 = new ArrayList<>();
						namedList1.add("pivot", pivots2);
						Optional.ofNullable((List<Map<String, Object>>)pivotJson1.get("pivot")).ifPresent(pivotsJson2 -> {
							pivotsJson2.forEach(pivotJson2 -> {
								NamedList<Object> namedList2 = new NamedList<>();
								pivots2.add(namedList2);
								namedList2.add("field", pivotJson2.get("field"));
								namedList2.add("value", pivotJson2.get("value"));
								namedList2.add("count", pivotJson2.get("count"));
								Optional.ofNullable((List<Object>)pivotJson1.get("pivot")).ifPresent(pivotsJson3 -> {
									
								});
							});
							
						});
						Optional.ofNullable(pivotJson1.get("ranges")).ifPresent(facetRangesJson -> {
							NamedList<Object> facetRanges = new NamedList<Object>();
							namedList1.add("ranges", facetRanges);
							((Map<String, Map<String, Object>>)facetRangesJson).forEach((key2, value2) -> {
								NamedList<Object> namedList2 = new NamedList<>();
								facetRanges.add(key2, namedList2);
								List<Object> countsJson = (List<Object>)value2.get("counts");
								NamedList<Integer> counts = new NamedList<>();
								namedList2.add("counts", counts);
								Optional.ofNullable((String)value2.get("gap")).ifPresent(gap -> {
									namedList2.add("gap", gap);
								});
								Optional.ofNullable((String)value2.get("start")).ifPresent(start -> {
									namedList2.add("start", start);
								});
								Optional.ofNullable((String)value2.get("end")).ifPresent(end -> {
									namedList2.add("end", end);
								});
								for(Integer i = 0; i < countsJson.size(); i+=2) {
									counts.add((String)countsJson.get(i), (Integer)countsJson.get(i + 1));
								}
							});
						});
					}
				});
			});
			Optional.ofNullable(facetCountsJson.get("facet_intervals")).ifPresent(facet_intervals -> {
				facetCounts.add("facet_intervals", new NamedList<Object>((Map<String, ? extends Object>)facet_intervals));
			});
		}

		// facets //
		NamedList<Object> facets = new SimpleOrderedMap<Object>();
		Optional.ofNullable(((Map<String, ? extends Object>)map.get("facets"))).orElse(new HashMap<>()).forEach((key, value) -> {
			facets.add(key, value);
		});

		// Look for known things
		l.add("responseHeader", new NamedList<Object>(((Map<String, ? extends Object>)map.get("responseHeader"))));
		l.add("response", response);
		l.add("sort_values", new NamedList<Object>(((Map<String, ? extends Object>)map.get("sort_values"))));
		l.add("facet_counts", facetCounts);
		l.add("debug", new NamedList<Object>(((Map<String, ? extends Object>)map.get("debug"))));
		l.add("grouped", new NamedList<Object>(((Map<String, ? extends Object>)map.get("grouped"))));
		l.add("expanded", new NamedList<Object>(((Map<String, ? extends Object>)map.get("expanded"))));
		l.add("highlighting", new NamedList<Object>(((Map<String, ? extends Object>)map.get("highlighting"))));
		l.add("spellcheck", new NamedList<Object>(((Map<String, ? extends Object>)map.get("spellcheck"))));
		l.add("clusters", clusters);
		l.add("facets", facets);
		l.add("suggest", suggestInfo);
		l.add("stats", new NamedList<Object>(((Map<String, ? extends Object>)map.get("stats"))));
		l.add("terms", terms);
		l.add("moreLikeThis", moreLikeThis);
		l.add(CursorMarkParams.CURSOR_MARK_NEXT, (String)map.get(CursorMarkParams.CURSOR_MARK_NEXT));

		QueryResponse r = new QueryResponse(l, null);
		return r;
	}

	private void searchFacetPivot() {
		
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
						String classCanonicalName = (String)solrDocument.get("classCanonicalName_stored_string");
						DEV o = (DEV)Class.forName(classCanonicalName).newInstance();
						MethodUtils.invokeMethod(o, "setSiteRequest_", siteRequest_);
						if(populate)
							MethodUtils.invokeMethod(o, "populateForClass", solrDocument);
						if(store)
							MethodUtils.invokeMethod(o, "storeForClass", solrDocument);
						l.add(o);
					}
				} catch (InstantiationException | IllegalAccessException | NoSuchMethodException
						| InvocationTargetException | ClassNotFoundException e) {
					ExceptionUtils.rethrow(e);
				}
			}
		}
	}

	protected void _first(Wrap<Object> w) {
		if(list.size() > 0)
			w.o(list.get(0));
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
}
