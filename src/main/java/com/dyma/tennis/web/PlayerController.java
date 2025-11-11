package com.dyma.tennis.web;

import com.dyma.tennis.Player;
import com.dyma.tennis.PlayerList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Tag(name = "Players API", description = "Tennis Players API")
@RestController
@RequestMapping("/players")
public class PlayerController {


    @Operation(summary = "Finds players", description = "Finds players")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Players list",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Player.class)))})
    })


    @GetMapping
    public List<Player> list() {
        return PlayerList.ALL;
    }


    @Operation(summary = "Finds player", description = "Finds player")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "player",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Player.class)))})
    })

    @GetMapping("/{lastName}")
    public  Player getByLastName(@PathVariable("lastName") String lastName) {
        return PlayerList.ALL.stream()
                .filter(player -> lastName.equals(player.lastName())).findFirst().orElseThrow();// Filtre sur le nom de famille
              
    }


    @Operation(summary = "Create player", description = "Create player")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Created player",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Player.class)))})
    })
    @PostMapping
    public Player createPlayer (@RequestBody @Valid Player player){
        return player;
    }


    @Operation(summary = "update a player", description = "update a player")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "updated a player",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Player.class)))})
    })
    @PutMapping
    public Player updatePlayer (@RequestBody @Valid Player player){
        return player;
    }

    @Operation(summary = "delete a player", description = "delete a player")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Player has been deleted"
            )
    })


     @DeleteMapping("/{lastName}")
    public void deletePlayerByLastName(@PathVariable("lastName") String lastename){


    }
}
