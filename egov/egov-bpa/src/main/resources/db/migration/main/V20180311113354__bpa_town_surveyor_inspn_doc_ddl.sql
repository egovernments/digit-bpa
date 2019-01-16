CREATE TABLE egbpa_ts_inspn_documents
(
  application bigint,
  fileStoreId bigint,
CONSTRAINT fk_egbpa_apptsinspndocs_id FOREIGN KEY (application)
      REFERENCES egbpa_application (id),
CONSTRAINT fk_egbpa_tsinspndocs_filemapper FOREIGN KEY (fileStoreId)
      REFERENCES eg_filestoremap (id)
);