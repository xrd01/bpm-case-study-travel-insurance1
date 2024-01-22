package de.viadee.bpm.camunda.travelinsuranceprocessapp.worker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import de.viadee.bpm.camunda.travelinsuranceprocessapp.model.TravelInsurance;
import de.viadee.bpm.camunda.travelinsuranceprocessapp.model.TravelInsuranceProcessException;
import de.viadee.bpm.camunda.travelinsuranceprocessapp.model.TravelInsuranceRequest;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import io.camunda.zeebe.spring.client.annotation.VariablesAsType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TransferCustomerId {
    private static final Logger log = LoggerFactory.getLogger(TransferCustomerId.class);
    private final JsonMapper jsonMapper;

    public TransferCustomerId(final JsonMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    @JobWorker(type = "transfer-customer-id", fetchVariables = {"partnerId", "travelInsurance"})
    public void tranferCustomerId(final JobClient client, final ActivatedJob job, @Variable String partnerId, @Variable TravelInsurance travelInsurance) {
        log.info("transferCustomerId job started");
        var raw = job.getVariables();
        log.info("partnerId: {}", partnerId);
//        var travelInsuranceData = fromJson(raw);
        log.info("insuranceData: {}", travelInsurance);
        travelInsurance.getPolicyHolder().setId(partnerId);
        client.newCompleteCommand(job)
                .variable("travelInsurance", travelInsurance)
                .send()
                .join();
    }
    private TravelInsuranceRequest fromJson(final Object jsonData) {
        try {
            return jsonMapper.readValue(String.valueOf(jsonData), TravelInsuranceRequest.class);

        } catch (JsonProcessingException e) {
            log.error("Some error occurred while reading json: {}", e.getMessage());
            throw new TravelInsuranceProcessException(e);
        }
    }
}
