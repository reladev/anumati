package org.reladev.anumati.tickets.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/v1/company")
public class CompanyController {

    public CompanyController() {
        System.out.println("CompanyController");
    }

    @RequestMapping(method = GET)
    public String get() {
        return "Welcome to company";
    }

    @RequestMapping(method = POST)
    public String create(@RequestBody Tester tester) {
        return "Welcome to company:" + tester.foo;
    }

    public static class Tester {
        public String foo;
    }

}
