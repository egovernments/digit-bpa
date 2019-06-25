alter table egbpa_docket_common add column checklisttype bigint;

alter table egbpa_docket_common add constraint fk_docket_common_checklisttype FOREIGN KEY (checklisttype) REFERENCES eg_checklist_type (ID) ;