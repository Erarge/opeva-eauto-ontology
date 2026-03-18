package com.erarge.opevaontology.service;

import java.util.List;
import java.util.Map;

import com.erarge.opevaontology.dto.FilterOptionsResponse;
import com.erarge.opevaontology.dto.FilteredQueryRequest;
import com.erarge.opevaontology.dto.TimeIntervalQueryRequest;
import com.erarge.opevaontology.dto.demo2.Demo2PowerResponseDTO;
import com.erarge.opevaontology.dto.demo3.Demo3DatasetResponse;
import com.erarge.opevaontology.dto.demo3.Demo3FilterOptionsResponse;
import com.erarge.opevaontology.dto.demo5.KpisResponse;
import com.erarge.opevaontology.dto.demo5.RouteSizeDistributionDTO;
import com.erarge.opevaontology.dto.demo5.RouteTimeWindowDTO;

public interface IService {
    public Object sparql(String query);

    public Object sparqlDemo5(String query);
    
    public List<Map<String, String>> queryByTimeInterval(TimeIntervalQueryRequest request);
    
    public List<Map<String, String>> queryFiltered(FilteredQueryRequest request);
    
    public FilterOptionsResponse getFilterOptions();

    Demo3FilterOptionsResponse getDemo3FilterOptions(String battery);

    Demo3DatasetResponse getDemo3Dataset(String battery, Double soc);

    Demo2PowerResponseDTO getDemo2PowerSeries();

    KpisResponse getKpis();
    List<RouteSizeDistributionDTO> getRouteMixSizeDistribution();
    List<RouteTimeWindowDTO> getRouteMixTimeWindow();

}
