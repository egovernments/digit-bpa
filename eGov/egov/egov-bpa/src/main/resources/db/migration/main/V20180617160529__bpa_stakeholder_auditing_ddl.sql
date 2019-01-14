alter table egbpa_mstr_stakeholder rename column createdby to createduser;
alter table egbpa_mstr_stakeholder rename column lastmodifiedby to lastupdateduser;
alter table egbpa_mstr_stakeholder rename column createddate to createdate;
alter table egbpa_mstr_stakeholder rename column lastmodifieddate to lastupdateddate;

alter table egbpa_mstr_stakeholder add column nooftimesrejected smallint;
alter table egbpa_mstr_stakeholder add column nooftimesblocked smallint;

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