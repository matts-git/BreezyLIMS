# BreezyLIMS

A laboratory information management system (LIMS) written in Java and designed by combining the Abstract Document, Facade, and Singleton design patterns. A LIMS is simply a system for managing experimental data. BreezyLIMS enables a user to enter data associated with an experimental result into an Apache Derby database.

# Apache Derby Database Information

This application requires an Apache Derby database.
- The local database files are included in the 'limsdb' folder
- The following table has been provided in the included database. It uses an auto-generated integer as the row primary key.

  CREATE TABLE resultdata (index INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), 
    project VARCHAR(40) NOT NULL, experiment VARCHAR(40) NOT NULL, sample VARCHAR(40) NOT NULL, result VARCHAR(40) NOT NULL, 
    CONSTRAINT primary_key PRIMARY KEY (index));

# Project Template

This is a Java Maven Project Template

# Abstract Document Design Pattern Description

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