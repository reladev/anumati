package org.reladev.anumati.hibernate_test.factory;


import org.reladev.anumati.hibernate_test.entity.Company;

public class CompanyFactory extends BaseFactory<CompanyFactory, Company> {

	public CompanyFactory() {
		super(new Company());
	}

	protected void ensureRequired() {
	}
}
