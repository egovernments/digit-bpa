CREATE TABLE egbpa_stakeholder_support_documents
(
  filestoreid bigint NOT NULL,
  stakeholderdocumentid bigint NOT NULL,
  CONSTRAINT fk_egbpa_stakeholder_document FOREIGN KEY (stakeholderdocumentid)
      REFERENCES egbpa_stakeholder_document (id)
);

ALTER TABLE egbpa_mstr_stakeholder RENAME COLUMN businesslicencenumber TO licencenumber;
ALTER TABLE egbpa_mstr_stakeholder RENAME COLUMN businesslicenceduedate TO buildinglicenceissuedate;
ALTER TABLE egbpa_mstr_stakeholder ADD COLUMN buildinglicenceexpirydate timestamp without time zone;

ALTER TABLE egbpa_mstr_stakeholder ADD COLUMN contactperson character varying(50);
ALTER TABLE egbpa_mstr_stakeholder ADD COLUMN designation character varying(50);

update eg_module set displayname ='Building Licensee' where name='Stake Holder Master';
update eg_action set displayname ='Create Building Licensee' where name='Create StakeHolder' and parentmodule =(select id from eg_module  where name  ='Stake Holder Master');
update eg_action set displayname ='Update Building Licensee' where name='search and show update StakeHolder result' and parentmodule =(select id from eg_module  where name  ='Stake Holder Master');
update eg_action set displayname ='View Building Licensee' where name='search and show view StakeHolder result' and parentmodule =(select id from eg_module  where name  ='Stake Holder Master');


INSERT INTO egbpa_mstr_chklistdetail(id, code, description, isactive, ismandatory, checklist, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL'), 'LICCERT', 'License Certificate', true, false, (select id from egbpa_mstr_checklist where checklisttype='STAKEHOLDERDOCUMENT'), 0, 1, now(), 1, now());

INSERT INTO egbpa_mstr_chklistdetail(id, code, description, isactive, ismandatory, checklist, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL'), 'OTHER', 'Others', true, false, (select id from egbpa_mstr_checklist where checklisttype='STAKEHOLDERDOCUMENT'), 0, 1, now(), 1, now());