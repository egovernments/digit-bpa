alter table EGBPA_PERMIT_RENEWAL add column demand bigint;

alter table EGBPA_PERMIT_RENEWAL add CONSTRAINT fk_permit_renewal_demand FOREIGN KEY (demand)
      REFERENCES eg_demand (id);