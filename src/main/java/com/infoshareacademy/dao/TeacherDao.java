package com.infoshareacademy.dao;

import com.infoshareacademy.model.Teacher;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class TeacherDao {
    
    @PersistenceContext
    private EntityManager entityManager;
    public String save(Teacher s) {
        entityManager.persist(s);
        return s.getPesel();
    }

    public Teacher update(Teacher s) {
        return entityManager.merge(s);
    }

    public void delete(String pesel) {
        final Teacher s = entityManager.find(Teacher.class, pesel);
        if (s != null) {
            entityManager.remove(s);
        }
    }

    public Teacher findById(String pesel) {
        return entityManager.find(Teacher.class, pesel);
    }

    public List<Teacher> findAll() {
        final Query query = entityManager.createQuery("SELECT s FROM Teacher s");

        return query.getResultList();
    }
}

