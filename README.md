## Overview 
We have two independent projects.  
Both projects are built using **spring boot** framework and **Maven** as the build tool.
* deepak-client 
* deepak-server

## Running Server and Client 

Clone the repository.
* `git clone https://github.com/DeepuSahni/deepakLab.git`

#### Starting the server
1. Go into directory `deepak-sever`
2. Build the project and make fresh jar file: 
   * `mvn clean install`
   * To skip tests use: 
    ```
    mvn clean install -Dmaven.test.skip=true -DskipTests -Dmaven.test.failure.ignore=true
    ```
3. Run the server on port 9091 (or any available port):
   * `java -jar target/deepak-server-0.1.0.jar --server.port=9091`
4. Please remember this port number, you would need this when making request from client.

#### Starting the client
1. Go into directory `deepak-client`
2. Build the project and make fresh jar file: 
   * `mvn clean install`
   * To skip tests use: 
    ```
    mvn clean install -Dmaven.test.skip=true -DskipTests -Dmaven.test.failure.ignore=true
    ```
3. Run the client on port 9090 (or any available port):
   * `java -jar target/deepak-client-0.1.0.jar --server.port=9090`

## Invoking Requests
1. Connect to the server (running on port 9091) and store data in client (running on 9090):
   * http://localhost:9090/fetchAndStore/9091
   * Use request type = GET (Repeat to keep adding new records to client db.)
   * Result: Fetches a new record from sever and saves the same into client database.

2. GET request: 
   * http://localhost:9090/getAddressById/1
   * Use request type = GET
   * Result: Gets the record matching with supplied ID. 


#### Use postman like tool to make other requests.
3. UPDATE request:
   * localhost:9090/updateAddress
   * Pass the full address object JSON in body (Raw and Select JSON in postman)
   * Use request type = PUT
   * Result: Updates the specified record in client db.

4. DELETE request:
   * localhost:9090/deleteAddress
   * Pass the address ID in body
   * Use request type = DELETE
   * Result: Deletes the specified record in client db.

5. SAVE request (direct save on client)
   * localhost:9090/saveAddress
   * Pass the full address object JSON in body (raw and select JSON)
   * Use request type = POST


## Future Improvements
   1. Add security. 
   2. Add logging.
   3. More specific error messages in error handling.
   4. Use properties files for error handling. For custom errors.
   5. Let client pass full server address or make it configurable. Eg. through properties file.
   6. Add conversion for date field in Address - make it human readable especially when using in UI.
   7. Add type of address for reusability. Eg. Home Address, Office Address.
   8. Add server tests: task said we could have used mock sever so have not added any server side tests.


## Troubleshooting
1. mvn: command not found?
  - Please install maven. I won't tell how. :)
2. Cannot clone? Git says host not found?
  - Please check your proxy settings.
  - You may need to export these variables into environment:
    ```
    export HTTP_PROXY="<your url here>"
    export HTTPS_PROXY=$HTTP_PROXY
    export http_proxy=$HTTP_PROXY
    export https_proxy=$HTTP_PROXY
    export NO_PROXY=“<your url here>”
    export no_proxy=$NO_PROXY
    ```
2. Maven build error?
  - This might be because of maven failing to find the JAVA_HOME.
  - Please export your JAVA_HOME. `export JAVA_HOME=<your java home>`
3. Cannot download maven dependencies?
  - Please check the proxy settings for maven
  - You can add proxies for maven in your  settings file `~/.m2/settings.xml`.
  - You may not need username and password in proxy elements.
  ```
  <proxy>
    <id>http8080</id>
    <active>true</active>
    <protocol>http</protocol>
    <host>your host here</host>
    <port>8080</port>
    <username></username>
    <password></password>
    <nonProxyHosts>your host|localhost|home</nonProxyHosts>
  </proxy>
  ```
  
## IntelliJ 
### View Code 
1. Click 'Import Project'
2. Navigate to `pom.xml` in any of the projects.
3. Click 'Open'
4. Check 'Import Maven projects automatically'.
5. Keep hitting 'Next' and then 'Finish'.  
All done, you should now have the beautiful project structure opened up!  

### Run Client/Server
1. Navigate to Edit/Add Configurations.
2. Click the **+** button to add a new 'JAR Application'.
3. Type any name for your application.
4. Path to JAR: Click on ... and navigate to your jar file for example:  
  ```
  ./deepak-projects/deepak-client/target/deepak-client-0.1.0.jar
  ```
5. VM Options: -Dserver.port=9091 (or any port you love).
6. Working Directory: Should look like `/Users/DeepakSahni/Downloads/deepakLab/deepak-projects/deepak-client`
7. Apply -> Ok.
8. Now you may run your client/server by hitting that play button on top.   
For more details please refer to https://www.jetbrains.com/help/idea/run-debug-configuration-jar-application.html
