import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

/**
 *
 * @author Logarithm
 */
public class GalleryServlet extends HttpServlet {
    
    /**
     * Gets the web page and allows the user to search for an image in the Image Database. 
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException 
     */
    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Image Search Result</title>");
        out.println("<link rel=\"stylesheet\" href=\"indexDesc.css\">");
        out.println("</head>");
        out.println("<body>");
        String creatorName = request.getParameter("creator");
        String keyword = request.getParameter("keyword");
        if((!creatorName.equals("")) && (keyword.equals("")))
        {
            String results = searchCreator(creatorName);
            if(results != null) {
                out.println("<div align='center'><h1>Image Search Result</h1>"
                    + "<p>" + results
                    + "</p></div>");
            }
            else {
                out.println("<div align='center'><h1>Image Search Result</h1>"
                + "<p>Nothing was found."
                + "</p></div>");
            }
        }
        else if((creatorName.equals("")) && (!keyword.equals(""))) {
            String results = searchKeyword(keyword);
            if(results != null) {
                out.println("<div align='center'><h1>Image Search Result</h1>"
                    + "<p>" + results
                    + "</p></div>"); 
            }
            else {
                out.println("<div align='center'><h1>Image Search Result</h1>"
                + "<p>Sorry, your search is with no results."
                + "</p></div>");
            }
        }
        else if((!creatorName.equals("")) && (!keyword.equals(""))) {
            String results = search(creatorName, keyword);
            if(results != null) {
                out.println("<div align='center'><h1>Image Search Result</h1>"
                    + "<p>" + results
                    + "</p></div>"); 
            }
            else {
                out.println("<div align='center'><h1>Image Search Result</h1>"
                + "<p>No results."
                + "</p></div>");
            }
        }
        else { //both are null
            out.println("<div align='center'><h1>Image Search Result</h1>"
                + "<p>No results found or you entered nothing."
                + "</p></div>");
        }
        //insert button redirection here since all pages will add this at the end!
        out.println("<div align='center'><form name=\"returnIndex\" action=\"/DSAProject4/\">"
                + "<input type=\"submit\" value=\"New Query\" /></form></div>");
        out.println("</body>");
        out.println("</html>");
    }
    
    /**
     * Searches the database for the creator's name.
     * @param name the creator's name to test
     * @return the results from the database
     */
    public String searchCreator(String name) {
        String result = "";
        String builder = "";
        Connection temp = connectToDatabase();
        try {
                Statement state = temp.createStatement();            
                ResultSet rs = state.executeQuery("SELECT * FROM ach31.Galleryach31 WHERE creatorname = \"" + name +"\"");
                while(rs.next()){
                    builder = "Image: " +
                            "<br>Creator's Name: " + rs.getString(1)
                            + "<br>Email: " + rs.getString(2) 
                            + "<br>Homepage: <a href='" + rs.getString(3) + "'>Link</a>"
                            + "<br>Mimetype: " + rs.getString(4)
                            + "<br>Location: <a href='" + rs.getString(5) + "'>Link</a>"
                            + "<br>Width: " + rs.getString(6)
                            + "<br>Height: " + rs.getString(7)
                            + "<br>Title: " + rs.getString(8)
                            + "<br>Copyright: " + rs.getString(9)
                            + "<br>Keywords: " + rs.getString(10)
                            + " " + rs.getString(11)
                            + " " + rs.getString(12)
                            + " " + rs.getString(13)
                            + " " + rs.getString(14)
                            + "<br>";
                    result += "<br>" + builder;
                }
            }
            catch(SQLException e){
                System.out.println("Cannot create query");
            }
        closeConnection(temp);
        return result;
    }
    
    /**
     * Searches the database for a matching keyword.
     * @param keyword the keyword to test
     * @return the results from the database
     */
    public String searchKeyword(String keyword) {
        String result = "";
        String builder = "";
        Connection temp = connectToDatabase();
        try {
                for(int i = 1; i < 6; i++) {
                    Statement state = temp.createStatement();        
                    ResultSet rs = state.executeQuery("SELECT * FROM ach31.Galleryach31 WHERE keyword" + i + " = \"" + keyword +"\"");
                    while(rs.next()){
                        builder = "Image: " +
                            "<br>Creator's Name: " + rs.getString(1)
                            + "<br>Email: " + rs.getString(2) 
                            + "<br>Homepage: <a href='" + rs.getString(3) + "'>Link</a>"
                            + "<br>Mimetype: " + rs.getString(4)
                            + "<br>Location: <a href='" + rs.getString(5) + "'>Link</a>"
                            + "<br>Width: " + rs.getString(6)
                            + "<br>Height: " + rs.getString(7)
                            + "<br>Title: " + rs.getString(8)
                            + "<br>Copyright: " + rs.getString(9)
                            + "<br>Keywords: " + rs.getString(10)
                            + " " + rs.getString(11)
                            + " " + rs.getString(12)
                            + " " + rs.getString(13)
                            + " " + rs.getString(14)
                            + "<br>";
                        result += "<br>" + builder;
                    }
                }
        }
        catch(SQLException e){
            System.out.println("Cannot create query");
        }
        closeConnection(temp);
        return result;
    }
    
    /**
     * Searches both creator names and keywords
     * @param name the creator's name
     * @param keyword the keyword to test
     * @return the results from the database
     */
    public String search(String name, String keyword) {
        String result = "";
        String builder = "";
        Connection temp = connectToDatabase();
        try {
                for(int i = 1; i < 6; i++) {
                    Statement state = temp.createStatement();        
                    ResultSet rs = state.executeQuery("SELECT * FROM ach31.Galleryach31 WHERE creatorname = \"" + name + "\" AND keyword"+i+" = \"" + keyword + "\"");
                    while(rs.next()){
                        builder = "Image: " +
                            "<br>Creator's Name: " + rs.getString(1)
                            + "<br>Email: " + rs.getString(2) 
                            + "<br>Homepage: <a href='" + rs.getString(3) + "'>Link</a>"
                            + "<br>Mimetype: " + rs.getString(4)
                            + "<br>Location: <a href='" + rs.getString(5) + "'>Link</a>"
                            + "<br>Width: " + rs.getString(6)
                            + "<br>Height: " + rs.getString(7)
                            + "<br>Title: " + rs.getString(8)
                            + "<br>Copyright: " + rs.getString(9)
                            + "<br>Keywords: " + rs.getString(10)
                            + " " + rs.getString(11)
                            + " " + rs.getString(12)
                            + " " + rs.getString(13)
                            + " " + rs.getString(14)
                            + "<br>";
                        result += "<br>" + builder;
                    }
                }
        }
        catch(SQLException e){
            System.out.println("Cannot create query");
        }
        closeConnection(temp);
        return result;
    }
    
    /**
     * Creates a connection to the appropriate database.
     * @return a successful database connection or null
     */
    public Connection connectToDatabase() {
        Connection temp = null;
        //Find JDBC Driver and Register it
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch(ClassNotFoundException e) {
            System.out.println("JDBC Driver does not exist. Exiting application.");
            System.exit(0);
        }
        System.out.println("Attempting to connect to Image Database...");
        //grab a connection to the database
        try {
            temp = DriverManager.getConnection("jdbc:mysql://localhost:3306/phpmyadmin", "root", null);
        }
        catch(SQLException e) {
            System.out.println("No connection was made. What a sad time to be in. Exiting application.");
            System.exit(0);
        }
        //make sure the connection is valid
        if(temp == null) {
            System.out.println("Connection failed. I can't let you break anything. Exiting application.");
            System.exit(0);
        }
        return temp;
    }
    
    /**
     * Close the connection to a database. 
     * @param s the connection that needs to be closed
     */
    public void closeConnection(Connection s){
        try{
            s.close();
        }
        catch(SQLException e){
            System.out.println("Connection to the image database could not close.");
            System.exit(0);
        }
    }
}
