package com.tts.TechTalentTwitter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tts.TechTalentTwitter.service.TweetService;
import com.tts.TechTalentTwitter.service.UserService;

public class UserController {
	@Autowired
    private UserService userService;
    
    @Autowired
    private TweetService tweetService;
    
    //add more code here
    @GetMapping(value = "/users/{username}")
    public String getUser(@PathVariable(value="username") String username, Model model) {	
    }

public String getUser(@PathVariable(value="username") String username, Model model) {	
    User user = userService.findByUsername(username);
    List<Tweet> tweets = tweetService.findAllByUser(user);
    model.addAttribute("tweetList", tweets);
    model.addAttribute("user", user);
    return "user";
}

}
