package com.erarge.opevaontology.service.impl;

import org.springframework.stereotype.Service;

import com.erarge.opevaontology.repository.CustomSPARQL;
import com.erarge.opevaontology.service.IService;

@Service
public class ServiceImpl implements IService {

    @Override
    public Object sparql(String query) {
        return CustomSPARQL.sparqlQueryExecution(query);
    }
}
