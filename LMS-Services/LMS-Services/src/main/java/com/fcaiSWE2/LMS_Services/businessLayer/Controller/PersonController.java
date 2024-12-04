package com.fcaiSWE2.LMS_Services.businessLayer.Controller;

import com.fcaiSWE2.LMS_Services.DataLayer.Models.*;
import com.fcaiSWE2.LMS_Services.DataLayer.Repositories.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonController {
    private PersonBsl personBsl;
    @PostMapping(value = "/persons")
    public void addPerson(@RequestBody Person person)
    {
        personBsl.add(person);
    }
    @GetMapping(value = "/persons/{id}")
    public void getPerson(@PathVariable("id") int id)
    {
        personBsl.getPerosn(id);
    }

    public PersonController(PersonBsl personBsl) {
        this.personBsl = personBsl;
    }
}

