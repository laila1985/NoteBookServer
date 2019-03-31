Only backEnd has been Implemented 

For the testing ==> PostMan 
The @PostMapping(path = "/execute", produces = MediaType.APPLICATION_JSON_VALUE) has been used to read data from body in json format

		<!-- https://mvnrepository.com/artifact/org.json/json -->
		<dependency>
			<groupId>org.json</groupId>
			<artifactId>json</artifactId>
			<version>20180130</version>
		</dependency>

To process the python code the PythonInterpreter has been implemented and the following dependency has been included :
		<dependency>
			<groupId>org.python</groupId>
			<artifactId>jython-standalone</artifactId>
			<version>2.7.1</version>
		</dependency>


To keep the state of the Python interpreter a file has been used to save the variable value from the previous request.

for the used spring boot I used the 2.0.3 because the 1.5.x doesn t accept the empty version and to have correct immplemetation of session in pom i need it empty. i couldn t find a compatible version with it:

<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<!-- lookup parent from repository -->
		<!--version>1.5.19.RELEASE</version-->
		<version>2.0.3.RELEASE</version>
		<!-- This version doesn't accept dependency with empty version and to use session within spring boot  -->
		<!-- I need to add maven dependency without version that is why i have change it just to work on the last challenge else the project without session step is fine  -->
		<relativePath /> <!-- lookup parent from repository -->
	</parent>

The sessiosn has been implemented


What should happen if the piece of code cannot be parsed?
==> A pyton exception will be thrown

What should happen if the type of interpreter is not known?
==> Exception will be thrown to inform user that only python is implemented

What should happen if the interpreter takes a long time to finish?
==> no solution has been imlemented

The user’s code can have side effects, so make sure that it is not executed multiple times.
What should happen if the python process encounters some kind of error?
==> if python encouters any issue a PytonExecutionException will be thrown

How to reliably test an application like this? Think about unit and integration testing.
==>Unit test has been imlplemented to check the process of different cases and that the exception is thrown.=>=NoteBookServerApplicationTests.java
The ExceptionHandlerControllerAdvice.java has been implemented to handle those exception.
