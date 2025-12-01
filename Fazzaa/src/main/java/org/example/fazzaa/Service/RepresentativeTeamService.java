package org.example.fazzaa.Service;

import lombok.RequiredArgsConstructor;
import org.example.fazzaa.Model.RepresentativeTeam;
import org.example.fazzaa.Repository.RepresentativeTeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RepresentativeTeamService {

    //connect to the database
    private final RepresentativeTeamRepository representativeTeamRepository;

    // get all representative teams
    public List<RepresentativeTeam> getAllRepresentativeTeams() {
        return representativeTeamRepository.findAll();
    }

    // add new representative team in the database
    public void addRepresentativeTeam(RepresentativeTeam team) {

        representativeTeamRepository.save(team);

    }

    // update a representative team within the database
    public boolean updateRepresentativeTeam(Integer repTeamId, RepresentativeTeam team) {

        // check if the team exists
        RepresentativeTeam oldTeam = representativeTeamRepository.findRepresentativeTeamByRepTeamId(repTeamId);
        if (oldTeam == null) {
            return false;
        }

        // update
        oldTeam.setTeamName(team.getTeamName());

        // save
        representativeTeamRepository.save(oldTeam);
        return true;
    }

    // delete a representative team from the database
    public boolean deleteRepresentativeTeam(Integer repTeamId) {

        // check if the team exists
        RepresentativeTeam team = representativeTeamRepository.findRepresentativeTeamByRepTeamId(repTeamId);
        if (team == null) {
            return false;
        }

        // delete
        representativeTeamRepository.delete(team);
        return true;
    }

    ///  extra endpoints

    // 1 4- get team by name
    public RepresentativeTeam getTeamByName(String teamName) {
        return representativeTeamRepository.findRepresentativeTeamByTeamNameIgnoreCase(teamName);
    }
}
