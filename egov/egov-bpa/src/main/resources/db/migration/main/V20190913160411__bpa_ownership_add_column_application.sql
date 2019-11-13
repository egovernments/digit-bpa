alter table egbpa_ownership_transfer drop column parent ;

alter table egbpa_ownership_transfer add column application bigint;
alter table egbpa_ownership_transfer add column parent bigint;

alter table egbpa_ownership_transfer add CONSTRAINT fk_egbpa_ownership_trnsfr_parent FOREIGN KEY (parent)
      REFERENCES egbpa_ownership_transfer (id);
alter table egbpa_ownership_transfer add CONSTRAINT fk_egbpa_ownership_trnsfr_application FOREIGN KEY (application)
      REFERENCES egbpa_application (id);


