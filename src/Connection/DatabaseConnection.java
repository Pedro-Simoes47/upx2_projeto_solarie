package Connection;
import Classes.EnergiaSolar;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/energia_solar";
    private static final String  USER = "PedroSimoes";
    private static final String PASSWORD = "admin";

    public static Connection getConnection(){
        try{
            return DriverManager.getConnection(URL,USER,PASSWORD);
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<EnergiaSolar> dadosGerados(){
        List<EnergiaSolar> dadosEnergia = new ArrayList<>();

        String query = "SElECT * FROM dados_energia";

        try(
            Connection connection = getConnection();

            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet rs = statement.executeQuery();
        ){
            while (rs.next()) {
                dadosEnergia.add(new EnergiaSolar(rs.getLong("ID"),
                        rs.getDate("date"),
                        rs.getDouble("energiaGerada"),
                        rs.getLong("panelID")));
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return dadosEnergia;
    }
    public List<EnergiaSolar> dadosGeradosPorDia(Date dataDesejada) {
        List<EnergiaSolar> dadosEnergia = new ArrayList<>();

        String query = "SELECT * FROM dados_energia WHERE date = ?";

        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {

            statement.setDate(1, dataDesejada);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    dadosEnergia.add(new EnergiaSolar(
                            rs.getLong("ID"),
                            rs.getDate("date"),
                            rs.getDouble("energiaGerada"),
                            rs.getLong("panelID")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dadosEnergia;
    }



}
