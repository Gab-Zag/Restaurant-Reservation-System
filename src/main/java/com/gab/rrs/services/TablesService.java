package com.gab.rrs.services;

import com.gab.rrs.repository.TablesRepository;
import org.springframework.stereotype.Service;

@Service
public class TablesService {

    private TablesRepository tablesRepository;

    public TablesService(TablesRepository tablesRepository){
        this.tablesRepository = tablesRepository;
    }
}
