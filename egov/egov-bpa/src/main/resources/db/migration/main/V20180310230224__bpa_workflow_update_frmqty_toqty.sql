update eg_wf_matrix set fromqty=0, toqty=300 where fromqty=0 and toqty=299 and objecttype ='BpaApplication';

update eg_wf_matrix set fromqty=301, toqty=750 where fromqty=300 and toqty=749 and objecttype ='BpaApplication';

update eg_wf_matrix set fromqty=751, toqty=1500 where fromqty=750 and toqty=1499 and objecttype ='BpaApplication';

update eg_wf_matrix set fromqty=1501, toqty=2500 where fromqty=1500 and toqty=9999 and objecttype ='BpaApplication';

update eg_wf_matrix set fromqty=2501, toqty=1000000 where fromqty=10000 and toqty=1000000 and objecttype ='BpaApplication';