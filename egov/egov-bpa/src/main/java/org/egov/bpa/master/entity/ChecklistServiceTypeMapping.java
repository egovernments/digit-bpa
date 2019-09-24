package org.egov.bpa.master.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.egov.common.entity.bpa.Checklist;
import org.egov.common.entity.bpa.ChecklistType;
import org.egov.infra.persistence.entity.AbstractAuditable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "egbpa_checklist_servicetype_mapping")
@SequenceGenerator(name = ChecklistServiceTypeMapping.SEQ, sequenceName = ChecklistServiceTypeMapping.SEQ, allocationSize = 1)
@JsonIgnoreProperties({ "createdBy", "lastModifiedBy" })
public class ChecklistServiceTypeMapping extends AbstractAuditable {

    private static final long serialVersionUID = 1L;

    public static final String SEQ = "seq_egbpa_checklist_servicetype_mapping";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "checklist")
    private Checklist checklist;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "servicetype")
    private ServiceType serviceType;

    @NotNull
    @Column(name = "isrequired")
    private boolean required;

    @NotNull
    @Column(name = "ismandatory")
    private boolean mandatory;

    private transient ChecklistType checklistType;

    private transient List<ChecklistServiceTypeMapping> mappingList = new ArrayList<>();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public List<ChecklistServiceTypeMapping> getMappingList() {
        return mappingList;
    }

    public void setMappingList(List<ChecklistServiceTypeMapping> mappingList) {
        this.mappingList = mappingList;
    }

    public Checklist getChecklist() {
        return checklist;
    }

    public void setChecklist(Checklist checklist) {
        this.checklist = checklist;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public ChecklistType getChecklistType() {
        return checklistType;
    }

    public void setChecklistType(ChecklistType checklistType) {
        this.checklistType = checklistType;
    }

}
