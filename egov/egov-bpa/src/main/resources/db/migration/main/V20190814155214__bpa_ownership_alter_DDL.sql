alter table egbpa_ownership_transfer add column mailPwdRequired boolean default false;

alter table egbpa_ownership_transfer
add column owner_id bigint,
 add column demand bigint,
add constraint FK_egbpa_ownership_OWNER FOREIGN KEY (owner_id)
  REFERENCES EGBPA_APPLICANT(ID),
 add CONSTRAINT FK_egbpa_ownership_DEMAND FOREIGN KEY (DEMAND)
      REFERENCES EG_DEMAND (ID);

alter table egbpa_ownership_transfer add column   remarks character varying(1000);

alter table egbpa_ownership_transfer add column isactive boolean DEFAULT true NOT NULL;

alter table egbpa_ownership_transfer add column admissionfeeAmount numeric  DEFAULT 0;