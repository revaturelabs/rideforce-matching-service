package com.revature.rideshare.matching.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import com.revature.rideshare.matching.interfaces.ListFilter;

/**
 * TODO: Javadoc
 * 
 * @author Sanford
 *
 */
public class ListBuilder<T> {

	private List<T> list;
	private List<ListFilter<T>> filters;
	private Stack<Comparator<T>> sorters;

	public ListBuilder() {
		super();
		this.list = Collections.emptyList();
		this.sorters = new Stack<>();
	}

	public ListBuilder(List<T> list) {
		super();
		this.list = list;
	}

	public ListBuilder<T> addFilter(ListFilter<T> filter) {
		filters.add(filter);
		return this;
	}

	public ListBuilder<T> addComparator(Comparator<T> comparator) {
		this.sorters.push(comparator);
		return this;
	}

	public List<T> build() {
		if (!list.isEmpty()) {
			list = list.stream().filter(e -> {
				boolean val = true;
				for (ListFilter<T> f : filters) {
					val = val && f.filter(e);
				}
				return val;
			}).collect(Collectors.toList());
		}
		while (!sorters.isEmpty()) {
			Collections.sort(this.list, sorters.pop());
		}
		return this.list;
	}

}
