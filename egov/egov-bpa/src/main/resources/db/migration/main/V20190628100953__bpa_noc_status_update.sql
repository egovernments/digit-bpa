update egbpa_status set code='Deemed Approved' where code='NOC_DEEMED_APPROVED';
update egbpa_status set code='Rejected' where code='NOC_REJECTED';
update egbpa_status set code='Approved' where code='NOC_APPROVED';
update egbpa_status set code='Initiated' where code='NOC_INITIATED';