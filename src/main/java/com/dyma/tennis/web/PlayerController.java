package com.dyma.tennis.web;

import com.dyma.tennis.Error;
import com.dyma.tennis.Player;
import com.dyma.tennis.PlayerTosave;
import com.dyma.tennis.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Players API", description = "Tennis Players API")
@RestController
@RequestMapping("/players")
public class PlayerController {

@Autowired
private PlayerService playerService;

    @Operation(summary = "Finds players", description = "Finds players")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Players list",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Player.class)))})
    })


    @GetMapping
    public List<Player> list() {
        return playerService.getAllPlayers();
    }


    @Operation(summary = "Finds player", description = "Finds player")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "player",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Player.class)))}),

            @ApiResponse(responseCode = "404", description = "Player with specified last name not found",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Error.class)))})
    })

    @GetMapping("/{lastName}")
    public  Player getByLastName(@PathVariable("lastName") String lastName) {
        return playerService.getPlayerByLastName(lastName);
              
    }


    @Operation(summary = "Create player", description = "Create player")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Created player",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PlayerTosave.class)))})
    ,
            @ApiResponse(responseCode = "404", description = "Player with specified last name Aready exist",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Error.class)))})

    })
    @PostMapping
    public Player createPlayer (@RequestBody @Valid PlayerTosave playerTosave){
        return playerService.createPlayer(playerTosave);
    }


    @Operation(summary = "update a player", description = "update a player")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "updated a player",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PlayerTosave.class)))}),
            @ApiResponse(responseCode = "404", description = "Player with specified last name not found",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Error.class)))})
    })
    @PutMapping
    public Player updatePlayer (@RequestBody @Valid PlayerTosave playerTosave){
        return playerService.update(playerTosave);
    }

    @Operation(summary = "delete a player", description = "delete a player")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Player has been deleted"
            )
    })

    @ApiResponse(responseCode = "404", description = "Player with specified last name not found",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Error.class)))})
     @DeleteMapping("/{lastName}")
    public void deletePlayerByLastName(@PathVariable("lastName") String lastename){
        playerService.delete(lastename);
    }
}
