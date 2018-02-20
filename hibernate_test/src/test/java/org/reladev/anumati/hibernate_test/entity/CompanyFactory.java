package org.reladev.anumati.hibernate_test.entity;


public class CompanyFactory extends BaseFactory<CompanyFactory, Company> {

    public static Company rawCompany(Long id) {
        return new Company(id);
    }

	public CompanyFactory() {
		super(new Company());
	}

	protected void ensureRequired() {
	}
}
