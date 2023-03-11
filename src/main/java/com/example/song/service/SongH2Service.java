/*
 * 
 * You can use the following import statements
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.jdbc.core.JdbcTemplate;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * import java.util.ArrayList;
 * 
 */

// Write your code here
package com.example.song.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.*;

import com.example.song.repository.SongRepository;
import com.example.song.model.Song;
import com.example.song.model.SongRowMapper;

 @Service
public class SongH2Service implements SongRepository{

    @Autowired
    private JdbcTemplate db;

    @Override
    public ArrayList<Song> getSongs(){
        List<Song> allsongs=db.query("select * from playlist",new SongRowMapper());
        //System.out.println(allsongs+" allsongs");
        ArrayList<Song> songs=new ArrayList<>(allsongs);
        return songs;
    }

    @Override
    public Song getsong(int songId){
        try{
            Song song=db.queryForObject("select * from playlist where songId = ? ",new SongRowMapper(),songId);
            return song;
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Song addsong(Song song){
        //System.out.println("song"+ song.getSongName()+"song"+song);
        db.update("insert into playlist(songName,lyricist,singer,musicDirector) values (?,?,?,?)",song.getSongName(),song.getLyricist(),song.getSinger(),song.getMusicDirector());
        Song newsong=db.queryForObject("select * from playlist where songName = ? and lyricist = ? and singer = ? and musicDirector = ?",new SongRowMapper(),song.getSongName(),song.getLyricist(),song.getSinger(),song.getMusicDirector());
        return newsong;
    }

    @Override
    public Song updatesong(int id,Song song){
        //System.out.println("id"+id);
        if(song.getSongName()!=null){
            db.update("update playlist set songName = ? where songId = ?",song.getSongName(),id);
        }
        if(song.getLyricist()!=null){
            db.update("update playlist set lyricist = ? where songId = ?",song.getLyricist(),id);
        }
        if(song.getSinger()!=null){
            db.update("update playlist set singer = ? where songId = ?",song.getSinger(),id);
        }
        if(song.getMusicDirector()!=null){
            db.update("update playlist set musicDirector = ? where songId = ?",song.getMusicDirector(),id);
        }
            //Song updatedsong=db.queryForObject("select * from playlist where songId = ?",new SongRowMapper(),id);
        return getsong(id);
    }

    @Override
    public void deletesong(int id){
        //System.out.println("id "+id);
        db.update("delete from playlist where songId = ?",id);
    }
}
