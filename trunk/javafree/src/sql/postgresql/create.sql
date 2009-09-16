CREATE TABLE jbb_badwords (
    id_badword bigint DEFAULT 0 NOT NULL,
    word character varying(50),
    replacement character varying(50)
) WITHOUT OIDS;
--
-- Structure for table jbb_category (OID = 234201) : 
--
CREATE TABLE jbb_category (
    id_category bigint DEFAULT 0 NOT NULL,
    nm_category character varying(200),
    cat_order integer DEFAULT 0 NOT NULL
) WITHOUT OIDS;
--
-- Structure for table jbb_forum (OID = 234205) : 
--
CREATE TABLE jbb_forum (
    id_forum bigint DEFAULT 0 NOT NULL,
    nome character varying(200),
    descricao text,
    forum_status integer,
    forum_order integer,
    id_category bigint DEFAULT 0,
    last_post_id bigint,
    topic_count bigint,
    post_count bigint,
    last_page_post bigint DEFAULT 1,
    last_post_user_name character varying(255),
    last_post_user_id bigint,
    last_post_date timestamp(0) without time zone,
    last_topic_id bigint
) WITHOUT OIDS;
--
-- Structure for table jbb_group (OID = 234213) : 
--
CREATE TABLE jbb_group (
    group_id bigint DEFAULT 0 NOT NULL,
    id_user bigint,
    group_name character varying(255)
) WITHOUT OIDS;
--
-- Structure for table jbb_group_moderator (OID = 234216) : 
--
CREATE TABLE jbb_group_moderator (
    group_id bigint DEFAULT 0 NOT NULL,
    id_forum bigint DEFAULT 0 NOT NULL
) WITHOUT OIDS;
--
-- Structure for table jbb_group_permission (OID = 234220) : 
--
CREATE TABLE jbb_group_permission (
    group_id bigint DEFAULT 0 NOT NULL,
    id_forum bigint DEFAULT 0,
    permission_type bigint
) WITHOUT OIDS;
--
-- Structure for table jbb_privmsg_inbox (OID = 234224) : 
--
CREATE TABLE jbb_privmsg_inbox (
    id_privmsg integer DEFAULT 0 NOT NULL,
    id_user_from integer DEFAULT 0,
    id_user_to integer DEFAULT 0,
    privmsg_topic character varying(255),
    privmsg_text text,
    privmsg_date timestamp(0) without time zone,
    allow_bbcode integer DEFAULT 0,
    allow_smiles integer DEFAULT 0,
    attach_sign integer DEFAULT 1,
    flag_read integer DEFAULT 0
) WITHOUT OIDS;
--
-- Structure for table jbb_privmsg_outbox (OID = 234236) : 
--
CREATE TABLE jbb_privmsg_outbox (
    id_privmsg integer DEFAULT 0 NOT NULL,
    id_user_from integer DEFAULT 0,
    id_user_to integer DEFAULT 0,
    privmsg_topic character varying(255),
    privmsg_text text,
    privmsg_date timestamp(0) without time zone,
    allow_bbcode integer DEFAULT 0,
    allow_smiles integer DEFAULT 0,
    attach_sign integer DEFAULT 1,
    flag_read integer DEFAULT 0
) WITHOUT OIDS;
--
-- Structure for table jbb_smiles (OID = 234248) : 
--
CREATE TABLE jbb_smiles (
    id bigint DEFAULT 0 NOT NULL,
    symbol character varying(50),
    emoticon character varying(75),
    filename character varying(100)
) WITHOUT OIDS;
--
-- Structure for table jbb_topics (OID = 234251) : 
--
CREATE TABLE jbb_topics (
    id_topic bigint DEFAULT 0 NOT NULL,
    id_user bigint DEFAULT 0,
    id_forum bigint DEFAULT 0,
    title_topic character varying(200),
    data_topico timestamp(0) without time zone,
    visualizacoes integer DEFAULT 0,
    respostas integer DEFAULT 0,
    topic_status integer DEFAULT 0,
    last_post_date timestamp(0) without time zone,
    last_post_id bigint,
    last_post_page smallint,
    last_post_user_id bigint,
    last_post_user_name character varying(200),
    topic_model smallint DEFAULT 0,
    notify_me integer
) WITHOUT OIDS;
--
-- Structure for table jbb_user_group (OID = 234260) : 
--
CREATE TABLE jbb_user_group (
    group_id bigint DEFAULT 0 NOT NULL,
    id_user bigint DEFAULT 0 NOT NULL
) WITHOUT OIDS;
--
-- Structure for table jbb_users (OID = 234264) : 
--
CREATE TABLE jbb_users (
    id_user bigint DEFAULT 0,
    user_name character varying(50),
    pws character varying(50),
    name character varying(100),
    email character varying(100),
    posts integer DEFAULT 0,
    admin integer DEFAULT 0,
    data_registro timestamp(0) without time zone,
    localizacao character varying(100),
    website character varying(200),
    occupation character varying(200),
    user_sig text,
    user_msnm character varying(200),
    user_actualvisit timestamp(0) without time zone,
    user_lastvisit timestamp(0) without time zone,
    user_dateformat character varying(50),
    user_allow_viewonline character varying(1),
    user_avatar character varying(200),
    user_icq character varying(50),
    user_interests character varying(200),
    user_aim character varying(200),
    user_yim character varying(200),
    show_mail integer DEFAULT 0,
    show_signature integer DEFAULT 0,
    user_status integer,
    user_code character varying(100),
    receive_news user_status integer DEFAULT 1,
    hash_fpwd character varying(100)
) WITHOUT OIDS;
--
-- Structure for table jbb_posts_text (OID = 234274) : 
--
CREATE TABLE jbb_posts_text (
    id_post bigint NOT NULL,
    post_body text
) WITHOUT OIDS;
--
-- Structure for table jbb_answer_notify (OID = 234279) : 
--
CREATE TABLE jbb_answer_notify (
    id_topic bigint NOT NULL,
    id_user bigint NOT NULL
) WITHOUT OIDS;
--
-- Structure for table jbb_posts (OID = 234281) : 
--
CREATE TABLE jbb_posts (
    id_post bigint DEFAULT 0 NOT NULL,
    id_user bigint DEFAULT 0,
    id_topic bigint DEFAULT 0,
    data_post timestamp(0) without time zone,
    assunto character varying(200),
    sig integer DEFAULT 0,
    ip character varying(20),
    post_state integer DEFAULT 0
) WITHOUT OIDS;
--
-- Structure for table jbb_forum_top_user (OID = 234310) : 
--
CREATE TABLE jbb_forum_top_user (
    id_user bigint NOT NULL,
    post_count bigint,
    date_row timestamp(0) without time zone,
    id_forum bigint NOT NULL
);
--
-- Structure for table jbb_rank (OID = 234315) : 
--
CREATE TABLE jbb_rank (
    rank_id bigint NOT NULL,
    rank_name character varying(255),
    rank_min integer,
    rank_max integer,
    rank_image character varying(255)
);

CREATE TABLE jbb_fav_topic (
  id_topic bigint DEFAULT 0 NOT NULL UNIQUE,
  id_user bigint DEFAULT 0 NOT NULL UNIQUE
) WITHOUT OIDS;

ALTER TABLE ONLY jbb_fav_topic
    ADD CONSTRAINT jbb_fav_topic_pkey PRIMARY KEY (id_topic,id_user);

CREATE TABLE jbb_newsletter (
  news_id bigint NOT NULL,
  name character varying(255),
  html_text text,
  active integer DEFAULT 0
) WITHOUT OIDS;


CREATE TABLE jbb_posts_files (
  file_id bigint NOT NULL,
  post_id bigint NOT NULL,
  file_name character varying(255),
  file_path character varying(255),
  file_size character varying(255),
  user_file_name character varying(255),
) WITHOUT OIDS;



--
-- Data for blobs (OID = 234198) (LIMIT 0,1)
--
INSERT INTO jbb_badwords (id_badword, word, replacement) VALUES (1, 'shit', '***');
--
-- Data for blobs (OID = 234201) (LIMIT 0,1)
--
INSERT INTO jbb_category (id_category, nm_category, cat_order) VALUES (2, 'Misc', 30);
--
-- Data for blobs (OID = 234205) (LIMIT 0,1)
--
INSERT INTO jbb_forum (id_forum, nome, descricao, forum_status, forum_order, id_category, last_post_id, topic_count, post_count, last_page_post, last_post_user_name, last_post_user_id, last_post_date, last_topic_id) VALUES (1, 'JavaBB Informations', 'Informations about JavaBB', 0, 1, 2, 4, 3, 3, 1, 'admin', 63, '2006-04-08 11:08:14', NULL);
--
-- Data for blobs (OID = 234248) (LIMIT 0,50)
--
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (2, ':-D', 'Very Happy', 'icon_biggrin.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (3, ':grin:', 'Very Happy', 'icon_biggrin.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (4, ':)', 'Smile', 'icon_smile.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (5, ':-)', 'Smile', 'icon_smile.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (6, ':smile:', 'Smile', 'icon_smile.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (7, ':(', 'Sad', 'icon_sad.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (8, ':-(', 'Sad', 'icon_sad.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (9, ':sad:', 'Sad', 'icon_sad.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (10, ':o', 'Metal Rules!!', 'icon_surprised.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (13, ':shock:', 'Shocked', 'icon_eek.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (14, ':?', 'Confused', 'icon_confused.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (15, ':-?', 'Confused', 'icon_confused.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (16, ':???:', 'Confused', 'icon_confused.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (17, '8)', 'Cool', 'icon_cool.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (18, '8-)', 'Cool', 'icon_cool.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (19, ':cool:', 'Cool', 'icon_cool.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (20, ':lol:', 'Laughing', 'icon_lol.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (21, ':x', 'Mad', 'icon_mad.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (22, ':-x', 'Mad', 'icon_mad.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (23, ':mad:', 'Mad', 'icon_mad.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (24, ':P', 'Razz', 'icon_razz.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (25, ':-P', 'Razz', 'icon_razz.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (26, ':razz:', 'Razz', 'icon_razz.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (27, ':oops:', 'Embarassed', 'icon_redface.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (28, ':cry:', 'Crying or Very sad', 'icon_cry.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (29, ':evil:', 'Evil or Very Mad', 'icon_evil.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (30, ':twisted:', 'Twisted Evil', 'icon_twisted.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (31, ':roll:', 'Rolling Eyes', 'icon_rolleyes.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (32, ':wink:', 'Wink', 'icon_wink.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (33, ';)', 'Wink', 'icon_wink.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (34, ';-)', 'Wink', 'icon_wink.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (35, ':!:', 'Exclamation', 'icon_exclaim.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (36, ':?:', 'Question', 'icon_question.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (37, ':idea:', 'Idea', 'icon_idea.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (38, ':arrow:', 'Arrow', 'icon_arrow.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (39, ':|', 'Neutral', 'icon_neutral.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (40, ':-|', 'Neutral', 'icon_neutral.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (41, ':neutral:', 'Neutral', 'icon_neutral.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (42, ':mrgreen:', 'Mr. Green', 'icon_mrgreen.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (43, ':(:', 'Desapontado', 'icon_disappointed.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (44, ':metal:', 'Ohhh yahh!!!', 'rockdevil.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (45, ':hauhau:', 'Chorando de rir', 'hauhau.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (46, ':pray:', 'Rezando', 'pray.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (47, ':getout:', 'Dando o fora', 'get_out.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (48, ':tantan:', 'tan-tan', 'tantan.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (49, ':amigos:', 'Amigos', 'amigos.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (50, ':bebado:', '', 'new_all_coholic.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (51, ':espanto:', 'Espanto', 'whoa.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (52, ':zoio:', 'Nuuuussaa!!!!', 'icon_zoiudo.gif');
INSERT INTO jbb_smiles (id, symbol, emoticon, filename) VALUES (53, ':P', 'Razz', 'icon_razz.gif');
--
-- Data for blobs (OID = 234251) (LIMIT 0,3)
--
INSERT INTO jbb_topics (id_topic, id_user, id_forum, title_topic, data_topico, visualizacoes, respostas, topic_status, last_post_date, last_post_id, last_post_page, last_post_user_id, last_post_user_name, topic_model, notify_me) VALUES (4, 63, 1, 'Support of JavaBB', '2006-04-08 11:08:14', 1, 0, 0, '2006-04-08 11:08:14', 4, 1, 63, 'admin', 0, 0);
INSERT INTO jbb_topics (id_topic, id_user, id_forum, title_topic, data_topico, visualizacoes, respostas, topic_status, last_post_date, last_post_id, last_post_page, last_post_user_id, last_post_user_name, topic_model, notify_me) VALUES (3, 63, 1, 'Themes and Buttons in your language', '2005-06-28 00:21:03', 1, 0, 0, '2005-06-28 00:19:48', 3, 1, 63, 'admin', 0, 0);
INSERT INTO jbb_topics (id_topic, id_user, id_forum, title_topic, data_topico, visualizacoes, respostas, topic_status, last_post_date, last_post_id, last_post_page, last_post_user_id, last_post_user_name, topic_model, notify_me) VALUES (2, 63, 1, 'Informations', '2005-06-28 00:21:06', 1, 0, 0, '2005-03-31 20:37:07', 2, 1, 63, 'admin', 0, 0);
--
-- Data for blobs (OID = 234264) (LIMIT 0,2)
--
INSERT INTO jbb_users (id_user, user_name, pws, name, email, posts, admin, data_registro, localizacao, website, occupation, user_sig, user_msnm, user_actualvisit, user_lastvisit, user_dateformat, user_allow_viewonline, user_avatar, user_icq, user_interests, user_aim, user_yim, show_mail, show_signature, user_status, user_code, hash_fpwd) VALUES (1, 'Anonymous', '', 'Anonymous', 'admin@admin.com.br', 0, 0, '2005-04-05 14:46:36', '', '', '', NULL, '', '2005-04-08 17:44:13', '2005-04-08 17:44:13', 'D M d, Y g:i a', '1', 'http://www.javafree.com.br/forum/images/avatars/blank.gif', '', '', '', '', 0, 0, NULL, 'f99891a27b384cfcb66e51e4b4dfc667', NULL);
INSERT INTO jbb_users (id_user, user_name, pws, name, email, posts, admin, data_registro, localizacao, website, occupation, user_sig, user_msnm, user_actualvisit, user_lastvisit, user_dateformat, user_allow_viewonline, user_avatar, user_icq, user_interests, user_aim, user_yim, show_mail, show_signature, user_status, user_code, hash_fpwd) VALUES (63, 'admin', '21232f297a57a5a743894a0e4a801fc3', 'asfda', 'admin@javabb.org', 3, 1, '2005-04-08 17:44:13', '', '', '', NULL, '', '2006-04-08 11:15:59', '2005-03-31 20:35:03', 'Null', NULL, '', '324234', '', '33', '', 1, 1, NULL, '0e49f4740fb600a158848cee6b84f9f1', NULL);
--
-- Data for blobs (OID = 234274) (LIMIT 0,3)
--
INSERT INTO jbb_posts_text (id_post, post_body) VALUES (4, 'Please, if you have some question or suggest to us, use our group in


[quote]
[img]http://groups.google.com/groups/img/groups_medium.gif[/img]
http://groups.google.com/group/javabb/

[/quote]


Important: We dont have much time to spend in this project, then if you help something to suggest or module of codes, this will be welcome.

Thanks,

[url=http://www.javabb.org]JavaBB.Org[/url]');
INSERT INTO jbb_posts_text (id_post, post_body) VALUES (3, 'Hi all!

To translate the buttons, you can download the german buttons at phpbb site ( http://www.phpbb.com/downloads.php ), and copy to folder:
[code]
javabb/forum/images/buttons/${portuguese-Brazilian-Folder}/
[/code]
like it: https://javabb.dev.java.net/source/browse/javabb/forum/images/buttons/

At admin painel(General Config), these files are showed to you, you must select one and modify.


To change the jb2 theme, you must to create a new folder-theme (like jb2, I suggest copy and past jb2 and change the name.).
You can modify the css, images and all others velocity themes. But you have to change the javabb configuration file in:

[code]javabb/appconf/javabb.properties[/code]

This file can be found here:
https://javabb.dev.java.net/source/browse/javabb/appconf/javabb.properties?view=markup

Change the property 

[code]config.forum.theme=jb2[/code]

to you theme folder name, like it:

[code]config.forum.theme=brazilianTheme[/code]


[url=http://www.javabb.org]JavaBB.Org[/url]');
INSERT INTO jbb_posts_text (id_post, post_body) VALUES (2, 'Hello user of JavaBB, thanks to you for download our software ;)


[quote]

[b]Administration info:[/b]

User: admin
Password: admin

[/quote]

[b]Attention:[/b]

This is version 0.73 of our project.

Supported features

[list]
[*] Flood Control
[*] Watch Topic
[*] Config file for custom changes
[*] Complete layout changes thru css-theme files
[*] Languages files and buttons file, based on phpBB
[*] Forum category
[*] Links to other urls
[*] Sticky posts and announcements
[*] BBcode
[*] Locked threads
[*] IP logging system
[*] Paginsystem
[*] Memberregistration and editing
[*] MemberList
[*] Private message system
[*] Adminsystem
[*] Badwordfilter
[*] And much more...

[/list]

[url=http://www.javabb.org]JavaBB.Org[/url]');
--
-- Data for blobs (OID = 234281) (LIMIT 0,3)
--
INSERT INTO jbb_posts (id_post, id_user, id_topic, data_post, assunto, sig, ip, post_state) VALUES (2, 63, 2, '2005-03-31 20:37:07', 'Informations', 1, 'Null', 0);
INSERT INTO jbb_posts (id_post, id_user, id_topic, data_post, assunto, sig, ip, post_state) VALUES (3, 63, 3, '2005-06-28 00:19:48', 'Themes and Buttons in your language', 0, 'Null', 0);
INSERT INTO jbb_posts (id_post, id_user, id_topic, data_post, assunto, sig, ip, post_state) VALUES (4, 63, 4, '2006-04-08 11:08:14', 'Support of JavaBB', 1, NULL, 0);
--
-- Data for blobs (OID = 234315) (LIMIT 0,5)
--
INSERT INTO jbb_rank (rank_id, rank_name, rank_min, rank_max, rank_image) VALUES (1, 'Participant', 0, 50, 'status/star-01.gif');
INSERT INTO jbb_rank (rank_id, rank_name, rank_min, rank_max, rank_image) VALUES (2, 'Assistant', 50, 100, 'status/star-02.gif');
INSERT INTO jbb_rank (rank_id, rank_name, rank_min, rank_max, rank_image) VALUES (3, 'Super Star', 100, 2000, 'status/star-03.gif');
INSERT INTO jbb_rank (rank_id, rank_name, rank_min, rank_max, rank_image) VALUES (4, 'Expert', 2000, 10000, 'status/star-04.gif');
INSERT INTO jbb_rank (rank_id, rank_name, rank_min, rank_max, rank_image) VALUES (5, 'Guru', 10000, 100000, 'status/star-05.gif');
--
-- Definition for index group_leader_fk (OID = 234288) : 
--
CREATE UNIQUE INDEX group_leader_fk ON jbb_group USING btree (id_user);
--
-- Definition for index id_badword (OID = 234289) : 
--
CREATE UNIQUE INDEX id_badword ON jbb_badwords USING btree (id_badword);
--
-- Definition for index id_category (OID = 234290) : 
--
CREATE UNIQUE INDEX id_category ON jbb_category USING btree (id_category);
--
-- Definition for index id_forum (OID = 234291) : 
--
CREATE INDEX id_forum ON jbb_topics USING btree (id_forum);
--
-- Definition for index id_user (OID = 234292) : 
--
CREATE INDEX id_user ON jbb_users USING btree (id_user);
--
-- Definition for index user_code (OID = 234293) : 
--
CREATE INDEX user_code ON jbb_users USING btree (user_code);
--
-- Definition for index jbb_answer_notify_fk (OID = 234294) : 
--
CREATE INDEX jbb_answer_notify_fk ON jbb_answer_notify USING btree (id_topic);
--
-- Definition for index jbb_answer_notify2_fk (OID = 234295) : 
--
CREATE INDEX jbb_answer_notify2_fk ON jbb_answer_notify USING btree (id_user);
--
-- Definition for index id_user3 (OID = 234296) : 
--
CREATE INDEX id_user3 ON jbb_topics USING btree (id_user);
--
-- Definition for index jbb_forum_id_forum_key (OID = 234297) : 
--
CREATE UNIQUE INDEX jbb_forum_id_forum_key ON jbb_forum USING btree (id_forum);
--
-- Definition for index jbb_group_moderator_fk (OID = 234298) : 
--
CREATE UNIQUE INDEX jbb_group_moderator_fk ON jbb_group_moderator USING btree (group_id);
--
-- Definition for index jbb_group_permission_fk (OID = 234299) : 
--
CREATE UNIQUE INDEX jbb_group_permission_fk ON jbb_group_permission USING btree (group_id);
--
-- Definition for index jbb_topics_id_topic_key (OID = 234300) : 
--
CREATE UNIQUE INDEX jbb_topics_id_topic_key ON jbb_topics USING btree (id_topic);
--
-- Definition for index jbb_user_group_fk (OID = 234301) : 
--
CREATE INDEX jbb_user_group_fk ON jbb_user_group USING btree (group_id);
--
-- Definition for index jbb_posts_id_post_key (OID = 234302) : 
--
CREATE UNIQUE INDEX jbb_posts_id_post_key ON jbb_posts USING btree (id_post);
--
-- Definition for index id_topic2 (OID = 234303) : 
--
CREATE INDEX id_topic2 ON jbb_posts USING btree (id_topic);
--
-- Definition for index index_dt_row (OID = 234312) : 
--
CREATE INDEX index_dt_row ON jbb_forum_top_user USING btree (date_row);
--
-- Definition for index index_id_forum (OID = 234313) : 
--
CREATE INDEX index_id_forum ON jbb_forum_top_user USING btree (id_forum);
--
-- Definition for index index_id_user (OID = 234314) : 
--
CREATE INDEX index_id_user ON jbb_forum_top_user USING btree (id_user);


CREATE INDEX jbb_posts_files_fk ON jbb_posts_files USING btree (file_id);
--
-- Definition for index pk_jbb_answer_notify (OID = 234304) : 
--
ALTER TABLE ONLY jbb_answer_notify
    ADD CONSTRAINT pk_jbb_answer_notify PRIMARY KEY (id_topic, id_user);
--
-- Definition for index pk_jbb_group (OID = 234306) : 
--
ALTER TABLE ONLY jbb_group
    ADD CONSTRAINT pk_jbb_group PRIMARY KEY (group_id);
--
-- Definition for index id_post (OID = 234308) : 
--
ALTER TABLE ONLY jbb_posts_text
    ADD CONSTRAINT id_post PRIMARY KEY (id_post);
--
-- Definition for index jbb_rank_pkey (OID = 234317) : 
--
ALTER TABLE ONLY jbb_rank
    ADD CONSTRAINT jbb_rank_pkey PRIMARY KEY (rank_id);
