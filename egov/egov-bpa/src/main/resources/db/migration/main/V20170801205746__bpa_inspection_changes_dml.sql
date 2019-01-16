alter table egbpa_documentscrutiny drop column isBoundaryDrawingSubmitted;
alter table egbpa_documentscrutiny drop column rightToMakeConstruction;
alter table egbpa_documentscrutiny drop column typeofLand;


alter table egbpa_inspection add column isBoundaryDrawingSubmitted boolean default false;
alter table egbpa_inspection add column rightToMakeConstruction boolean default false;
alter table egbpa_inspection add column typeofLand character varying(128);


delete from egbpa_docket_detail where  checklistdetail in (select id from egbpa_mstr_chklistdetail where code in ('123','126','129','133','136','153','156','159','163','166','169'));

delete from egbpa_mstr_chklistdetail where code in ('123','126','129','133','136','153','156','159','163','166','169');