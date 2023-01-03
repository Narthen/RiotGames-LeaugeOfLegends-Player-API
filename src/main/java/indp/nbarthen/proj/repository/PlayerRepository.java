package indp.nbarthen.proj.repository;

import org.springframework.data.repository.CrudRepository;


//PlayerAcc - name of class we want saved to database (will include all of the data for a give acc (i.e name, lvl, rank.... match history)
//String indicated the @Id used to identify a given user.

public interface PlayerRepository extends CrudRepository<PlayerAcc, String> {}
