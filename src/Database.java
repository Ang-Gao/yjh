import java.sql.*;

public class Database {

    //This method executes a query and returns the number of albums for the artist with name artistName
    public int getTitles(String artistName) throws ClassNotFoundException, SQLException {
        int titleNum = 0;
        //Implement this method
        //Prevent SQL injections!
        Class.forName("org.postgresql.Driver");

        String url = Credentials.URL;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DriverManager.getConnection(url,Credentials.USERNAME, Credentials.PASSWORD);
            String sql = "select albumid from album where artistid = (select artistid from artist where name=?)";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.clearParameters();

            preparedStatement.setString(1,artistName);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                titleNum ++;
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            preparedStatement.close();
            connection.close();
        }
        return titleNum;
    }

    // This method establishes a DB connection & returns a boolean status
    public boolean establishDBConnection()  {
        //Implement this method
        //5 sec timeout
        Connection connection = null;
        Statement st = null;
        try {
            Class.forName("org.postgresql.Driver");
            String url = Credentials.URL;
            connection = DriverManager.getConnection(url,Credentials.USERNAME, Credentials.PASSWORD);
            st = connection.createStatement();
            //timeout --> SQLTimeoutException is thrown
            st.setQueryTimeout(5);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            try {
                connection.close();
                st.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}