package org.egov.bpa.master.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.egov.infra.utils.DateUtils;
import org.egov.infra.workflow.entity.StateAware;
import org.egov.pims.commons.Position;

@SuppressWarnings("serial")
@Entity
@Table(name = "EGBPA_STAKEHOLDER_STATE")
@SequenceGenerator(name = StakeHolderState.SEQ_STAKEHOLDER_STATE, sequenceName = StakeHolderState.SEQ_STAKEHOLDER_STATE, allocationSize = 1)
public class StakeHolderState extends StateAware<Position> {

	public static final String SEQ_STAKEHOLDER_STATE = "SEQ_STAKEHOLDER_STATE";
	@Id
	@GeneratedValue(generator = SEQ_STAKEHOLDER_STATE, strategy = GenerationType.SEQUENCE)
	private Long id;

	@OneToOne
	@JoinColumn(name = "stakeholder", nullable = false)
	private StakeHolder stakeHolder;

	@Override
	public String getStateDetails() {
		return String.format("Applicant Name: %s Acknowledgement Number %s Dated %s ",
				stakeHolder.getName() == null ? "Not Specified" : stakeHolder.getName(),
				stakeHolder.getCode() == null ? null : stakeHolder.getCode(),
				stakeHolder.getCreateDate() == null ? DateUtils.toDefaultDateFormat(new Date())
						: DateUtils.toDefaultDateFormat(stakeHolder.getCreateDate()));
	}

	@Override
	public String myLinkId() {
		return stakeHolder.getId().toString();
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	protected void setId(final Long id) {
		this.id = id;
	}

	public StakeHolder getStakeHolder() {
		return stakeHolder;
	}

	public void setStakeHolder(StakeHolder stakeHolder) {
		this.stakeHolder = stakeHolder;
	}

}
