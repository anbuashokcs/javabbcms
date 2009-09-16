/*
 * Copyright 09/05/2005 - <a href="http://www.liber.ufpe.br">Liber - UFPE</a>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.javabb.action;

import org.javabb.action.infra.BaseAction;
import org.javabb.lucene.index.Indexer;
import org.javabb.transaction.PostTransaction;

import com.opensymphony.xwork.Action;

/**
 *
 * @author <a href="mailto:jackganzha@dev.java.net">Marcos Pereira</a>
 *
 * @since 09/05/2005
 * @version $Id: LuceneIndexAction.java,v 1.1 2009/05/11 20:26:52 daltoncamargo Exp $
 *
 */
public class LuceneIndexAction extends BaseAction {

	private PostTransaction postTransaction;
	private Indexer indexer;
	
	public String createIndex() {
	
		String result = Action.ERROR;
		
		//List posts = postTransaction.
		
		return result;
		
	}
	
}
