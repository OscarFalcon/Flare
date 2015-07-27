package com.flare.database;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.commons.dbcp2.BasicDataSource;



public class MySQL {
	
    private  static java.sql.Connection connection = null;
    private  static BasicDataSource dataSource = null;

    
    public static final int BIG_DECIMAL = 0;
    public static final int BOOLEAN = 1;
    public static final int DOUBLE = 2;
    public static final int BYTE = 3;
    public static final int DATE = 4;
    public static final int FLOAT = 5;
    public static final int INTEGER = 6;
    public static final int LONG = 7;
    public static final int OBJECT = 8;
    public static final int STRING = 9;
    
 
	 static
	 {
		dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setInitialSize(1); 	// sets the initial amount of connections. **/
		dataSource.setMaxTotal(1); 		//Sets the maximum total number of idle and borrows connections that can be active at the same time. **/
		
		
		dataSource.setUrl("jdbc:mysql://localhost:3306/flare?" +
				"user=flare&password=flare-sql-password");
	 }
	 
	 
	 public static void setURL(String url)
	 {
		 dataSource.setUrl(url);
	 }
	 
	 public static void setUsername(String username)
	 {
		 dataSource.setUsername(username);
	 }
	 
	 public static void setPassword(String password)
	 {
		 dataSource.setPassword(password);	
	 }
	 
		
	/**
	 * Attempts to get a connection from the connection pool.
	 * @return TRUE: if a connection has already been established and that connection is not closed. Or there is
	 * no current opened connection and a connection was successfully established. FALSE: otherwise
	 * error no is either set to CONNECTION_ERROR or NO_INTERNET_CONNECTION_ERROR
	 */
	private static boolean connect() {
		if(isClosed())
		{
			try
			{
				connection = dataSource.getConnection();
			    connection.setAutoCommit(false);
			    
		   }
			catch (SQLException e)
			{
			   isInternetReachable(); // is the server down, or Internet down? 
			   e.printStackTrace();
			   return false;
		   }
		}
		System.out.println("Connected to database");
		return true;
	}
	
	/**
	 * disconnects from the MySQL database. If connection is already closed or is null, does nothing
	 */
	private static void disconnect()
	{ 
		if(connection == null)
			return;
		try {
			if(!(connection.isClosed()))
				connection.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		System.out.println("Disconnected from database");
   }
	
	/**
	 * IMPORTANT this method can return false(indicating that the connection is opened)
	 * even when the connection might be closed. This is due to a SQL exception that 
	 * might be raised.
	 * @return TRUE if the connection has been closed, false otherwise.
	 */
	private static boolean isClosed()
	{
		boolean value = false;
		
		if(connection == null)
			return true;
		
		try
		{
			if(connection.isClosed())
				value = true;
			else
				value = false;
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return value;
	}
	
	
	
	/**
	 * ASSUMES: that there is a live connection for the passed in prepared statement.
	 * 
	 * helper method that executes a prepared statement that does NOT return a resultSet.
	 * This method does not close or open any connections
	 * 
	 *  **/
	private static boolean execute(java.sql.PreparedStatement preparedStatement) 
	{
		if( preparedStatement ==null || isClosed() )
			return false;
		try 
		{
			preparedStatement.execute();
			preparedStatement.getConnection().commit();
			return true;
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	* ASSUMES: there is a live connection associated with the passed in prepared statement.
	* 
	* helper method that executes a query from a prepared statement that returns a resultSet
	* This method does not open or close any connections 
	* **/
	private static final ResultSet executeQuery(java.sql.PreparedStatement preparedStatement) 
	{
		ResultSet rs = null;
		
		if( preparedStatement == null || isClosed() )
			return null;
		try
		{
			rs = preparedStatement.executeQuery();
			preparedStatement.getConnection().commit();
		} catch (SQLException e) 
		{
			e.printStackTrace();
			return null;
		}
		return rs;
	}
	
	
	/** 
	* helper method that prepares a statement based on the given string 
	**/
    private static final java.sql.PreparedStatement prepareStatement(String statement)
    {
    	java.sql.PreparedStatement ps;
    	
    	if(isClosed())
    	{
    		return null;
    	}
    		
    	try 
    	{
			ps = connection.prepareStatement(statement);
			
		} 
    	catch (SQLException e) 
    	{
			e.printStackTrace();
			return null;
		}
    	return ps;
    }

    private static final boolean setValue(java.sql.PreparedStatement preparedStatement,int position, Object value)
    {
    	
    	try
    	{
    		
	    	if(value instanceof BigDecimal)
			{
				preparedStatement.setBigDecimal(position,(BigDecimal)value);
			}
			else if(value instanceof Boolean)
			{
				preparedStatement.setBoolean(position,(Boolean)value);
			}
			
			else if(value instanceof Double)
			{
				preparedStatement.setDouble(position, (Double)value);
			}
			else if(value instanceof Byte)
			{
				preparedStatement.setByte(position,(Byte)value);
			}
			else if(value instanceof Date )
			{
				preparedStatement.setDate(position, (Date)value);
			}
			else if(value instanceof Float)
			{
				preparedStatement.setFloat(position, (Float)value);
			}
			else if(value instanceof Integer)
			{
				preparedStatement.setInt(position, (Integer)value);
			}
			else if(value instanceof Long)
			{
				preparedStatement.setLong(position, (Long)value);
			}
			else if(value instanceof String)
			{
				preparedStatement.setString(position,(String)value);
			}
			else
			{
				preparedStatement.setObject(position,value);					
			}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return false;
    	}
    	return true;
    }
    
    
    
    
	/**
	* @param mysql_string a valid SQL prepared statement string that represents the query to be performed
	* @param args the arguments that will pair for each '?' in the prepared statement string
	* @param arg_types the argument types of any '?' in preparedStatmentString in the form of a string array(ie 'Integer' if the first argument is of type Integer)
	* @param result_array_types a list of the type specifiers that represent each individual type of the returned object array. 
	* @return an ArrayList of Object[] that represent a result set object 
	*/
    public static final ArrayList<Object[]> executeQuery(String mysql_string, Object[] arguments, int[] result_types)
    {	
		int length,i;
		java.sql.PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<Object[]> list = null;
		
		
		
		if(mysql_string == null )
		{
			throw new RuntimeException("mysql_string cannot be null");
		}
		
		if(result_types == null)
		{
			throw new RuntimeException("result_types cannot be null");
		}
		
		
		if(arguments == null)
		{
			length = 0;
		}else
		{
			length = arguments.length;
		}
		
		
		if(connect() == false) 			
		{ 
			return null; 		
		}
		
		
		if((preparedStatement = prepareStatement(mysql_string)) == null) 
		{	
			return null;
		}
			
		try
		{
			
			for(i = 0; i < length; i++)
			{	
				
				setValue(preparedStatement,i+1,arguments[i]);
			
			}		
			
			if((resultSet = executeQuery(preparedStatement)) == null)
			{
				return null;
			}
			
			
			list = new ArrayList<Object[]>();
			length = result_types.length;
			while(resultSet.next())
			{
				Object[] tmp = new Object[length];
				
				for(i = 0; i < length; i++)
				{
					
					switch(result_types[i])
					{
						case BIG_DECIMAL:
							tmp[i] = resultSet.getBigDecimal(i+1);
							break;
						case BOOLEAN:
							tmp[i] = resultSet.getBoolean(i+1);
							break;
						case DOUBLE:
							tmp[i] = resultSet.getDouble(i+1);
							break;
						case BYTE:
							tmp[i] = resultSet.getByte(i+1);
							break;
						case DATE:
							tmp[i] = resultSet.getDate(i+1);
							break;
						case FLOAT:
							tmp[i] = resultSet.getFloat(i+1);
							break;
						case INTEGER:
							tmp[i] = resultSet.getInt(i+1);
							break;
						case LONG:
							tmp[i] = resultSet.getLong(i+1);
							break;	
						case OBJECT:
							ObjectInputStream objectIn = null;
							byte[] buf = null;
							Object object = null;	
							buf = resultSet.getBytes(i+1);
							if (buf != null){
						    	objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));
								object = objectIn.readObject();
								tmp[i] = object;
							}
							break;
						case STRING:
							tmp[i] = resultSet.getString(i+1);
							break;
						default:
							return null;
							
					}//switch
							
				}//for loop
				list.add(tmp);
			}//while
		}catch(SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(resultSet != null)
					resultSet.close();
				if(preparedStatement != null)
					preparedStatement.close();
				disconnect();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		return list;
	}
    
    
    
	public static final boolean execute(String mysql_string, Object[] arguments){
		int length,i;
		java.sql.PreparedStatement preparedStatement = null;
						
			if(mysql_string == null || arguments == null)
			{
				return false;
			}
			
			if(!connect())
			{ 
				return false; 
			}
			
			
			if((preparedStatement = prepareStatement(mysql_string)) == null)
			{
				return false;
			}
			
			try
			{
				length = arguments.length;
				Object value;
				for(i = 0; i < length; i++){	/** loop through args and call the appropriate set method of preparedStatement depending on arg_types **/
					value = arguments[i];
					
					
					if(value instanceof Byte)
					{
						preparedStatement.setByte(i+1,(Byte)value);
					}
					else if(value instanceof Date )
					{
						preparedStatement.setDate(i+1, (Date)value);
					}
					else if(value instanceof Integer)
					{
						preparedStatement.setInt(i+1, (Integer)value);
					}
					else if(value instanceof String)
					{
						preparedStatement.setString(i+1,(String)value);
					}
					else if( value instanceof BigDecimal)
					{
						preparedStatement.setBigDecimal(i+1, (BigDecimal) value); 
					}
					
					else
					{
						preparedStatement.setObject(i+1,value);
					}
				}
				
				if(execute(preparedStatement) == false)
					return false;
				
				
			}catch(SQLException e)
			{
				e.printStackTrace();
				return false;
			}
			finally{
				try
				{
					if(preparedStatement != null)
					{
						preparedStatement.close();
					}
					disconnect();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
		return true;
	}
	
	private static boolean isInternetReachable()
	{
        try {
        	URL url = new URL("http://www.google.com");/** make a URL to a known source **/
            HttpURLConnection urlConnect = (HttpURLConnection)url.openConnection();/** //open a connection to that source **/
            @SuppressWarnings("unused")
			Object objData = urlConnect.getContent(); /** trying to retrieve data from the source. If there is no connection, this line will fail **/
        } catch (IOException e1) {
            return false;
        }
        return true;
    }
	
	
}
