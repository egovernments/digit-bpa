 ALTER TABLE Egf_ACcountcode_Purpose    Drop COLUMN version  ; 
 ALTER TABLE TransactionSummary  Drop COLUMN version  ;

 ALTER TABLE Egf_ACcountcode_Purpose    Drop COLUMN createdby ;
 ALTER TABLE Egf_ACcountcode_Purpose    Drop COLUMN createdDate ;
 ALTER TABLE Egf_ACcountcode_Purpose    Drop COLUMN lastModifiedBy ;
 ALTER TABLE Egf_ACcountcode_Purpose    Drop COLUMN lastModifiedDate ; 
  
 ALTER TABLE TransactionSummary  Drop COLUMN createdby ;
 ALTER TABLE TransactionSummary  Drop COLUMN createdDate ;

ALTER TABLE Egf_ACcountcode_Purpose ADD COLUMN createdby bigint;
ALTER TABLE Egf_ACcountcode_Purpose ADD COLUMN createddate date;
ALTER TABLE Egf_ACcountcode_Purpose ADD COLUMN modifieddate date;
ALTER TABLE Egf_ACcountcode_Purpose ADD COLUMN modifiedby bigint;