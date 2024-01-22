package de.viadee.bpm.camunda.travelinsuranceprocessapp.worker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import de.viadee.bpm.camunda.travelinsuranceprocessapp.model.*;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import io.camunda.zeebe.spring.client.annotation.VariablesAsType;
import org.apache.hc.client5.http.utils.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class CompareData {
    private static final Logger log = LoggerFactory.getLogger(CompareData.class);
    private final JsonMapper jsonMapper;

    private HttpHeaders headers;

    private final RestTemplate restTemplate;

    public CompareData(final RestTemplate restTemplate, final JsonMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
        this.restTemplate = restTemplate;
        String plainCreds = "user1:KUDipj7Z";
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        this.headers = headers;
    }

    @JobWorker(type = "compare-system-data-with-input", fetchVariables = {"userAddress", "systemData"})
    public void compareSystemData(final JobClient client, final ActivatedJob job, @Variable Partner systemData,
        @Variable Address userAddress) {
        log.info("compareData job started");
        log.info("systemData: {}", systemData);
        log.info("userAddress: {}", userAddress.toString());
        log.info("systemAddress: {}", systemData.getAddress().toString());
        var isAddressEqual = systemData.getAddress().toString().equals(userAddress.toString());
        log.info("isEqual: {}", isAddressEqual);
        client.newCompleteCommand(job).variable("inputMatchesSystemData", isAddressEqual).send().join();
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
