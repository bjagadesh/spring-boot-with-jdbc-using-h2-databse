/*
 * 
 * You can use the following import statements
 * 
 * import org.springframework.web.bind.annotation.*;
 * import java.util.*;
 *
 */

// Write your code here
package com.example.song.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

import com.example.song.service.*;
import com.example.song.model.*;

@RestController
public class SongController{

    @Autowired
    public SongH2Service songservice;

    @GetMapping("/songs")
    public ArrayList<Song> getsongs(){
        return songservice.getSongs();
    }

    @GetMapping("/songs/{songId}")
    public Song getsong(@PathVariable("songId") int songId){
        return songservice.getsong(songId);
    }

    @PostMapping("/songs")
    public Song addsong(@RequestBody Song song){
        return songservice.addsong(song);
    }

    @PutMapping("/songs/{songId}")
    public Song updatesong(@PathVariable("songId") int songId,@RequestBody Song song){
        return songservice.updatesong(songId, song);
    }

    @DeleteMapping("/songs/{songId}")
    public void deletesong(@PathVariable("songId") int songId){
        songservice.deletesong(songId);
    }
}