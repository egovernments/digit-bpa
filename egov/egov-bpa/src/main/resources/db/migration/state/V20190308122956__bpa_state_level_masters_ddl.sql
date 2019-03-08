CREATE TABLE egbpa_mstr_stakeholder
(
  id bigint NOT NULL,
  stakeholdertype bigint NOT NULL,
  code character varying(128) NOT NULL,
  licencenumber character varying(64) NOT NULL,
  buildinglicenceissuedate timestamp without time zone NOT NULL,
  coaenrolmentnumber character varying(64),
  coaenrolmentduedate timestamp without time zone,
  isenrolwithlocalbody boolean,
  organizationname character varying(128),
  organizationaddress character varying(128),
  organizationurl character varying(64),
  organizationmobno character varying(15),
  isonbehalfoforganization boolean,
  isactive boolean NOT NULL DEFAULT true,
  tinnumber character varying(11),
  version numeric DEFAULT 0,
  createduser bigint,
  createdate timestamp without time zone,
  lastupdateduser bigint,
  lastupdateddate timestamp without time zone,
  buildinglicenceexpirydate timestamp without time zone,
  contactperson character varying(50),
  designation character varying(50),
  source bigint,
  comments character varying(1024),
  status character varying(30),
  isaddresssame boolean,
  nooftimesrejected smallint,
  nooftimesblocked smallint,
  demand bigint,
  CONSTRAINT pk_egbpa_mstr_stormwaterdrain PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_stormwaterdrain_crtby FOREIGN KEY (createduser)
      REFERENCES eg_user (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_egbpa_stormwaterdrain_mdfdby FOREIGN KEY (lastupdateduser)
      REFERENCES eg_user (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT unq_stakeholder_code UNIQUE (code)
);

CREATE TABLE egbpa_mstr_stakeholder_aud
(
  id bigint NOT NULL,
  rev integer NOT NULL,
  code character varying(128) NOT NULL,
  licencenumber character varying(64) NOT NULL,
  comments character varying(1024),
  status character varying(30),
  isactive boolean,
  revtype numeric,
  lastupdateddate timestamp without time zone,
  lastupdateduser bigint,
  CONSTRAINT pk_egbpa_mstr_stakeholder_aud PRIMARY KEY (id, rev)
);



CREATE TABLE egbpa_stakeholder_document
(
  id bigint NOT NULL,
  checklistdetail bigint,
  stakeholder bigint,
  isattached boolean,
  documentid bigint,
  version numeric DEFAULT 0,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  CONSTRAINT pk_egbpa_stakeholder_document PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_stakeholder_document_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_egbpa_stakeholder_document_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_egbpa_stakeholder_document_stakeholder FOREIGN KEY (stakeholder)
      REFERENCES egbpa_mstr_stakeholder (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


CREATE TABLE egbpa_stakeholder_support_documents
(
  filestoreid bigint NOT NULL,
  stakeholderdocumentid bigint NOT NULL,
  CONSTRAINT fk_egbpa_stakeholder_document FOREIGN KEY (stakeholderdocumentid)
      REFERENCES egbpa_stakeholder_document (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE seq_egbpa_mstr_stakeholder
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE SEQUENCE seq_egbpa_stakeholder_document
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;