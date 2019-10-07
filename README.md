# Project Template

This is a Java Maven Project Template

# Apache Derby Database Information

This application requires an Apache Derby database.
- The local database files are included in the 'limsdb' folder
- The following table has been provided in the included database. It uses an auto-generated integer as the row primary key.

  CREATE TABLE resultdata (index INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), 
    project VARCHAR(40) NOT NULL, experiment VARCHAR(40) NOT NULL, sample VARCHAR(40) NOT NULL, result VARCHAR(40) NOT NULL, 
    CONSTRAINT primary_key PRIMARY KEY (index));

# Task 1: Design pattern and its Use Case Scenario Description.

I have selected the Abstract Document design pattern to apply to the use case of a toy laboratory information management system (LIMS). 
A LIMS is simply a system for managing experimental data. The toy LIMS I have submitted enables a user to enter data associated with 
an experimental result into an Apache Derby database, as well as retreive such data from the database. In order to fully implement 
this toy LIMS application, I have combined the use of the Abstract Document pattern with the Facade and Singleton design patterns.

The Abstract Document design pattern is structural design pattern. This pattern is not simply restricted to use in generating documents.
The term ‘document’ in the name refers to document-oriented databases, and this pattern lends itself well to streaming data between an application and a database. The pattern uses the concept of traits while separating different properties of a class into interfaces. The Abstract Document pattern allows for organizing objects in loosely typed key-value stores (here, in Map objects) and exposing the data using typed views. The purpose of the Abstract Document pattern is to achieve flexibility between components in a strongly typed language where new properties can be added to the object-tree on the fly while maintaining support of type-safety. 

The Abstract Document Pattern enables the developer to store variables (like experimental details) in an untyped tree structure and 
operate on the ‘documents’ using typed views. This tree structure facilitates traversal of properties and sub-documents through 
streaming, and these streams lend themselves well to writing efficient code for working with databases. New views can be implemented 
without altering the internal ‘document’ structure. This provides a loosely coupled system. However, the disadvantage is it introduces 
increased risk of casting errors since the type of a property is not always certain.

The interface "Document" declares a put() method for editing properties, a get() method for reading properties, and a children() method 
for traversing sub-documents. Method children() retruns a stream of typed views of a child document from a map argument containing the 
child document data. The abstract class “AbstractDocument” implements the “Document” interface and provides concrete implementations of 
the put(), get(), and children() methods. 

The concrete Document class “Experiment” extends “AbstractDocument”, implements various interfaces representing properties owned by 
“Experiment” ‘document’ objects, and contains only a constructor method. The concrete Document class “Sample” is similar to the 
“Experiment” ‘document’ class, but implements different property interfaces. Importantly, “Sample” represents a sub-document of 
“Experiment” and samples belonging to experiments as properties can be viewed through a stream returned by the getSamples() method 
in the propterty interface “HasSamples”.

As refered to above, there are multiple property interfaces located in the package “documentdomain”. Each of these properties is 
associated with an emum declared in the class “Property” located in the package “traitsdomain.enums”.

The class “Application” provides a simple user interface and serves as a Facade pattern. Facade patterns provide interfaces to complex 
subsystems. Here, I have a subsystem consisting of the Abstract Document pattern and another subsystem for reading and writing to an 
Apache Derby database. This database subsystem is layered, consisting of classes “DBwriter” and “DBreader”. These classes stream 
document properties into and out of the database, respectively. The Facade class “Application” associates with this these database 
subsystem layers directly, and associates with the Abstract Document subsystem indirectly through the class “DocumentBuilder”, which
essentially serves as a Visitor without being implemented formally as an actual design pattern.

The database functionality subsystem classes obtain database connections through a Singleton design pattern provided by class 
“ConnectionSingleton”. The operation of this use case application requires a database connection, so I have taken an eager 
initialization approach. A ConnectionSingelton object is created at the time of class loading, and access to the database connection 
through this object is accessible to all classes.

I have implemented this application with the intention of providing sufficiency, understandability, modularity, cohesion, coupling, 
flexibility, reusability, and information hiding, efficiency, and reliability. 

-	Sufficiency is covered by utilizing the advantages of the selected design patterns. An example of this is utilizing the streaming of 
‘document’ property tree structures to write into and read from the database.
-	Understandability is covered through using appropriate design patterns, applying helpful refactoring techniques, and including 
extensive comments and references in the code for more complex methods. 
-	Modularity and Cohesion are covered by applying appropriate design patterns and dividing the program into well defined classes which 
encapsulate relevant fields and methods. I have also organized these classes into well-defined packages.
-	Coupling has been covered by applying appropriate design patterns. Although there are many associations between classes and 
interfaces in the Abstract Document domain, there are relatively few between the actual classes. The Facade pattern allows for 
subsystems to interact without actually coupling together directly as well.
-	Flexibility and Reusability are provided through the use of the Abstract Document design pattern, which facilitates future addition 
of ‘document’ properties.
-	Information hiding is covered through use of the Facade pattern, which isolates client code from the complexity of the included 
subsystems.
-	Efficiency is covered by utilizing streaming to traverse ‘document’ property tree strucutres and using these streams to send data 
to and from the database. Also, the use efficient algorithms has been applied (no algorithms are slower than O(n))
-	Reliability was covered by designing thorough tests and from diligent debugging.


# How to compile the project

We use Apache Maven to compile and run this project. 

You need to install Apache Maven (https://maven.apache.org/)  on your system. 

Type on the command line: 

```bash
mvn clean compile
```

# How to create a binary runnable package 


```bash
mvn clean compile assembly:single
```


# How to run

```bash
mvn -q clean compile exec:java -Dexec.executable="edu.bu.met.cs665.Main" -Dlog4j.configuration="file:log4j.properties"
```

We recommand the above command for running the project. 

Alternativly, you can run the following command. It will generate a single jar file with all of the dependencies. 

```bash
mvn clean compile assembly:single

java -Dlog4j.configuration=file:log4j.properties -classpath ./target/JavaProjectTemplate-1.0-SNAPSHOT-jar-with-dependencies.jar  edu.bu.met.cs665.Main
```


# Run all the unit test classes.


```bash
mvn clean compile test

```

# Using Findbugs 

To see bug detail using the Findbugs GUI, use the following command "mvn findbugs:gui"

Or you can create a XML report by using  


```bash
mvn findbugs:gui 
```

or 


```bash
mvn findbugs:findbugs
```


For more info about FindBugs see 

http://findbugs.sourceforge.net/

And about Maven Findbug plugin see 
https://gleclaire.github.io/findbugs-maven-plugin/index.html


You can install Findbugs Eclipse Plugin 

http://findbugs.sourceforge.net/manual/eclipse.html



SpotBugs https://spotbugs.github.io/ is the spiritual successor of FindBugs.


# Run Checkstyle 

CheckStyle code styling configuration files are in config/ directory. Maven checkstyle plugin is set to use google code style. 
You can change it to other styles like sun checkstyle. 

To analyze this example using CheckStyle run 

```bash
mvn checkstyle:check
```

This will generate a report in XML format


```bash
target/checkstyle-checker.xml
target/checkstyle-result.xml
```

and the following command will generate a report in HTML format that you can open it using a Web browser. 

```bash
mvn checkstyle:checkstyle
```

```bash
target/site/checkstyle.html
```


# Generate  coveralls:report 

```bash
mvn -DrepoToken=YOUR-REPO-TOCKEN-ON-COVERALLS  cobertura:cobertura coveralls:report
```


