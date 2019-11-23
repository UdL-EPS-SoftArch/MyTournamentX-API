package cat.udl.eps.softarch.mytournamentx.controller;

import cat.udl.eps.softarch.mytournamentx.domain.Tournament;
import cat.udl.eps.softarch.mytournamentx.domain.types.TournamentState;
import cat.udl.eps.softarch.mytournamentx.exception.ForbiddenException;
import cat.udl.eps.softarch.mytournamentx.service.TournamentService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@BasePathAwareController
public class TournamentController {

    @Autowired
    TournamentService tournamentService;

    @PostMapping("/tournaments/{name}/generate")
    public @ResponseBody
    PersistentEntityResource generateTournamentBracket(
            @PathVariable(value="name") String name,
            PersistentEntityResourceAssembler resourceAssembler) throws Exception {

        Tournament tournament = tournamentService.getTournament(name);

        if(tournament.getState() != TournamentState.UNINITIALIZED)
        {
            throw new ForbiddenException();
        }
        tournamentService.createTournament(tournament);

        return resourceAssembler.toResource(tournament);
    }


    @PostMapping("/tournaments/{name}/start")
    public @ResponseBody
    PersistentEntityResource startTournament(
            @PathVariable(value="name") String name,
            PersistentEntityResourceAssembler resourceAssembler) throws Exception {

        Tournament tournament = tournamentService.getTournament(name);

        if(tournament.getState() != TournamentState.INITIALIZED)
        {
            throw new ForbiddenException();
        }
        tournament = tournamentService.advanceState(tournament);

        return resourceAssembler.toResource(tournament);
    }


}
