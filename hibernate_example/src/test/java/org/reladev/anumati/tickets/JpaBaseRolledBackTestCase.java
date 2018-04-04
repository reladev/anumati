package org.reladev.anumati.tickets;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public abstract class JpaBaseRolledBackTestCase {
    protected static EntityManagerFactory emf;

    protected EntityManager em;

    @BeforeClass
    public static void createEntityManagerFactory() {
        emf = Persistence.createEntityManagerFactory("test");
    }

    @AfterClass
    public static void closeEntityManagerFactory() {
        emf.close();
    }

    @Before
    public void beginTransaction() {
        //BaseFactory.clearCache();
        em = emf.createEntityManager();
        em.getTransaction().begin();
        //BaseFactory.setEntityManager(em);
    }

    @After
    public void rollbackTransaction() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }

        if (em.isOpen()) {
            em.close();
        }
    }
}