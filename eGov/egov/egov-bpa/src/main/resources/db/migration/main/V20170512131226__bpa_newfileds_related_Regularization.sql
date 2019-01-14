alter table egbpa_application add column feeAmountRecieptNo character varying(128);
alter table egbpa_application add column revisedPermitNumber character varying(128);
alter table egbpa_application add column revisedApplicationNumber character varying(128);
alter table egbpa_application add column approvedReceiptDate date;
alter table egbpa_application add column isExistingApprovedPlan boolean;
alter table egbpa_sitedetail add column conststages bigint;
alter table egbpa_sitedetail add column stateOfConstruction character varying(128);
alter table egbpa_sitedetail add column isappForRegularization boolean;
