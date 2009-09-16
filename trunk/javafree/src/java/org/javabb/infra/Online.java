package org.javabb.infra;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
 * $Id: Online.java,v 1.1 2009/05/11 20:27:01 daltoncamargo Exp $
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org </a> <br>
 * @author Ronald Tetsuo Miura
 */
public class Online {

    /**
     * Controle de "Quem está online"
     * @return os visitantes online.
     */
    public Collection getGuestsOnline() {
        return ApplicationContext.getContext().getOnlineGuests();
    }

    /**
     * Controle de "Quem está online"
     * @return o total de pessoas online.
     */
    public int getTotalUsersOnline() {
        ApplicationContext ctx = ApplicationContext.getContext();
        return ctx.getOnlineGuests().size() + ctx.getOnlineRegisteredUsers().size();
    }

    /**
     * Controle de "Quem está online"
     * @return o total de usuários online.
     */
    public Collection getUsersOnline() {
        ApplicationContext ctx = ApplicationContext.getContext();

        Set users = new HashSet(ctx.getOnlineGuests());
        users.addAll(ctx.getOnlineRegisteredUsers());
        return users;
    }

    /**
     * Quantidade de usuários cadastrados online
     * @return number
     */
    public int getNumberUsersOnline() {
        ApplicationContext ctx = ApplicationContext.getContext();
        return ctx.getOnlineGuests().size() + ctx.getOnlineRegisteredUsers().size();
    }

    /**
     * Quantidade de visitantes online
     * @return number
     */
    public int getNumberGuestesOnline() {
        Collection onlineGuests = ApplicationContext.getContext().getOnlineGuests();
        return onlineGuests.size();
    }
}
