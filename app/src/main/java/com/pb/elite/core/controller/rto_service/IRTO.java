package com.pb.elite.core.controller.rto_service;

import com.pb.elite.core.IResponseSubcriber;
import com.pb.elite.core.requestmodel.AdditionHypothecationRequestEntity;
import com.pb.elite.core.requestmodel.AddressEndorsementRCRequestEntity;
import com.pb.elite.core.requestmodel.AnalysisCurrentHealthRequestEntity;
import com.pb.elite.core.requestmodel.AssistanceObtainingRequestEntity;
import com.pb.elite.core.requestmodel.BeyondLifeFinancialRequestEntity;
import com.pb.elite.core.requestmodel.ClaimGuidanceHospRequestEntity;
import com.pb.elite.core.requestmodel.ComplimentaryCreditReportRequestEntity;
import com.pb.elite.core.requestmodel.ComplimentaryLoanAuditRequestEntity;
import com.pb.elite.core.requestmodel.DriverDLVerificationRequestEntity;
import com.pb.elite.core.requestmodel.LifeInsurancePolicyNomineeRequestEntity;
import com.pb.elite.core.requestmodel.MiscReminderPUCRequestEntity;
import com.pb.elite.core.requestmodel.PaperToSmartCardRequestEntity;
import com.pb.elite.core.requestmodel.ProductPriceRequestEntity;
import com.pb.elite.core.requestmodel.ProvideClaimAssRequestEntity;
import com.pb.elite.core.requestmodel.RCRequestEntity;
import com.pb.elite.core.requestmodel.SpecialBenefitsRequestEntity;
import com.pb.elite.core.requestmodel.TransferBenefitsNCBRequestEntity;
import com.pb.elite.core.requestmodel.TransferOwnershipRequestEntity;
import com.pb.elite.core.requestmodel.VehicleRegCertificateRequestEntity;

/**
 * Created by Rahul  on 14-12-2018.
 */

public interface IRTO {

    void saveRCService1(RCRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void saveAssistanceObtaining(AssistanceObtainingRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void saveAdditionHypothecation(AdditionHypothecationRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void saveTransferOwnership(TransferOwnershipRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void saveDriverDLVerification(DriverDLVerificationRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void saveAddressEndorsementRC(AddressEndorsementRCRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void savePaperSmartCard(PaperToSmartCardRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void saveVehicleRegCertificate(VehicleRegCertificateRequestEntity entity, IResponseSubcriber iResponseSubcriber);


}