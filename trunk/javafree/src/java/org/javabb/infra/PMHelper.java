package org.javabb.infra;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javabb.transaction.PrivMsgTransaction;


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
public class PMHelper {

	private static final Log LOG = LogFactory.getLog(UserContext.class);
	
    private PrivMsgTransaction pmTrans = null;
    public void setPmTransaction(PrivMsgTransaction pmTrans) {
		this.pmTrans = pmTrans;
	}
    
    public int getCountMessages() throws Exception{
    	try {
			return pmTrans.countMsgByUser(UserContext.getContext().getUser());
		} catch (Exception e) {
			LOG.debug("Error at getCountMessages " + e.getMessage());
			return 0;
		}
    }
}
