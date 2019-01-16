alter table egbpa_inspection rename column isboundaryDrawingSubmitted to boundaryDrawingSubmitted;

update egbpa_mstr_permit_conditions set description ='Permit issued based on the Approval Certificate of  PCB as per the Order No. ' where code='PC01';
update egbpa_mstr_permit_conditions set description ='Permit issued based on the Approval Certificate of Fire & Rescue Service as per the Order No. ' where code='PC02';
update egbpa_mstr_permit_conditions set description ='Permit issued based on the Approval Certificate of Ministry of Environment & Forests as per the Order No. ' where code='PC03';
update egbpa_mstr_permit_conditions set description ='Permit issued based on the concurrence of CTP vide No. ' where code='PC04';
update egbpa_mstr_permit_conditions set description ='Permit issued based on the Approval Certificate of Railway Department as per the Order No. ' where code='PC05';
update egbpa_mstr_permit_conditions set description ='Permit issued based on the Approval Certificate of Defence Department as per the Order No. ' where code='PC06';
update egbpa_mstr_permit_conditions set description ='Permit issued based on the concurrence of Art & Heritage Commission vide No. ' where code='PC07';
update egbpa_mstr_permit_conditions set description ='Permit issued based on the concurrence of KCZMA vide No. ' where code='PC08';
update egbpa_mstr_permit_conditions set description ='Permit issued based on the Approval Certificate of DMRC/Light Metro Project as per the Order No. ' where code='PC09';
update egbpa_mstr_permit_conditions set description ='Permit issued based on the Approval Certificate of National Highway Authority as per the Order. ' where code='PC10';
update egbpa_mstr_permit_conditions set description ='The work shall be carried out strictly following the KMBR provision under the supervision of a qualified engineer as per the plans. The name and address of engineer having supervision over the construction shall be informed in advance.' where code='PC12';
update egbpa_mstr_permit_conditions set description ='Arrangement should be there to dispose the solid and liquid waste from the proposed building inside the owners site itself and it should not be diverted to any public place drain or public place. A drawing showing the treatment plant proposed shall be submitted in advance.' where code='PC14';
update egbpa_mstr_permit_conditions set description ='Disabled persons entries shall be made as per KMBR.' where code='PC16';
update egbpa_mstr_permit_conditions set description ='For the development, that happens and warrants tree to be cut, at least same number of trees shall be planted, maintained and brought up with in the plot in the immediate vicinity of the development.' where code='PC22';
update egbpa_mstr_permit_conditions set description ='Pipe composting /biogas plants/vermi composting etc.. anyone of these should be provided with appropriate technique at the time of completion of building, for processing organic waste at source itself.' where code='PC23';