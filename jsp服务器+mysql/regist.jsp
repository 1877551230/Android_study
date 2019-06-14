<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.sql.*" %>


<%

String username=request.getParameter("name");
 String psd=request.getParameter("pass");
String cpsd=request.getParameter("pass3");
//out.print("1");
if(psd.trim().equals(cpsd.trim()))
{
	try{
		Class.forName("com.mysql.jdbc.Driver");
	String uri="jdbc:mysql://127.0.0.1:3306/information?serverTimezone=UTC";
	String user="root";
	String password="root";
	Connection con=DriverManager.getConnection(uri,user,password);
	Statement sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
	//out.print("12");
	String cond = "SELECT * FROM info";
	ResultSet rs = sql.executeQuery(cond);
	ResultSetMetaData meta=rs.getMetaData();
	int columnCount=meta.getColumnCount();
	String[] columnName=new String[columnCount];
	for(int i=0;i<columnName.length;i++){
             columnName[i]=meta.getColumnName(i+1);	
		 
		}
	rs.last();
	int rowNumber=rs.getRow();
	String[][] tableRecord=new String[rowNumber][columnCount];
	rs.beforeFirst();
	int i=0;
	while(rs.next())
	{
		for(int k=0;k<columnCount;k++)
			     tableRecord[i][k]=rs.getString(k+1);
			   i++;
			
	}
	int key=0;
	for(i=0;i<rowNumber;i++)
	{
		//out.print(username);
		//out.print(psd);
	if(tableRecord[i][0].trim().equals(username))
	{
		key=1;
	}
	}
	
	if(key==0){
		PreparedStatement sql1=con.prepareStatement("insert info VALUES(?,?)");
		sql1.setString(1,username);
		sql1.setString(2,psd);
		
		sql1.executeUpdate();
		out.print("ok");
	}else{
	out.print("flase");
	
	}
	}
	catch(Exception e){
out.print(e.getMessage());
}
}

%>
