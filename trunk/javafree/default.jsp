<%
response.setStatus(301);
response.setHeader( "Location", "index.jf" );
response.setHeader( "Connection", "close" );
%>