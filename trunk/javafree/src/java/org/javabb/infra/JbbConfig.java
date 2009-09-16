package org.javabb.infra;

import org.javabb.vh.ForumConfig;
import org.javabb.vo.User;

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
 * $Id: JbbConfig.java,v 1.1 2009/05/11 20:27:01 daltoncamargo Exp $
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org </a> <br>
 * @author Ronald Tetsuo Miura
 */
public class JbbConfig {

    private static final JbbConfig INSTANCE = new JbbConfig();

    private User lastUserRegistered = new User();

    private int numberOfUsers;

    private int totalMessageCount;

    /**
     * @return config
     */
    public static JbbConfig getConfig() {
        return INSTANCE;
    }

    /**
     * Retorna o total de mensagens no fórum
     * @return total
     */
    public int getTotalMessages() {
        return totalMessageCount;
    }

    /**
     * Retorna o total de usuários cadastrados no fórum
     * @return total
     */
    public int getTotalUsers() {
        return numberOfUsers;
    }

    /**
     * Retorna o último usuário registrado no fórum
     * @return last user
     */
    public User getLastUserRegistered() {
        return lastUserRegistered;
    }

    /**
     * @return config
     */
    public ForumConfig getForumConfig() {
        return new ForumConfig();
    }
    
}
