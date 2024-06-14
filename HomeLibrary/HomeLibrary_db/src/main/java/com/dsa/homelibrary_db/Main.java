/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.dsa.homelibrary_db;

import entities.Person;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author Krzysztof Dobosz
 */
public class Main {

    public static void main(String[] args) {
        Person person = new Person();
        person.setName("John");
        Main main = new Main();
        main.persistObject(person);
        main.findObject();
    }

    public void persistObject(Object object) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.DSA_HomeLibrary_db_jar_PU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public void findObject() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.DSA_HomeLibrary_db_jar_PU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            Query query = em.createQuery("SELECT p FROM Person p");
            List<Person> personList = query.getResultList();
            for (Person person : personList) {
                System.out.println("Found object: " + person.getName());
            }
        } catch (PersistenceException e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
}
