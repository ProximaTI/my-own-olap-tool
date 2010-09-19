package br.com.bi.model;


import br.com.bi.model.connection.OlapConnectionHolder;
import br.com.bi.model.entity.queries.Query;
import br.com.bi.model.persistence.queries.dao.QueryDAO;
import br.com.bi.model.translator.queries.QueryToMdxTranslator;

import java.io.PrintWriter;
import java.io.StringWriter;

import java.sql.SQLException;

import java.util.List;

import org.olap4j.CellSet;
import org.olap4j.OlapException;
import org.olap4j.OlapStatement;
import org.olap4j.query.RectangularCellSetFormatter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class QueryFacade {
    private OlapConnectionHolder connectionHolder;
    private static QueryFacade instance;

    private ApplicationContext context =
        new ClassPathXmlApplicationContext("/META-INF/beans.xml");

    private QueryFacade() {
        super();
    }

    public static QueryFacade getInstance() {
        if (instance == null)
            instance = new QueryFacade();
        return instance;
    }

    public List<Query> getQueries() {
        return getQueryDAO().findAll();
    }


    public String translateToMdx(Query query) {
        QueryToMdxTranslator translator = new QueryToMdxTranslator(query);
        return translator.translate();
    }

    /**
     * @return the connectionHolder
     */
    private OlapConnectionHolder getConnectionHolder() {
        if (connectionHolder == null) {
            connectionHolder = new OlapConnectionHolder();
        }
        return connectionHolder;
    }

    public String executeMdx(String mdx) throws OlapException, SQLException {
        OlapConnectionHolder holder = getConnectionHolder();
        OlapStatement st = holder.getOlapConnection().createStatement();
        CellSet cellSet = st.executeOlapQuery(mdx);

        RectangularCellSetFormatter formatter =
            new RectangularCellSetFormatter(false);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        formatter.format(cellSet, pw);

        cellSet.close();
        st.close();

        // TODO operar o cache de forma adequada
        holder.invalidateConnection();

        return sw.toString();
    }

    public void saveQuery(Query query) {
        if (query.isPersisted())
            getQueryDAO().update(query);
        else
            getQueryDAO().insert(query);
    }

    private QueryDAO getQueryDAO() {
        return (QueryDAO)context.getBean(QueryDAO.BEAN_NAME);
    }

    public void deleteQuery(Query query) {
        getQueryDAO().delete(query);
    }
}
