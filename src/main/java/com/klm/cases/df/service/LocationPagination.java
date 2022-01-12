package com.klm.cases.df.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.klm.cases.df.locations.Location;

public interface LocationPagination {
	
	public Page<Location> findPaginated(Pageable pageable,List<Location> locationsList);

}
