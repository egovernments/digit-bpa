CREATE TABLE IF NOT EXISTS state.egp_inbox
(
  id bigint NOT NULL,
  tenantId character varying(250),
  moduleid bigint NOT NULL,
  servicetype character varying(128) NOT NULL,
  applicationnumber character varying(50) NOT NULL,
  entityrefnumber character varying(50),
  entityrefid bigint,
  headermessage character varying(256),
  detailedmessage character varying(2048) NOT NULL,
  link character varying(256) NOT NULL,
  read boolean,
  resolved boolean DEFAULT false,
  applicationdate timestamp without time zone NOT NULL,
  slaenddate timestamp without time zone,
  state_id bigint,
  status character varying(100) NOT NULL,
  createdby bigint NOT NULL,
  createddate timestamp without time zone,
  lastmodifieddate timestamp without time zone,
  lastmodifiedby bigint,
  version bigint DEFAULT 0,
  priority character varying(20),
  resolveddate timestamp without time zone,
  applicantname character varying(100),
  CONSTRAINT pk_portalinbox PRIMARY KEY (id),
  CONSTRAINT fk_portalinbox_createdby FOREIGN KEY (createdby)
      REFERENCES state.eg_user (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_portalinbox_lastmdby FOREIGN KEY (lastmodifiedby)
      REFERENCES state.eg_user (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT egp_inbox_unique_module_applicationnumber UNIQUE (moduleid, applicationnumber)
);

CREATE TABLE IF NOT EXISTS state.egp_inboxusers
(
  id bigint NOT NULL,
  tenantId character varying(250),
  userid bigint NOT NULL,
  portalinbox bigint NOT NULL,
  CONSTRAINT pk_portalinboxusers PRIMARY KEY (id),
  CONSTRAINT fk_inboxuserid FOREIGN KEY (portalinbox)
      REFERENCES egp_inbox (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_portalinbox_userid FOREIGN KEY (userid)
      REFERENCES state.eg_user (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS state.egp_citizen
(
  id bigint NOT NULL,
  activationcode character varying(5),
  version numeric DEFAULT 0,
  CONSTRAINT pk_citizen PRIMARY KEY (id),
  CONSTRAINT fk_citizen_user FOREIGN KEY (id)
      REFERENCES state.eg_user (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS state.egp_citizeninbox
(
  id integer NOT NULL,
  tenantId character varying(250),
  module_id integer,
  message_type character varying(20) NOT NULL,
  identifier character varying(50),
  header_msg character varying(500) NOT NULL,
  detailed_msg character varying(2048) NOT NULL,
  link character varying(256),
  read boolean,
  msg_date timestamp without time zone NOT NULL,
  state_id integer NOT NULL,
  assigned_to_user integer NOT NULL,
  status character varying(100),
  createdby bigint NOT NULL,
  createddate timestamp without time zone,
  lastmodifieddate timestamp without time zone,
  lastmodifiedby bigint NOT NULL,
  version bigint NOT NULL,
  priority character varying(20) NOT NULL,
  CONSTRAINT pk_c_inbox PRIMARY KEY (id),
  CONSTRAINT fk_c_inbox_createdby FOREIGN KEY (createdby)
      REFERENCES state.eg_user (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_c_inbox_lastmodifiedby FOREIGN KEY (lastmodifiedby)
      REFERENCES state.eg_user (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE IF NOT EXISTS state.seq_egp_inbox
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE SEQUENCE IF NOT EXISTS state.seq_egp_inboxusers
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE SEQUENCE IF NOT EXISTS state.seq_egp_citizeninbox
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;