alter table egbpa_inspection_application add column buildingconstructionstage bigint;
alter table egbpa_inspection_application add constraint FK_EGBPA_MSTR_INSP_CONSTSTAGE FOREIGN KEY (buildingconstructionstage)
         REFERENCES EGBPA_BUILDING_CONSTRUCTIONSTAGE (ID) ;

