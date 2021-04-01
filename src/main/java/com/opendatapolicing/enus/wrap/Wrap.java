package com.opendatapolicing.enus.wrap;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Keyword: classSimpleNameWrap
 **/ 

public class Wrap<DEV> implements Serializable {

	public String var;

	public Wrap<DEV> var(String o) {
		var = o;
		return this;
	}

	public DEV o;

	public Wrap<DEV> o(DEV o) {
		this.o = o;
		return this;
	}

	public Class<?> c;

	public Wrap<DEV> c(Class<?> o) {
		c = o;
		return this;
	}

	public Object p;

	public Wrap<DEV> p(Object o) {
		p = o;
		return this;
	}

	public Class<?> cChild;

	public Wrap<DEV> cChild(Class<?> o) {
		cChild = o;
		return this;
	}

	public Boolean alreadyInitialized = false;

	public Wrap<DEV> alreadyInitialized(Boolean o) {
		alreadyInitialized = o;
		return this;
	}

	public Object siteRequestObject;

	public Wrap<DEV> siteRequestObject(Object o) {
		siteRequestObject = o;
		return this;
	}

	public ArrayList<String> pageVars = new ArrayList<String>();

	public void  pageVarsAdd(String...pageVars) {
		for(String pageVar : pageVars)
			this.pageVars.add(pageVar);
	}
}


