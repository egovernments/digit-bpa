--------------------------DELETE OCCUPANCY INSPECTION TYPES-----------------------

delete from egbpa_mstr_chklistdetail where checklist in (select id from egbpa_mstr_checklist where checklisttype ='OCINSPECTIONLOCATION');
delete from egbpa_mstr_chklistdetail where checklist in (select id from egbpa_mstr_checklist where checklisttype ='OCINSPECTIONMEASUREMENT');
delete from egbpa_mstr_chklistdetail where checklist in (select id from egbpa_mstr_checklist where checklisttype ='OCINSPECTIONACCESS');
delete from egbpa_mstr_chklistdetail where checklist in (select id from egbpa_mstr_checklist where checklisttype ='OCINSPECTIONSURROUNDING');
delete from egbpa_mstr_chklistdetail where checklist in (select id from egbpa_mstr_checklist where checklisttype ='OCINSPECTIONTYPEOFLAND');
delete from egbpa_mstr_chklistdetail where checklist in (select id from egbpa_mstr_checklist where checklisttype ='OCINSPECTIONPROPOSEDSTAGEWORK');
delete from egbpa_mstr_chklistdetail where checklist in (select id from egbpa_mstr_checklist where checklisttype ='OCINSPECTIONWORKCOMPLETEDPERPLAN');
delete from egbpa_mstr_chklistdetail where checklist in (select id from egbpa_mstr_checklist where checklisttype ='OCINSPECTIONHGTBUILDABUTROAD');
delete from egbpa_mstr_chklistdetail where checklist in (select id from egbpa_mstr_checklist where checklisttype ='OCINSPECTIONAREALOC');
delete from egbpa_mstr_chklistdetail where checklist in (select id from egbpa_mstr_checklist where checklisttype ='OCINSPECTIONLENGTHOFCOMPOUNDWALL');
delete from egbpa_mstr_chklistdetail where checklist in (select id from egbpa_mstr_checklist where checklisttype ='OCINSPECTIONNUMBEROFWELLS');

delete from egbpa_mstr_checklist where checklisttype in ('OCINSPECTIONLOCATION','OCINSPECTIONMEASUREMENT','OCINSPECTIONACCESS','OCINSPECTIONSURROUNDING','OCINSPECTIONTYPEOFLAND',
'OCINSPECTIONPROPOSEDSTAGEWORK','OCINSPECTIONWORKCOMPLETEDPERPLAN','OCINSPECTIONHGTBUILDABUTROAD','OCINSPECTIONAREALOC','OCINSPECTIONLENGTHOFCOMPOUNDWALL','OCINSPECTIONNUMBEROFWELLS');

------------------------------CHECKLIST TYPES-------------------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate) 
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCPLANSCRUTINYRULE', null,0,1,now());

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCPLANSCRUTINYDRAWING', null,0,1,now());

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCINSPECTION', null,0,1,now());

------------------------------OCPLANSCRUTINYRULE CHECKLIST DETAIL-------------------

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCPLANSCRUTINYRULE-01','Whether all habitable rooms are abutting an 
exterior or interior open space, as specified',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCPLANSCRUTINYRULE'), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCPLANSCRUTINYRULE-02','Is the proposed construction is in accordance 
with the various provisions as per master plan zoning regulations/ Detailed town planning schemes rules for the area?',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCPLANSCRUTINYRULE'), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCPLANSCRUTINYRULE-03','Whether the plot area specified in plan info is in 
accordance with the plot area as per land deed document/ possession certificate?',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCPLANSCRUTINYRULE'), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCPLANSCRUTINYRULE-04','The height of building - Whether marked correct 
as specified in rule book and related amendments?',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCPLANSCRUTINYRULE'), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCPLANSCRUTINYRULE-05','Whether the practicality of the provided car parking, two wheeler parking, loading and unloading space, manoeuvring space, slope, maximum open yard area allowable to 
be covered by parking, width of driveway etc. are ensured with regards to provisions as per rule book and related amendments?',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCPLANSCRUTINYRULE'), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCPLANSCRUTINYRULE-06','Whether exit doorways are opened to an enclosed stairway or a horizontal exit or a corridor or passageway providing continuous and protected means of egress as 
per rule book and related amendments??',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCPLANSCRUTINYRULE'), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCPLANSCRUTINYRULE-07','Whether the conditions related to lighting and ventilation 
as stipulated in rule book and related amendments are complied with?',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCPLANSCRUTINYRULE'), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCPLANSCRUTINYRULE-08','Whether the conditions on incorporating hazardous uses 
with residential uses as stipulated is complied with?',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCPLANSCRUTINYRULE'), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCPLANSCRUTINYRULE-09','Apartment house, lodging or rooming house, 
dormitory, hostel or hotel with residential accommodation having floor area of 300 m2 or more with a capacity for accommodating more than 20 person, are having at least two doorways as stipulated in rule 53 1(b)?',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCPLANSCRUTINYRULE'), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCPLANSCRUTINYRULE-10','In the case of wedding halls, whether proper and adequate 
arrangements for collection any hygienic disposal of solid and liquid wastes are provided?',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCPLANSCRUTINYRULE'), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCPLANSCRUTINYRULE-11',
'Whether commercial buildings proposed in commercial zones, satisfies rule 56-1 (a)',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCPLANSCRUTINYRULE'), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCPLANSCRUTINYRULE-12',
'Whether minimum access width, enclosure conditions as per rule 56 (4), 56 (5), for fish / meat stalls are satisfied?',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCPLANSCRUTINYRULE'), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCPLANSCRUTINYRULE-13',
'Whether the area of work rooms provided in industrial buildings are adequate as per rule 57-6?',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCPLANSCRUTINYRULE'), 0,1,now());

------------------------------OCPLANSCRUTINYDRAWING CHECKLIST DETAIL-------------------

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCPLANSCRUTINYDRAWING-01','Whether all drawings and details, 
specified as per rule book are uploaded in the system, as drawings in .pdf format?',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCPLANSCRUTINYDRAWING'), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCPLANSCRUTINYDRAWING-02','Comparison of EDCR report and PDF drawings - for identicalness with the captured details and the details provided in pdf drawings. 
Whether all the details given in EDCR report is the same as that given in pdf drawings?',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCPLANSCRUTINYDRAWING'), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCPLANSCRUTINYDRAWING-03','Whether the various uses/ occupancies of different 
spaces in the building/s as specified in the generated EDCR report for the particular application, are in accordance with the uses specified against different spaces specified in the uploaded drawings in pdf format, based on occupancy classes defined 
as per prevailing rule book and related amendments?',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCPLANSCRUTINYDRAWING'), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCPLANSCRUTINYDRAWING-04','Whether the polygons for calculating Built Up area, 
Floor area and covered area are marked correctly?',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCPLANSCRUTINYDRAWING'), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCPLANSCRUTINYDRAWING-05','Whether the drawings uploaded in .dxf format is 
accurately prepared satisfying all the conditions and methods specified in Suvega drawing preparation guidelines applicable?',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCPLANSCRUTINYDRAWING'), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCPLANSCRUTINYDRAWING-06','Whether Front yard, Side yard 1, Side yard 2, Rear yard and plot boundary polygons are accurately marked (Plot boundaries shall be excluding the road widening area if any), 
and conditions with regards to overhangs stipulated in rules 24-10, 24-11, 62-2 are complied with?',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCPLANSCRUTINYDRAWING'), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCPLANSCRUTINYDRAWING-07','The height of building - Whether marked correct and the same 
value of height is given in pdf?',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCPLANSCRUTINYDRAWING'), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCPLANSCRUTINYDRAWING-08','Type of abutting road, building footprint, the minimum distance between plot boundary and building etc. 
accurately specified with correct layer and dimensions?',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCPLANSCRUTINYDRAWING'), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCPLANSCRUTINYDRAWING-09','Whether all the dimensions related to the buildings, 
open well, waste disposal facilities, roads and boundaries are accurately marked?',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCPLANSCRUTINYDRAWING'), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCPLANSCRUTINYDRAWING-10','Whether all additional polygons required for calculating 
the parkings requirements are marked as per the respective occupancies?',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCPLANSCRUTINYDRAWING'), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCPLANSCRUTINYDRAWING-11','Whether the additional polygons for validating 
dimensions/ distances related to parts of buildings including Stair, Lift, ramps, sanitation facilities, exit width, mezzanine floors, height of rooms etc. are accurately marked?',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCPLANSCRUTINYDRAWING'), 0,1,now());


----------------------------------------OCINSPECTION CHECKLIST DETAIL----------------------------------------

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCINSPECTION-01','Whether all relevant details including the following dimensions, 
quantities, numbers, areas, occupancies of the constructed building as per site conditions are exactly in accordance the various details provided in approved drawings and documents?',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCINSPECTION'), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCINSPECTION-02','Over all and floorwise/roomwise horizontal and vertical 
dimensions, occupancies and features of the building(s)/ parts of the building(s) including height of rooms.',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCINSPECTION'), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCINSPECTION-03','Access width',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='OCINSPECTION'), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCINSPECTION-04','Open yards',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='OCINSPECTION'), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCINSPECTION-05','Presence of various types of openings on external walls',true,
false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCINSPECTION'), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCINSPECTION-06','Numbers and dimensions of car parking, two wheeler parking, 
loading and unloading space, parking for physically challenged provided',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCINSPECTION'), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCINSPECTION-07','Biometric waste treatment plant and solar plants',true,false,
(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCINSPECTION'), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCINSPECTION-08','Facilities for liquid waste recycling and reusing, 
Safe facility for waste disposal, Solid waste treatment plant/ In Situ waste treatment plant.',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCINSPECTION'), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCINSPECTION-09','Type and dimensions of corridors giving access to stair and 
other corridors/ Verandahs',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCINSPECTION'), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCINSPECTION-10','Horizontal, vertical clearances and voltage of overhead electric 
lines',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCINSPECTION'), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCINSPECTION-11','Types, width and extent of roads/ lanes/ streets/ cul de sacs 
abutting the plot',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCINSPECTION'), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCINSPECTION-12','Distance from C/L of road to the building, boundary of 
the road to building?',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCINSPECTION'), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCINSPECTION-13','Type, location and various distance parameters related with 
waste treatment facilities and open well.',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCINSPECTION'), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCINSPECTION-14','Distance between buildings',true,false,
(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCINSPECTION'), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCINSPECTION-15','Numbers and dimensions of various features intended for 
physically challenged',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCINSPECTION'), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCINSPECTION-16','Numbers and dimensions of various sanitation facilities',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCINSPECTION'), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCINSPECTION-17','Numbers and dimensions of doors, windows, stairs, ramps and lifts',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCINSPECTION'), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate) values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCINSPECTION-18','Other if any (Specify)',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCINSPECTION'), 0,1,now());


