package org.reladev.anumati.tickets.service;

import javax.inject.Inject;

import org.reladev.anumati.SecurityContext;
import org.reladev.anumati.tickets.dto.CompanyDto;
import org.reladev.anumati.tickets.entity.Company;
import org.reladev.anumati.tickets.repository.CompanyRepository;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    @Inject
    CompanyRepository companyRepository;

    public CompanyDto convert(Company company) {
        CompanyDto dto = new CompanyDto();
        dto.copyTo(company);

        return dto;
    }

    public Company updateCreate(CompanyDto companyDto) {
        SecurityContext.assertSuperAdmin();

        Company company;
        if (companyDto.getId() != null) {
            company = companyRepository.get(companyDto.getId());
        } else {
            company = new Company();
        }
        companyDto.copyTo(company);

        companyRepository.save(company);

        return company;
    }
}
