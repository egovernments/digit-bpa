ALTER TABLE egbpa_application ADD COLUMN applicationtype bigint;

      ALTER TABLE egbpa_application
ADD CONSTRAINT fk_egbpa_application_applicationtype
   FOREIGN KEY (applicationtype)
   REFERENCES egbpa_mstr_applicationtype (id);
   
alter table egbpa_application add column infrastructureCost bigint;

alter table egbpa_application add column constructionCost bigint;