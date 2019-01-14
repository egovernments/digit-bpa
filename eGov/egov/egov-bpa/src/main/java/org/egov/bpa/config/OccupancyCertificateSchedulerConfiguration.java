/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2017>  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
package org.egov.bpa.config;

import org.egov.bpa.config.conditions.BpaSchedulerConfigCondition;
import org.egov.bpa.config.properties.BpaApplicationSettings;
import org.egov.bpa.scheduler.oc.OcCancelAppointmentJob;
import org.egov.bpa.scheduler.oc.OcOpenSlotsJob;
import org.egov.bpa.scheduler.oc.OcScheduleAppointmentJob;
import org.egov.infra.config.scheduling.QuartzSchedulerConfiguration;
import org.egov.infra.config.scheduling.SchedulerConfigCondition;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.quartz.CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING;

@Configuration
@Conditional(SchedulerConfigCondition.class)
public class OccupancyCertificateSchedulerConfiguration extends QuartzSchedulerConfiguration {

    @Autowired
    private BpaApplicationSettings bpaApplicationSettings;

    @Bean(destroyMethod = "destroy")
    @Conditional(BpaSchedulerConfigCondition.class)
    public SchedulerFactoryBean bpaOcScheduler(DataSource dataSource) {
        SchedulerFactoryBean bpaOcScheduler = createScheduler(dataSource);
        bpaOcScheduler.setSchedulerName("bpa-oc-scheduler");
        bpaOcScheduler.setAutoStartup(true);
        bpaOcScheduler.setOverwriteExistingJobs(true);
        List<Trigger> triggers = new ArrayList<>();
        if (bpaApplicationSettings.openSlotsSchedulerEnabledForOC())
            triggers.add(ocOpenSlotsCronTrigger().getObject());
        if (bpaApplicationSettings.scheduleAppointmentSchedulerEnabledForOC())
            triggers.add(ocScheduleAppointmentCronTrigger().getObject());
        if (bpaApplicationSettings.cancelApplicationsSchedulerEnabledForOC())
            triggers.add(ocCancelAppointmentCronTrigger().getObject());
        bpaOcScheduler.setTriggers(triggers.toArray(new Trigger[triggers.size()]));
        return bpaOcScheduler;
    }

    @Bean
    public CronTriggerFactoryBean ocOpenSlotsCronTrigger() {
        CronTriggerFactoryBean openSlotsCron = new CronTriggerFactoryBean();
        openSlotsCron.setJobDetail(ocOpenSlotsJobDetail().getObject());
        openSlotsCron.setGroup("BPA_OC_TRIGGER_GROUP");
        openSlotsCron.setName("BPA_OC_OPEN_SLOT_TRIGGER");
        openSlotsCron.setCronExpression(bpaApplicationSettings.getValue("bpa.oc.open.slots.job.cron"));
        openSlotsCron.setMisfireInstruction(MISFIRE_INSTRUCTION_DO_NOTHING);
        return openSlotsCron;
    }

    @Bean
    public CronTriggerFactoryBean ocScheduleAppointmentCronTrigger() {
        CronTriggerFactoryBean scheduleAppointmentCron = new CronTriggerFactoryBean();
        scheduleAppointmentCron.setJobDetail(ocScheduleAppointmentJobDetail().getObject());
        scheduleAppointmentCron.setGroup("BPA_OC_TRIGGER_GROUP");
        scheduleAppointmentCron.setName("BPA_OC_SCHEDULE_APPOINTMENT_TRIGGER");
        scheduleAppointmentCron.setCronExpression(bpaApplicationSettings.getValue("bpa.oc.schedule.appointment.job.cron"));
        scheduleAppointmentCron.setMisfireInstruction(MISFIRE_INSTRUCTION_DO_NOTHING);
        return scheduleAppointmentCron;
    }

    @Bean
    public CronTriggerFactoryBean ocCancelAppointmentCronTrigger() {
        CronTriggerFactoryBean cancelAppointmentCron = new CronTriggerFactoryBean();
        cancelAppointmentCron.setJobDetail(ocCancelAppointmentJobDetail().getObject());
        cancelAppointmentCron.setGroup("BPA_OC_TRIGGER_GROUP");
        cancelAppointmentCron.setName("BPA_OC_CANCEL_APPOINTMENT_TRIGGER");
        cancelAppointmentCron.setCronExpression(bpaApplicationSettings.getValue("bpa.oc.cancel.applications.job.cron"));
        cancelAppointmentCron.setMisfireInstruction(MISFIRE_INSTRUCTION_DO_NOTHING);
        return cancelAppointmentCron;
    }

    @Bean("ocOpenSlotsJob")
    public OcOpenSlotsJob openSlotsJob() {
        return new OcOpenSlotsJob();
    }

    @Bean("ocScheduleAppointmentJob")
    public OcScheduleAppointmentJob scheduleAppointmentJob() {
        return new OcScheduleAppointmentJob();
    }

    @Bean("ocCancelAppointmentJob")
    public OcCancelAppointmentJob scheduleDocumentScrutinyJob() {
        return new OcCancelAppointmentJob();
    }

    @Bean
    public JobDetailFactoryBean ocOpenSlotsJobDetail() {
        JobDetailFactoryBean openSlotsJobDetail = new JobDetailFactoryBean();
        openSlotsJobDetail.setGroup("BPA_OC_JOB_GROUP");
        openSlotsJobDetail.setName("BPA_OC_OPEN_SLOTS_JOB");
        openSlotsJobDetail.setDurability(true);
        openSlotsJobDetail.setJobClass(OcOpenSlotsJob.class);
        openSlotsJobDetail.setRequestsRecovery(true);
        Map<String, String> jobDetailMap = new HashMap<>();
        jobDetailMap.put("jobBeanName", "ocOpenSlotsJob");
        jobDetailMap.put("userName", "system");
        jobDetailMap.put("cityDataRequired", "true");
        jobDetailMap.put("moduleName", "bpa");
        openSlotsJobDetail.setJobDataAsMap(jobDetailMap);
        return openSlotsJobDetail;
    }

    @Bean
    public JobDetailFactoryBean ocScheduleAppointmentJobDetail() {
        JobDetailFactoryBean scheduleAppointmentJobDetail = new JobDetailFactoryBean();
        scheduleAppointmentJobDetail.setGroup("BPA_OC_JOB_GROUP");
        scheduleAppointmentJobDetail.setName("BPA_OC_SCHEDULE_APPOINTMENT_JOB");
        scheduleAppointmentJobDetail.setDurability(true);
        scheduleAppointmentJobDetail.setJobClass(OcScheduleAppointmentJob.class);
        scheduleAppointmentJobDetail.setRequestsRecovery(true);
        Map<String, String> jobDetailMap = new HashMap<>();
        jobDetailMap.put("jobBeanName", "ocScheduleAppointmentJob");
        jobDetailMap.put("userName", "system");
        jobDetailMap.put("cityDataRequired", "true");
        jobDetailMap.put("moduleName", "bpa");
        scheduleAppointmentJobDetail.setJobDataAsMap(jobDetailMap);
        return scheduleAppointmentJobDetail;
    }

    @Bean
    public JobDetailFactoryBean ocCancelAppointmentJobDetail() {
       JobDetailFactoryBean cancelAppointmentJobDetail = new JobDetailFactoryBean();
        cancelAppointmentJobDetail.setGroup("BPA_OC_JOB_GROUP");
        cancelAppointmentJobDetail.setName("BPA_OC_CANCEL_APPOINTMENT_JOB");
        cancelAppointmentJobDetail.setDurability(true);
        cancelAppointmentJobDetail.setJobClass(OcCancelAppointmentJob.class);
        cancelAppointmentJobDetail.setRequestsRecovery(true);
        Map<String, String> jobDetailMap = new HashMap<>();
        jobDetailMap.put("jobBeanName", "ocCancelAppointmentJob");
        jobDetailMap.put("userName", "system");
        jobDetailMap.put("cityDataRequired", "true");
        jobDetailMap.put("moduleName", "bpa");
        cancelAppointmentJobDetail.setJobDataAsMap(jobDetailMap);
        return cancelAppointmentJobDetail;
    }
}
