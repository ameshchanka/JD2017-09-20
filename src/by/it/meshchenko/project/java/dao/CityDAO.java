package by.it.meshchenko.project.java.dao;

import by.it.meshchenko.project.java.beans.City;
import by.it.meshchenko.project.java.connection.ConnectionCreator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CityDAO extends AbstractDAO implements IDAO<City> {

    @Override
    public City read(int id) {
        List<City> items = getAll("WHERE ID=" + id + " LIMIT 0,1");
        if (items.size() > 0) {
            return items.get(0);
        } else
            return null;
    }

    @Override
    public boolean create(City item) {
        String sql = String.format(
                "INSERT INTO addr_cities(" +
                        "Name,CountryId)" +
                        " VALUES('%s',%d);",
                item.getName(),
                item.getCountryId()
        );
        item.setId(executeCreate(sql));
        return (item.getId()>0);
    }

    @Override
    public boolean update(City item) {
        String sql = String.format(
                "UPDATE addr_cities SET " +
                        "Name = '%s', CountryId = %d WHERE Id = %d",
                item.getName(),
                item.getCountryId(),
                item.getId()
        );
        return executeUpdate(sql);
    }

    @Override
    public boolean delete(City item) {
        String sql = String.format(
                "DELETE FROM addr_cities WHERE Id = %d", item.getId());
        return executeUpdate(sql);
    }

    @Override
    public List<City> getAll(String WhereAndOrder) {
        List<City> items = new ArrayList<>();
        String sql = "SELECT * FROM addr_cities " + WhereAndOrder + " ;";
        try (Connection connection = ConnectionCreator.getConnection();
             Statement statement = connection.createStatement()
        ) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                City item = new City();

                item.setId(rs.getInt("Id"));
                item.setName(rs.getString("Name"));
                item.setCountryId(rs.getInt("CountryId"));

                items.add(item);
            }
        } catch (SQLException e) {
            //тут нужно логгирование SQLException(e);
        }
        return items;
    }
}
