package com.klm.cases.df.service;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;

import com.klm.cases.df.locations.Location;

/*
 * This class will help us in creating pagination while displaying the list 
 */
@Service
public class LocationPaginationImpl implements LocationPagination {

	public Page<Location> findPaginated(Pageable pageable, List<Location> locationsList) {
		List<Location> books = locationsList;
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<Location> list;

		if (books.size() < startItem) {
			list = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, books.size());
			list = books.subList(startItem, toIndex);
		}

		Page<Location> bookPage = new PageImpl<Location>(list, PageRequest.of(currentPage, pageSize), books.size());

		return bookPage;
	}

}
