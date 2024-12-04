package com.fcaiSWE2.LMS_Services.DataLayer.Repositories;
import java.util.*;
import com.fcaiSWE2.LMS_Services.DataLayer.Models.Person;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import  java.util.*;
@Service
public class PersonBsl {
    private List<Person> personList;

    public PersonBsl() {
        personList = new ArrayList<>();
    }


    public void add(Person person)
    {
        for(Person person1 :personList)
        {
            if(person1.getId()==person.getId())
                return;
        }
        personList.add(person);
        System.out.println("===================OK===========");
    }
    public Person getPerosn(int id)
    {
        for(Person person1 :personList)
        {
            if(person1.getId()==id)
                return person1;
        }
       return null;
    }

    public List<Person> getPersonList() {
        return personList;
    }
}
