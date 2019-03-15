
ALTER TABLE egbpa_mstr_slotmapping ADD COLUMN slotApplicationType bigint;

      ALTER TABLE egbpa_mstr_slotmapping
ADD CONSTRAINT fk_egbpa_mstr_slotmapping_applicationtype
   FOREIGN KEY (slotApplicationType)
   REFERENCES egbpa_mstr_applicationtype (id);

ALTER TABLE egbpa_mstr_slotmapping ALTER COLUMN applicationtype DROP NOT NULL;


ALTER TABLE egbpa_mstr_slotmapping drop column applicationtype;
