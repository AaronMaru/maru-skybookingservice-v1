package com.skybooking.stakeholderservice.v1_0_0.service.implement.country;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.country.CountryEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.country.CountryRP;
import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.country.CountrySV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.country.CountryRS;
import com.skybooking.stakeholderservice.v1_0_0.util.header.HeaderBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class CountryIP implements CountrySV {

    @Autowired
    private CountryRP countryRP;

    @Autowired
    private HeaderBean headerBean;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * list country'name include english and locale name.
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return list of country
     */
    @Override
    @Transactional
    public List<CountryRS> getItems() {

        List<CountryEntity> countries = countryRP.findAllCountry();
        List<CountryRS> countriesRS = new ArrayList<>();

        String selectedLocalization = headerBean.getLocalization(null);

        countries.stream().forEach(country -> {
            var countryLocaleList = country.getCountryLocaleEntities();
            var defaultLocale = countryLocaleList
                    .stream()
                    .filter(countryLocaleEntity -> countryLocaleEntity.getLocale().getLocale().equalsIgnoreCase("en"))
                    .findFirst()
                    .orElse(null);
            var filterLocale = countryLocaleList
                    .stream()
                    .filter(countryLocaleEntity -> countryLocaleEntity.getLocale().getLocale().equalsIgnoreCase(selectedLocalization))
                    .findFirst()
                    .orElse(defaultLocale);

            var countryRS = new CountryRS();
            countryRS.setId(country.getId());
            countryRS.setName(defaultLocale.getName());
            countryRS.setNameLocale(filterLocale.getName());

            countriesRS.add(countryRS);

        });

        return countriesRS;
    }
}
