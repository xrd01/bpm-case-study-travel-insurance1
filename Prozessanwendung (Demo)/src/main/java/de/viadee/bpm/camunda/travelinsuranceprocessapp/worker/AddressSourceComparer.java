package de.viadee.bpm.camunda.travelinsuranceprocessapp.worker;

import de.viadee.bpm.camunda.travelinsuranceprocessapp.model.Address;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.VariablesAsType;

import java.util.Objects;

public class AddressSourceComparer {

    @JobWorker(type = "compare-address-sources", fetchVariables = {"addressAsPerUser", "addressAsPerSystem"})
    public void compare(final JobClient client, final ActivatedJob job,
                        @VariablesAsType Address addressAsPerUser, @VariablesAsType Address addressAsPerSystem) {
        boolean addressSourcesConsistent = sourcesConsistent(addressAsPerUser, addressAsPerSystem);
        client.newCompleteCommand(job)
                .variable("addressSourcesConsistent", addressSourcesConsistent)
                .send()
                .join();
    }

    private boolean sourcesConsistent(Address addressAsPerUser, Address addressAsPerSystem) {
        return Objects.equals(addressAsPerUser, addressAsPerSystem);
    }
}
