package uk.techreturners.VirtuArt.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import uk.techreturners.VirtuArt.exception.ApiServiceException;
import uk.techreturners.VirtuArt.model.aicapi.AicApiArtworkResult;
import uk.techreturners.VirtuArt.model.aicapi.AicApiElasticSearchQuery;
import uk.techreturners.VirtuArt.model.aicapi.AicApiSearchResult;

@Repository
public class AicApiDAO {

    @Autowired
    @Qualifier("aicApiWebClient")
    private WebClient webClient;

    public AicApiSearchResult getArtworks(String limit, String page) {
        try {
            String params = "query[term][is_public_domain]=true&sort[updated_at][order]=desc&fields=id,title,artist_title,date_display,image_id";
            return webClient
                    .get()
                    .uri("/search?"+ params + "&limit=" + limit + "&page=" + page)
                    .retrieve()
                    .bodyToMono(AicApiSearchResult.class)
                    .block();
        } catch (WebClientResponseException e) {
            HttpStatus status = HttpStatus.valueOf(e.getStatusCode().value());
            throw new ApiServiceException(e.getMessage(), status);
        } catch (WebClientRequestException e) {
            throw new ApiServiceException(e.getMessage());
        }
    }

    public AicApiArtworkResult getArtworkById(String artworkId) {
        String params = "?fields=id,title,artist_title,image_id,place_of_origin,date_display,description,alt_image_ids,medium_display,department_title";
        try {
            return webClient
                    .get()
                    .uri("/" + artworkId + params)
                    .retrieve()
                    .bodyToMono(AicApiArtworkResult.class)
                    .block();
        } catch (WebClientResponseException e) {
            HttpStatus status = HttpStatus.valueOf(e.getStatusCode().value());
            throw new ApiServiceException(e.getMessage(), status);
        } catch (WebClientRequestException e) {
            throw new ApiServiceException(e.getMessage());
        }
    }

    public AicApiSearchResult getArtworksByElasticSearchQuery(AicApiElasticSearchQuery searchQuery) {
        try {
            return webClient
                    .post()
                    .uri("/search")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(searchQuery)
                    .retrieve()
                    .bodyToMono(AicApiSearchResult.class)
                    .block();
        } catch (WebClientResponseException e) {
            HttpStatus status = HttpStatus.valueOf(e.getStatusCode().value());
            throw new ApiServiceException(e.getMessage(), status);
        } catch (WebClientRequestException e) {
            throw new ApiServiceException(e.getMessage());
        }
    }
}