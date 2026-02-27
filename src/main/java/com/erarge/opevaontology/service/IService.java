package com.erarge.opevaontology.service;

import java.util.List;
import java.util.Map;

import com.erarge.opevaontology.dto.FilterOptionsResponse;
import com.erarge.opevaontology.dto.FilteredQueryRequest;
import com.erarge.opevaontology.dto.TimeIntervalQueryRequest;

public interface IService {
    public Object sparql(String query);

    public Object sparqlDemo5(String query);
    
    public List<Map<String, String>> queryByTimeInterval(TimeIntervalQueryRequest request);
    
    public List<Map<String, String>> queryFiltered(FilteredQueryRequest request);
    
    public FilterOptionsResponse getFilterOptions();
}
