CREATE TABLE egbpa_plan_scrutiny_checklist
(
  id bigint NOT NULL,
  inspection bigint NOT NULL,
  checklistdetail bigint NOT NULL,
  scrutinyValue bigint,
  remarks character varying(1024),
  ordernumber bigint,
  version numeric DEFAULT 0,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  CONSTRAINT pk_egbpa_plan_scrutiny_checklist PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_plan_scrutiny_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_plan_scrutiny_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_plan_scrutinychecklist FOREIGN KEY (checklistdetail)
      REFERENCES egbpa_mstr_chklistdetail (id),
  CONSTRAINT fk_egbpa_plan_scrutinyinspection FOREIGN KEY (inspection)
      REFERENCES EGBPA_INSPECTION (id)
);

create sequence seq_egbpa_plan_scrutiny_checklist;

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'PLANSCRUTINY', null,
0,1,now());

INSERT INTO egbpa_mstr_chklistdetail(id, code, description, isactive, ismandatory, checklist, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL'), 'PLSCTY01', 'Is the proposed construction allowable as per master plan zoning regulations/ Detailed town planning schemes for the area', true, false, (select id from egbpa_mstr_checklist where checklisttype='PLANSCRUTINY'), 0, 1, now(), 1, now());

INSERT INTO egbpa_mstr_chklistdetail(id, code, description, isactive, ismandatory, checklist, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL'), 'PLSCTY02', 'The height of building - Whether marked correct and the same value of height is given in pdf?', true, false, (select id from egbpa_mstr_checklist where checklisttype='PLANSCRUTINY'), 0, 1, now(), 1, now());

INSERT INTO egbpa_mstr_chklistdetail(id, code, description, isactive, ismandatory, checklist, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL'), 'PLSCTY03', 'Plot area, same as given in land deed document and possession certificate?', true, false, (select id from egbpa_mstr_checklist where checklisttype='PLANSCRUTINY'), 0, 1, now(), 1, now());

INSERT INTO egbpa_mstr_chklistdetail(id, code, description, isactive, ismandatory, checklist, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL'), 'PLSCTY04', 'Type of abutting road, building footprint, the minimum distance between plot boundary and building etc. accurately specified with correct layer and dimensions?', true, false, (select id from egbpa_mstr_checklist where checklisttype='PLANSCRUTINY'), 0, 1, now(), 1, now());

INSERT INTO egbpa_mstr_chklistdetail(id, code, description, isactive, ismandatory, checklist, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL'), 'PLSCTY05', 'All Habitable room polygon, exterior boundaries and interior boundaries correctly marked?', true, false, (select id from egbpa_mstr_checklist where checklisttype='PLANSCRUTINY'), 0, 1, now(), 1, now());

INSERT INTO egbpa_mstr_chklistdetail(id, code, description, isactive, ismandatory, checklist, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL'), 'PLSCTY06', 'Front yard, side yard 1, side yard 2, rear yard and plot boundary polygons accurately marked?
', true, false, (select id from egbpa_mstr_checklist where checklisttype='PLANSCRUTINY'), 0, 1, now(), 1, now());

INSERT INTO egbpa_mstr_chklistdetail(id, code, description, isactive, ismandatory, checklist, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL'), 'PLSCTY07', 'Whether the BUILT_UP_AREA, FAR deduct polygons accurately marked?
', true, false, (select id from egbpa_mstr_checklist where checklisttype='PLANSCRUTINY'), 0, 1, now(), 1, now());

INSERT INTO egbpa_mstr_chklistdetail(id, code, description, isactive, ismandatory, checklist, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL'), 'PLSCTY08', 'Whether the distance to centre line of the road from the buildings accurately marked?
', true, false, (select id from egbpa_mstr_checklist where checklisttype='PLANSCRUTINY'), 0, 1, now(), 1, now());

INSERT INTO egbpa_mstr_chklistdetail(id, code, description, isactive, ismandatory, checklist, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL'), 'PLSCTY09', 'Whether the Access width accurately marked
', true, false, (select id from egbpa_mstr_checklist where checklisttype='PLANSCRUTINY'), 0, 1, now(), 1, now());

INSERT INTO egbpa_mstr_chklistdetail(id, code, description, isactive, ismandatory, checklist, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL'), 'PLSCTY10', 'Whether the residential units, dining area polygons accurately marked.
', true, false, (select id from egbpa_mstr_checklist where checklisttype='PLANSCRUTINY'), 0, 1, now(), 1, now());

INSERT INTO egbpa_mstr_chklistdetail(id, code, description, isactive, ismandatory, checklist, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL'), 'PLSCTY11', 'The practicality of the provided car parking, two wheeler parking, loading and unloading space, manoeuvring space, slope etc. are ensured with regards to provisions as per rule.', true, false, (select id from egbpa_mstr_checklist where checklisttype='PLANSCRUTINY'), 0, 1, now(), 1, now());

INSERT INTO egbpa_mstr_chklistdetail(id, code, description, isactive, ismandatory, checklist, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL'), 'PLSCTY12', 'Comparison of EDCR report and pdf drawings- for identicalness with the captured details and details provided in pdf drawings. Whether all the details given in EDCR report is the same to that given in pdf drawings?', true, false, (select id from egbpa_mstr_checklist where checklisttype='PLANSCRUTINY'), 0, 1, now(), 1, now());