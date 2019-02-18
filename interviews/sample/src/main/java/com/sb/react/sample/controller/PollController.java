package com.sb.react.sample.controller;

import com.sb.react.sample.repository.PollRepository;
import com.sb.react.sample.repository.UserRepository;
import com.sb.react.sample.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.nio.ch.PollSelectorProvider;

@RestController
@RequestMapping("/api/polls")
public class PollController {

    @Autowired
    private PollRepository pollRepository ;

    @Autowired
    private VoteRepository voteRepository ;

    @Autowired
    private UserRepository userRepository ;

    @Autowired
    private PollService pollService ;
}
