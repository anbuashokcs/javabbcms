package org.javabb.vo;

import java.io.Serializable;

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

/**
 * @author Lucas Teixeira - <a href="mailto:lucas@javabb.org">lucas@javabb.org </a> <br>
 */

public class PrivMsgSent extends PrivMsg implements Serializable {
    
    public PrivMsgSent() {
        
    }
    
    public PrivMsgSent(PrivMsg p) {
    	super(p);
    }
    
}
