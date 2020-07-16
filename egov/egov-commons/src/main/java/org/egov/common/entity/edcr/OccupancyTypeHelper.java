/*
 * eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 * accountability and the service delivery of the government  organizations.
 *
 *  Copyright (C) <2019>  eGovernments Foundation
 *
 *  The updated version of eGov suite of products as by eGovernments Foundation
 *  is available at http://www.egovernments.org
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see http://www.gnu.org/licenses/ or
 *  http://www.gnu.org/licenses/gpl.html .
 *
 *  In addition to the terms of the GPL license to be adhered to in using this
 *  program, the following additional terms are to be complied with:
 *
 *      1) All versions of this program, verbatim or modified must carry this
 *         Legal Notice.
 *      Further, all user interfaces, including but not limited to citizen facing interfaces,
 *         Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
 *         derived works should carry eGovernments Foundation logo on the top right corner.
 *
 *      For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
 *      For any further queries on attribution, including queries on brand guidelines,
 *         please contact contact@egovernments.org
 *
 *      2) Any misrepresentation of the origin of the material is prohibited. It
 *         is required that all modified versions of this material be marked in
 *         reasonable ways as different from the original version.
 *
 *      3) This license does not grant any rights to any user of the program
 *         with regards to rights under trademark law for use of the trade names
 *         or trademarks of eGovernments Foundation.
 *
 *  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */

package org.egov.common.entity.edcr;

import java.io.Serializable;
import java.util.Objects;

import org.egov.common.entity.dcr.helper.OccupancyHelperDetail;

public class OccupancyTypeHelper implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 80L;

    private OccupancyHelperDetail type;
    private OccupancyHelperDetail subtype;
    private OccupancyHelperDetail usage;
    private OccupancyHelperDetail convertedType;
    private OccupancyHelperDetail convertedSubtype;
    private OccupancyHelperDetail convertedUsage;

    public OccupancyTypeHelper() {
        //
    }

    public OccupancyHelperDetail getType() {
        return type;
    }

    public void setType(OccupancyHelperDetail type) {
        this.type = type;
    }

    public OccupancyHelperDetail getSubtype() {
        return subtype;
    }

    public void setSubtype(OccupancyHelperDetail subtype) {
        this.subtype = subtype;
    }

    public OccupancyHelperDetail getUsage() {
        return usage;
    }

    public void setUsage(OccupancyHelperDetail usage) {
        this.usage = usage;
    }

    public OccupancyHelperDetail getConvertedType() {
        return convertedType;
    }

    public void setConvertedType(OccupancyHelperDetail convertedType) {
        this.convertedType = convertedType;
    }

    public OccupancyHelperDetail getConvertedSubtype() {
        return convertedSubtype;
    }

    public void setConvertedSubtype(OccupancyHelperDetail convertedSubtype) {
        this.convertedSubtype = convertedSubtype;
    }

    public OccupancyHelperDetail getConvertedUsage() {
        return convertedUsage;
    }

    public void setConvertedUsage(OccupancyHelperDetail convertedUsage) {
        this.convertedUsage = convertedUsage;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof OccupancyTypeHelper))
            return false;
        final OccupancyTypeHelper that = (OccupancyTypeHelper) other;
        if (that.usage != null  && usage!=null) {
            return that.usage != null && Objects.equals(usage.getCode(), that.usage.getCode());
        } else if (that.subtype != null && subtype!=null && subtype.getCode()!=null) { 
            return that.subtype != null && Objects.equals(subtype.getCode(), that.subtype.getCode());
        }
        return that.type != null && type!=null && Objects.equals(type.getCode(), that.type.getCode());
    }

    @Override
    public int hashCode() {
        if (usage != null) {
            return Objects.hash(usage != null ? usage.getCode() : "");
        } else if (subtype != null) {
            return Objects.hash(subtype != null ? subtype.getCode() : "");
        }
        return Objects.hash(type != null ? type.getCode() : "");
    }
}
