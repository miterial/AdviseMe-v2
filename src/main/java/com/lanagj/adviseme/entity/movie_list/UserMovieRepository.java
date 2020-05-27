package com.lanagj.adviseme.entity.movie_list;

import com.lanagj.adviseme.entity.user.Role;
import com.lanagj.adviseme.entity.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserMovieRepository extends MongoRepository<UserMovie, String> {

}
