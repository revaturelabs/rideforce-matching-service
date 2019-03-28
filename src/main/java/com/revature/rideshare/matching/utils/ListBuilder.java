package com.revature.rideshare.matching.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;
import java.util.function.Predicate;

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

	public void setList(List<T> list) {
		this.list = list;
	}

	public ListBuilder<T> add(T e) {
		list.add(e);
		return this;
	}

	public ListBuilder<T> add(int index, T element) {
		this.list.add(index, element);
		return this;
	}

	public ListBuilder<T> add(Collection<? extends T> c) {
		this.list.addAll(c);
		return this;
	}

	public ListBuilder<T> add(int index, Collection<? extends T> c) {
		this.list.addAll(index, c);
		return this;
	}

	public ListBuilder<T> remove(int index) {
		this.list.remove(index);
		return this;
	}

	public ListBuilder<T> remove(Object o) {
		this.list.remove(o);
		return this;
	}

	public ListBuilder<T> removeIf(Predicate<? super T> filter) {
		this.list.removeIf(filter);
		return this;
	}

	public ListBuilder<T> remove(Collection<?> c) {
		this.list.removeAll(c);
		return this;
	}

	/**
	 * Adds a ListFilter to be used in filtering the elements of the list and
	 * removing them.
	 * 
	 * @param filter
	 * @return the {@link ListBuilder} to be operated upon again
	 */
	public ListBuilder<T> addFilter(ListFilter<T> filter) {
		filters.add(filter);
		return this;
	}

	/**
	 * Adds a comparator to be used to sort the elements in the list the first one
	 * gets the highest priority in terms of order the list
	 * 
	 * @param comparator
	 * @return the {@link ListBuilder} to be operated upon again
	 */
	public ListBuilder<T> addComparator(Comparator<T> comparator) {
		sorters.push(comparator);
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
			list.sort(sorters.pop());
		}

		try {
			return list;
		} finally {
			list = Collections.emptyList();
			filters.clear();
			sorters.clear();
		}
	}

}
