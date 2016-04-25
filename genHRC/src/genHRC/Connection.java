package genHRC;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 * singleton that make the connection between the database and the other classes
 *	with simple requests
 * @author Laura
 *	
 */
public class Connection {

	private String url;
	private String login;
	private String passwd;
	private java.sql.Connection co;
	private Statement st;
	private ResultSet rs;

	/**
	 * Private constructor for singleton
	 */
	private Connection()
	{
		url = "jdbc:mysql://localhost/formation";
		login = "vqeg";
		passwd = "vqeg";
		co = null;
		st = null;
		rs = null;
	}

	//unique instance of connection
	private static Connection onlyInstance = new Connection();


	/**
	 * @return the only instance of Connection
	 */
	public static Connection getInstance()
	{
		return onlyInstance;
	}

	/**
	 * open the "connection" with database
	 */
	public void open()
	{
		try 
		{
			// load driver
			Class.forName("com.mysql.jdbc.Driver");

			// establishment of connection
			co = DriverManager.getConnection(url, login, passwd);

			// creation of statement
			st = co.createStatement();
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}	
	}


	/**
	 * close connection with database
	 */
	public void close()
	{
		try {
			// free memory resources
			co.close();
			st.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}


	/**
	 * @param src : String that defines the source
	 * @param hrc : String that defines the HRC
	 * @return hash : Hash code linked to the pair src-HRC
	 */
	public String getHash(String src, String hrc)
	{
		String hash = null;

		// sql request
		String sql = "SELECT Hash FROM Hash_table WHERE src = '" + src + "' AND HRC = '" + hrc + "'";  


		try 
		{
			//executing sql request
			rs = st.executeQuery(sql);

			if (rs.next())
			{
				hash = new String(rs.getString("Hash"));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

		return hash;
	}	


	/**
	 * @param hash : String
	 * @return a List of String that represents the src linked to the hash given
	 */
	public List<String> getSrc(String hash)
	{
		List<String> src = new ArrayList<String>();

		// sql request
		String sql = "SELECT src FROM Hash_table WHERE Hash = '" + hash +"'";  

		try
		{
			//executing sql request
			rs = st.executeQuery(sql);


			while (rs.next())
			{
				src.add(rs.getString("Src"));
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}


		return src;
	}


	/**
	 * @param hash : String
	 * @return List of String that represents the src linked to the hash given
	 */
	public List<String> getHRC(String hash)
	{
		List<String> hrc = new ArrayList<String>();

		// sql request
		String sql = "SELECT hrc FROM Hash_table WHERE Hash = '" + hash +"'";  

		try
		{
			//executing sql request
			rs = st.executeQuery(sql);


			while (rs.next())
			{
				hrc.add(rs.getString("HRC"));
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

		return hrc;
	}


	/**
	 * create a line into the table 'Hash_table' with arguments src, HRC and Hash given
	 * @param src : String
	 * @param hrc : String
	 * @param hash : String
	 */
	public void createLine(String src, String hrc, String hash)
	{
		// sql request
		String sql = "INSERT INTO Hash_table VALUES ('" + src + "','" + hrc + "','" + hash + "' )";  

		try
		{
			//executing sql request
			st.executeUpdate(sql);
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

	}


	/**
	 * delete the line that contains src-HRC given from the table 'Hash_table'  
	 * @param src : String
	 * @param hrc : String
	 */
	public void delete(String src, String hrc)
	{
		// sql request
		String sql = "DELETE FROM Hash_table WHERE src = '" + src + "' AND HRC = '" + hrc + "'";  

		try
		{
			//executing sql request
			st.executeUpdate(sql);
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}


	/**
	 * getter of different values from table Process
	 * @param type : value wanted from table Process
	 * @param ref : String : reference of the process you wish to access
	 * @return result of the sql request : String
	 */
	private String getterOnProcess(String type, String ref)
	{
		String result = null;

		//sql request
		String sql = "SELECT " + type + " FROM Process WHERE Reference = '" + ref +"'";

		try
		{
			//executing sql request
			rs = st.executeQuery(sql);

			if (rs.next())
			{
				result = new String(rs.getString(type));
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return result;
	}


	/**
	 * @param ref : String  : reference of process you want the command
	 * @return Command
	 */
	public String getCommand(String ref)
	{
		return getterOnProcess("Command", ref);
	}


	/**
	 * @param ref : String  : reference of process you want the config file
	 * @return ConfigFile ( String)
	 */
	public String getConfigFile(String ref)
	{
		return getterOnProcess("ConfigFile", ref);
	}


	/**
	 * @param ref : String  : reference of process you want the input
	 * @return Input
	 */
	public String getInput(String ref)
	{
		return getterOnProcess("Input", ref);
	}

	/**
	 * @param ref : String  : reference of process you want the input_ext
	 * @return InputExt
	 */
	public String getInputExt(String ref)
	{
		return getterOnProcess("Input_ext", ref);
	}
	
	/**
	 * @param ref : String  : reference of process you want the output_ext
	 * @return OutputExt
	 */
	public String getOutputExt(String ref)
	{
		return getterOnProcess("Output_ext",ref);
	}

	/**
	 * @param ref : String  : reference of process you want the output_ext
	 * @return Output
	 */
	public String getOutput(String ref)
	{
		return getterOnProcess("OutPut", ref);
	}


	/**
	 * @param ref : String  : reference of process you want the description
	 * @return Description
	 */
	public String getDescription(String ref)
	{
		return getterOnProcess("Description", ref);
	}


	/**
	 * get the real HRC chain from its abbreviation ( 8 char geginning with 'Z') 
	 * @param hrc : abbreviation ( 8 char beginning with 'Z') 
	 * @return String: realHRC
	 */
	public String getRealHRC(String hrc)
	{
		String realHRC = null;

		// sql request
		String sql = "SELECT HRC FROM HRC_abbr_relation WHERE HRC_abbr = '" + hrc + "'";  

		try 
		{
			//executing sql request
			rs = st.executeQuery(sql);

			if (rs.next())
			{
				realHRC = new String(rs.getString("HRC"));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

		return realHRC;
		
	}
	
	
	/**
	 * get the src file name from its abbreviation ( src)
	 * @param src : abbreviation
	 * @return real srcFileName : String
	 */
	public String getSrcFileName(String src)
	{
		String srcFileName = null;

		// sql request
		String sql = "SELECT srcFileName FROM Src_abbr_relation WHERE src = '" + src + "'";  

		try 
		{
			//executing sql request
			rs = st.executeQuery(sql);

			if (rs.next())
			{
				srcFileName = new String(rs.getString("srcFileName"));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

		return srcFileName;
	}
	
	

}
