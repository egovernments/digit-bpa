ALTER TABLE Egf_ACcountcode_Purpose DROP COLUMN createdby;
ALTER TABLE Egf_ACcountcode_Purpose DROP COLUMN createddate;
ALTER TABLE Egf_ACcountcode_Purpose DROP COLUMN modifieddate;
ALTER TABLE Egf_ACcountcode_Purpose DROP COLUMN modifiedby;
ALTER TABLE Egf_ACcountcode_Purpose ADD COLUMN version numeric;

ALTER TABLE TransactionSummary ADD COLUMN version numeric;

ALTER TABLE Egf_ACcountcode_Purpose ADD COLUMN createdby bigint;
ALTER TABLE Egf_ACcountcode_Purpose ADD COLUMN createdDate timestamp;
ALTER TABLE Egf_ACcountcode_Purpose ADD COLUMN lastModifiedBy bigint;
ALTER TABLE Egf_ACcountcode_Purpose ADD COLUMN lastModifiedDate timestamp; 
 
ALTER TABLE TransactionSummary ADD COLUMN createdby bigint;
ALTER TABLE TransactionSummary ADD COLUMN createdDate timestamp;