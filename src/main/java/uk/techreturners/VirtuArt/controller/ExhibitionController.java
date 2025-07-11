package uk.techreturners.VirtuArt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import uk.techreturners.VirtuArt.model.dto.ExhibitionDTO;
import uk.techreturners.VirtuArt.model.dto.ExhibitionDetailDTO;
import uk.techreturners.VirtuArt.model.request.AddArtworkRequest;
import uk.techreturners.VirtuArt.model.request.CreateExhibitionRequest;
import uk.techreturners.VirtuArt.model.request.UpdateExhibitionRequest;
import uk.techreturners.VirtuArt.service.ExhibitionService;

import java.util.List;

@RestController
@RequestMapping("api/v1/exhibitions")
public class ExhibitionController {

    @Autowired
    private ExhibitionService exhibitionService;

    @GetMapping
    public ResponseEntity<List<ExhibitionDTO>> getUserExhibitions(@AuthenticationPrincipal Jwt jwt) {
        return new ResponseEntity<>(exhibitionService.getAllUserExhibitions(jwt), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ExhibitionDTO> createExhibition(
            @RequestBody CreateExhibitionRequest request,
            @AuthenticationPrincipal Jwt jwt) {
        return new ResponseEntity<>(exhibitionService.createUserExhibition(request, jwt), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExhibition(@PathVariable("id") String exhibitId, @AuthenticationPrincipal Jwt jwt) {
        return new ResponseEntity<>(exhibitionService.deleteExhibition(exhibitId, jwt), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExhibitionDetailDTO> getExhibitionDetails(
            @PathVariable("id") String exhibitId, @AuthenticationPrincipal Jwt jwt) {
        return new ResponseEntity<>(exhibitionService.getExhibitionById(exhibitId, jwt), HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ExhibitionDTO> addArtworkToExhibition(
            @PathVariable("id") String exhibitId,
            @RequestBody AddArtworkRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return new ResponseEntity<>(exhibitionService.addArtworkToExhibition(exhibitId, request, jwt), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/{apiId}/{source}")
    public ResponseEntity<Void> deleteArtworkFromExhibition(
            @PathVariable("id") String exhibitionId,
            @PathVariable("apiId") String apiId,
            @PathVariable("source") String source,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return new ResponseEntity<>(
                exhibitionService.removeArtworkFromExhibition(exhibitionId, apiId, source, jwt), HttpStatus.NO_CONTENT
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ExhibitionDTO> patchExhibitionDetails(
            @PathVariable("id") String id,
            @RequestBody UpdateExhibitionRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return new ResponseEntity<>(exhibitionService.updateExhibitionDetails(id, request, jwt), HttpStatus.OK);
    }
}