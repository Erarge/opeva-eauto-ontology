package com.erarge.opevaontology.service;

import java.util.List;
import java.util.Map;

import com.erarge.opevaontology.dto.FilterOptionsResponse;
import com.erarge.opevaontology.dto.FilteredQueryRequest;
import com.erarge.opevaontology.dto.TimeIntervalQueryRequest;
import com.erarge.opevaontology.dto.demo1.Demo1BalancingResponseDTO;
import com.erarge.opevaontology.dto.demo2.Demo2PowerResponseDTO;
import com.erarge.opevaontology.dto.demo3.Demo3DatasetResponse;
import com.erarge.opevaontology.dto.demo3.Demo3FilterOptionsResponse;
import com.erarge.opevaontology.dto.demo4.Demo4EnergyRateDTO;
import com.erarge.opevaontology.dto.demo5.KpisResponse;
import com.erarge.opevaontology.dto.demo5.RouteSizeDistributionDTO;
import com.erarge.opevaontology.dto.demo5.RouteTimeWindowDTO;
import com.erarge.opevaontology.dto.demo9.Demo9ActivePowerResponseDTO;

public interface IService {
    public Object sparql(String query);

    public Object sparqlDemo5(String query);

    public List<Map<String, String>> queryByTimeInterval(TimeIntervalQueryRequest request);

    public List<Map<String, String>> queryFiltered(FilteredQueryRequest request);

    public FilterOptionsResponse getFilterOptions();

    Demo1BalancingResponseDTO getDemo1BalancingSeries();

    Demo2PowerResponseDTO getDemo2PowerSeries();

    Demo3FilterOptionsResponse getDemo3FilterOptions(String battery);

    Demo3DatasetResponse getDemo3Dataset(String battery, Double soc);

    KpisResponse getKpis();

    List<RouteSizeDistributionDTO> getRouteMixSizeDistribution();

    List<RouteTimeWindowDTO> getRouteMixTimeWindow();

    List<Demo4EnergyRateDTO> getDemo4EnergyRate();

    Demo9ActivePowerResponseDTO getDemo9ActivePowerSeries();

}
