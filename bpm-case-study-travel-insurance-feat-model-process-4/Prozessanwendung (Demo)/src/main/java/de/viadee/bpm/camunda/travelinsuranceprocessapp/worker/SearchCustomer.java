package de.viadee.bpm.camunda.travelinsuranceprocessapp.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import io.camunda.zeebe.spring.client.annotation.VariablesAsType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.stereotype.Component;

@Component
public class SearchCustomer {
    private static final Logger log = LoggerFactory.getLogger(SearchCustomer.class);

    @JobWorker(type = "search-customer", fetchVariables = {"firstName"})
    public void searchCustomer(final JobClient client, final ActivatedJob job) {
        log.info("Search customer job started");
        var raw = job.getVariables();
        log.info(raw);
        client.newCompleteCommand(job)
                .variable("foundCustomerWithName", false)
                .send()
                .join();
    }
}
