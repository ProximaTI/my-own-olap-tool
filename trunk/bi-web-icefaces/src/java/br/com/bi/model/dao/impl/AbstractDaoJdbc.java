/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.dao.impl;

import java.util.List;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author Luiz
 */
public abstract class AbstractDaoJdbc extends JdbcDaoSupport {

    protected String listToString(List<Integer> l) {
        StringBuilder fragment = new StringBuilder();

        for (Integer i : l) {
            fragment.append(i.toString()).append(",");
        }

        fragment.delete(fragment.length() - 1, fragment.length());

        return fragment.toString();
    }
}
