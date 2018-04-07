package com.cr.gankio.data.database;

import java.util.LinkedList;
import java.util.List;

/**
 * @author RUI CAI
 * @date 2018/4/6
 */
public class GankNewsList {
	private Boolean error;
	private List<GankNews> results;

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	public List<GankNews> getResults() {
		return results;
	}

	public void setResults(List<GankNews> results) {
		this.results = results;
	}
}
