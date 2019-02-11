package uk.gov.ons.collection.PersistenceLayer.entity;

/*
    Name: contributorEntity.java

    Description: contributorEntity.java provides the model for the table we're connecting to

*/
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
//The @Table decorator here takes the arguments for table ContributorEntity and the schema
@IdClass(ContributorKey.class)
@Table(name = "Contributor", schema = "dev01")
//@Indexed
@ApiModel(value = "Contributor", description = "A Contributor entity")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ContributorEntity {

    // Default public constructor needed to run queries as constructor is otherwise private with the Builder class
    // and causes issues when using Hibernate
    public ContributorEntity() {

    }
    //@Id points out where the id column is located
    @ApiModelProperty(value = "The primary key in the table for this entity")
    // Alternative way of using composite key - Currently using @IdClass annotation
    //@EmbeddedId
    //private ContributorKey conPrimaryKey;

    @JsonManagedReference
    @OneToMany(mappedBy = "contributorEntity")
//    @JoinColumns({
//            @JoinColumn(updatable = false, insertable = false, name = "reference", referencedColumnName = "reference"),
//            @JoinColumn(updatable = false, insertable = false, name = "period", referencedColumnName = "period"),
//            @JoinColumn(updatable = false, insertable = false, name = "survey", referencedColumnName = "survey")
//    })
    private List<ResponseEntity> responseEntityList;

    @Id
    @Column(name = "reference", length = 11, nullable = false)
    private String reference;

    @Id
    @Column(name = "period", length = 6, nullable = false)
    private String period;

    @Id
    @Column(name = "survey", length = 3, nullable = false)
    private String survey;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="formid", insertable = false, updatable = false)
    private FormEntity formEntity;

    @Column(name = "formid")
    private Integer formid;

    @Column(name = "status", length = 11, nullable = false)
    private String status;

    @Column(name = "receiptdate", length = 7, nullable = false)
    private Timestamp receiptDate;

    @Column(name = "lockedby", length = 16, nullable = false)
    private String lockedBy;

    @Column(name = "lockeddate", length = 7, nullable = false)
    private Timestamp lockedDate;

    @Column(name = "createdby", length = 16, nullable = false)
    private String createdBy;

    @Column(name = "createddate", length = 7, nullable = false)
    private Timestamp createdDate;

    @Column(name = "lastupdatedby", length = 16, nullable = false)
    private String lastUpdatedBy;

    @Column(name = "lastupdateddate", length = 7, nullable = false)
    private Timestamp lastUpdatedDate;

    @Column(name = "formtype", length = 4, nullable = false)
    private String formType;

    @Column(name = "checkletter", length = 1, nullable = false)
    private String checkletter;

    @Column(name = "frozensicoutdated", length = 5, nullable = false)
    private String frozenSicOutdated;

    @Column(name = "rusicoutdated", length = 5, nullable = false)
    private String ruSicOutdated;

    @Column(name = "frozensic", length = 5, nullable = false)
    private String frozenSic;

    @Column(name = "rusic", length = 5, nullable = false)
    private String ruSic;

    @Column(name = "frozenemployees", length = 14, nullable = false)
    private Integer frozenEmployees;

    @Column(name = "employees", nullable = false)
    private Integer employees;

    @Column(name = "frozenemployment", nullable = false)
    private Integer frozenEmployment;

    @Column(name = "employment", nullable = false)
    private Integer employment;

    @Column(name = "frozenfteemployment", nullable = false)
    private BigDecimal frozenFteEmployment;

    @Column(name = "fteemployment", nullable = false)
    private BigDecimal fteEmployment;

    @Column(name = "frozenturnover", nullable = false)
    private Integer frozenTurnover;

    @Column(name = "turnover", nullable = false)
    private Integer turnover;

    @Column(name = "enterprisereference", length = 10, nullable = false)
    private String enterpriseReference;

    @Column(name = "wowenterprisereference", length = 10, nullable = false)
    private String wowEnterpriseReference;

    @Column(name = "cellnumber", nullable = false)
    private String cellNumber;

    @Column(name = "currency", length = 1, nullable = false)
    private String currency;

    @Column(name = "vatreference", length = 12, nullable = false)
    private String vatReference;

    @Column(name = "payereference", length = 13, nullable = false)
    private String payeReference;

    @Column(name = "companyregistrationnumber", length = 8, nullable = false)
    private String companyRegistrationNumber;

    @Column(name = "numberlivelocalunits",  nullable = false)
    private Integer numberLiveLocalUnits;

    @Column(name = "numberlivevat", nullable = false)
    private Integer numberLiveVat;

    @Column(name = "numberlivepaye", nullable = false)
    private Integer numberLivePaye;

    @Column(name = "legalstatus", length = 1, nullable = false)
    private String legalStatus;

    @Column(name = "reportingunitmarker", length = 5, nullable = false)
    private String reportingUnitMarker;

    @Column(name = "region", length = 2, nullable = false)
    private String region;

    @Column(name = "birthdate", length = 5, nullable = false)
    private String birthDate;

    @Column(name = "enterprisename", length = 107, nullable = false)
    private String enterpriseName;

    @Column(name = "referencename", length = 107, nullable = false)
    private String name;

    @Column(name = "referenceaddress", length = 154, nullable = false)
    private String address;

    @Column(name = "referencepostcode", length = 8, nullable = false)
    private String postcode;

    @Column(name = "tradingstyle", length = 107, nullable = false)
    private String tradingStyle;

    @Column(name = "contact", length = 30, nullable = false)
    private String contact;

    @Column(name = "telephone", length = 20, nullable = false)
    private String telephone;

    @Column(name = "fax", length = 20, nullable = false)
    private String fax;

    @Column(name = "selectiontype", length = 1, nullable = false)
    private String selectionType;

    @Column(name = "inclusionexclusion", length = 1, nullable = false)
    private String inclusionExclusion;


    // Getters &


    public List<ResponseEntity> getResponseEntityList() {
        return responseEntityList;
    }

    public void setResponseEntityList(List<ResponseEntity> responseEntityList) {
        this.responseEntityList = responseEntityList;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getPeriod() {
        return period;
    }

    public Integer getFormid() {
        return formid;
    }

    public void setFormid(Integer formid) {
        this.formid = formid;
    }

    public void setPeriod(String period) {
        this.period = period;

    }

    public FormEntity getFormEntity() {
        return formEntity;
    }

    public void setFormEntity(FormEntity formEntity) {
        this.formEntity = formEntity;
    }

    public String getSurvey() {
        return survey;
    }

    public void setSurvey(String survey) {
        this.survey = survey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(Timestamp receiptDate) {
        this.receiptDate = receiptDate;
    }

    public String getLockedBy() {
        return lockedBy;
    }

    public void setLockedBy(String lockedBy) {
        this.lockedBy = lockedBy;
    }

    public Timestamp getLockedDate() {
        return lockedDate;
    }

    public void setLockedDate(Timestamp lockedDate) {
        this.lockedDate = lockedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Timestamp getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public String getCheckletter() {
        return checkletter;
    }

    public void setCheckletter(String checkletter) {
        this.checkletter = checkletter;
    }

    public String getFrozenSicOutdated() {
        return frozenSicOutdated;
    }

    public void setFrozenSicOutdated(String frozenSicOutdated) {
        this.frozenSicOutdated = frozenSicOutdated;
    }

    public String getRuSicOutdated() {
        return ruSicOutdated;
    }

    public void setRuSicOutdated(String ruSicOutdated) {
        this.ruSicOutdated = ruSicOutdated;
    }

    public String getFrozenSic() {
        return frozenSic;
    }

    public void setFrozenSic(String frozenSic) {
        this.frozenSic = frozenSic;
    }

    public String getRuSic() {
        return ruSic;
    }

    public void setRuSic(String ruSic) {
        this.ruSic = ruSic;
    }

    public Integer getFrozenEmployees() {
        return frozenEmployees;
    }

    public void setFrozenEmployees(Integer frozenEmployees) {
        this.frozenEmployees = frozenEmployees;
    }

    public Integer getEmployees() {
        return employees;
    }

    public void setEmployees(Integer employees) {
        this.employees = employees;
    }

    public Integer getFrozenEmployment() {
        return frozenEmployment;
    }

    public void setFrozenEmployment(Integer frozenEmployment) {
        this.frozenEmployment = frozenEmployment;
    }

    public Integer getEmployment() {
        return employment;
    }

    public void setEmployment(Integer employment) {
        this.employment = employment;
    }

    public BigDecimal getFrozenFteEmployment() {
        return frozenFteEmployment;
    }

    public void setFrozenFteEmployment(BigDecimal frozenFteEmployment) {
        this.frozenFteEmployment = frozenFteEmployment;
    }

    public BigDecimal getFteEmployment() {
        return fteEmployment;
    }

    public void setFteEmployment(BigDecimal fteEmployment) {
        this.fteEmployment = fteEmployment;
    }

    public Integer getFrozenTurnover() {
        return frozenTurnover;
    }

    public void setFrozenTurnover(Integer frozenTurnover) {
        this.frozenTurnover = frozenTurnover;
    }

    public Integer getTurnover() {
        return turnover;
    }

    public void setTurnover(Integer turnover) {
        this.turnover = turnover;
    }

    public String getEnterpriseReference() {
        return enterpriseReference;
    }

    public void setEnterpriseReference(String enterpriseReference) {
        this.enterpriseReference = enterpriseReference;
    }

    public String getWowEnterpriseReference() {
        return wowEnterpriseReference;
    }

    public void setWowEnterpriseReference(String wowEnterpriseReference) {
        this.wowEnterpriseReference = wowEnterpriseReference;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getVatReference() {
        return vatReference;
    }

    public void setVatReference(String vatReference) {
        this.vatReference = vatReference;
    }

    public String getPayeReference() {
        return payeReference;
    }

    public void setPayeReference(String payeReference) {
        this.payeReference = payeReference;
    }

    public String getCompanyRegistrationNumber() {
        return companyRegistrationNumber;
    }

    public void setCompanyRegistrationNumber(String companyRegistrationNumber) {
        this.companyRegistrationNumber = companyRegistrationNumber;
    }

    public Integer getNumberLiveLocalUnits() {
        return numberLiveLocalUnits;
    }

    public void setNumberLiveLocalUnits(Integer numberLiveLocalUnits) {
        this.numberLiveLocalUnits = numberLiveLocalUnits;
    }

    public Integer getNumberLiveVat() {
        return numberLiveVat;
    }

    public void setNumberLiveVat(Integer numberLiveVat) {
        this.numberLiveVat = numberLiveVat;
    }

    public Integer getNumberLivePaye() {
        return numberLivePaye;
    }

    public void setNumberLivePaye(Integer numberLivePaye) {
        this.numberLivePaye = numberLivePaye;
    }

    public String getLegalStatus() {
        return legalStatus;
    }

    public void setLegalStatus(String legalStatus) {
        this.legalStatus = legalStatus;
    }

    public String getReportingUnitMarker() {
        return reportingUnitMarker;
    }

    public void setReportingUnitMarker(String reportingUnitMarker) {
        this.reportingUnitMarker = reportingUnitMarker;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getTradingStyle() {
        return tradingStyle;
    }

    public void setTradingStyle(String tradingStyle) {
        this.tradingStyle = tradingStyle;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getSelectionType() {
        return selectionType;
    }

    public void setSelectionType(String selectionType) {
        this.selectionType = selectionType;
    }

    public String getInclusionExclusion() {
        return inclusionExclusion;
    }

    public void setInclusionExclusion(String inclusionExclusion) {
        this.inclusionExclusion = inclusionExclusion;
    }

    // Builder class
    public static class Builder {

        // Required parameters
        private final String reference;
        private final String period;
        private final String survey;

        // Optional parameters
        private int formId = 0;
        private String status = "";
        private Timestamp receiptDate;
        private String lockedBy = "";
        private Timestamp lockedDate;
        private String formType = "";
        private String createdBy = "";
        private Timestamp createdDate;
        private String lastUpdatedBy = "";
        private Timestamp lastUpdatedDate;
        private String checkletter = "";
        private String frozenSicOutdated = "";
        private String ruSicOutdated = "";
        private String frozenSic = "";
        private String ruSic = "";
        private int frozenEmployees = 0;
        private int employees = 0;
        private int frozenEmployment = 0;
        private int employment = 0;
        private BigDecimal frozenFteEmployment = BigDecimal.valueOf(0);
        private BigDecimal fteEmployment = BigDecimal.valueOf(0);
        private int frozenTurnover = 0;
        private int turnover = 0;
        private String enterpriseReference = "";
        private String wowEnterpriseReference = "";
        private String cellNumber = "";
        private String currency = "";
        private String vatReference = "";
        private String payeReference = "";
        private String companyRegistrationNumber = "";
        private int numberLiveLocalUnits = 0;
        private int numberLiveVat = 0;
        private int numberLivePaye = 0;
        private String legalStatus = "";
        private String reportingUnitMarker = "";
        private String region = "";
        private String birthDate = "";
        private String enterpriseName = "";
        private String name = "";
        private String address = "";
        private String postcode = "";
        private String tradingStyle = "";
        private String contact = "";
        private String telephone = "";
        private String fax = "";
        private String selectionType = "";
        private String inclusionExclusion = "";

        public Builder(String reference, String period, String survey) {
            this.reference = reference;
            this.period = period;
            this.survey = survey;
        }

        public Builder formId(int formId) {
            this.formId = formId;
            return this;
        }

        public Builder status(String status) {
            this.status = (status == null) ? "" : status;
            return this;
        }

        public Builder receiptDate(Timestamp receiptDate) {
            this.receiptDate = receiptDate;
            return this;
        }


        public Builder lockedBy(String lockedBy) {
            this.lockedBy = (lockedBy == null) ? "" : lockedBy;
            return this;
        }

        public Builder lockedDate(Timestamp lockedDate) {
            this.lockedDate = lockedDate;
            return this;
        }

        public Builder createdBy(String createdBy) {
            this.createdBy = (createdBy == null) ? "" : createdBy;
            return this;
        }

        public Builder createdDate(Timestamp createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public Builder lastUpdatedBy(String lastUpdatedBy) {
            this.lastUpdatedBy = (lastUpdatedBy == null) ? "" : lastUpdatedBy;
            return this;
        }

        public Builder lastUpdatedDate(Timestamp lastUpdatedDate) {
            this.lastUpdatedDate = lastUpdatedDate;
            return this;
        }

        public Builder formType(String formType) {
            this.formType = (formType == null) ? "" : formType;
            return this;
        }

        public Builder checkletter(String checkletter) {
            this.checkletter = (checkletter == null) ? "" : checkletter;
            return this;
        }

        public Builder frozenSicOutdated(String frozenSicOutdated) {
            this.frozenSicOutdated = (frozenSicOutdated == null) ? "" : frozenSicOutdated;
            return this;
        }

        public Builder ruSicOutdated(String ruSicOutdated) {
            this.ruSicOutdated = (ruSicOutdated == null) ? "" : ruSicOutdated;
            return this;
        }

        public Builder frozenSic(String frozenSic) {
            this.frozenSic = (frozenSic == null) ? "" : frozenSic;
            return this;
        }

        public Builder ruSic(String ruSic) {
            this.ruSic = (ruSic == null) ? "" : ruSic;
            return this;
        }

        public Builder frozenEmployees(int frozenEmployees) {
            this.frozenEmployees = frozenEmployees;
            return this;
        }

        public Builder employees(int employees) {
            this.employees = employees;
            return this;
        }

        public Builder frozenEmployment(int frozenEmployment) {
            this.frozenEmployment = frozenEmployment;
            return this;
        }

        public Builder employment(int employment) {
            this.employment = employment;
            return this;
        }

        public Builder frozenFteEmployment(BigDecimal frozenFteEmployment) {
            this.frozenFteEmployment = frozenFteEmployment;
            return this;
        }

        public Builder fteEmployment(BigDecimal fteEmployment) {
            this.fteEmployment = fteEmployment;
            return this;
        }

        public Builder frozenTurnover(int frozenTurnover) {
            this.frozenTurnover = frozenTurnover;
            return this;
        }

        public Builder turnover(int turnover) {
            this.turnover = turnover;
            return this;
        }

        public Builder enterpriseReference(String enterpriseReference) {
            this.enterpriseReference = (enterpriseReference == null) ? "" : enterpriseReference;
            return this;
        }

        public Builder wowEnterpriseReference(String wowEnterpriseReference) {
            this.wowEnterpriseReference = (wowEnterpriseReference == null) ? "" : wowEnterpriseReference;
            return this;
        }

        public Builder cellNumber(String cellNumber) {
            this.cellNumber = (cellNumber == null) ? "" : cellNumber;
            return this;
        }

        public Builder currency(String currency) {
            this.currency = (currency == null) ? "" : currency;
            return this;
        }

        public Builder vatReference(String vatReference) {
            this.vatReference = (vatReference == null) ? "" : vatReference;
            return this;
        }

        public Builder payeReference(String payeReference) {
            this.payeReference = (payeReference == null) ? "" : payeReference;
            return this;
        }

        public Builder companyRegistrationNumber(String companyRegistrationNumber) {
            this.companyRegistrationNumber = (companyRegistrationNumber == null) ? "" : companyRegistrationNumber;
            return this;
        }

        public Builder numberLiveLocalUnits(int numberLiveLocalUnits) {
            this.numberLiveLocalUnits = numberLiveLocalUnits;
            return this;
        }

        public Builder numberLiveVat(int numberLiveVat) {
            this.numberLiveVat = numberLiveVat;
            return this;
        }

        public Builder numberLivePaye(int numberLivePaye) {
            this.numberLivePaye = numberLivePaye;
            return this;
        }

        public Builder legalStatus(String legalStatus) {
            this.legalStatus = (legalStatus == null) ? "" : legalStatus;
            return this;
        }

        public Builder reportingUnitMarker(String reportingUnitMarker) {
            this.reportingUnitMarker = (reportingUnitMarker == null) ? "" : reportingUnitMarker;
            return this;
        }

        public Builder region(String region) {
            this.region = (region == null) ? "" : region;
            return this;
        }

        public Builder birthDate(String birthDate) {
            this.birthDate = (birthDate == null) ? "" : birthDate;
            return this;
        }

        public Builder enterpriseName(String enterpriseName) {
            this.enterpriseName = (enterpriseName == null) ? "" : enterpriseName;
            return this;
        }

        public Builder name(String name) {
            this.name = (name == null) ? "" : name;
            return this;
        }

        public Builder address(String address) {
            this.address = (address == null) ? "" : address;
            return this;
        }

        public Builder postcode(String postcode) {
            this.postcode = (postcode == null) ? "" : postcode;
            return this;
        }

        public Builder tradingStyle(String tradingStyle) {
            this.tradingStyle = (tradingStyle == null) ? "" : tradingStyle;
            return this;
        }

        public Builder contact(String contact) {
            this.contact = (contact == null) ? "" : contact;
            return this;
        }

        public Builder telephone(String telephone) {
            this.telephone = (telephone == null) ? "" : telephone;
            return this;
        }

        public Builder fax(String fax) {
            this.fax = (fax == null) ? "" : fax;
            return this;
        }

        public Builder selectionType(String selectionType) {
            this.selectionType = (selectionType == null) ? "" : selectionType;
            return this;
        }

        public Builder inclusionExclusion(String inclusionExclusion) {
            this.inclusionExclusion = (inclusionExclusion == null) ? "" : inclusionExclusion;
            return this;
        }


        public ContributorEntity build() {
            return new ContributorEntity(this);
        }
    }

    private ContributorEntity (Builder builder) {
        this.reference = builder.reference;
        this.period = builder.period;
        this.survey = builder.survey;
        this.formid = builder.formId;
        this.status = builder.status;
        this.receiptDate = builder.receiptDate;
        this.lockedBy = builder.lockedBy;
        this.lockedDate = builder.lockedDate;
        this.createdBy = builder.createdBy;
        this.createdDate = builder.createdDate;
        this.lastUpdatedBy = builder.lastUpdatedBy;
        this.lastUpdatedDate = builder.lastUpdatedDate;
        this.formType = builder.formType;
        this.checkletter = builder.checkletter;
        this.frozenSicOutdated = builder.frozenSicOutdated;
        this.ruSicOutdated = builder.ruSicOutdated;
        this.frozenSic = builder.frozenSic;
        this.ruSic = builder.ruSic;
        this.frozenEmployees = builder.frozenEmployees;
        this.employees = builder.employees;
        this.frozenEmployment = builder.frozenEmployment;
        this.employment = builder.employment;
        this.frozenFteEmployment = builder.frozenFteEmployment;
        this.fteEmployment = builder.fteEmployment;
        this.frozenTurnover = builder.frozenTurnover;
        this.turnover = builder.turnover;
        this.enterpriseReference = builder.enterpriseReference;
        this.wowEnterpriseReference = builder.wowEnterpriseReference;
        this.cellNumber = builder.cellNumber;
        this.currency = builder.currency;
        this.vatReference = builder.vatReference;
        this.payeReference = builder.payeReference;
        this.companyRegistrationNumber = builder.companyRegistrationNumber;
        this.numberLiveLocalUnits = builder.numberLiveLocalUnits;
        this.numberLiveVat = builder.numberLiveVat;
        this.numberLivePaye = builder.numberLivePaye;
        this.legalStatus = builder.legalStatus;
        this.reportingUnitMarker = builder.reportingUnitMarker;
        this.region = builder.region;
        this.birthDate = builder.birthDate;
        this.enterpriseName = builder.enterpriseName;
        this.name = builder.name;
        this.address = builder.address;
        this.postcode = builder.postcode;
        this.tradingStyle = builder.tradingStyle;
        this.contact = builder.contact;
        this.telephone = builder.telephone;
        this.fax = builder.fax;
        this.selectionType = builder.selectionType;
        this.inclusionExclusion = builder.inclusionExclusion;
    }

}

