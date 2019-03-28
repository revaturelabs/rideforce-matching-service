package com.revature.rideshare.matching.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import com.revature.rideshare.matching.interfaces.ListFilter;

public class ListBuilder<T> {

	private List<T> list;
	private List<ListFilter<T>> filters;
	private Stack<Comparator<T>> sorters;

	public ListBuilder() {
		super();
		this.list = Collections.emptyList();
		setup();
	}

	public ListBuilder(List<T> list) {
		super();
		this.list = list;
		setup();
	}

	private void setup() {
		this.sorters = new Stack<>();
		this.filters = new ArrayList<>();
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
		if (list == null || list.isEmpty())
			return list;

		list.removeIf(e -> {
			for (ListFilter<T> f : filters)
				if (!f.filter(e))
					return false;
			return true;
		});

		while (!sorters.isEmpty()) {
			Collections.sort(this.list, sorters.pop());
		}

		return this.list;
	}

}
