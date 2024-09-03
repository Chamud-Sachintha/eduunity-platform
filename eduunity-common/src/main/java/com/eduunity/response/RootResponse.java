package com.eduunity.response;

import lombok.Data;

import java.util.List;

@Data
public class RootResponse {
    private List<Candidate> candidates;

    // Getters and setters
    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }
}
