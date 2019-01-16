alter table  egbpa_mstr_servicetype add column  isAmenity boolean NOT NULL DEFAULT false;



CREATE TABLE egbpa_ApplicationAmenity
(
  application bigint NOT NULL,
  amenityId bigint NOT NULL,
  CONSTRAINT fk_egbpa_appAmenity_Id FOREIGN KEY(application)
         REFERENCES egbpa_application(ID),
           CONSTRAINT fk_egbpa_appAmenity_servId FOREIGN KEY(amenityId)
         REFERENCES egbpa_mstr_servicetype(ID)
);