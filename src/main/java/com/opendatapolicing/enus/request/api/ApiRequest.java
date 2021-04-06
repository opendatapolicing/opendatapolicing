package com.opendatapolicing.enus.request.api;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.opendatapolicing.enus.wrap.Wrap;

/**
 * Keyword: classSimpleNameApiRequest
 */
public class ApiRequest extends ApiRequestGen<Object> {
	
	/**
	 * {@inheritDoc}
	 * Ignore: true
	 */
	protected void _siteRequest_(Wrap<SiteRequestEnUS> c) {}
	
	protected void _created(Wrap<ZonedDateTime> c) {
		c.o(ZonedDateTime.now());
	}

	protected void _rows(Wrap<Integer> c) {
	}

	protected void _numFound(Wrap<Long> c) {
	}

	protected void _numPATCH(Wrap<Long> c) {
		c.o(0L);
	}

	protected void _uuid(Wrap<String> c) {
		c.o(UUID.randomUUID().toString());
	}

	protected void _id(Wrap<String> c) {
		c.o("PATCH-" + uuid);
	}

	protected void _pk(Wrap<Long> c) {
	}

	protected void _original(Wrap<Object> c) {
	}

	protected void _pks(List<Long> c) {
	}

	protected void _classes(List<String> c) {
	}

	protected void _vars(List<String> c) {
	}
}