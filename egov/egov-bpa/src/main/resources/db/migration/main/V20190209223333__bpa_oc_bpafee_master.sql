---------------- OC BPA FEE MASTER---------------
----------------OCCUPANCY CERTIFICATE FEES
INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'OCSanctionFee',(select id from egbpa_mstr_servicetype where  description=  'New Construction'),'50','Occupancy Certificate Fees',true,true,false,1,now());

INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'OCSanctionFee',(select id from egbpa_mstr_servicetype where  description=  'Reconstruction'),'150','Occupancy Certificate Fees',true,true,false,1,now());

INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'OCSanctionFee',(select id from egbpa_mstr_servicetype where  description=  'Addition or Extension'),'250','Occupancy Certificate Fees',true,true,false,1,now());

INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'OCSanctionFee',(select id from egbpa_mstr_servicetype where  description=  'Change in occupancy'),'350','Occupancy Certificate Fees',true,true,false,1,now());

INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'OCSanctionFee',(select id from egbpa_mstr_servicetype where  description=  'Alteration'),'450','Occupancy Certificate Fees',true,true,false,1,now());

 
 ------------- OC OTHER FESS
 INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'OCSanctionFee',(select id from egbpa_mstr_servicetype where  description=  'New Construction'),'51','Other Fees',true,true,false,1,now());

INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'OCSanctionFee',(select id from egbpa_mstr_servicetype where  description=  'Reconstruction'),'151','Other Fees',true,true,false,1,now());

INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'OCSanctionFee',(select id from egbpa_mstr_servicetype where  description=  'Addition or Extension'),'251','Other Fees',true,true,false,1,now());

INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'OCSanctionFee',(select id from egbpa_mstr_servicetype where  description=  'Change in occupancy'),'351','Other Fees',true,true,false,1,now());

INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'OCSanctionFee',(select id from egbpa_mstr_servicetype where  description=  'Alteration'),'451','Other Fees',true,true,false,1,now());

-----------------------OC BPA FEE DETAILS----------------


insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate,additionaltype) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='50'),null,null,10,null,now(),1,now(),'Residential'  );

insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate,additionaltype) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='50'),null,null,10,null,now(),1,now(),'Others'  );


insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate,additionaltype) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='150'),null,null,10,null,now(),1,now(),'Residential'  );

insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate,additionaltype) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='150'),null,null,10,null,now(),1,now(),'Others'  );


insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate,additionaltype) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='250'),null,null,10,null,now(),1,now(),'Residential'  );

insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate,additionaltype) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='250'),null,null,10,null,now(),1,now(),'Others'  );


insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate,additionaltype) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='350'),null,null,10,null,now(),1,now(),'Residential'  );

insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate,additionaltype) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='350'),null,null,10,null,now(),1,now(),'Others'  );


insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate,additionaltype) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='450'),null,null,10,null,now(),1,now(),'Residential'  );

insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate,additionaltype) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='450'),null,null,10,null,now(),1,now(),'Others'  );




 insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate,additionaltype) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='51'),null,null,0,null,now(),1,now(),null );


 insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate,additionaltype) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='151'),null,null,0,null,now(),1,now(),null );


 insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate,additionaltype) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='251'),null,null,0,null,now(),1,now(),null );


 insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate,additionaltype) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='351'),null,null,0,null,now(),1,now(),null );


 insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate,additionaltype) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='451'),null,null,0,null,now(),1,now(),null );



