/**
 * 
 */
package fr.imie.videodb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.imie.videodb.dto.FilmDTO;
import fr.imie.videodb.exception.VideoDBPersistenceException;

/**
 * @author imie
 *
 */
public class FilmDAO implements IFilmDAO {

	/* (non-Javadoc)
	 * @see fr.imie.videodb.dao.IFilmDAO#findAllFilm()
	 */
	@Override
	public List<FilmDTO> findAllFilm() throws VideoDBPersistenceException {
		List<FilmDTO> retour = new ArrayList<FilmDTO>();

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/videotheque", "postgres",
					"postgres");

			String query = "select id, libell, duree, datesortie from video";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				FilmDTO filmDTO = new FilmDTO();
				filmDTO.setId(resultSet.getInt("id"));
				filmDTO.setLibelle(resultSet.getString("libelle"));
				filmDTO.setDuree(resultSet.getInt("duree"));
				filmDTO.setDateSortie(resultSet.getDate("datesortie"));
				retour.add(filmDTO);
			}

		} catch (SQLException e) {
			throw new VideoDBPersistenceException(e);
		} finally {
			try {

				if (resultSet != null && !resultSet.isClosed()) {
					resultSet.close();
				}
				if (statement != null && !statement.isClosed()) {
					statement.close();
				}
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}

			} catch (SQLException e) {
				throw new VideoDBPersistenceException(e);
			}
		}

		return retour;
	}
}