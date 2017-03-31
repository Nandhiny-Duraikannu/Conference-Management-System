# 18655-Spring-2017-Team-5
# Conference management system

## Setup

What do you need to setup the project:

* SBT (http://www.scala-sbt.org/download.html)
* MySQL database (create one before running the project)
* Intellij Idea with scala plugin (or use IDE of your choice, but I don't have instructions for you then)

I used intellij idea to set up a project. Intellij will download all the dependencies when you open the project.
After that, setup your database connection in *conf/application_template.conf* (read the instructions in that file).
Your database credentials will not be kept in git so anyone can have any password they want.

Then try to run the project. You can do it from console with *sbt run* or from intellij 
(right-click on *app/controllers/HomeController* - *Run play 2 app*).
Then the app should be available at *localhost:9000*.
At the first run, it may show an error and ask to apply 'database evolution'.
Just press the button on this web page and it should work.
 
I used this template to initiate the project: https://github.com/playframework/play-java-ebean-example
There they have 2 tables in database and models ("company" and "computer"). 
I modified it to contain a "user" table and model to better fit our goals.

Note about database:
Play has a notion of database 'evolutions', which is the same as 'migration' in other systems.
Ebean (a library to work with databases) automatically creates 'evolutions' when you modify class properties in 'models' folder.
And you can run them from the browser!
This feature seems to me a bit too magical, but we can go with it for now.

## Play

Play documentation is here:

[https://playframework.com/documentation/latest/Home](https://playframework.com/documentation/latest/Home)

## EBean

EBean is a Java ORM library that uses SQL:

[https://www.playframework.com/documentation/2.5.x/JavaEbean](https://www.playframework.com/documentation/2.5.x/JavaEbean)

and

[https://ebean-orm.github.io/](https://ebean-orm.github.io/)

## Play Bootstrap

The Play HTML templates use the Play Bootstrap library:

[https://github.com/adrianhurt/play-bootstrap](https://github.com/adrianhurt/play-bootstrap)

library to integrate Play with Bootstrap, the popular CSS Framework.
