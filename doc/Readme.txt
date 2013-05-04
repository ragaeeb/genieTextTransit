===============================================================

canabang inc.
genieText transit v1.00 readme file
(c) 2009 canabang inc. all rights reserved.

Prepared by: Ragaeeb Haq
Revised by: Ragaeeb Haq

===============================================================

Development: Ragaeeb Haq
Documentation: Ragaeeb Haq
Testing: Ragaeeb Haq

===============================================================

Contact Information for Bugs/Other inquiries:
Ragaeeb Haq

===============================================================

Introduction:

	genieText is a suite of products created to help bring
server-side information to mobile phones through SMS technology.
genieText: Transit is a sub-project to bring transit service
information to a mobile phone through the SMS technology.

===============================================================

History:

	Ever since I signed up for the unlimited text messaging
package from my phone provider but not the unlimited Internet
data plan, I was thinking of ways how I could access data on
the fly from anywhere and anytime. Examples of such things
included transit service information, and prayer time information.
This is when the idea for this project was born.

===============================================================

Future Plans:

-Better exception handling.
-Port # needs to blank on focus.
-Button factory pattern.

===============================================================

Requirements:
1) To run/host the system:
	-JRE 1.6+

2) To develop the system:
	-JDK 1.6+
	-Javamail 1.4.1+ library
	-JUnit 4.5+ library 


===============================================================

Set Up/Execution Instructions:

Hosting from the command line:
	java -jar "genieTextTransit_1.00.jar" "C:\serviceInput.xml"
	
	Here is a breakdown of the arguments:
	1) java: Starts up the JVM to run our executable
	2) -jar: Tells the JVM we want to run the following JAR executable
	         file.
	3) genieTextTransit_1.00.jar:  This is the name of the executable as
	   						  	   downloaded from the project website.
	   						  	   If you downloaded a different version,
	   						  	   then make sure that the name matches the
	   						  	   file name. For example if you've downloaded
	   						  	   "genieTextTransit_1.02.jar" then you should run:
	   						  	   java -jar "genieTextTransit_1.00.jar" "C:\sampleXml.xml"
							  
	It is recommended that all arguments be quoted because this treats arguments with
	spaces as one argument rather than two. If you wish to run more than one service
	simultaneously, they may just be followed as additional arguments:
	
	java -jar "genieTextTransit_1.00.jar" "C:\serviceFile1.xml" "serviceFile2.xml"
	
	A current limitation however is that the two services need to be accessing two totally
	different accounts otherwise the client will be receiving duplicate responses.

Testing from a mobile phone:
	If your phone and provider supports texting to an email address, send a text message
	to the address you specified in the XML file and you should get a response!
