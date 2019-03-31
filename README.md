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
==>Unit test has been imlplemented to check the process of different cases and that the exception is thrown.==NoteBookServerApplicationTests.java
a ExceptionHandlerControllerAdvice.java has been implemented to handle those exception.