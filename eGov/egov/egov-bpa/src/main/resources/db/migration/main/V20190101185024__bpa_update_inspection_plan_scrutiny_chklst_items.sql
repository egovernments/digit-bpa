ALTER TABLE egbpa_mstr_chklistdetail ALTER COLUMN description TYPE character varying(1024);

------Building Plan scrutiny rule validation--------

update egbpa_mstr_chklistdetail set description='Is the proposed construction is in accordance with the various provisions as per master plan zoning regulations/ Detailed town planning schemes rules for the area?' where code='PLSCTY01';

update egbpa_mstr_chklistdetail set description='Whether the plot area specified in plan info is in accordance with the plot area as per land deed document/ possession certificate?' where code='PLSCTY03';

update egbpa_mstr_chklistdetail set description='Whether all habitable rooms are abutting an exterior or interior open space, as specified?' where code='PLSCTY05';

update egbpa_mstr_chklistdetail set description='Whether the practicality of the provided car parking, two wheeler parking, loading and unloading space, manoeuvring space, slope, maximum open yard area allowable to be covered by parking, width of drive way etc. are ensured with regards to provisions as per KMBR 1999 and related amendments?' where code='PLSCTY11';

update egbpa_mstr_chklistdetail set isactive=false where code='PLSCTY02';
update egbpa_mstr_chklistdetail set isactive=false where code='PLSCTY04';
update egbpa_mstr_chklistdetail set isactive=false where code='PLSCTY06';
update egbpa_mstr_chklistdetail set isactive=false where code='PLSCTY07';
update egbpa_mstr_chklistdetail set isactive=false where code='PLSCTY08';
update egbpa_mstr_chklistdetail set isactive=false where code='PLSCTY09';
update egbpa_mstr_chklistdetail set isactive=false where code='PLSCTY10';
update egbpa_mstr_chklistdetail set isactive=false where code='PLSCTY12';

INSERT INTO egbpa_mstr_chklistdetail(id, code, description, isactive, ismandatory, checklist, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL'), 'PLSCTY13', 'The height of building - Whether marked correct as specified in KMBR 1999 and related amendments?', true, false, (select id from egbpa_mstr_checklist where checklisttype='PLANSCRUTINY'), 0, 1, now(), 1, now());

INSERT INTO egbpa_mstr_chklistdetail(id, code, description, isactive, ismandatory, checklist, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL'), 'PLSCTY14', 'Whether exit doorways are opened to an enclosed stairway or a horizontal exit or a corridor or passage way providing continous and protected means of egress  as per KMBR 1999 and related amendments?', true, false, (select id from egbpa_mstr_checklist where checklisttype='PLANSCRUTINY'), 0, 1, now(), 1, now());

INSERT INTO egbpa_mstr_chklistdetail(id, code, description, isactive, ismandatory, checklist, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL'), 'PLSCTY15', 'Whether the conditions related to lighting and ventilation as stipulated in KMBR rule 49 and related amendments are complied with?', true, false, (select id from egbpa_mstr_checklist where checklisttype='PLANSCRUTINY'), 0, 1, now(), 1, now());

INSERT INTO egbpa_mstr_chklistdetail(id, code, description, isactive, ismandatory, checklist, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL'), 'PLSCTY16', 'Whether the conditions on incorporating hazardous uses with residential uses  as stipulated in rule 53-2 are complied with?', true, false, (select id from egbpa_mstr_checklist where checklisttype='PLANSCRUTINY'), 0, 1, now(), 1, now());

INSERT INTO egbpa_mstr_chklistdetail(id, code, description, isactive, ismandatory, checklist, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL'), 'PLSCTY17', 'Apartment house, lodging  or rooming house, dormitory, hostel or hotel with residential accommodation having floor area of 300 m2 or more with a capacity for accommodating more than 20 person, are having at least two doorways as stipulated in rule 53 1(b)?', true, false, (select id from egbpa_mstr_checklist where checklisttype='PLANSCRUTINY'), 0, 1, now(), 1, now());

INSERT INTO egbpa_mstr_chklistdetail(id, code, description, isactive, ismandatory, checklist, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL'), 'PLSCTY18', 'In the case of wedding halls, whether proper and adequate arrangements for collection ang hygenic disposal of solid and liquid wastes are provided?', true, false, (select id from egbpa_mstr_checklist where checklisttype='PLANSCRUTINY'), 0, 1, now(), 1, now());

INSERT INTO egbpa_mstr_chklistdetail(id, code, description, isactive, ismandatory, checklist, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL'), 'PLSCTY19', 'Whether commercial buildings proposed in commercial zones, satisfies rule 56-1 (a)', true, false, (select id from egbpa_mstr_checklist where checklisttype='PLANSCRUTINY'), 0, 1, now(), 1, now());

INSERT INTO egbpa_mstr_chklistdetail(id, code, description, isactive, ismandatory, checklist, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL'), 'PLSCTY20', 'Whether minimum acces width, enclosure conditions as per rule 56 (4), 56 (5), for fish / meat stalls are satisfied?', true, false, (select id from egbpa_mstr_checklist where checklisttype='PLANSCRUTINY'), 0, 1, now(), 1, now());

INSERT INTO egbpa_mstr_chklistdetail(id, code, description, isactive, ismandatory, checklist, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL'), 'PLSCTY21', 'Whether the area of work rooms provided in industrial buildings are adequate as per rule 57-6?', true, false, (select id from egbpa_mstr_checklist where checklisttype='PLANSCRUTINY'), 0, 1, now(), 1, now());



------Building Plan scrutiny drawing details--------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'PLANSCRUTINYDRAWING', null,
0,1,now());

INSERT INTO egbpa_mstr_chklistdetail(id, code, description, isactive, ismandatory, checklist, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL'), 'PLSCTYDRW01', 'Whether all drawings and details, specified as per KMBR are uploaded in the system, as drawings in .pdf format?', true, false, (select id from egbpa_mstr_checklist where checklisttype='PLANSCRUTINYDRAWING'), 0, 1, now(), 1, now());

INSERT INTO egbpa_mstr_chklistdetail(id, code, description, isactive, ismandatory, checklist, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL'), 'PLSCTYDRW02', 'Comparison of EDCR report and PDF drawings - for identicalness with the captured details and the details provided in pdf drawings. Whether all the details given in EDCR report is the same as that given in pdf drawings?', true, false, (select id from egbpa_mstr_checklist where checklisttype='PLANSCRUTINYDRAWING'), 0, 1, now(), 1, now());

INSERT INTO egbpa_mstr_chklistdetail(id, code, description, isactive, ismandatory, checklist, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL'), 'PLSCTYDRW03', 'Whether the various uses/ occupancies of different spaces in the building/s as specified in the generated EDCR report for the particular application are in accordance with the uses specified against different spaces specified in the uploaded drawings in pdf format, based on occupancy classes defined as per prevailing KMBR rules and related amendments? Whether the polygons for calculating Built Up area, Floor area, and covered area are marked correctly?', true, false, (select id from egbpa_mstr_checklist where checklisttype='PLANSCRUTINYDRAWING'), 0, 1, now(), 1, now());

INSERT INTO egbpa_mstr_chklistdetail(id, code, description, isactive, ismandatory, checklist, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL'), 'PLSCTYDRW04', 'Whether the drawings uploaded in .dxf format is accurately prepared satisfying all the conditions and methods specified in ''Suvega drawing preparation guidelines'' applicable?', true, false, (select id from egbpa_mstr_checklist where checklisttype='PLANSCRUTINYDRAWING'), 0, 1, now(), 1, now());

INSERT INTO egbpa_mstr_chklistdetail(id, code, description, isactive, ismandatory, checklist, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL'), 'PLSCTYDRW05', 'Whether Front yard, Side yard 1, Side yard 2, Rear yard and plot boundary polygons are accurately marked (Plot boundaries shall be excluding the road widening area if any), and conditions with regards to overhangs stipulated in rules 24-10, 24-11, 62-2 are complied with?', true, false, (select id from egbpa_mstr_checklist where checklisttype='PLANSCRUTINYDRAWING'), 0, 1, now(), 1, now());

INSERT INTO egbpa_mstr_chklistdetail(id, code, description, isactive, ismandatory, checklist, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL'), 'PLSCTYDRW06', 'The height of building - Whether marked correct and the same value of height is given in pdf?', true, false, (select id from egbpa_mstr_checklist where checklisttype='PLANSCRUTINYDRAWING'), 0, 1, now(), 1, now());

INSERT INTO egbpa_mstr_chklistdetail(id, code, description, isactive, ismandatory, checklist, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL'), 'PLSCTYDRW07', 'Type of abutting road, building footprint, the minimum distance between plot boundary and building etc. accurately specified with correct layer and dimensions?', true, false, (select id from egbpa_mstr_checklist where checklisttype='PLANSCRUTINYDRAWING'), 0, 1, now(), 1, now());

INSERT INTO egbpa_mstr_chklistdetail(id, code, description, isactive, ismandatory, checklist, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL'), 'PLSCTYDRW08', 'Whether all the dimensions related to the buildings, open well, waste disposal facilities, roads, and boundaries are accurately marked?', true, false, (select id from egbpa_mstr_checklist where checklisttype='PLANSCRUTINYDRAWING'), 0, 1, now(), 1, now());

INSERT INTO egbpa_mstr_chklistdetail(id, code, description, isactive, ismandatory, checklist, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL'), 'PLSCTYDRW09', 'Whether all additional polygons required for calculating the parking requirements are marked as per the respective occupancies?', true, false, (select id from egbpa_mstr_checklist where checklisttype='PLANSCRUTINYDRAWING'), 0, 1, now(), 1, now());

INSERT INTO egbpa_mstr_chklistdetail(id, code, description, isactive, ismandatory, checklist, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL'), 'PLSCTYDRW10', 'Whether additional details as required for construction involving depth of cutting more than 1.50 m are incorporated?', true, false, (select id from egbpa_mstr_checklist where checklisttype='PLANSCRUTINYDRAWING'), 0, 1, now(), 1, now());

INSERT INTO egbpa_mstr_chklistdetail(id, code, description, isactive, ismandatory, checklist, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL'), 'PLSCTYDRW11', 'Whether the additional polygons for validating dimensions/ distances related to parts of buildings including Stair, Lift, ramps, sanitation facilities, exit width, mezzanine floors, height of rooms etc. are accurately marked?', true, false, (select id from egbpa_mstr_checklist where checklisttype='PLANSCRUTINYDRAWING'), 0, 1, now(), 1, now());

-----plan scrutiny alter----

alter table egbpa_plan_scrutiny_checklist add column scrutinyChecklistType character varying(50);

update egbpa_plan_scrutiny_checklist set scrutinyChecklistType='COMBINED' where scrutinyChecklistType is null;