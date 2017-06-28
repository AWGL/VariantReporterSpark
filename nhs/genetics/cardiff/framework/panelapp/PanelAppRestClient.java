package nhs.genetics.cardiff.framework.panelapp;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;

/**
 * REST client for PanelApp
 */
public class PanelAppRestClient {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static PanelAppResponse searchByGene(String gene) throws IOException {
        return objectMapper.readValue(new URL(
                "https://bioinfo.extge.co.uk/crowdsourcing/WebServices/search_genes/" + gene + "?format=json"
        ), PanelAppResponse.class);
    }
}