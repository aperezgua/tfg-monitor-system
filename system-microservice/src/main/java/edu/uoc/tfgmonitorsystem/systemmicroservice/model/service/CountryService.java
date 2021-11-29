package edu.uoc.tfgmonitorsystem.systemmicroservice.model.service;

import edu.uoc.tfgmonitorsystem.common.model.document.Country;
import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;
import edu.uoc.tfgmonitorsystem.common.model.repository.CountryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryService implements ICountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Override
    public List<Country> findAll() throws TfgMonitorSystenException {
        return countryRepository.findAll();
    }

}
