package org.javabb.action;

import java.util.ArrayList;
import java.util.List;

import org.javabb.action.infra.BaseAction;
import org.javabb.transaction.SmileTransaction;
import org.javabb.vo.Smile;

/*
 * Copyright 2004 JavaFree.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* $Id: SmileAction.java,v 1.1 2009/05/11 20:26:52 daltoncamargo Exp $ */
/**
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org </a> <br>
 * @author Ronald Tetsuo Miura
 */
public class SmileAction extends BaseAction {

    private SmileTransaction _smileTransaction;

    private Smile _smile = new Smile();
    private List _smiles = new ArrayList();

    private String _emoticon = "";
    private String _symbol = "";
    private String _filename = "";

    // ####################################################################
    // Parameters
    // ####################################################################

    /**
     * @param filename
     */
    public void setFilename(String filename) {
        this._filename = filename;
    }

    /**
     * @param symbol
     */
    public void setSymbol(String symbol) {
        this._symbol = symbol;
    }

    /**
     * @param emotion
     */
    public void setEmoticon(String emotion) {
        this._emoticon = emotion;
    }

    // ####################################################################
    // Dependencies
    // ####################################################################

    /**
     * @param smileTransaction
     */
    public void setSmileTransaction(SmileTransaction smileTransaction) {
        this._smileTransaction = smileTransaction;
    }

    // ####################################################################
    // Actions
    // ####################################################################

    /**
     * @return result
     * @throws Exception
     */
    public String listSmiles() throws Exception {
        _smiles = _smileTransaction.listAll();
        return SUCCESS;
    }

    /**
     * @return result
     * @throws Exception
     */
    public String loadSmile() throws Exception {
        _smile = _smileTransaction.getSmile(_smileId);
        return SUCCESS;
    }

    /**
     * @return result
     * @throws Exception
     */
    public String removeSmile() throws Exception {
        _smileTransaction.delete(_smileId);
        return SUCCESS;
    }

    /**
     * @return result
     * @throws Exception
     */
    public String updateSmile() throws Exception {
        _smileTransaction.updateSmile(_smileId, _emoticon, _symbol, _filename);
        return SUCCESS;
    }

    /**
     * @return result
     * @throws Exception
     */
    public String addSmile() throws Exception {
        _smileTransaction.addSmile(_emoticon, _symbol, _filename);
        return SUCCESS;
    }

    // ####################################################################
    // View objects accessors
    // ####################################################################

    /**
     * @return smile
     */
    public Smile getSmile() {
        return _smile;
    }

    /**
     * @return Returns the badWords.
     */
    public List getSmiles() {
        return _smiles;
    }

}