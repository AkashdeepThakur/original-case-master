Travel API Client
=================

Steps to run the Project 
- 1.Clone the project(master branch) into IDE, or download a zip and import it as gradle project 
- 2.Clone The mock backend to communicate can be found [here on GitHub](https://github.com/Pim-Huisman/simple-travel-api-mock).
- 3.Run the mock backend project( project which will produce the fares,airport list etc) first and then only run this project 
- 4.Now run the  original-case-master project(This Project)
- 5.Goto https://drive.google.com/drive/folders/1M_PkmC6ppAAx3pEdPt1EttmoECNDyypa?usp=sharing (google drive) and download "Prometheus&Grafana" folder unzip it(implemented for monitoring project)
- 6.Run promitheus goto "\Prometheus&Grafana\prometheus-2.32.1.windows-amd64\prometheus-2.32.1.windows-amd64" run command .\prometheus.exe
- 7.Run grafana goto "\Prometheus&Grafana\grafana-enterprise-8.4.0-46589pre.windows-amd64\grafana-8.4.0-46589pre\bin" run command  .\grafana-server.exe

To view the assignment (after starting both the application) go to:

[http://localhost:9000/klm/list](http://localhost:9000/klm/list)

For observing metrics to view http request response and others  (after starting both(promethus and grafana) the application) go to:

[http://localhost:3000](http://localhost:3000) or
[http://localhost:3000/?orgId=1](http://localhost:3000/?orgId=1)

if it ask for login credential give username : admin password : admin and goto --> browse--> dashboard ---> springboot-apm-dashboard 

Or you can directly try to access below link :
[http://localhost:3000/d/X034JGT7Gz/springboot-apm-dashboard?orgId=1&refresh=10s](http://localhost:3000/d/X034JGT7Gz/springboot-apm-dashboard?orgId=1&refresh=10s)

Functionalities Implemented  :

- 1.Will list all the  airports in paginated way 
- 2.User can get more details of any airport by clicking more details 
- 3.User can get fare for Source and destination. 
- 4.User can search airport 
- 5.Implemented  Exception handling for  incorrect URL and  backend down along with other exception
- 6.I have also added a field to check backend api is up or down
- 7.I have also Implemented a custom endpoint in actuator through HandlerInterceptor for all requested api monitoring things.
    but have commented the code since have implemented a better way (through prometheus and grafana).
    if want to use that end point as well goto WebConfiguration class and uncomment code from line 23 to 30 
    and got :  http://localhost:9000/actuator/apimonitor and Json data will be visible 

