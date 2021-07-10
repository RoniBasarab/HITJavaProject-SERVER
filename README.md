"# RoniJavaProjectServer" 
"# HITJavaProject-SERVER" 

*A short video presenting my project: https://www.youtube.com/watch?v=lXcebxhOQEs
// DISCLAIMER: The video is presented in the HEBREW language.

Welcome to my CLIENT/SERVER java project.
This project emulates a job searching application. The user is able to search in an existing database for jobs based on several parameters (city, salary, worker count, job id)
similarly to an online job searching websites such as "AllJobs".
Additionally, there is an option to add new job (or remove) postings to the existing database.

In this work, I've implemented a SERVER/CLIENT TCP protocol  (*It uses the user PC as a local host*) together with a string-matching algorithmic unit using an open/close principle (open for extension, closed for modification). The implemented algorithms are KMP and RK.
Furthermore, the system archtitecture I've used is Controller-Service-Repository together with the Factory design pattern, Singleton design pattern and Strategy design pattern.
For the client side, I've used the MVC design pattern.

Finally, the requests/resonses in both the server and the client are handled in an asynchronos way using an executor and a thread pool.


//** For the application to work, run the "Startup" class and type in the console "start" to launch the server. Afterwards, you must run the client side for the GUI **//
