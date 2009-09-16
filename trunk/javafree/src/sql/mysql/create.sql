CREATE TABLE `jbb_answer_notify` (
  `id_topic` bigint(20) NOT NULL default '0',
  `id_user` bigint(20) NOT NULL default '0',
  PRIMARY KEY  (`id_topic`,`id_user`),
  KEY `jbb_answer_notify_ind1_fk` (`id_topic`),
  KEY `jbb_answer_notify_ind2_fk` (`id_user`),
  KEY `JBB_ANSWER_NOTIFY_FK` (`id_topic`),
  KEY `JBB_ANSWER_NOTIFYIDEX_FK` (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for the `jbb_badwords` table : 
#

CREATE TABLE `jbb_badwords` (
  `id_badword` bigint(20) unsigned NOT NULL auto_increment,
  `word` varchar(50) default NULL,
  `replacement` varchar(50) default NULL,
  PRIMARY KEY  (`id_badword`),
  UNIQUE KEY `id_badword` (`id_badword`),
  KEY `id_badword_2` (`id_badword`),
  KEY `id_badword_3` (`id_badword`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for the `jbb_category` table : 
#

CREATE TABLE `jbb_category` (
  `id_category` bigint(3) unsigned NOT NULL auto_increment,
  `nm_category` varchar(200) NOT NULL default '',
  `cat_order` int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (`id_category`),
  UNIQUE KEY `id_category` (`id_category`),
  KEY `id_category_2` (`id_category`),
  KEY `id_category_3` (`id_category`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for the `jbb_forum` table : 
#

CREATE TABLE `jbb_forum` (
  `id_forum` bigint(20) unsigned NOT NULL auto_increment,
  `nome` varchar(200) default NULL,
  `descricao` varchar(200) default NULL,
  `forum_status` int(2) unsigned default NULL,
  `forum_order` int(2) unsigned default NULL,
  `id_category` bigint(20) unsigned NOT NULL default '0',
  `last_post_id` bigint(20) unsigned default NULL,
  `topic_count` bigint(20) unsigned default NULL,
  `post_count` bigint(20) unsigned default NULL,
  `last_page_post` bigint(20) unsigned default '1',
  `last_post_user_name` varchar(255) default NULL,
  `last_post_user_id` bigint(20) unsigned default NULL,
  `last_post_date` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `last_topic_id` bigint(20) unsigned default NULL,
  PRIMARY KEY  (`id_forum`),
  UNIQUE KEY `id_forum` (`id_forum`),
  KEY `id_forum_2` (`id_forum`),
  KEY `id_forum_3` (`id_forum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for the `jbb_forum_top_user` table : 
#

CREATE TABLE `jbb_forum_top_user` (
  `id_user` int(11) NOT NULL default '0',
  `post_count` int(11) NOT NULL default '0',
  `date_row` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `id_forum` int(11) default NULL,
  KEY `id_user` (`id_user`),
  KEY `id_forum` (`id_forum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for the `jbb_posts` table : 
#

CREATE TABLE `jbb_posts` (
  `id_post` bigint(20) unsigned NOT NULL auto_increment,
  `id_user` bigint(20) unsigned NOT NULL default '0',
  `id_topic` bigint(20) unsigned NOT NULL default '0',
  `data_post` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `assunto` varchar(200) default NULL,
  `sig` int(1) unsigned default '0',
  `ip` varchar(20) default NULL,
  `post_state` int(2) unsigned default '0',
  PRIMARY KEY  (`id_post`),
  UNIQUE KEY `id_post` (`id_post`),
  KEY `id_user` (`id_user`),
  KEY `id_topic` (`id_topic`),
  KEY `id_post_2` (`id_post`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for the `jbb_posts_text` table : 
#

CREATE TABLE `jbb_posts_text` (
  `id_post` bigint(20) NOT NULL default '0',
  `post_body` text,
  PRIMARY KEY  (`id_post`),
  KEY `id_post` (`id_post`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for the `jbb_privmsg_inbox` table : 
#

CREATE TABLE `jbb_privmsg_inbox` (
  `id_privmsg` int(11) NOT NULL auto_increment,
  `id_user_from` int(11) NOT NULL default '0',
  `id_user_to` int(11) NOT NULL default '0',
  `privmsg_topic` varchar(255) NOT NULL default '',
  `privmsg_text` text NOT NULL,
  `privmsg_date` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `allow_bbcode` int(1) default '0',
  `allow_smiles` int(1) default '0',
  `attach_sign` int(1) default '1',
  `flag_read` int(1) default '0',
  PRIMARY KEY  (`id_privmsg`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for the `jbb_privmsg_outbox` table : 
#

CREATE TABLE `jbb_privmsg_outbox` (
  `id_privmsg` int(11) NOT NULL auto_increment,
  `id_user_from` int(11) NOT NULL default '0',
  `id_user_to` int(11) NOT NULL default '0',
  `privmsg_topic` varchar(255) NOT NULL default '',
  `privmsg_text` text NOT NULL,
  `privmsg_date` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `allow_bbcode` int(1) default '0',
  `allow_smiles` int(1) default '0',
  `attach_sign` int(1) default '1',
  `flag_read` int(1) default '0',
  PRIMARY KEY  (`id_privmsg`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for the `jbb_rank` table : 
#

CREATE TABLE `jbb_rank` (
  `rank_id` int(11) unsigned NOT NULL auto_increment,
  `rank_name` varchar(255) default NULL,
  `rank_min` int(11) default NULL,
  `rank_max` int(11) default NULL,
  `rank_image` varchar(255) default NULL,
  PRIMARY KEY  (`rank_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for the `jbb_smiles` table : 
#

CREATE TABLE `jbb_smiles` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `symbol` varchar(50) NOT NULL default '',
  `emoticon` varchar(75) NOT NULL default '',
  `filename` varchar(100) NOT NULL default '',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for the `jbb_topics` table : 
#

CREATE TABLE `jbb_topics` (
  `id_topic` bigint(20) unsigned NOT NULL auto_increment,
  `id_user` bigint(20) unsigned NOT NULL default '0',
  `id_forum` bigint(20) unsigned NOT NULL default '0',
  `title_topic` varchar(200) NOT NULL default '',
  `data_topico` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `visualizacoes` int(10) unsigned default '0',
  `respostas` int(10) unsigned default '0',
  `topic_status` int(10) unsigned default '0',
  `topic_model` tinyint(3) unsigned default '0',
  `last_post_date` timestamp NOT NULL default '0000-00-00 00:00:00',
  `last_post_id` bigint(20) unsigned default NULL,
  `last_post_page` tinyint(3) unsigned default NULL,
  `last_post_user_id` bigint(20) unsigned default NULL,
  `last_post_user_name` varchar(200) default NULL,
  `notify_me` tinyint(3) unsigned default '0',
  PRIMARY KEY  (`id_topic`),
  UNIQUE KEY `id_topic` (`id_topic`),
  KEY `id_topic_2` (`id_topic`),
  KEY `id_user` (`id_user`),
  KEY `id_forum` (`id_forum`),
  KEY `id_topic_3` (`id_topic`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for the `jbb_users` table : 
#

CREATE TABLE `jbb_users` (
  `id_user` bigint(20) unsigned NOT NULL auto_increment,
  `user_name` varchar(50) default NULL,
  `pws` varchar(50) default NULL,
  `name` varchar(100) default NULL,
  `email` varchar(100) default NULL,
  `posts` int(10) unsigned default '0',
  `admin` int(1) unsigned default '0',
  `data_registro` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `localizacao` varchar(100) default NULL,
  `website` varchar(200) default NULL,
  `occupation` varchar(200) default NULL,
  `user_sig` text,
  `user_msnm` varchar(200) default NULL,
  `user_actualvisit` timestamp NOT NULL default '0000-00-00 00:00:00',
  `user_lastvisit` timestamp NOT NULL default '0000-00-00 00:00:00',
  `user_dateformat` varchar(50) default NULL,
  `user_allow_viewonline` int(1) unsigned default NULL,
  `user_avatar` varchar(200) default NULL,
  `user_icq` varchar(50) default NULL,
  `user_interests` varchar(200) default NULL,
  `user_aim` varchar(200) default NULL,
  `user_yim` varchar(200) default NULL,
  `show_mail` int(1) default '0',
  `show_signature` int(1) unsigned default '0',
  `user_status` int(10) unsigned default NULL,
  `user_code` varchar(100) default NULL,
  `hash_fpwd` varchar(100) default NULL,
  `receive_news` INTEGER(11) DEFAULT '1',
  PRIMARY KEY  (`id_user`),
  UNIQUE KEY `id_user` (`id_user`),
  KEY `user_code` (`user_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `jbb_fav_topic` (
  `id_topic` bigint(20) NOT NULL default '0',
  `id_user` bigint(20) NOT NULL default '0',
  PRIMARY KEY  (`id_topic`,`id_user`),
  KEY `jbb_fav_topic_ind1_fk` (`id_topic`),
  KEY `jbb_fav_topic_ind2_fk` (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `jbb_posts_files` (
  `file_id` int(11) unsigned NOT NULL auto_increment,
  `post_id` int(11) NOT NULL,
  `file_name` varchar(255) default NULL,
  `file_path` varchar(255) default NULL,
  `file_size` varchar(20) default NULL,
  `user_file_name` varchar(255) default NULL,
  `downloads` int(11) default '0',
  PRIMARY KEY  (`file_id`),
  UNIQUE KEY `file_id` (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `jbb_newsletter` (
  `news_id` int(11) NOT NULL auto_increment,
  `name` varchar(255) default NULL,
  `html_text` text,
  `active` int(11) default '0',
  PRIMARY KEY  (`news_id`),
  UNIQUE KEY `news_id` (`news_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `jbb_search_lockup` (
  `lockup_id` int(11) unsigned zerofill NOT NULL auto_increment,
  `key_search` varchar(255) default NULL,
  `search_date` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `forum_id` int(11) default NULL,
  PRIMARY KEY  (`lockup_id`),
  UNIQUE KEY `lockup_id` (`lockup_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


#
# Data for the `jbb_badwords` table  (LIMIT 0,500)
#

INSERT INTO `jbb_badwords` (`id_badword`, `word`, `replacement`) VALUES 
  ('1','shit','***');

COMMIT;

#
# Data for the `jbb_category` table  (LIMIT 0,500)
#

INSERT INTO `jbb_category` (`id_category`, `nm_category`, `cat_order`) VALUES 
  ('2','Misc',30);

COMMIT;

#
# Data for the `jbb_forum` table  (LIMIT 0,500)
#

INSERT INTO `jbb_forum` (`id_forum`, `nome`, `descricao`, `forum_status`, `forum_order`, `id_category`, `last_post_id`, `topic_count`, `post_count`, `last_page_post`, `last_post_user_name`, `last_post_user_id`, `last_post_date`, `last_topic_id`) VALUES 
  ('1','JavaBB Informations','Informations about JavaBB',0,1,'2','4','3','3','1','admin','63','2006-04-08 10:04:52',NULL);

COMMIT;

#
# Data for the `jbb_forum_top_user` table  (LIMIT 0,500)
#

INSERT INTO `jbb_forum_top_user` (`id_user`, `post_count`, `date_row`, `id_forum`) VALUES 
  (63,3,'2006-04-08 10:51:00',1);

COMMIT;

#
# Data for the `jbb_posts` table  (LIMIT 0,500)
#

INSERT INTO `jbb_posts` (`id_post`, `id_user`, `id_topic`, `data_post`, `assunto`, `sig`, `ip`, `post_state`) VALUES 
  ('2','63','2','2005-03-31 20:37:07','Informations',1,NULL,0),
  ('3','63','3','2005-06-28 00:19:48','Themes and Buttons in your language',0,NULL,0),
  ('4','63','4','2006-04-08 10:04:52','Support of JavaBB',1,NULL,0);

COMMIT;

#
# Data for the `jbb_posts_text` table  (LIMIT 0,500)
#

INSERT INTO `jbb_posts_text` (`id_post`, `post_body`) VALUES 
  (2,'Hello user of JavaBB, thanks to you for download our software ;)\r\n\r\n\r\n[quote]\r\n\r\n[b]Administration info:[/b]\r\n\r\nUser: admin\r\nPassword: admin\r\n\r\n[/quote]\r\n\r\n[b]Attention:[/b]\r\n\r\nThis is version 0.73 of our project.\r\n\r\nSupported features\r\n\r\n[list]\r\n[*] Flood Control\r\n[*] Watch Topic\r\n[*] Config file for custom changes\r\n[*] Complete layout changes thru css-theme files\r\n[*] Languages files and buttons file, based on phpBB\r\n[*] Forum category\r\n[*] Links to other urls\r\n[*] Sticky posts and announcements\r\n[*] BBcode\r\n[*] Locked threads\r\n[*] IP logging system\r\n[*] Paginsystem\r\n[*] Memberregistration and editing\r\n[*] MemberList\r\n[*] Private message system\r\n[*] Adminsystem\r\n[*] Badwordfilter\r\n[*] And much more...\r\n\r\n[/list]\r\n\r\n[url=http://www.javabb.org]JavaBB.Org[/url]'),
  (3,'Hi all!\r\n\r\nTo translate the buttons, you can download the german buttons at phpbb site ( http://www.phpbb.com/downloads.php ), and copy to folder:\r\n[code]\r\njavabb/forum/images/buttons/${portuguese-Brazilian-Folder}/\r\n[/code]\r\nlike it: https://javabb.dev.java.net/source/browse/javabb/forum/images/buttons/\r\n\r\nAt admin painel(General Config), these files are showed to you, you must select one and modify.\r\n\r\n\r\nTo change the jb2 theme, you must to create a new folder-theme (like jb2, I suggest copy and past jb2 and change the name.).\r\nYou can modify the css, images and all others velocity themes. But you have to change the javabb configuration file in:\r\n\r\n[code]javabb/appconf/javabb.properties[/code]\r\n\r\nThis file can be found here:\r\nhttps://javabb.dev.java.net/source/browse/javabb/appconf/javabb.properties?view=markup\r\n\r\nChange the property \r\n\r\n[code]config.forum.theme=jb2[/code]\r\n\r\nto you theme folder name, like it:\r\n\r\n[code]config.forum.theme=brazilianTheme[/code]\r\n\r\n\r\n[url=http://www.javabb.org]JavaBB.Org[/url]'),
  (4,'Please, if you have some question or suggest to us, use our group in\r\n\r\n\r\n[quote]\r\n[img]http://groups.google.com/groups/img/groups_medium.gif[/img]\r\nhttp://groups.google.com/group/javabb/\r\n\r\n[/quote]\r\n\r\n\r\nImportant: We dont have much time to spend in this project, then if you help something to suggest or module of codes, this will be welcome.\r\n\r\nThanks,\r\n\r\n[url=http://www.javabb.org]JavaBB.Org[/url]');

COMMIT;

#
# Data for the `jbb_rank` table  (LIMIT 0,500)
#

INSERT INTO `jbb_rank` (`rank_id`, `rank_name`, `rank_min`, `rank_max`, `rank_image`) VALUES 
  (1,'Participant',0,50,'status/star-01.gif'),
  (2,'Assistant',50,100,'status/star-02.gif'),
  (3,'Super Star',100,2000,'status/star-03.gif'),
  (4,'Expert',2000,10000,'status/star-04.gif'),
  (5,'Guru',10000,100000,'status/star-05.gif');

COMMIT;

#
# Data for the `jbb_smiles` table  (LIMIT 0,500)
#

INSERT INTO `jbb_smiles` (`id`, `symbol`, `emoticon`, `filename`) VALUES 
  ('2',':-D','Very Happy','icon_biggrin.gif'),
  ('3',':grin:','Very Happy','icon_biggrin.gif'),
  ('4',':)','Smile','icon_smile.gif'),
  ('5',':-)','Smile','icon_smile.gif'),
  ('6',':smile:','Smile','icon_smile.gif'),
  ('7',':(','Sad','icon_sad.gif'),
  ('8',':-(','Sad','icon_sad.gif'),
  ('9',':sad:','Sad','icon_sad.gif'),
  ('10',':o','Metal Rules!!','icon_surprised.gif'),
  ('13',':shock:','Shocked','icon_eek.gif'),
  ('14',':?','Confused','icon_confused.gif'),
  ('15',':-?','Confused','icon_confused.gif'),
  ('16',':???:','Confused','icon_confused.gif'),
  ('17','8)','Cool','icon_cool.gif'),
  ('18','8-)','Cool','icon_cool.gif'),
  ('19',':cool:','Cool','icon_cool.gif'),
  ('20',':lol:','Laughing','icon_lol.gif'),
  ('21',':x','Mad','icon_mad.gif'),
  ('22',':-x','Mad','icon_mad.gif'),
  ('23',':mad:','Mad','icon_mad.gif'),
  ('24',':P','Razz','icon_razz.gif'),
  ('25',':-P','Razz','icon_razz.gif'),
  ('26',':razz:','Razz','icon_razz.gif'),
  ('27',':00ps:','Embarassed','icon_redface.gif'),
  ('28',':cry:','Crying or Very sad','icon_cry.gif'),
  ('29',':evil:','Evil or Very Mad','icon_evil.gif'),
  ('30',':twisted:','Twisted Evil','icon_twisted.gif'),
  ('31',':roll:','Rolling Eyes','icon_rolleyes.gif'),
  ('32',':wink:','Wink','icon_wink.gif'),
  ('33',';)','Wink','icon_wink.gif'),
  ('34',';-)','Wink','icon_wink.gif'),
  ('35',':!:','Exclamation','icon_exclaim.gif'),
  ('36',':?:','Question','icon_question.gif'),
  ('37',':idea:','Idea','icon_idea.gif'),
  ('38',':arrow:','Arrow','icon_arrow.gif'),
  ('39',':|','Neutral','icon_neutral.gif'),
  ('40',':-|','Neutral','icon_neutral.gif'),
  ('41',':neutral:','Neutral','icon_neutral.gif'),
  ('42',':mrgreen:','Mr. Green','icon_mrgreen.gif'),
  ('43',':(:','Desapontado','icon_disappointed.gif'),
  ('44',':metal:','Ohhh yahh!!!','rockdevil.gif'),
  ('45',':hauhau:','Chorando de rir','hauhau.gif'),
  ('46',':pray:','Rezando','pray.gif'),
  ('47',':getout:','Dando o fora','get_out.gif'),
  ('48',':tantan:','tan-tan','tantan.gif'),
  ('49',':amigos:','Amigos','amigos.gif'),
  ('50',':bebado:','','new_all_coholic.gif'),
  ('51',':espanto:','Espanto','whoa.gif'),
  ('52',':zoio:','Nuuuussaa!!!!','icon_zoiudo.gif'),
  ('53',':P','Razz','icon_razz.gif');

COMMIT;

#
# Data for the `jbb_topics` table  (LIMIT 0,500)
#

INSERT INTO `jbb_topics` (`id_topic`, `id_user`, `id_forum`, `title_topic`, `data_topico`, `visualizacoes`, `respostas`, `topic_status`, `topic_model`, `last_post_date`, `last_post_id`, `last_post_page`, `last_post_user_id`, `last_post_user_name`, `notify_me`) VALUES 
  ('2','63','1','Informations','2006-04-08 10:59:43',0,0,0,0,'2005-03-31 20:37:07','2',1,'63','admin',0),
  ('3','63','1','Themes and Buttons in your language','2006-04-08 10:59:44',0,0,0,0,'2005-06-28 00:19:48','3',1,'63','admin',0),
  ('4','63','1','Support of JavaBB','2006-04-08 10:59:45',0,0,0,0,'2006-04-08 10:04:52','4',1,'63','admin',0);

COMMIT;

#
# Data for the `jbb_users` table  (LIMIT 0,500)
#

INSERT INTO `jbb_users` (`id_user`, `user_name`, `pws`, `name`, `email`, `posts`, `admin`, `data_registro`, `localizacao`, `website`, `occupation`, `user_sig`, `user_msnm`, `user_actualvisit`, `user_lastvisit`, `user_dateformat`, `user_allow_viewonline`, `user_avatar`, `user_icq`, `user_interests`, `user_aim`, `user_yim`, `show_mail`, `show_signature`, `user_status`, `user_code`, `hash_fpwd`) VALUES 
  ('1','Anonymous','','Anonymous','admin@admin.com.br',0,0,'2005-04-05 14:46:36','','','','','','2005-04-08 17:44:13','2005-04-08 17:44:13','D M d, Y g:i a',1,'http://www.javafree.com.br/forum/images/avatars/blank.gif','','','','',0,0,NULL,'f99891a27b384cfcb66e51e4b4dfc667',NULL),
  ('63','admin','21232f297a57a5a743894a0e4a801fc3','Administrator of JavaBB','admin@javabb.org',3,1,'2005-04-08 17:44:13','','','','','','2006-04-08 10:59:06','2005-03-31 20:35:03',NULL,NULL,'','','','','',1,1,NULL,'0e49f4740fb600a158848cee6b84f9f1',NULL);

COMMIT;



