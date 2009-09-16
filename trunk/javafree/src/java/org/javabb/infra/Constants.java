package org.javabb.infra;

import java.util.HashMap;

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
 * $Id: Constants.java,v 1.1 2009/05/11 20:27:00 daltoncamargo Exp $
 * 
 * @author Dalton Camargo - <a
 *         href="mailto:dalton@javabb.org">dalton@javabb.org</a>
 */
public class Constants {

    public static final String moveTopicMailTemplate = "mail_move_topic.vm";
    public static final String mpMailTemplate = "mp_mail.vm";
    public static final String watchTopicTemplate = "watch_topic.vm";
    public static final String mailForgetPwd = "mail_forget_pwd.vm";
    public static final String sendNewPassword = "send_new_password.vm";
    public static final String warnNotify = "warn.vm";

    public static boolean LUCENE_INDEX_NOTICIAS = false;

    @SuppressWarnings("unchecked")
    /**
     * HashMap com os ids dos artigos antigos e com 
     * os ids dos artigos novos. Apenas para nao perder as referencias do google
     */
    public static Integer getIdNewArticleMigrated(Long id) {
	HashMap map = new HashMap();
	map.put(116, 871431);
	map.put(216, 871432);
	map.put(205, 871433);
	map.put(184, 871434);
	map.put(210, 871435);
	map.put(180, 871436);
	map.put(194, 871437);
	map.put(173, 871444);
	map.put(179, 871445);
	map.put(91, 871446);
	map.put(5, 871447);
	map.put(220, 871448);
	map.put(198, 871449);
	map.put(16, 871450);
	map.put(177, 871451);
	map.put(183, 871452);
	map.put(1, 871453);
	map.put(43, 871454);
	map.put(7, 871455);
	map.put(18, 871456);
	map.put(2, 871457);
	map.put(8, 871458);
	map.put(6, 871459);
	map.put(88, 871460);
	map.put(34, 871461);
	map.put(3, 871462);
	map.put(9, 871463);
	map.put(89, 871464);
	map.put(187, 871465);
	map.put(53, 871466);
	map.put(122, 871467);
	map.put(186, 871468);
	map.put(106, 871469);
	map.put(46, 871470);
	map.put(41, 871475);
	map.put(213, 871476);
	map.put(22, 871477);
	map.put(188, 871478);
	map.put(190, 871479);
	map.put(120, 871480);
	map.put(57, 871481);
	map.put(14, 871482);
	map.put(15, 871483);
	map.put(185, 871484);
	map.put(4, 871485);
	map.put(112, 871487);
	map.put(182, 871488);
	map.put(115, 855399);
	map.put(26, 871489);
	map.put(178, 871490);
	map.put(72, 871491);
	map.put(40, 871492);
	map.put(208, 12088);
	map.put(13, 1356);
	map.put(11, 847654);
	map.put(42, 871493);
	map.put(189, 871494);
	map.put(12, 723);
	map.put(207, 871495);
	map.put(85, 871496);
	map.put(86, 871497);
	map.put(84, 871498);
	map.put(195, 871499);
	map.put(176, 871500);
	map.put(192, 871501);
	map.put(175, 5792);
	map.put(191, 871502);
	map.put(206, 871503);
	map.put(219, 871504);
	Object ret =  map.get(id.intValue());
	if(ret != null){
	    return (Integer)ret;
	}
	return 0;
    }

}
