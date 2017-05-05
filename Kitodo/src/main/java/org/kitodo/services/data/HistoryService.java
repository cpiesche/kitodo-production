/*
 * (c) Kitodo. Key to digital objects e. V. <contact@kitodo.org>
 *
 * This file is part of the Kitodo project.
 *
 * It is licensed under GNU General Public License version 3 or later.
 *
 * For the full copyright and license information, please read the
 * GPL3-License.txt file that was distributed with this source code.
 */

package org.kitodo.services.data;

import com.sun.research.ws.wadl.HTTPMethods;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.kitodo.data.database.beans.History;
import org.kitodo.data.database.exceptions.DAOException;
import org.kitodo.data.database.persistence.HistoryDAO;
import org.kitodo.data.elasticsearch.exceptions.CustomResponseException;
import org.kitodo.data.elasticsearch.index.Indexer;
import org.kitodo.data.elasticsearch.index.type.HistoryType;
import org.kitodo.data.elasticsearch.search.Searcher;
import org.kitodo.services.ServiceManager;
import org.kitodo.services.data.base.SearchService;

/**
 * HistoryService.
 */
public class HistoryService extends SearchService<History> {

    private HistoryDAO historyDAO = new HistoryDAO();
    private HistoryType historyType = new HistoryType();
    private Indexer<History, HistoryType> indexer = new Indexer<>(History.class);
    private final ServiceManager serviceManager = new ServiceManager();
    private static final Logger logger = Logger.getLogger(HistoryService.class);

    /**
     * Constructor with searcher's assigning.
     */
    public HistoryService() {
        super(new Searcher(History.class));
    }

    /**
     * Method saves object to database and insert document to the index of
     * Elastic Search.
     *
     * @param history
     *            object
     */
    public void save(History history) throws CustomResponseException, DAOException, IOException {
        historyDAO.save(history);
        indexer.setMethod(HTTPMethods.PUT);
        indexer.performSingleRequest(history, historyType);
    }

    /**
     * Method saves history object to database.
     *
     * @param history
     *            object
     */
    public void saveToDatabase(History history) throws DAOException {
        historyDAO.save(history);
    }

    /**
     * Method saves history document to the index of Elastic Search.
     *
     * @param history
     *            object
     */
    public void saveToIndex(History history) throws CustomResponseException, IOException {
        indexer.setMethod(HTTPMethods.PUT);
        indexer.performSingleRequest(history, historyType);
    }

    /**
     * Method saves process related to modified history.
     *
     * @param history
     *            object
     */
    protected void saveDependenciesToIndex(History history) throws CustomResponseException, IOException {
        //TODO: is it possible that  process is modified during save to history?
        serviceManager.getProcessService().saveToIndex(history.getProcess());
    }

    public History find(Integer id) throws DAOException {
        return historyDAO.find(id);
    }

    public List<History> findAll() throws DAOException {
        return historyDAO.findAll();
    }

    /**
     * Search History objects by given query.
     *
     * @param query
     *            as String
     * @return list of History objects
     */
    public List<History> search(String query) throws DAOException {
        return historyDAO.search(query);
    }

    /**
     * Method removes object from database and document from the index of
     * Elastic Search.
     *
     * @param history
     *            object
     */
    public void remove(History history) throws CustomResponseException, DAOException, IOException {
        historyDAO.remove(history);
        indexer.setMethod(HTTPMethods.DELETE);
        indexer.performSingleRequest(history, historyType);
    }

    /**
     * Method removes object from database and document from the index of
     * Elastic Search.
     *
     * @param id
     *            of object
     */
    public void remove(Integer id) throws CustomResponseException, DAOException, IOException {
        historyDAO.remove(id);
        indexer.setMethod(HTTPMethods.DELETE);
        indexer.performSingleRequest(id);
    }

    /**
     * Method adds all object found in database to Elastic Search index.
     */
    public void addAllObjectsToIndex() throws CustomResponseException, DAOException, InterruptedException, IOException {
        indexer.setMethod(HTTPMethods.PUT);
        indexer.performMultipleRequests(findAll(), historyType);
    }
}
