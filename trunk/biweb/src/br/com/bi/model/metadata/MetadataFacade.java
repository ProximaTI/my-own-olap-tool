/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.metadata;


import br.com.bi.model.connection.OlapConnectionHolder;
import br.com.bi.model.entity.queries.Query;
import br.com.bi.model.translator.queries.QueryToMdxTranslator;

import java.io.PrintWriter;
import java.io.StringWriter;

import java.sql.SQLException;

import org.olap4j.CellSet;
import org.olap4j.OlapConnection;
import org.olap4j.OlapException;
import org.olap4j.OlapStatement;
import org.olap4j.metadata.Cube;
import org.olap4j.metadata.Dimension;
import org.olap4j.metadata.Hierarchy;
import org.olap4j.metadata.Level;
import org.olap4j.metadata.Measure;
import org.olap4j.metadata.NamedList;
import org.olap4j.query.RectangularCellSetFormatter;


/**
 *
 * @author Luiz Augusto
 */
public class MetadataFacade {

    private OlapConnectionHolder connectionHolder;
    private static MetadataFacade instance;

    private MetadataFacade() {
    }

    public static MetadataFacade getInstance() {
        if (instance == null) {
            instance = new MetadataFacade();
        }
        return instance;
    }

    public NamedList<Cube> findAllCubes() throws OlapException {
        return getOlapConnection().getSchema().getCubes();
    }

    public Cube findCubeByName(String cubeName) throws OlapException {
        return getOlapConnection().getSchema().getCubes().get(cubeName);
    }

    public NamedList<Dimension> findDimensionsByCube(String cubeName) throws OlapException {
        if (findCubeByName(cubeName) != null) {
            findCubeByName(cubeName).getDimensions();
        }

        return null;
    }

    public NamedList<Measure> findMeasuresByCube(String cubeName) throws OlapException {
        if (findCubeByName(cubeName) != null) {
            findCubeByName(cubeName).getMeasures();
        }

        return null;
    }

    public NamedList<Hierarchy> findHierarchiesByCubeDimension(String cubeName,
                                                               String dimensionName) throws OlapException {
        if (findCubeByName(cubeName) != null &&
            findDimensionsByCube(cubeName).get(dimensionName) != null) {
            findDimensionsByCube(cubeName).get(dimensionName).getHierarchies();
        }

        return null;
    }

    public NamedList<Level> findLevelByCubeDimensionHierarchy(String cubeName,
                                                              String dimensionName,
                                                              String hierarchyName) throws OlapException {
        if (findCubeByName(cubeName) != null &&
            findDimensionsByCube(cubeName).get(dimensionName) != null &&
            findHierarchiesByCubeDimension(cubeName,
                                           dimensionName).get(hierarchyName) !=
            null) {
            findHierarchiesByCubeDimension(cubeName,
                                           dimensionName).get(hierarchyName).getLevels();
        }

        return null;
    }

    private OlapConnection getOlapConnection() {
        return getConnectionHolder().getOlapConnection();
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
}
