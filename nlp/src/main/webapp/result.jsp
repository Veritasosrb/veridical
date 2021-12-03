<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<%@ page import="java.util.*" %>
<%@page import="java.io.*"%>
<%@page import="org.apache.poi.hssf.usermodel.HSSFSheet"%>
<%@page import="org.apache.poi.hssf.usermodel.HSSFWorkbook"%>
<%@page import="org.apache.poi.hssf.usermodel.HSSFRow"%>
<%@page import="org.apache.poi.hssf.usermodel.HSSFCell"%>
<%
String value1=request.getParameter("firstName");
String value2=request.getParameter("lastName");
String value3=request.getParameter("userName");
String value4=request.getParameter("address");
String value5=request.getParameter("email");
String value6=request.getParameter("dob");
try{
HSSFWorkbook wb = new HSSFWorkbook();
HSSFSheet sheet = wb.createSheet("Excel Sheet");
HSSFRow rowhead = sheet.createRow((short)0);
rowhead.createCell((short) 0).setCellValue("First Name");
rowhead.createCell((short) 1).setCellValue("Last Name");
rowhead.createCell((short) 2).setCellValue("User Name");
rowhead.createCell((short) 3).setCellValue("Address");
rowhead.createCell((short) 4).setCellValue("E-mail Id");
rowhead.createCell((short) 5).setCellValue("Date Of Birth");

HSSFRow row = sheet.createRow((short)1);
row.createCell((short)0).setCellValue(value1);
row.createCell((short)1).setCellValue(value2);
row.createCell((short)2).setCellValue(value3);
row.createCell((short)3).setCellValue(value4);
row.createCell((short)4).setCellValue(value5);
row.createCell((short)5).setCellValue(value6);
FileOutputStream fileOut = new FileOutputStream("c:\\File.xls");
wb.write(fileOut);
fileOut.close();
out.println("Data is saved in excel file.");
}catch ( Exception ex ){
}
%>