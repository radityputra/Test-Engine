package radit.si6a.kontak.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    @Update
    int updateUser(User user);

    @Query("DELETE FROM users WHERE id = :userId")
    int deleteUser(int userId);
}
