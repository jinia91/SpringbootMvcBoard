package com.project.board.board.controller;

import org.junit.jupiter.api.Test;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class withGitControllerTest {

    @Value("${git.gitId}")
    private String gitId;
    @Value("${git.gitToken}")
    private String gitToken;

    @Test
    void testGit() throws IOException {

        System.out.println("gitToken = " + gitToken);
        
        GitHub gitHub = new GitHubBuilder().withOAuthToken("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").build();
        GHRepository repository = gitHub.getRepository("jinia91/TIL");
        repository.createContent().content("test").message("test").branch("master").commit();


    }

}